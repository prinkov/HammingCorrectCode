package xyz.prinkov.hamming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by akaroot on 22.11.16.
 */
public class Frame extends JFrame {
    JPanel pane;
    public Frame(String s) throws HeadlessException {
        super(s);
        pane = new JPanel();
        final Hamming ham = new Hamming();
        setContentPane(pane);
        setSize(480, 640);
        final JTextArea text = new JTextArea(9, 34);
        final JTextField hamCode = new JTextField(34);
        final JTextArea binaryText = new JTextArea(9, 34);
        Font font = new Font("Courier", Font.BOLD, 22);
        text.setFont(font);
        JScrollPane spText = new JScrollPane(text);
        JScrollPane spBinaryText = new JScrollPane(binaryText);
        binaryText.setLineWrap(true);
        text.setLineWrap(true);
        hamCode.setFont(font);
        binaryText.setFont(font);
        JButton btnCode = new JButton("<html><center>Добавить код Хемминга</center></html>");
        btnCode.setSize(new Dimension(100, 100));
        btnCode.setPreferredSize(new Dimension(150, 70));
        JButton btnCorrect = new JButton("<html><center>Проверить или исправить сообщение</center></html>");
        btnCorrect.setPreferredSize(new Dimension(150, 70));
        JButton btnErr = new JButton("<html><center>Сделать случайную ошибку</center></html>");
        btnErr.setPreferredSize(new Dimension(150, 70));
        JPanel btnPane = new JPanel();
        btnPane.add(btnCode);
        setResizable(false);
        btnPane.add(btnCorrect);
        btnPane.add(btnErr);
        pane.add(spText);
        pane.add(hamCode);
        pane.add(spBinaryText);
        pane.add(btnPane);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        text.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent keyEvent) {

            }

            public void keyPressed(KeyEvent keyEvent) {

            }

            public void keyReleased(KeyEvent keyEvent) {
                binaryText.setText(toBinaryString(text.getText().toString()));
            }
        });

        btnCode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                binaryText.setText(ham.code(binaryText.getText().toString()));
                hamCode.setText(ham.findCode(binaryText.getText()));
            }
        });

        btnErr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String t = ham.randomError(binaryText.getText());
                    binaryText.setText(t);
                    binaryText.setSelectedTextColor(Color.RED);
                    binaryText.requestFocusInWindow();
                    binaryText.select(ham.getLastError(), ham.getLastError() + 1);
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(null, "Проверьте введенные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        btnCorrect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String text = binaryText.getText();
                    if(ham.correctString(text).equals(text))
                        JOptionPane.showMessageDialog(null, "Ошибок не обнаружено!", "Ура", JOptionPane.INFORMATION_MESSAGE);
                    else {
                        binaryText.setText(ham.correctString(text));
                        binaryText.setSelectedTextColor(Color.GREEN);
                        binaryText.requestFocusInWindow();
                        binaryText.select(ham.getLastCorrect(), ham.getLastCorrect() + 1);
                        JOptionPane.showMessageDialog(null, "Ошибка обнаружена и исправлена!", "Ура", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(null, "Проверьте введенные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }


    private String toBinaryString(String str) {
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < str.length(); i++)
            buf.append(Integer.toBinaryString(((int) str.charAt(i))));
        return buf.toString();
    }
}
