package View;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Conversor extends JFrame {
    private JLabel valor;
    private JLabel precioDivisa;
    private JComboBox <String> cambiarA;
    private JComboBox <String> cambiarDe;
    private JButton convertirButton;
    private JLabel resultadoLabel;
    private JTextField cantidad;
    private Data dataInstance;


    public  Conversor () {
        setTitle("Conversor de Divisas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        dataInstance = new Data();


        cambiarDe = new JComboBox<>(new String[]{"USD", "EUR", "GBP", "JPY", "AUD", "CAD"}); // Divisas disponibles
        cambiarA = new JComboBox<>(new String[]{"PEN", "EUR", "GBP", "JPY", "AUD", "CAD"}); // Divisas disponibles
        cantidad = new JTextField(10); //
        convertirButton = new JButton("Convertir");
        resultadoLabel = new JLabel();

        convertirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = cambiarDe.getSelectedItem().toString();
                String to = cambiarA.getSelectedItem().toString();
                String amountText = cantidad.getText(); // Get the text from JTextField

                try {
                    double amount = Double.parseDouble(amountText); // Convert the text to a double value
                    JSONObject jsonObject = dataInstance.performApiRequest(from, to, amount);
                    double rate = jsonObject.getDouble("result");
                    resultadoLabel.setText(amount + from + " = " + rate + " " + to);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(cambiarDe);
        add(cambiarA);
        add(cantidad);
        add(convertirButton);
        add(resultadoLabel); // Agrega el JLabel al frame


        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Conversor();
            }
        });
    }
}
