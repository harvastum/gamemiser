package GUI;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import spider.Shopper;
import spider.SteamShopper;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
                /*
                ArrayList<Shopper> shoppers = new ArrayList<>();
                String query = "gothic";
                shoppers.add(new SteamShopper(query));
                for (Shopper shopper : shoppers) {
                    shopper.run();

                    URL url = null;
                    try {
                        url = new URL(shopper.getImgSrc());
                    } catch (MalformedURLException malformedURLException) {
                        // TODO: j4log
                        malformedURLException.printStackTrace();
                    }

                    try {
                        BufferedImage img = ImageIO.read(url);
                        ImageIcon image = new ImageIcon(img);
                    } catch (IOException ioException) {
                        // TODO: j4log
                        ioException.printStackTrace();
                        String image = "Loading image failed.";
                    }

                    ImageIcon icon = new ImageIcon(shopper.getImgSrc());
                    row[0] = shopper.getTitle();
                    row[1] = shopper.getPrice();
                    row[2] = new ImageIcon(shopper.getImgSrc());
                    model.addRow(row);
                }*/
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
