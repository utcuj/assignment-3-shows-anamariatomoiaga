package View;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Reflaction {
public JTable createTable(List<Object> o){
		
		DefaultTableModel t = new DefaultTableModel();
		
		for(Field field: o.get(0).getClass().getDeclaredFields()){
			t.addColumn(field.getName());
		}
		
		for(Object ob : o){
			Vector<Object> row = new Vector<Object>();
			for(Field field: ob.getClass().getDeclaredFields()){
				field.setAccessible(true);
				try{
					row.add(field.get(ob));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			t.addRow(row);
		}
		JTable table = new JTable(t);
		return table;
	}
}
