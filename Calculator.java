import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator implements ActionListener {
    JFrame frame;
    JTextField textfield;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[26];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton, negButton;
    JButton ceilButton, floorButton, atanButton, tanButton, cosButton, sinButton, abstractButton, sequareButton;
    JButton div2Button, powerButton, acosButton, asinButton, inverseButton, log10Button, logButton, rightBraceButton,
            leftBraceButton;
    JPanel panel;

    Font myFont = new Font("Arial", Font.BOLD, 30);
    Font myFont2 = new Font("Arial", Font.BOLD, 20);

    double num1 = 0, num2 = 0, result = 0;
    char operator;

    Calculator() {

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(ColorUIResource.GRAY);
        Image icon = Toolkit.getDefaultToolkit().getImage("png/jpg_icon_file_path_here");
        frame.setIconImage(icon);

        textfield = new JTextField(20);
        textfield.setBounds(80, 40, 700, 70);
        textfield.setFont(myFont);
        textfield.setEditable(true);
        textfield.setBorder(BorderFactory.createLineBorder(ColorUIResource.BLACK, 3, true));

        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton(new ImageIcon("png/jpg_delete_icon_file_path_here"));
        // delButton = new JButton("D");

        // ImageIcon icon2 = new ImageIcon("png/jpg_delete_icon_file_path_here");
        // Image img = icon.getImage();
        // Image imgScale = img.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        // ImageIcon scaledIcon = new ImageIcon(imgScale);

        clrButton = new JButton("C");
        negButton = new JButton("(-)");

        sequareButton = new JButton("2√x");//
        abstractButton = new JButton("abs");//
        sinButton = new JButton("sin");//
        cosButton = new JButton("cos");//
        tanButton = new JButton("tan");//
        atanButton = new JButton("atan");//
        floorButton = new JButton("floor");//
        ceilButton = new JButton("ceil");//
        leftBraceButton = new JButton("(");
        rightBraceButton = new JButton(")");
        logButton = new JButton("ln");
        log10Button = new JButton("log");
        inverseButton = new JButton("3√x");//
        asinButton = new JButton("asin");//
        acosButton = new JButton("acos");//
        powerButton = new JButton("x^y");//
        div2Button = new JButton("%");//

        //////////////////////////////////////
        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = decButton;
        functionButtons[5] = equButton;
        functionButtons[6] = delButton;
        functionButtons[7] = clrButton;
        functionButtons[8] = negButton;

        functionButtons[9] = div2Button; // xxxxxx
        functionButtons[10] = powerButton;// xxxxxxx
        functionButtons[11] = acosButton;// xxxxxxxxxxxx
        functionButtons[12] = asinButton;// xxxxxxxxx
        functionButtons[13] = inverseButton;// xxxxxx
        functionButtons[14] = log10Button;// xxxxxxx
        functionButtons[15] = logButton;// xxxxx
        functionButtons[16] = rightBraceButton;
        functionButtons[17] = leftBraceButton;
        functionButtons[18] = ceilButton;// xxxxxxxxxx
        functionButtons[19] = floorButton;// xxxxxxxx
        functionButtons[20] = atanButton;// xxxxxxxxxx
        functionButtons[21] = tanButton;// xxxxxxxxxxxxx
        functionButtons[22] = sinButton;// xxxxxxxxx
        functionButtons[23] = cosButton;// xxxxxxx
        functionButtons[24] = abstractButton;// xxxxxxxxxx
        functionButtons[25] = sequareButton;// xxxxxxxxx
        ////////////////////////////////
        for (int i = 0; i < 26; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFocusable(false);
            functionButtons[i].setBackground(ColorUIResource.LIGHT_GRAY);
            functionButtons[i].setBorder(BorderFactory.createBevelBorder(0));

            if (i < 10) {
                if (i == 5)
                    functionButtons[i].setBackground(ColorUIResource.ORANGE);
                functionButtons[i].setFont(myFont);
            } else {
                functionButtons[i].setFont(myFont2);
                functionButtons[i].setForeground(ColorUIResource.WHITE);
            }
        }

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
            numberButtons[i].setBackground(ColorUIResource.LIGHT_GRAY);
            numberButtons[i].setForeground(ColorUIResource.BLACK);
            numberButtons[i].setBorder(BorderFactory.createBevelBorder(0));

        }

        panel = new JPanel();
        panel.setBounds(80, 130, 700, 400);
        panel.setLayout(new GridLayout(6, 6, 5, 5));
        panel.setBackground(ColorUIResource.BLACK);

        // 1'st row
        panel.add(abstractButton);
        panel.add(leftBraceButton);
        panel.add(rightBraceButton);
        panel.add(logButton);
        panel.add(log10Button);
        panel.add(inverseButton);

        /// 2'd row
        panel.add(sequareButton);
        panel.add(powerButton);
        panel.add(clrButton);
        panel.add(delButton);
        panel.add(div2Button);
        panel.add(divButton);

        /// 3'rd row
        panel.add(sinButton);
        panel.add(cosButton);
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(mulButton);

        /// 4'th row
        panel.add(asinButton);
        panel.add(acosButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        /// 5'th row
        panel.add(tanButton);
        panel.add(atanButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(addButton);

        // last row
        panel.add(ceilButton);
        panel.add(floorButton);
        panel.add(negButton);
        panel.add(numberButtons[0]);
        panel.add(decButton);
        panel.add(equButton);

        frame.add(panel);

        frame.add(textfield);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        Calculator calc = new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                textfield.setText(textfield.getText().concat(String.valueOf(i)));
            }
        }
        if (e.getSource() == decButton) {
            textfield.setText(textfield.getText().concat("."));
        }
        if (e.getSource() == addButton) {
            textfield.setText(textfield.getText().concat("+"));
        }
        if (e.getSource() == subButton) {
            textfield.setText(textfield.getText().concat("-"));
        }
        if (e.getSource() == mulButton) {
            textfield.setText(textfield.getText().concat("*"));
        }
        if (e.getSource() == divButton) {
            textfield.setText(textfield.getText().concat("/"));
        }
        if (e.getSource() == div2Button) {
            textfield.setText(textfield.getText().concat("%"));
        }
        if (e.getSource() == leftBraceButton) {
            textfield.setText(textfield.getText().concat("("));
        }
        if (e.getSource() == rightBraceButton) {
            textfield.setText(textfield.getText().concat(")"));
        }
        if (e.getSource() == powerButton) {
            num1 = Double.parseDouble(textfield.getText());
            operator = '^';
            textfield.setText("");
        }
        if (e.getSource() == equButton) {
            // num2 = Double.parseDouble(textfield.getText());

            try {
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                String foot = textfield.getText();
                textfield.setText(String.valueOf(engine.eval(foot)));
            } catch (ScriptException ex) {
                throw new RuntimeException(ex);
            }

            // case '^': /// power
            // result = Math.pow(num1, num2);
            // break;
            // }
            // textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == clrButton) {
            textfield.setText("");
        }
        if (e.getSource() == delButton) {
            String string = textfield.getText();
            textfield.setText("");
            for (int i = 0; i < string.length() - 1; i++) {
                textfield.setText(textfield.getText() + string.charAt(i));
            }
        }
        if (e.getSource() == negButton) {
            double temp = Double.parseDouble(textfield.getText());
            temp *= -1;
            textfield.setText(String.valueOf(temp));
        }
        /////
        if (e.getSource() == sinButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.sin(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == cosButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.cos(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == asinButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.asin(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == acosButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.acos(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == tanButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.tan(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == atanButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.atan(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == floorButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.floor(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == ceilButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.ceil(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == sequareButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.sqrt(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == inverseButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.cbrt(num1);
            textfield.setText(String.valueOf(result));
        }
        if (e.getSource() == abstractButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.abs(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == logButton) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.log(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
        if (e.getSource() == log10Button) {
            num1 = Double.parseDouble(textfield.getText());
            result = Math.log10(num1);
            textfield.setText(String.valueOf(result));
            num1 = result;
        }
    }
}
