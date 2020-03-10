import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {
    //JFrame icon
    ImageIcon imageIcon = new ImageIcon("src/icon.jpg");

    //JFrame dimensions
    private final int WIDTH = 300;
    private final int HEIGHT = 420;

    //get container
    Container container = getContentPane();

    //JButton[] of numbers
    JButton[] numButtons = new JButton[10];

    //JButton[] of operations
    JButton[] opButtons = new JButton[4];

    //JButton[] of others
    JButton[] otherButtons = new JButton[4];

    //String[] of numbers
    String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    //String[] of operations
    String[] operations = {"+", "-", "*", "/"};

    //String[] of others
    String[] others = {"C", "<-", "=", "."};

    //[i][0] - gridx, [i][1] - gridy,
    //[i][2] - gridwidth, [i][3] - gridheight
    private int[][] numConstraints =
            {
                    {0, 5, 2, 1},
                    {0, 4, 1, 1},
                    {1, 4, 1, 1},
                    {2, 4, 1, 1},
                    {0, 3, 1, 1},
                    {1, 3, 1, 1},
                    {2, 3, 1, 1},
                    {0, 2, 1, 1},
                    {1, 2, 1, 1},
                    {2, 2, 1, 1}
            };

    private int[][] opConstraints =
            {
                    {3, 2, 1, 1},
                    {3, 3, 1, 1},
                    {3, 4, 1, 1},
                    {3, 5, 1, 1}
            };

    private int[][] otherConstraints =
            {
                    {4, 2, 1, 1},
                    {4, 3, 1, 1},
                    {4, 4, 1, 2},
                    {2, 5, 1, 1}
            };

    //GridBagLayout
    GridBagLayout gbl = new GridBagLayout();

    //GridBagConstraints
    GridBagConstraints gbc = new GridBagConstraints();

    //JTextField
    JTextField resultField = new JTextField();

    //colors
    Color buttonColor = new Color(64, 64, 64);
    Color fontColor = new Color(255, 255, 255);
    Color fieldColor = new Color(224, 224, 224);

    public Calculator() {
        //JFrame settings
        setIconImage(imageIcon.getImage());
        setTitle("Calculator");
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set the layout
        gbl.columnWidths = new int[] {60, 60, 60, 60, 60};
        gbl.rowHeights = new int[] {70, 70, 70, 70, 70, 70};
        container.setLayout(gbl);

        //gbc configurations
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(2, 2, 2, 2);

        //populate the buttons' arrays
        populateComponentsArray(numButtons, numbers);
        populateComponentsArray(opButtons, operations);
        populateComponentsArray(otherButtons, others);

        //add the components
        addComponents(numButtons, numConstraints);
        addComponents(opButtons, opConstraints);
        addComponents(otherButtons, otherConstraints);

        //change the color of "=" button
        otherButtons[2].setBackground(new Color(255, 153, 51));

        //add JTextField
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 5;
        resultField.setEnabled(false);
        resultField.setFont(new Font("Monospace", Font.PLAIN, 22));
        resultField.setDisabledTextColor(Color.BLACK);
        resultField.setBackground(fieldColor);
        container.add(resultField, gbc);

        //add action listeners
        addActionListeners(numButtons);
        addActionListeners(opButtons);
        addActionListeners(otherButtons);
    }

    //populate arrays
    private void populateComponentsArray(JButton[] buttonsArray, String[] symbols) {
        for (int i = 0; i < symbols.length; i++) {
            buttonsArray[i] = new JButton(symbols[i]);
        }
    }

    //add components
    private void addComponents(JButton[] buttonArray, int[][] buttonConstraints) {
        for (int i = 0; i < buttonArray.length; i++) {
            gbc.gridx = buttonConstraints[i][0];
            gbc.gridy = buttonConstraints[i][1];
            gbc.gridwidth = buttonConstraints[i][2];
            gbc.gridheight = buttonConstraints[i][3];

            buttonArray[i].setBackground(buttonColor);
            buttonArray[i].setForeground(fontColor);
            container.add(buttonArray[i], gbc);
        }
    }

    //add actionListeners to components
    private void addActionListeners(JButton[] buttonArray) {
        for (int i = 0; i < buttonArray.length; i++) {
            buttonArray[i].addActionListener(this);
        }
    }

    //calculation variables
    double num1 = 0;
    double num2 = 0;
    double total = 0;
    char operation = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)(e.getSource());
        String sourceText = source.getText();
        String resultText = "";

        if (!(sourceText.equals("<-") || sourceText.equals("C"))) {
            resultField.setText(resultField.getText() + sourceText);
            resultText = resultField.getText();
        }
        else if (sourceText.equals("<-")) {
            int len = resultField.getText().length();

            if (len > 0) {
                resultField.setText(resultField.getText().substring(0, len - 1));
            }
        }
        else {
            resultField.setText("");
        }

        if (sourceText.equals("+")) {
            num1 = Double.parseDouble(resultText.substring(0, resultText.indexOf("+")));
            resultField.setText("");
            operation = '+';
        }
        else if (sourceText.equals("-")) {
            num1 = Double.parseDouble(resultText.substring(0, resultText.indexOf('-')));
            resultField.setText("");
            operation = '-';
        }
        else if (sourceText.equals("*")) {
            num1 = Double.parseDouble(resultText.substring(0, resultText.indexOf('*')));
            resultField.setText("");
            operation = '*';
        }
        else if (sourceText.equals("/")) {
            num1 = Double.parseDouble(resultText.substring(0, resultText.indexOf('/')));
            resultField.setText("");
            operation = '/';
        }
        else if (sourceText.equals("=")) {
            num2 = Double.parseDouble(resultText.substring(0, resultText.indexOf('=')));

            if (operation == '+') {
                total = num1 + num2;
            }
            else if (operation == '-') {
                total = num1 - num2;
            }
            else if (operation == '*') {
                total = num1 * num2;
            }
            else {
                total = num1 / num2;
            }

            resultField.setText(String.format("%.2f", total));
        }
    }

    public static void main(String[] args) {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
    }
}