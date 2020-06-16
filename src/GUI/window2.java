package GUI;

import javax.swing.*;
import java.awt.*;

public class window2 extends JPanel {

    private JPanel View2;
    private JTable Results;

    public window2(){
        String[] columns = {"Title", "Website", "Price"};

        String[][] data = {{"GTA V", "Steam", "120"},
                {"GTA V", "Epic Store", "0"}};

        Results = new JTable(data, columns);
        Results.setPreferredScrollableViewportSize(new Dimension(500, 50));
        Results.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(Results);
        add(scrollPane);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Window 2");
        window2 w = new window2();
        frame.setContentPane(w.View2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
