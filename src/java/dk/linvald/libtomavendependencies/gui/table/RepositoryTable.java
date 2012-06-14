package dk.linvald.libtomavendependencies.gui.table;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import dk.linvald.libtomavendependencies.gui.Main;
import dk.linvald.libtomavendependencies.liblogic.RepoEntry;


/**
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class RepositoryTable extends JPanel implements TableModelListener{
    protected JTable _table;
    protected PropertyTableModel _model;
	private final String[] _headings = new String[] {"Artifact name","version", "groupid","Is in order", "Move to repository"};
	private Object[][] data;
	
	public RepositoryTable(){
	    super(new GridLayout(1,0));
	    initGui();
	}
	
	private void initGui(){
	    _table = new JTable(new PropertyTableModel(new Object[0][0],_headings));
	    _table.setPreferredScrollableViewportSize(new Dimension(Main.APP_WIDTH-10, 1200));
	    _table.setDefaultRenderer(Boolean.class, new ColorRenderer());
	    _table.setDefaultRenderer(String.class, new ColorRenderer());
	    
	    JScrollPane scrollPane = new JScrollPane(_table);
        add(scrollPane);
	}
	
	
	public void addEntries(ArrayList versioned, ArrayList unversioned){
	    boolean ok = versioned.addAll(unversioned);
		data = new Object[versioned.size() ][_headings.length+1];
		int i = 0;
		for ( ; i< versioned.size(); i++) {
			RepoEntry entry = (RepoEntry) versioned.get(i);
			data[i][0] = entry.getArtifactName();
			data[i][1] = entry.getVersionId();
			data[i][2] = entry.getGroupId(); 
			data[i][3] = new Boolean(entry.isVersioned()).toString();
			data[i][4] = new Boolean(entry.isToBeCopied());
			data[i][5] = entry;
		}	

		_model = new PropertyTableModel(data,_headings);
	    _model.addTableModelListener(this);
	    _table.setModel(_model);
	    _table.validate();
	}

	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		PropertyTableModel model = (PropertyTableModel) e.getSource();
		Object val = (Object) model.getValueAt(row,column);
		String columnName = model.getColumnName(column);
		Object data = model.getValueAt(row, column);
		System.out.println("Data changed\n" +
						   "{Row:" + row + 
						   " column:" + column + 
						   " Columnname:"+ columnName + 
						   " data: " + data +"}");
		
		System.out.println("The entry:"+_model.getValueAt(row,5));
		//update the RepoEntry
		if(_model.getValueAt(row,5) instanceof RepoEntry){
		    RepoEntry entry = (RepoEntry)_model.getValueAt(row,5);
		    if(columnName.equals("version")){
		        entry.setVersionId((String)data);
		    }else if(columnName.equals("Artifact name")){
		        entry.setArtifactName((String)data);
		    }else if(columnName.equals("groupid")){
		        String id = (String)data;
		        String version = (String)_table.getValueAt(row, 1);
		        if(id != "?" && id != "" && version != ""){
		            this.data[row][column+1] = "true";
		            entry.setVersioned(true);
		        }
		        entry.setGroupId((String)data);
		    }else if(columnName.equals("Move to repository")){
		        entry.setToBeCopied(((Boolean)data).booleanValue());
		    }else if(columnName.equals("Is in order")){
		        Boolean b = new Boolean(data.toString());
		        entry.setVersioned( new Boolean(data.toString()).booleanValue());
		    }
		    
		}
		this.invalidate();
	}
	
    /**
     * @return Returns the data.
     */
    public Object[][] getData() {
       return (data==null) ? new Object[0][0] : data;
    }

}
