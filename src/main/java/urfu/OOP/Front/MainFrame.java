package urfu.OOP.Front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.jdatepicker.*;

import urfu.OOP.BackEnd.AllNotesTableRecord;
import urfu.OOP.BackEnd.MySqlNotesHandler;
import urfu.OOP.BackEnd.SQLRecord;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JFrame jFrame1;
    private JPanel tableHead;
    private JDatePicker datePicker;
    private JPanel panel;
    private MySqlNotesHandler mySqlNotesHandler;

    public MainFrame() {
        jFrame1 = new JFrame("Notes");
        makeTableHead();
        makeDatePicker();
        makeAddNoteButton();
        panel = new JPanel();
        mySqlNotesHandler = new MySqlNotesHandler();

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        jFrame1.setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);


        jFrame1.add(datePicker, BorderLayout.NORTH);

        updateNotesByDay();
        jFrame1.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);


        jFrame1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    exit();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        jFrame1.setVisible(true);
    }

    private void makeAddNoteButton() {
        JButton addNote = new JButton("Добавить заметку");

        addNote.setBackground(new Color(255, 207, 64));
        jFrame1.add(addNote, BorderLayout.SOUTH);
        addNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newLabelNoteToPanel(panel);
                jFrame1.revalidate();
            }
        });
    }

    private void makeDatePicker() {
        datePicker = new JDatePicker();
        datePicker.setBackground(new Color(255, 207, 64));
        JButton nextDate = new JButton(">");
        nextDate.setBackground(new Color(255, 207, 64));
        nextDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePicker.getModel().addDay(1);
                updateNotesByDay();
            }
        });

        JButton prevDate = new JButton("<");
        prevDate.setBackground(new Color(255, 207, 64));
        prevDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datePicker.getModel().addDay(-1);
                updateNotesByDay();
            }
        });
        datePicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateNotesByDay();
            }
        });

        jFrame1.add(nextDate, BorderLayout.EAST);
        jFrame1.add(prevDate, BorderLayout.WEST);
    }

    private void makeTableHead() {
        tableHead = new JPanel();
        tableHead.setLayout(new GridLayout(1, 4));
        tableHead.setBackground(new Color(255, 207, 64));

        tableHead.add(addLabelToTableHead("Запись", new Font("Serif", Font.BOLD, 25), Color.BLACK));
        tableHead.add(addLabelToTableHead("Прогресс", new Font("Serif", Font.BOLD, 25), Color.BLACK));
        tableHead.add(addLabelToTableHead("Изменить", new Font("Serif", Font.BOLD, 25), Color.BLACK));
        tableHead.add(addLabelToTableHead("Удалить", new Font("Serif", Font.BOLD, 25), Color.BLACK));

    }

    private void exit() throws IOException {
        Object[] options = {"Да", "Нет"};
        int s = JOptionPane.showOptionDialog(null, "Вы уверены что хотите выйти?", "Уведомление",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (s == 0) {
            System.out.println("Closed");
            System.exit(0);
        }
    }

    public JLabel addLabelToTableHead(String textOfHead, Font font, Color borderColor) {
        JLabel newLabel = new JLabel(textOfHead);
        newLabel.setFont(font);
        newLabel.setBorder(BorderFactory.createLineBorder(borderColor));
        return newLabel;
    }

    public java.sql.Date getDate() {
        java.sql.Date sqlPackageDate = new java.sql.Date(datePicker.getModel().getYear() - 1900,
                datePicker.getModel().getMonth(), datePicker.getModel().getDay());
        return sqlPackageDate;
    }

    public void newLabelNoteToPanel(JPanel panel) {
        String name = "";
        int progress = 0;
        MyDialog myDialog = new MyDialog(jFrame1, "Напишите заметку:", "На сколько процентов выполнено?");
        myDialog.setVisible(true);

        if (!myDialog.getHaveException() && !Objects.equals(myDialog.getInputString(), "")) {
            name = myDialog.getInputString();
            progress = myDialog.getInputInt();
            String time = "16:10";
            Note note = new Note(name, progress, getDate(), time);
            mySqlNotesHandler.insertRecord(new AllNotesTableRecord(getDate(), name, progress, "16:21"));
            makeNoteVisible(panel, note);
        }
    }
    private JPanel configureTableString(){
        JPanel tableString = new JPanel();
        tableString.setLayout(new GridLayout(1, 10));
        tableString.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tableString.setLayout(new GridLayout(1, 10));
        tableString.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return tableString;
    }
    private JTextField configureTextField(Note note){
        JTextField textField = new JTextField();
        textField.setText(note.getName());
        textField.setEditable(false);
        textField.setBackground(new Color(244, 169, 0));
        textField.setFont(new Font("Serif", Font.BOLD, 20));
        return textField;
    }
    private void rewriteNote(JTextField textField, JProgressBar progressBar){
        MyDialog myDialog = new MyDialog(jFrame1, "Исправить заметку:", "На сколько процентов выполнено?");
        myDialog.setVisible(true);
        System.out.println("Сейчас будем изменять запись!");
        String oldRecord = textField.getText();
        System.out.println("Старая запись: " + oldRecord);
        int oldPercents = progressBar.getValue();

        if (!myDialog.getHaveException() && !Objects.equals(myDialog.getInputString(), "")) {
            textField.setText(myDialog.getInputString());
            progressBar.setValue(myDialog.getInputInt());
        }
        String newRecord = textField.getText();
        int newPercents = progressBar.getValue();
        System.out.println("Новая запись: " + newRecord);
        System.out.println("Новые проценты: " + newPercents);
        System.out.println("Изменили запись!");

        // Update sql таблицы сюда
        updateNoteByDateAndRecord(oldRecord, newRecord, oldPercents, newPercents);
        updateNotesByDay();
    }
    public void makeNoteVisible(JPanel panel, Note note) {
        JPanel tableString = configureTableString();
        JTextField textField = configureTextField(note);
        JScrollPane jScrollPane = new JScrollPane(textField);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        tableString.add(jScrollPane);
        mySqlNotesHandler.readRecords(getDate().toString());
        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(note.getProgress());
        progressBar.setBackground(new Color(244, 169, 0));
        tableString.add(progressBar);

        JButton buttonRewrite = new JButton("Rewrite");
        buttonRewrite.setBackground(new Color(244, 169, 0));

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.setBackground(new Color(244, 169, 0));
        buttonRewrite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rewriteNote(textField, progressBar);
            }
        });
        tableString.add(buttonRewrite);
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mySqlNotesHandler.deleteRecord(new AllNotesTableRecord(getDate(), note.getName(), note.getProgress(), note.getTime()));
                updateNotesByDay();
            }
        });
        tableString.add(buttonDelete);
        panel.add(tableString);
    }

    public void updateNoteByDateAndRecord(String oldRecord, String newRecord, int oldPercents, int newPercents) {
        java.sql.Date today = getDate();
        mySqlNotesHandler.updateRecord(new AllNotesTableRecord(today, oldRecord, oldPercents, null), newRecord, newPercents);
    }

    public void updateNotesByDay() {
        if (panel != null) {
            panel.removeAll();
        }
        jFrame1.repaint();
        jFrame1.revalidate();
        panel.setBackground(new Color(255, 219, 139));
        panel.setLayout(new GridLayout(10, 1));
        panel.add(tableHead);
        java.sql.Date today = getDate();
        System.out.println(today);

        List<SQLRecord> list = mySqlNotesHandler.getRecords(today.toString());
        for (SQLRecord record : list) {
            AllNotesTableRecord recTmp = ((AllNotesTableRecord) record);
            makeNoteVisible(panel, new Note(recTmp.Record(), (int) recTmp.Percent(), getDate(), recTmp.Time()));
        }
        panel.revalidate();
        panel.repaint();
        jFrame1.add(panel);
        jFrame1.revalidate();
        jFrame1.repaint();
    }
}