package gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mark Skuratov, KhNURE.
 * @version 1.0.
 *          Date: 24.10.12.
 */
public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Square Attack");
        this.setResizable(false);
        this.setSize(600, 400);
        initContent();
    }

    private void initContent() {
        JPanel borderPanel = new JPanel();
        borderPanel.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4));
        for(int i = 0; i < 16; i++){
        gridPanel.add(new JButton()); }

        borderPanel.add(gridPanel, BorderLayout.CENTER);

        this.add(borderPanel);
    }


}
