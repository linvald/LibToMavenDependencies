package dk.linvald.libtomavendependencies.gui.table;

import javax.swing.table.AbstractTableModel;

public class PropertyTableModel extends AbstractTableModel {
	private Object[][] data = new Object[0][0];
	private String[] columnNames = new String[0];
	
	public PropertyTableModel(Object[][] data) {
		this.data = data;
	}

	public PropertyTableModel(Object[][] data, String[] columnNames) {
		this.data = data;
		this.columnNames = columnNames;
	}
	
	/**
	 * @param columnNames2
	 */
	public PropertyTableModel(String[] columnNames2) {
		this.columnNames = columnNames2;
	}

	public int getColumnCount() {
		return columnNames.length;
	}
	public int getRowCount() {
		return data.length;
	}
	public String getColumnName(int col) {
		return columnNames[col].toString();
	}
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	public Object getValueByKey(String key) {
		for (int i = 0; i < data.length; i++) {
			String rowVal = (String) data[i][0];
			if (key.equals(rowVal)) {
				return data[i][1];
			}
		}
		return null;
	}
	
	public void setValue(String key, Object val) {
		for (int i = 0; i < data.length; i++) {
			String rowVal = (String) data[i][0];
			if (key.equals(rowVal)) {
				data[i][1] = val;
			}
		}
	}

	public Class getColumnClass(int c) {
	    if(getValueAt(0, c)!=null){
	        return getValueAt(0, c).getClass();
	    }    
	    else{
	        return Object.class;
	    }
	}
	
	public boolean isCellEditable(int row, int col) {
		if(this.getColumnName(col).equals("Is in order")){
		    return false;
		}
		return true;
	}

	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}
}