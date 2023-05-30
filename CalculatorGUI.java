import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI {
    private JTextField displayField;
    private double result;
    private String operator;
    private boolean startNewInput;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculatorGUI().createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.decode("#333333")); // Set background color

        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(Color.WHITE); // Set display background color
        displayField.setOpaque(true); // Enable background color

        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBackground(Color.decode("#444444")); // Set button panel background color

        frame.add(displayField, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "C", "+", "="
        };

        for (String label : buttonLabels) {
            JButton button = createButton(label);
            panel.add(button);
        }

        return panel;
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setFocusPainted(false); // Remove button focus border
        button.setBackground(Color.decode("#888888")); // Set button background color
        button.setForeground(Color.WHITE); // Set button text color

        if (label.matches("[0-9.]")) {
            button.addActionListener(new DigitButtonListener());
        } else if (label.matches("[/\\*\\-+]")) {
            button.addActionListener(new OperatorButtonListener());
        } else if (label.equals("=")) {
            button.addActionListener(new EqualButtonListener());
            button.setBackground(Color.decode("#FF6600")); // Set equal button background color
        } else if (label.equals("C")) {
            button.addActionListener(new ClearButtonListener());
            button.setBackground(Color.decode("#FF3333")); // Set clear button background color
        }

        return button;
    }

    private class DigitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String digit = e.getActionCommand();

            if (startNewInput) {
                displayField.setText(digit);
                startNewInput = false;
            } else {
                displayField.setText(displayField.getText() + digit);
            }
        }
    }

    private class OperatorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String op = e.getActionCommand();

            if (operator != null) {
                calculate();
            }

            result = Double.parseDouble(displayField.getText());
            operator = op;
            startNewInput = true;
        }
    }

    private class EqualButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculate();
            operator = null;
            startNewInput = true;
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayField.setText("");
            result = 0;
            operator = null;
            startNewInput = false;
        }
    }

    private void calculate() {
        double secondNumber = Double.parseDouble(displayField.getText());

        switch (operator) {
            case "/":
                result /= secondNumber;
                break;
            case "*":
                result *= secondNumber;
                break;
            case "-":
                result -= secondNumber;
                break;
            case "+":
                result += secondNumber;
                break;
        }

        displayField.setText(String.valueOf(result));
    }
}
