package dk.linvald.libtomavendependencies.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dk.linvald.libtomavendependencies.liblogic.LibResolver;
import dk.linvald.libtomavendependencies.liblogic.Locations;
import dk.linvald.libtomavendependencies.liblogic.PomGenerator;
import dk.linvald.libtomavendependencies.liblogic.RepositoryManager;

/**
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class LocationSelectorPanel extends JPanel implements ActionListener {
	
	public final static String LIBLOCATION = "liblocaton";
	public final static String REPLOCATION = "reploaction";
	private final byte LIB = 0;
	private final byte REP = 1;
	private final byte POM = 2;
	
	private PropertyChangeSupport beanSupport;
	private Locations locations;
	private LibResolver resolver;
	private RepositoryManager repManager;
	private JTextField tLibLocation, tRepLocation, tPomLocation;
	private JLabel lLibLocation, lRepLocation, lPomLocation;
	private JButton bLibLocation, bRepLocation, bPomLocation;;
	private Main main;
	
	public LocationSelectorPanel() {
		super();
		locations = new Locations();
		beanSupport = new PropertyChangeSupport(this);
		init();
		addComponents();
		this.validate();
	}
	
	private void init() {
		this.tLibLocation = new JTextField();
		this.tRepLocation = new JTextField();
		this.tPomLocation = new JTextField();
		this.lLibLocation = new JLabel("Location of top lib folder conataining jars");
		this.lRepLocation = new JLabel("Location of your Maven repository");
		this.lPomLocation = new JLabel("Location of generated pom (optional)");
		this.bLibLocation = new JButton("Browse");
		this.bRepLocation = new JButton("Browse");
		this.bPomLocation = new JButton("Browse");

		
		bLibLocation.addActionListener(this);
		bRepLocation.addActionListener(this);
		bPomLocation.addActionListener(this);

	}
	
	private void addComponents() {
		this.setLayout(new GridLayout(3,1));	
		add(lRepLocation);
		add(tRepLocation);
		add(bRepLocation);
		
		add(lLibLocation);
		add(tLibLocation);
		add(bLibLocation);
		
		add(lPomLocation);
		add(tPomLocation);
		add(bPomLocation);		

	}
	
	private void showBrowseDialog(String title, byte src) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		fileChooser.setDialogType(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			if(src == LIB) {
			    if(this.getRepositoryLocation()== null){
			        JOptionPane.showMessageDialog(
			                this,
			            	"Please select repository location before your lib location...",
			            	"Location selection",
			            	JOptionPane.INFORMATION_MESSAGE);
			    }else{
			        this.setLibLocation(fileChooser.getSelectedFile().getAbsolutePath());
			        this.tLibLocation.setText(fileChooser.getSelectedFile().toString());
			        beanSupport.firePropertyChange(LIBLOCATION,"", this.tLibLocation.getText());
			    }
			}else if (src == REP){
	
			        this.setRepositoryLocation(fileChooser.getSelectedFile().getAbsolutePath());
			        this.tRepLocation.setText(fileChooser.getSelectedFile().toString());
			        beanSupport.firePropertyChange(REPLOCATION,"", this.tRepLocation.getText());
			}else if(src == POM){
			    	this.locations.setPomLocation(fileChooser.getSelectedFile());
			    	this.tPomLocation.setText(fileChooser.getSelectedFile().toString());
			    	beanSupport.firePropertyChange("pom","", this.tPomLocation.getText());
			}
		}
	}
	
	//******** Bean methods ****************
	public void addPropertyChangeListener( PropertyChangeListener l ){
					beanSupport.addPropertyChangeListener(l);
	}


	public void removePropertyChangeListener( PropertyChangeListener l ) {
					beanSupport.removePropertyChangeListener(l);
	}
	
	
	/**
	 * @return
	 */
	public String getLibLocation() {
		return locations.getLibLocation();
	}
	/**
	 * @return
	 */
	public String getRepositoryLocation() {
		return locations.getRepositoryLocation();
	}
	/**
	 * @param libLocation
	 */
	public void setLibLocation(String libLocation) {
		locations.setLibLocation(libLocation);
	}
	/**
	 * @param repositoryLocation
	 */
	public void setRepositoryLocation(String repositoryLocation) {
		locations.setRepositoryLocation(repositoryLocation);
	}
	
	public static void main(String[] args) {

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LocationSelectorPanel loc = new LocationSelectorPanel();
		f.getContentPane().add(loc);
		f.pack();
		f.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == bLibLocation) {
			showBrowseDialog("Select location of your library files", LIB);
		}else if(src == bRepLocation) {
			showBrowseDialog("Select location of your library files", REP);
		}else if(src == bPomLocation){
		    showBrowseDialog("Select location of project.xml", POM);
		}
	}
	

	
	/**
	 * @return Returns the locations.
	 */
	public Locations getLocations() {
		return locations;
	}
	/* (non-Javadoc)
	 * @see java.awt.Component#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return new Dimension((int)this.getSize().getWidth(),70);
	}
}
