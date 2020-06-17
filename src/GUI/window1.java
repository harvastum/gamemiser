package GUI;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class window1 extends JFrame{

    private JPanel View1;
    private JButton SearchButton;
    private JLabel AppTitle;
    private JTable Results;
    private JPanel ResultView;
    private JTextArea textArea;
    private DefaultTableModel model;
    private String text;
    String[] columns = {"Title", "Website", "Price"};

    Object[][] data = {{"GTA V", "Steam", 120.61},
            {"GTA V", "Epic Store", 1.00},
            {"GTA V", "Steam", 121.32},
            {"GTA V", "Epic Store", 0.00},
            {"GTA V", "Steam", 20.82},
            {"GTA V", "Epic Store", 0.00}};
    int flag = 0;

    public Class getColumnClass(int column){
        return data[0][column].getClass();
    }
    public window1() {
        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text = textArea.getText();

                model = new DefaultTableModel(data, columns){
                    @Override
                    public Class<?> getColumnClass(int col) {

                        Class retVal = Object.class;

                        if(getRowCount() > 0)
                            retVal =  getValueAt(0, col).getClass();

                        return retVal;
                    }
                };
                model.setColumnIdentifiers(columns);
                Results.setModel(model);
                Results.setPreferredScrollableViewportSize(new Dimension(400, 50));

                //Results.setAutoCreateRowSorter(true);
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(Results.getModel());
                Results.setRowSorter(sorter);
                List<RowSorter.SortKey> sortKey = new ArrayList<>();

                int sortColumn = 2;
                sortKey.add(new RowSorter.SortKey(sortColumn, SortOrder.ASCENDING));

                sorter.setSortKeys(sortKey);
                sorter.sort();

                Results.getColumnModel().getColumn(0).setHeaderValue("Title");
                Object[] row = new Object[3];
                TableColumn column2 = Results.getColumnModel().getColumn(2);
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Gamemiser");
        frame.setSize(600, 300);
        frame.setLocation(410, 140);

        window1 w = new window1();
        w.View1.setBackground(Color.LIGHT_GRAY);

        frame.setContentPane(w.View1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

class DoubleRenderer extends DefaultTableCellRenderer {
    DoubleRenderer() {
        setHorizontalAlignment(SwingConstants.RIGHT);
    }

    @Override
    public void setValue(Object aValue) {
        Object result = aValue;
        if ((aValue != null) && (aValue instanceof Number)) {
            Number numberValue = (Number) aValue;
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            result = formatter.format(numberValue.doubleValue());
        }
        super.setValue(result);
    }
}
