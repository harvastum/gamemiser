package GUI;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import spider.*;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class window1 extends JFrame{

    private static final Logger Log = Logger.getLogger(window1.class);
    private JPanel View1;
    private JButton SearchButton;
    private JLabel AppTitle;
    private JTable Results;
    private JPanel ResultView;
    private JTextArea textArea;
    private DefaultTableModel model;

    String[] columns = {"Title", "Website", "Price"};

    Object[][] data = {{"GTA V", "Steam", 120.61},
            {"GTA V", "Epic Store", 1.00},
            {"GTA V", "Steam", 121.32},
            {"GTA V", "Epic Store", 0.00},
            {"GTA V", "Steam", 20.82},
            {"GTA V", "Epic Store", 0.00}};
    int flag = 0;

    public window1() {
        SearchButton.addActionListener(e -> {
            model.setRowCount(0);
            String query = textArea.getText();
            Log.info("Searching for results " + query + " started");
            Object[] row = new Object[4];
            ArrayList<Shopper> shoppers = new ArrayList<>();
            shoppers.add(new SteamShopper(query));
            shoppers.add(new UbiShopper(query));
            // Loop that calls shoppers and adds rows to the table
            for (Shopper shopper : shoppers) {
                Log.info("Searching in " + shopper.getShop() + " started");
                shopper.run();
                URL url = null;
                ImageIcon image = null;
                try {
                    url = new URL(shopper.getImgSrc());
                } catch (MalformedURLException malformedURLException) {
                    Log.error("MalformedURLException while attempting to reach URL");
                    malformedURLException.printStackTrace();
                }

                try {
                    BufferedImage img = ImageIO.read(url);
                    image = new ImageIcon(img);
                } catch (IOException ioException) {
                    Log.error("IOException while attempting to get image");
                    ioException.printStackTrace();
                    System.out.println("Loading image failed.");
                }
                row[0] = shopper.getShop();
                row[1] = shopper.getTitle();
                row[2] = shopper.getPrice();
                row[3] = image;
                System.out.println(url);
                model.addRow(row);
                Log.info("Searching in " + shopper.getShop() + " finished");
            }
            Log.info("Searching for " + query + " finished");
        });
        // ############### MODEL #################
        model = new DefaultTableModel(
                new Object[][][][]{},
                new String[]{
                        "Shop", "Title", "Price", "Image"
                }
        ){
            Class[] types = new Class[]{
                    String.class, String.class, String.class, ImageIcon.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
//        model.setColumnIdentifiers(columns);
        Results.setModel(model);
        Results.setPreferredScrollableViewportSize(new Dimension(400, 50));
        Results.setRowHeight(100);

        //Results.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(Results.getModel());
        Results.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKey = new ArrayList<>();

        int sortColumn = 2;
        sortKey.add(new RowSorter.SortKey(sortColumn, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKey);
        sorter.sort();

        Results.getColumnModel().getColumn(0).setHeaderValue("Title");
        TableColumn column2 = Results.getColumnModel().getColumn(2);
    }

    public static void main(String[] args){
        //BasicConfigurator.configure();
        Log.info("Gamemiser start");

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
