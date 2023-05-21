package urfu.OOP.Front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyDialog extends JDialog {
    private JTextField textField1;
    private JTextField textField2;
    private  Boolean haveException = false;
    private String inputString = "";
    private int inputInt;

    public MyDialog(Frame parent, String first, String second) {
        super(parent, "Диалоговое окно", true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel label1 = new JLabel(first);
        textField1 = new JTextField();

        JLabel label2 = new JLabel(second);
        textField2 = new JTextField();

        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputString = textField1.getText();
                try{
                    inputInt = Integer.parseInt(textField2.getText());

                } catch (NumberFormatException ex) {
                    haveException = true;
                    JOptionPane.showMessageDialog(parent,"Thats WAS NOT NUMBER!");
                }
                dispose();
            }
        });

        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(new JLabel()); // Пустая метка для выравнивания кнопки
        panel.add(button);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public String getInputString() {
        return inputString;
    }

    public int getInputInt() {
        return inputInt;
    }

    public Boolean getHaveException() {
        return haveException;
    }
}