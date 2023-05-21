package urfu.OOP.Front;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdatepicker.*;


public class Interface {
    public static void main(String[] args) throws IOException {
        //Java Sql timeStand
        JDatePicker i = new JDatePicker();
        System.out.println(i.getModel().getYear());

        i.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(i.getModel().getMonth());
                java.sql.Date sqlPackageDate
                        = new java.sql.Date(i.getModel().getYear()-1900, i.getModel().getMonth(), i.getModel().getDay());
                System.out.println(sqlPackageDate);
            }
        });
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame jFrame1 = new JFrame("Notes");

        jFrame1.setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        JButton b = new JButton("Добавить заметку");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255,219,139));
        JPanel tableHead = new JPanel();
        tableHead.setLayout(new GridLayout(1, 4));

        JLabel record = new JLabel("Запись");
        record.setFont(new Font("Serif", Font.BOLD, 25));
        record.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableHead.add(record);
        tableHead.setBackground(new Color(255,207,64));

        JLabel progress = new JLabel("Прогресс");
        progress.setFont(new Font("Serif", Font.BOLD, 25));
        progress.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableHead.add(progress);

        JLabel change = new JLabel("Изменить");
        change.setFont(new Font("Serif", Font.BOLD, 25));
        change.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableHead.add(change);

        JLabel delete = new JLabel("Удалить");
        delete.setFont(new Font("Serif", Font.BOLD, 25));
        delete.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableHead.add(delete);

        panel.setLayout(new GridLayout(10, 1));

        i.setBackground(new Color(255,207,64));
        b.setBackground(new Color(255,207,64));
        jFrame1.add(i, BorderLayout.NORTH);
        jFrame1.add(b, BorderLayout.SOUTH);
        JButton nextDate = new JButton(">");
        nextDate.setBackground(new Color(255,207,64));
        nextDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                i.getModel().addDay(1);
            }
        });
        JButton prevDate = new JButton("<");
        prevDate.setBackground(new Color(255,207,64));
        prevDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                i.getModel().addDay(-1);
            }
        });
        jFrame1.add(nextDate, BorderLayout.EAST);
        jFrame1.add(prevDate, BorderLayout.WEST);
        panel.add(tableHead);
        jFrame1.add(panel);
        jFrame1.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                newLabelToPanel(panel, jFrame1);
                jFrame1.revalidate();
            }
        });
//        date.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("date change");
//            }
//        });


        jFrame1.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {

                try {
                    exit();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jFrame1.setVisible(true);

    }
    private static void exit() throws IOException {
        Object[] options = {"Да",
                "Нет"};
        int s = JOptionPane.showOptionDialog(null, "Вы уверены что хотите выйти?", "Уведомление", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (s == 0) {
            System.out.println("Closed");
//            File file = new File(System.getenv("USERPROFILE") + "\\setting.txt");
//            if (file.createNewFile()) {
//                System.out.println("File is created!");
//            } else {
//                System.out.println("File already exists.");
//            }
//            FileWriter writer = new FileWriter(file);
//            writer.write("1\n");
//            writer.write("Test data:\n");
//            JInternalFrame[] arr = desktopPane.getAllFrames();
//            for (JInternalFrame var : arr) {
//                writer.write("\"" + var.getTitle() + "\" ");
//                if (var.isIcon())
//                    writer.write("\"Не Свёрнуто\" ");
//                else
//                    writer.write("\"Свёрнуто\" ");
//                Rectangle a = var.getBounds();
//                writer.write("x=" + a.x + " y=" + a.y + " width=" + a.width + " ");
//                writer.write("height=" + a.height + "\n");
//            }
//            writer.close();
            System.exit(0);
        }
    }
    //Добавить проценты выполнения , добавить удаление по 100% , смену дней, фиксация дня из базы, стрелочки побокам
    public static void newLabelToPanel(JPanel panel, JFrame jFrame1){
        JPanel tableString = new JPanel();
        tableString.setLayout(new GridLayout(1, 10));
        tableString.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        String name;
        name = JOptionPane.showInputDialog("Напишите свою заметку:");
        JTextField textField = new JTextField();
        textField.setText(name);
        textField.setEditable(false);
        textField.setBackground(new Color(244,169,0));
        textField.setFont(new Font("Serif", Font.BOLD, 20));
        JScrollPane jScrollPane = new JScrollPane(textField);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        tableString.add(jScrollPane);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBackground(new Color(244,169,0));
        tableString.add(progressBar);

        JButton buttonRewrite = new JButton("Rewrite");
        buttonRewrite.setBackground(new Color(244,169,0));
        buttonRewrite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyDialog myDialog = new MyDialog(jFrame1, "Исправить заметку:", "На сколько процентов выполнено?");
                myDialog.setVisible(true);
                if(!myDialog.getHaveException()){
                    textField.setText(myDialog.getInputString());
                    progressBar.setValue(myDialog.getInputInt());
                }
                panel.revalidate();
                panel.repaint();
            }
        });
        tableString.add(buttonRewrite);

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setBackground(new Color(244,169,0));
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(tableString);
                panel.revalidate();
                panel.repaint();
            }
        });
        tableString.add(buttonDelete);


        panel.add(tableString);
    }
}
