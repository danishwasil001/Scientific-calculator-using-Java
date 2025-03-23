import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    // GUI Components
    private final JTextField textField;
    private final JPanel panel;
    private final JButton[] numberButtons = new JButton[10];
    private final JButton[] functionButtons = new JButton[26];

    // Basic function buttons
    private final JButton addButton, subButton, mulButton, divButton;
    private final JButton decButton, equButton, delButton, clrButton, negButton;

    // Scientific function buttons
    private final JButton sqrtButton, absButton, sinButton, cosButton, tanButton, atanButton;
    private final JButton floorButton, ceilButton, leftBraceButton, rightBraceButton;
    private final JButton powerButton, logButton, log10Button, cbrtButton, asinButton, acosButton, modButton;

    // Fonts
    private final Font fontLarge = new Font("Arial", Font.BOLD, 30);
    private final Font fontSmall = new Font("Arial", Font.BOLD, 20);

    // Engine for expression evaluation
    private final CalculatorEngine engine;

    // Fields for handling two-step power operation
    private double pendingBase = 0;
    private boolean waitingForExponent = false;

    // Constructor: Build the UI and initialize components
    public Calculator() {
        super("Scientific Calculator");
        engine = new CalculatorEngine();

        // Set up frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mohammad Danish's Scientific Calculator");
        setSize(800, 600);
        setLayout(null);
        getContentPane().setBackground(ColorUIResource.GRAY);

        // Setup text field
        textField = new JTextField(20);
        textField.setBounds(80, 40, 700, 70);
        textField.setFont(fontLarge);
        textField.setEditable(true);
        textField.setBorder(BorderFactory.createLineBorder(ColorUIResource.BLACK, 3, true));

        // Create basic function buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("DEL");
        clrButton = new JButton("CLS");
        negButton = new JButton("(-)");

        // Create scientific function buttons (renamed for clarity)
        sqrtButton = new JButton("√x");
        powerButton = new JButton("x^y");
        absButton = new JButton("abs");
        sinButton = new JButton("sin");
        cosButton = new JButton("cos");
        tanButton = new JButton("tan");
        atanButton = new JButton("atan");
        floorButton = new JButton("floor");
        ceilButton = new JButton("ceil");
        leftBraceButton = new JButton("(");
        rightBraceButton = new JButton(")");
        logButton = new JButton("log");
        log10Button = new JButton("log10");
        cbrtButton = new JButton("3√x");
        asinButton = new JButton("asin");
        acosButton = new JButton("acos");
        modButton = new JButton("%");

        // Populate the functionButtons array for easy iteration and styling.
        functionButtons[0]  = addButton;
        functionButtons[1]  = subButton;
        functionButtons[2]  = mulButton;
        functionButtons[3]  = divButton;
        functionButtons[4]  = decButton;
        functionButtons[5]  = equButton;
        functionButtons[6]  = delButton;
        functionButtons[7]  = clrButton;
        functionButtons[8]  = negButton;
        functionButtons[9]  = modButton;
        functionButtons[10] = powerButton;
        functionButtons[11] = acosButton;
        functionButtons[12] = asinButton;
        functionButtons[13] = cbrtButton;
        functionButtons[14] = log10Button;
        functionButtons[15] = logButton;
        functionButtons[16] = rightBraceButton;
        functionButtons[17] = leftBraceButton;
        functionButtons[18] = ceilButton;
        functionButtons[19] = floorButton;
        functionButtons[20] = atanButton;
        functionButtons[21] = tanButton;
        functionButtons[22] = sinButton;
        functionButtons[23] = cosButton;
        functionButtons[24] = absButton;
        functionButtons[25] = sqrtButton;

        // Set up function buttons (apply same listener, font and color improvements)
        for (int i = 0; i < functionButtons.length; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFocusable(false);
            functionButtons[i].setBackground(ColorUIResource.LIGHT_GRAY);
            functionButtons[i].setBorder(BorderFactory.createBevelBorder(0));
            if (i < 10) {
                if (i == 5) { // Equals button gets a different background
                    functionButtons[i].setBackground(ColorUIResource.ORANGE);
                }
                functionButtons[i].setFont(fontLarge);
            } else {
                functionButtons[i].setFont(fontSmall);
                functionButtons[i].setForeground(ColorUIResource.WHITE);
            }
        }

        // Create number buttons 0-9
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(fontLarge);
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBackground(ColorUIResource.LIGHT_GRAY);
            numberButtons[i].setForeground(ColorUIResource.BLACK);
            numberButtons[i].setBorder(BorderFactory.createBevelBorder(0));
        }

        // Create panel to hold buttons in grid layout (6 rows x 6 columns)
        panel = new JPanel();
        panel.setBounds(80, 130, 700, 400);
        panel.setLayout(new GridLayout(6, 6, 5, 5));
        panel.setBackground(ColorUIResource.BLACK);

        // Assemble the buttons into the panel row by row

        // Row 1
        panel.add(absButton);
        panel.add(leftBraceButton);
        panel.add(rightBraceButton);
        panel.add(logButton);
        panel.add(log10Button);
        panel.add(cbrtButton);

        // Row 2
        panel.add(sqrtButton);
        panel.add(powerButton);
        panel.add(clrButton);
        panel.add(delButton);
        panel.add(modButton);
        panel.add(divButton);

        // Row 3
        panel.add(sinButton);
        panel.add(cosButton);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(mulButton);

        // Row 4
        panel.add(asinButton);
        panel.add(acosButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        // Row 5
        panel.add(tanButton);
        panel.add(atanButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(addButton);

        // Row 6
        panel.add(ceilButton);
        panel.add(floorButton);
        panel.add(negButton);
        panel.add(numberButtons[0]);
        panel.add(decButton);
        panel.add(equButton);

        // Add text field and panel to frame
        add(textField);
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle number buttons
        for (int i = 0; i < 10; i++) {
            if(e.getSource() == numberButtons[i]) {
                textField.setText(textField.getText() + i);
                return;
            }
        }

        // Handle decimal point
        if(e.getSource() == decButton) {
            textField.setText(textField.getText() + ".");
            return;
        }

        // Handle basic mathematical operators by simply appending their symbol.
        if(e.getSource() == addButton) {
            textField.setText(textField.getText() + "+");
            return;
        }
        if(e.getSource() == subButton) {
            textField.setText(textField.getText() + "-");
            return;
        }
        if(e.getSource() == mulButton) {
            textField.setText(textField.getText() + "*");
            return;
        }
        if(e.getSource() == divButton) {
            textField.setText(textField.getText() + "/");
            return;
        }
        if(e.getSource() == modButton) {
            textField.setText(textField.getText() + "%");
            return;
        }
        if(e.getSource() == leftBraceButton) {
            textField.setText(textField.getText() + "(");
            return;
        }
        if(e.getSource() == rightBraceButton) {
            textField.setText(textField.getText() + ")");
            return;
        }

        // When powerButton is pressed, store current number as base and wait for exponent.
        if(e.getSource() == powerButton) {
            try {
                pendingBase = Double.parseDouble(textField.getText());
                waitingForExponent = true;
                textField.setText("");
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number for base", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        // The equals button triggers either a pending power operation or a standard evaluation.
        if(e.getSource() == equButton) {
            if(waitingForExponent) {  // Process power operation
                try {
                    double exponent = Double.parseDouble(textField.getText());
                    double result = Math.pow(pendingBase, exponent);
                    textField.setText(String.valueOf(result));
                    waitingForExponent = false;  // Reset flag
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid exponent", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Use the CalculatorEngine to evaluate the full expression.
                try {
                    String expression = textField.getText();
                    double result = engine.evaluate(expression);
                    textField.setText(String.valueOf(result));
                } catch (ScriptException ex) {
                    JOptionPane.showMessageDialog(this, "Error in expression", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            return;
        }

        // Clear the text field
        if(e.getSource() == clrButton) {
            textField.setText("");
            waitingForExponent = false; // Reset if a pending power operation exists.
            return;
        }

        // Delete last character (using StringBuilder for efficient update)
        if(e.getSource() == delButton) {
            String currentText = textField.getText();
            if (!currentText.isEmpty()) {
                StringBuilder sb = new StringBuilder(currentText);
                sb.deleteCharAt(sb.length() - 1);
                textField.setText(sb.toString());
            }
            return;
        }

        // Change sign of the number in the text field
        if(e.getSource() == negButton) {
            try {
                double value = Double.parseDouble(textField.getText());
                value *= -1;
                textField.setText(String.valueOf(value));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Number", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        // Scientific functions: directly compute and update textField
        try {
            double input = Double.parseDouble(textField.getText());
            Double result;

            // Trigonometric functions
            if(e.getSource() == sinButton) {
                result = Math.sin(Math.toRadians(input));
            }
            else if(e.getSource() == cosButton) {
                result = Math.cos(input);

            }
            else if(e.getSource() == tanButton) {
                result = Math.tan(Math.toRadians(input));
            }
            // Inverse trigonometric functions
            else if(e.getSource() == asinButton) {
                result = Math.toDegrees(Math.asin(input));
            }
            else if(e.getSource() == acosButton) {
                result = Math.toDegrees(Math.acos(input));
            }
            else if(e.getSource() == atanButton) {
                result = Math.toDegrees(Math.atan(input));
            }
            // Other functions
            else if(e.getSource() == floorButton) {
                result = Math.floor(input);
            }
            else if(e.getSource() == ceilButton) {
                result = Math.ceil(input);
            }
            else if(e.getSource() == absButton) {
                result = Math.abs(input);
            }
            else if(e.getSource() == cbrtButton){
                result = Math.cbrt(input);
            }
            else if(e.getSource() == sqrtButton) {
                result = Math.sqrt(input);
            }
            else {
                return; // Exit if no known source matches.
            }

            textField.setText(String.valueOf(result));

        } catch (NumberFormatException ex) {
            JOptionPane.
                    showMessageDialog(this, "Invalid Input for operation", "Error", JOptionPane
                            .ERROR_MESSAGE);
        }

    }

    // Main method: Launch the calculator.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }

}
