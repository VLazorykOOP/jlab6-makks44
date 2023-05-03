import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MySwingApp extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton startButton;
    private JTextField fileNameTextField;
    private JLabel fileNameLabel;
    private JScrollPane scrollPane;
    private JTable table;
    private String[] columnNames = {"Text"};

    public MySwingApp() {
        setTitle("Random String Movement");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        topPanel = new JPanel();
        fileNameLabel = new JLabel("File Name: ");
        fileNameTextField = new JTextField(20);
        startButton = new JButton("Start");
        startButton.addActionListener(new StartButtonActionListener());

        topPanel.add(fileNameLabel);
        topPanel.add(fileNameTextField);
        topPanel.add(startButton);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        table = new JTable();
        scrollPane = new JScrollPane(table);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class StartButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String fileName = fileNameTextField.getText();

            try {
                String[] strings = readStringsFromFile(fileName);
                String[][] data = new String[strings.length][1];

                for (int i = 0; i < strings.length; i++) {
                    data[i][0] = strings[i];
                }

                table = new JTable(data, columnNames);
                scrollPane.setViewportView(table);

                Random random = new Random();
                int direction = random.nextInt(2); // 0 - move from left to right, 1 - move from right to left

                if (direction == 0) {
                    moveStringsFromLeftToRight(strings);
                } else {
                    moveStringsFromRightToLeft(strings);
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (MyException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String[] readStringsFromFile(String fileName) throws FileNotFoundException, IOException, MyException {
        File file = new File(fileName);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        reader.close();
        String[] strings = sb.toString().split(System.lineSeparator());

        if (strings.length == 0) {
            throw new MyException("File contains no strings");
        }

        return strings;
    }

    private void moveStringsFromLeftToRight(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            try {
                Thread.sleep(500);

                for (int j = 0; j < strings[i].length(); j++) {
                    System.out.print(strings[i].charAt(j));
                    Thread.sleep(100);
                }

                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveStringsFromRightToLeft(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            try {
                Thread.sleep(500);

                for (int j = strings[i].length() - 1; j >= 0; j--) {
                    System.out.print(strings[i].charAt(j));
                    Thread.sleep(100);
                }

                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MySwingApp();
    }
}

class MyException extends ArithmeticException {
    private static final long serialVersionUID = 1L;

    public MyException(String message) {
        super(message);
    }
}