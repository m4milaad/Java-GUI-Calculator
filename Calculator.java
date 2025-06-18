import java.awt.*;
import java.awt.event.*;

public class Calculator extends Frame implements ActionListener {
    double first;
    String operator;
    TextField display = new TextField("0");
    Button[] buttons = new Button[20];
    Color backgroundColor = new Color(0x1E1F22);
    Color backgroundColor2 = new Color(0x3D3F45);
    Color textColor = Color.white;
    Font f = new Font("consolas", Font.BOLD, 24);

    public Calculator() {
        // Set framecmd
        this.setSize(335, 477);
        this.setVisible(true);
        this.setTitle("Calculator");
        this.setLayout(null);

        // Initialize digit buttons
        for (int i = 0; i < 10; i++) {
            buttons[i] = new Button(String.valueOf(i));
        }

        // Other buttons
        buttons[10] = new Button("Light");
        buttons[11] = new Button("+");
        buttons[12] = new Button("-");
        buttons[13] = new Button("×");
        buttons[14] = new Button("÷");
        buttons[15] = new Button(".");
        buttons[16] = new Button("=");
        buttons[17] = new Button("CE");
        buttons[18] = new Button("AC");
        buttons[19] = new Button("<-");

        for (int i = 0; i < 20; i++) {
            buttons[i].setFont(f);
        }

        // Layout
        display.setBounds(7, 34, 321, 73);
        display.setFont(f);
        display.setEditable(false);

        buttons[10].setBounds(7, 114, 75, 65);   // Theme
        buttons[19].setBounds(89, 114, 75, 65);  // Backspace
        buttons[17].setBounds(171, 114, 75, 65); // CE
        buttons[18].setBounds(253, 114, 75, 65); // AC

        buttons[7].setBounds(7, 186, 75, 65);
        buttons[8].setBounds(89, 186, 75, 65);
        buttons[9].setBounds(171, 186, 75, 65);
        buttons[14].setBounds(253, 186, 75, 65); // ÷

        buttons[4].setBounds(7, 258, 75, 65);
        buttons[5].setBounds(89, 258, 75, 65);
        buttons[6].setBounds(171, 258, 75, 65);
        buttons[13].setBounds(253, 258, 75, 65); // ×

        buttons[1].setBounds(7, 330, 75, 65);
        buttons[2].setBounds(89, 330, 75, 65);
        buttons[3].setBounds(171, 330, 75, 65);
        buttons[12].setBounds(253, 330, 75, 65); // -

        buttons[0].setBounds(7, 402, 75, 65);
        buttons[15].setBounds(89, 402, 75, 65);  // .
        buttons[16].setBounds(171, 402, 75, 65); // =
        buttons[11].setBounds(253, 402, 75, 65); // +

        // Add to frame
        for (Button b : buttons) {
            this.add(b);
        }
        this.add(display);

        // Close window
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Frame frame = (Frame) e.getSource();
                frame.dispose();
            }
        });

        // Theme switch
        buttons[10].addActionListener(e -> {
            if (buttons[10].getLabel().equals("Light")) {
                setBackground(backgroundColor);
                display.setBackground(backgroundColor);
                display.setForeground(textColor);
                for (Button b : buttons) {
                    b.setBackground(backgroundColor2);
                    b.setForeground(textColor);
                }
                buttons[10].setLabel("Dark");
            } else {
                setBackground(Color.white);
                display.setBackground(Color.white);
                display.setForeground(Color.black);
                for (Button b : buttons) {
                    b.setBackground(Color.white);
                    b.setForeground(Color.black);
                }
                buttons[10].setLabel("Light");
            }
        });

        // Add listener to all except theme button
        for (int i = 0; i < 20; i++) {
            if (i != 10) {
                buttons[i].addActionListener(this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "AC" -> {
                operator = null;
                first = 0;
                display.setText("0");
            }
            case "CE" -> display.setText("0");
            case "<-" -> {
                String text = display.getText();
                if (!text.isEmpty() && !text.equals("0")) {
                    text = text.substring(0, text.length() - 1);
                    if (text.isEmpty()) text = "0";
                    display.setText(text);
                }
            }
            case "." -> {
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
            }
            case "+", "-", "×", "÷" -> {
                try {
                    first = Double.parseDouble(display.getText());
                    operator = command;
                    display.setText("");
                } catch (NumberFormatException ex) {
                    display.setText("Error");
                }
            }
            case "=" -> {
                try {
                    double second = Double.parseDouble(display.getText());
                    double result = switch (operator) {
                        case "+" -> first + second;
                        case "-" -> first - second;
                        case "×" -> first * second;
                        case "÷" -> second == 0 ? Double.NaN : first / second;
                        default -> second;
                    };
                    display.setText(Double.isNaN(result) ? "Error" : String.valueOf(result));
                    first = result;
                } catch (Exception ex) {
                    display.setText("Error");
                }
            }
            default -> {
                if (display.getText().equals("0")) {
                    display.setText(command);
                } else {
                    display.setText(display.getText() + command);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
