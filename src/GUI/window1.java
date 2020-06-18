package GUI;

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

import org.apache.log4j.Logger;

public class window1 extends JFrame {

    private static final Logger log = Logger.getLogger(window1.class);
    private JPanel View1;
    private JButton SearchButton;
    private JLabel AppTitle;
    private JTable Results;
    private JPanel ResultView;
    private JTextField textArea;
    private DefaultTableModel model;


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
        ) {
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
        Results.setModel(model);
        Results.setPreferredScrollableViewportSize(new Dimension(400, 50));
        Results.setRowHeight(175);
        Results.getColumn("Title").setPreferredWidth(800);
        Results.getColumn("Image").setPreferredWidth(200);
        Results.getColumn("Shop").setPreferredWidth(220);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(Results.getModel());
        Results.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKey = new ArrayList<>();
        textArea.setHorizontalAlignment(JTextField.CENTER);
        int sortColumn = 2;
        sortKey.add(new RowSorter.SortKey(sortColumn, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKey);
        sorter.sort();

        TableColumn column2 = Results.getColumnModel().getColumn(2);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int x=0;x<3;x++){
            Results.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }


    }

    public static void main(String[] args) {
        //BasicConfigurator.configure();
        log.info("GameMiser started");

        JFrame frame = new JFrame("Gamemiser");
        frame.setSize(1900, 1000);
        frame.setLocation(20, 10);

        window1 w = new window1();
        w.View1.setBackground(Color.LIGHT_GRAY);

        frame.setContentPane(w.View1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private ActionListener search = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            model.setRowCount(0);
            String query = textArea.getText();

            ArrayList<Shopper> shoppers = new ArrayList<>();
            shoppers.add(new SteamShopper(query));
            shoppers.add(new UbiShopper(query));
            shoppers.add(new AllegroShopper(query));

            // Loop that calls shoppers and adds rows to the table
            ArrayList<Thread> threads = new ArrayList<>();
            for (Shopper shopper : shoppers)threads.add(new Thread(shopper));
            for (Thread shopper : threads)shopper.start();
            for (Thread shopper : threads) {
                try {
                    shopper.join();
                } catch (InterruptedException e) {
                    log.error("Threads did an oopsie. "+e.getMessage());
                    e.printStackTrace();
                }
            }
            for (Shopper shopper : shoppers)write(shopper);

        }
    };

    public void write(Shopper shopper){
        Object[] row = new Object[4];
        URL url;
        ImageIcon image;
        try {
            url = new URL(shopper.getImgSrc());
        } catch (MalformedURLException malformedURLException) {
            // TODO: j4log
            //malformedURLException.printStackTrace();
            return;
        }

        try {
            BufferedImage img = ImageIO.read(url);
            image = new ImageIcon(img);
        } catch (IOException ioException) {
            // TODO: j4log
            ioException.printStackTrace();
            System.out.println("Loading image failed.");
            return;
        }
        // Image
        Image imageIcon = image.getImage(); // transform it
        Image newImg = imageIcon.getScaledInstance(200, 175, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        image = new ImageIcon(newImg);  // transform it back

        // Title with link

        JLabel hyperlink = new JLabel("shopper.getPrice()");
        row[0] = shopper.getShop();
        row[1] = shopper.getTitle();
        row[2] = shopper.getPrice();
        row[3] = image;
        model.addRow(row);
        log.info(shopper.getShop()+" found a game! Link:"+shopper.getLink());

    }
}
