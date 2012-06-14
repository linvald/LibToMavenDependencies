package dk.linvald.libtomavendependencies.gui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRenderer extends DefaultTableCellRenderer {
    private JCheckBox box = new JCheckBox();
    private final Color VERSIONED = Color.green;
    private final Color UNVERSIONED = Color.red;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        boolean isCheckBox = table.getColumnName(column)=="Move to repository";
     	boolean isVersioned =  table.getValueAt(row, 3) == "true";

        Component comp = super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
        Color color = isVersioned ? VERSIONED : UNVERSIONED;
        comp.setBackground(color);
        if(isCheckBox){
            box.setSelected(((Boolean) value).booleanValue());
            box.setBackground(color);
            comp = box;        
        }
        
        // darken the background when selected
        if (isSelected) {
            comp.setBackground(getBackground().darker());
        }
       
        return comp;
    }
    


}