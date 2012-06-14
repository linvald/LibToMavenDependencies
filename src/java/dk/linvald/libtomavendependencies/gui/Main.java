package dk.linvald.libtomavendependencies.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dk.linvald.libtomavendependencies.exceptions.ResolveException;
import dk.linvald.libtomavendependencies.gui.table.RepositoryTable;
import dk.linvald.libtomavendependencies.liblogic.LibResolver;
import dk.linvald.libtomavendependencies.liblogic.PomGenerator;
import dk.linvald.libtomavendependencies.liblogic.RepoEntry;
import dk.linvald.libtomavendependencies.liblogic.RepositoryManager;

/**
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class Main extends JFrame implements PropertyChangeListener, ActionListener{

	public final static int APP_WIDTH = 840;
	public final static int APP_HEIGHT = 650;
	
	private RepositoryManager repManager;
	private LibResolver resolver;
	
	private LocationSelectorPanel locationSelector;
	private Container content = this.getContentPane();
	private JPanel pBottom;
	private JButton bAddAll;
    private RepositoryTable _table;
	
	public Main() {
		super("Lib collection 2 Maven repository");
		init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
	}
	
	public void init() {
	    JPanel north, center, south = new JPanel();
	    north = new JPanel();center=center = new JPanel();
	    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
	    center.setLayout(new GridLayout(1,0));
	    _table = new RepositoryTable();
	    locationSelector = new LocationSelectorPanel();
	    resolver = new LibResolver(locationSelector.getLocations());
		repManager = new RepositoryManager(resolver);
		
	    bAddAll = new JButton("Transfer to repository");

	    north.setLayout(new BorderLayout());
	    north.add(locationSelector, BorderLayout.NORTH);
	    
	    center.add(_table);
	    south.setLayout(new BorderLayout());
	    south.add(bAddAll, BorderLayout.SOUTH);
	    bAddAll.addActionListener(this);
		locationSelector.addPropertyChangeListener(this);
		
	    content.add(north);
		content.add(center);
		content.add(south);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return new Dimension(APP_WIDTH,APP_HEIGHT);
	}
	
		
	public static void main(String[] args) {
		Main main = new Main();
		main.pack();
		main.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getSource().getClass() == dk.linvald.libtomavendependencies.gui.LocationSelectorPanel.class) {
			if(resolver.getWhere().getLibLocation() != "" && resolver.getWhere().getRepositoryLocation() != ""){
				String libLoc = (String)evt.getNewValue();
				resolver.getWhere().setLibLocation(libLoc);
			}
			
			if(evt.getPropertyName().equals(LocationSelectorPanel.LIBLOCATION)) {
				try {
				    resolver.clearAll();
					resolver.resolve();
					repManager.sortJars();
					System.out.println("Resolved unversioned:" + repManager.getUnversionedJars().size());
					System.out.println("Resolved versioned:" + repManager.getVersionedJars().size());
					_table.addEntries(repManager.getEntries(),repManager.getUnversionedJars() );

					content.validate();
					
				} catch (ResolveException e) {
					System.err.println("ResolveException:" +e);
				}
			}else if(evt.getPropertyName().equals(LocationSelectorPanel.REPLOCATION) ) {
				String repLoc = (String)evt.getNewValue();
				resolver.getWhere().setRepositoryLocation(repLoc+"\\");
			}
		}
	}

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        File repoLocation = null;
        if(this.repManager.getUnversionedJars().size() + this.repManager.getVersionedJars().size()>0){
            //order the entries
            if(e.getSource().equals(this.bAddAll)){
                Object[][] data = _table.getData();
                for (int i = 0; i < data.length; i++) {
                    RepoEntry entry = (RepoEntry)data[i][5];
                    if(repoLocation==null)repoLocation = entry.getRepoLocation();
                    if(entry.isToBeCopied()){
                        //check if entry exist
                        File maybeExist = entry.getRepoFile();
                        if(!maybeExist.exists()){
                            entry.copyToRepository();
                        }else{
                            long existing = maybeExist.length();
                		    long newFile = entry.getTheArtifact().length();
                		    //ask user
                		    String msg = maybeExist.getName() + " already exist in repository (size=" + existing + ") - File to copy (size:" + newFile + ")";
                		    int answer = JOptionPane.showConfirmDialog(
                	                this,
                	                msg,
                	            	"Overwrite?",
                	            	JOptionPane.ERROR_MESSAGE);
   
                		    switch (answer) {
                		    	case JOptionPane.YES_OPTION:
                		    	    entry.copyToRepository();
                		    	break;
                		    	case JOptionPane.CLOSED_OPTION:
                		    	case JOptionPane.CANCEL_OPTION:
                		    	case JOptionPane.NO_OPTION:
                		    	    break;
                		    	default:
                		    	    break;
                            }
                	
                		}
                     }
                  }
                //write a POM ?
                File loc = locationSelector.getLocations().getPomLocation();
                if(loc!=null){
                    PomGenerator generator = new PomGenerator(new File(loc.getAbsolutePath() + "//project.xml"),repManager.getEntries() );
                    generator.run();
                }
               }
                //update table to reflect succes/failure
                _table.validate();
        }else{
            JOptionPane.showMessageDialog(
	                this,
	            	"No jars to process",
	            	"Serious error",
	            	JOptionPane.ERROR_MESSAGE);
        }
    }
}
