package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class View {
    public int width = 800;
    public int height = 700;
    public JFrame frame;
    public <T> void refreshTable(ArrayList<T> objects, DefaultTableModel model){
        model.setRowCount(0);
        for(Object object : objects){
            ArrayList<Object> values = new ArrayList<>();
            for(Field field : object.getClass().getFields()){
                field.setAccessible(true);
                try {
                    values.add(field.get(object));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(values.toArray());
        }
    }
    public <T> void refreshProductsTable(ArrayList<T> objects, DefaultTableModel model){
        model.setRowCount(0);
        for(Object object : objects){
            ArrayList<Object> values = new ArrayList<>();
            for(Field field : object.getClass().getFields()){
                field.setAccessible(true);
                try {
                    if(object.getClass().toString().equals("class Model.CompositeProduct"))
                        if(field.getName().equals("products"))
                            continue;
                    values.add(field.get(object));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(values.toArray());
        }
    }

    public DefaultTableModel createTableModel(Object object) {
        ArrayList<String> properties = new ArrayList<>();
        for (Field field : object.getClass().getFields()) {
            properties.add(field.getName());
        }
        DefaultTableModel tableModel = new DefaultTableModel(null, properties.toArray()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        return tableModel;
    }

    public void hideFrame(){
        frame.setVisible(false);
    }
    public void showError(String message){ JOptionPane.showMessageDialog(frame, message); }
    public void frameListener(WindowAdapter wa){ frame.addWindowListener(wa);}

}
