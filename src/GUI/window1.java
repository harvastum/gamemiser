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
    private JTextField textArea;
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
        SearchButton.addActionListener(search);
        textArea.addActionListener(search);
        // ############### MODEL #################
        model = new DefaultTableModel(
                new Object[][]{},
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
        frame.setSize(1000, 1000);
        frame.setLocation(510, 50);

        window1 w = new window1();
        w.View1.setBackground(Color.LIGHT_GRAY);

        frame.setContentPane(w.View1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    ActionListener search= new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            model.setRowCount(0);
            String query = textArea.getText();
            Object[] row = new Object[4];
            ArrayList<Shopper> shoppers = new ArrayList<>();
            shoppers.add(new SteamShopper(query));
            shoppers.add(new UbiShopper(query));
            shoppers.add(new AllegroShopper(query));

            // Loop that calls shoppers and adds rows to the table
            for (Shopper shopper : shoppers) {
                shopper.run();
                URL url;
                ImageIcon image;
                try {
                    url = new URL(shopper.getImgSrc());
                } catch (MalformedURLException malformedURLException) {
                    // TODO: j4log
                    //malformedURLException.printStackTrace();
                    continue;
                }

                try {
                    BufferedImage img = ImageIO.read(url);
                    image = new ImageIcon(img);
                } catch (IOException ioException) {
                    // TODO: j4log
                    ioException.printStackTrace();
                    System.out.println("Loading image failed.");
                    continue;
                }

                Image imageIcon = image.getImage(); // transform it
                Image newImg = imageIcon.getScaledInstance(200, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                image = new ImageIcon(newImg);  // transform it back

                row[0] = shopper.getShop();
                row[1] = shopper.getTitle();
                row[2] = shopper.getPrice();
                row[3] = image;
                model.addRow(row);
            }
        }};
}
