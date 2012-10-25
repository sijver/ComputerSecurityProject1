package gui;

import aes.AES;
import aes.SquareAttack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Mark Skuratov, KhNURE.
 * @version 1.0.
 *          Date: 24.10.12.
 */
public class MainFrame extends JFrame {

    private final static JTextArea consoleTextArea = new JTextArea();

    public MainFrame() throws HeadlessException {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Square Attack");
        this.setResizable(false);
        this.setSize(800, 580);
        this.getContentPane().setLayout(null);
        initComponents();
    }

    private void initComponents() {
        initKeyGeneratingPanel();
        initOriginalKeyButton();
        initButtonGrid();
        initConsoleWindow();
    }

    private void initKeyGeneratingPanel() {
        final TextField keyField = new TextField();
        keyField.setLocation(30, 85);
        keyField.setSize(240, 20);
        this.add(keyField);

        JButton generateKeyButton = new JButton();
        generateKeyButton.setLocation(30, 30);
        generateKeyButton.setSize(240, 40);
        generateKeyButton.setText("Generate key");
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyField.setText("");
                byte[] generatedKey = SquareAttack.generateKey();
                for (byte b : generatedKey) {
                    keyField.setText(keyField.getText().concat(byteToHex(b)));
                }
            }
        });

        this.add(generateKeyButton);

        JButton setKeyButton = new JButton();
        setKeyButton.setLocation(30, 120);
        setKeyButton.setSize(240, 40);
        setKeyButton.setText("Set chosen key");
        setKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isHex(keyField.getText())) {
                    MainFrame.printToConsole("Key is not hex!\n");
                } else if (keyField.getText().length() < 32) {
                    MainFrame.printToConsole("Key is too short!\n");
                } else if (keyField.getText().length() > 32) {
                    MainFrame.printToConsole("Key is too long!\n");
                } else {
                    SquareAttack.setKey(hexStringToByteArray(keyField.getText()));
                }
            }
        });

        this.add(setKeyButton);
    }

    private void initOriginalKeyButton() {
        JButton showOriginalKeyButton = new JButton();
        showOriginalKeyButton.setLocation(30, 200);
        showOriginalKeyButton.setSize(240, 40);
        showOriginalKeyButton.setText("Show Original KA4 key");
        showOriginalKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    byte[][] roundKey = AES.getRoundKey(4);
                    printToConsole("KA4:");
                    for (int i = 0; i < roundKey.length; i++) {
                        for (int j = 0; j < roundKey[i].length; j++) {
                            printToConsole(String.format(" %1$s", byteToHex(roundKey[i][j])));
                        }
                    }
                    printToConsole("\n");
                } catch (NullPointerException exc) {
                    MainFrame.printToConsole("First make encryption and after try to get KA4 again\n");
                }

            }
        });

        this.add(showOriginalKeyButton);
    }

    private void initButtonGrid() {
        JLabel textLabel = new JLabel();
        textLabel.setLocation(15, 300);
        textLabel.setSize(270, 20);
        textLabel.setText("Click button to recover appropriate byte of KA4");
        this.add(textLabel);

        for (int i = 0; i < 16; i++) {
            final JButton button = new JButton();
            button.setLocation(50 + (i % 4) * 50, 330 + i / 4 * 50);
            button.setSize(50, 50);
            button.setText(String.valueOf(i + 1));
            final int activeByte = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (null == SquareAttack.getKey()) {
                        MainFrame.printToConsole("Key is not set. Set the key and try again\n");
                    } else {
                        byte[][] plainTexts = SquareAttack.generate256Texts(activeByte);
                        byte[][] cipherTexts = SquareAttack.encryptTexts(plainTexts);
                        byte recoveredByte = SquareAttack.attackKeyByte(cipherTexts, activeByte);
                        button.setText(byteToHex(recoveredByte));
                    }
                }
            });
            this.add(button);
        }
    }

    private void initConsoleWindow() {
        JScrollPane textAreaScroll = new JScrollPane(consoleTextArea);
        textAreaScroll.setLocation(300, 30);
        textAreaScroll.setSize(470, 500);

        this.add(textAreaScroll);
    }

    public static void printToConsole(String s) {
        consoleTextArea.append(s);
    }

    public static String byteToHex(byte b) {
        return String.format("%02x", b);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    private static boolean isHexStringChar(char c) {
        return (Character.isDigit(c) ||
                Character.isWhitespace(c) ||
                (("0123456789abcdefABCDEF".indexOf(c)) >= 0));
    }

    /**
     * Return true if the argument string seems to be a
     * Hex data string, like "a0 13 2f ". Whitespace is
     * ignored.
     */
    private static boolean isHex(String sampleData) {
        for (int i = 0; i < sampleData.length(); i++) {
            if (!isHexStringChar(sampleData.charAt(i))) return false;
        }
        return true;
    }

}
