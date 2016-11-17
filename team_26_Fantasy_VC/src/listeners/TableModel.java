package listeners;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
    
	@Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
    }
	
}