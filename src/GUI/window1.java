package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class window1 extends JFrame{

    private JPanel View1;
    private JButton SearchButton;
    private JLabel AppTitle;
    private JTextPane WritingField;
    private JTable Results;
    String[] columns = {"Title", "Website", "Price"};

    String[][] data = {{"GTA V", "Steam", "120"},
            {"GTA V", "Epic Store", "0"},
            {"GTA V", "Steam", "120"},
            {"GTA V", "Epic Store", "0"},
            {"GTA V", "Steam", "120"},
            {"GTA V", "Epic Store", "0"}};

    public window1() {
        SearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(columns);
                Results.setModel(model);
                Results.setPreferredScrollableViewportSize(new Dimension (400, 50));

                Results.getColumnModel().getColumn(0).setHeaderValue("Title");
                Object[] row = new Object[3];
                for(int i = 0; i < data.length ; i++){
                    row[0] = data[i][0];
                    row[1] = data[i][1];
                    row[2] = data[i][2];
                    model.addRow(row);
                }

            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Window 1");
        window1 w = new window1();
        frame.setContentPane(w.View1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
