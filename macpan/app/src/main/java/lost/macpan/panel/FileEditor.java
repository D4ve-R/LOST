package lost.macpan.panel;

import lost.macpan.App;
import lost.macpan.utils.ResourceHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Font;
import java.util.Scanner;

public class FileEditor extends JPanel implements ActionListener, ResourceHandler {
    private final JFileChooser fc = new JFileChooser();
    private JFrame f;
    private final JButton openBtn = new JButton("Open File");
    private final JButton saveBtn = new JButton("Save");
    private final JButton backBtn = new JButton("zur\u00fcck");
    private final JTextArea textArea = new JTextArea(20, 30);
    private File file;
    private Scanner sc;

    public FileEditor(JFrame f) {
        this.f = f;
        initialize();
        setLayout(new BorderLayout());

        JPanel btns = new JPanel();
        btns.add(openBtn);
        btns.add(saveBtn);
        backBtn.addActionListener(this);
        openBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        openBtn.setFont(new Font("Arial", 0, 16));
        saveBtn.setFont(new Font("Arial", 0, 16));
        textArea.setFont(new Font(Font.MONOSPACED, 0, 14));

        add(btns, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        add(backBtn, BorderLayout.PAGE_END);
    }

    private void initialize() {
        try{
            file = getFileFromFS("level_1.txt");
            sc = new Scanner(file);
            fc.setCurrentDirectory(file);
        }catch(Exception e){
            e.printStackTrace();
        }

        while(sc.hasNextLine()){
            textArea.append(sc.nextLine());
            textArea.append("\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == openBtn){
            int returnVal = fc.showOpenDialog(FileEditor.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                textArea.setText("");
                try{
                    sc = new Scanner(file);
                }catch(FileNotFoundException ex){
                    ex.printStackTrace();
                }

                while(sc.hasNextLine()){
                    textArea.append(sc.nextLine());
                    textArea.append("\n");
                }
            }
        }
        else if(e.getSource() == saveBtn){
            String s = textArea.getText();

            try {
                FileWriter myWriter = new FileWriter(file);
                myWriter.write(s);
                myWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == backBtn){
            MainMenu mM = new MainMenu((App)f);
            f.setContentPane(mM);
            f.revalidate();
        }
    }
}