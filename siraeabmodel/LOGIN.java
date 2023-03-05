package siraeabmodel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import siraeabmodel.clasesBase.Usuario;

public class LOGIN extends JFrame implements ActionListener {

    JPanel panel;
    JLabel user_label, password_label, message;
    JTextField userName_text;
    JPasswordField password_text;
    JButton submit, cancel;

    LOGIN() {
        
       
        user_label = new JLabel();
        user_label.setText("Usuario :");
        userName_text = new JTextField();
        
      

        password_label = new JLabel();
        password_label.setText("contraseña :");
        password_text = new JPasswordField();

    

        submit = new JButton("Entrar");

        panel = new JPanel(new GridLayout(3, 1));

        panel.add(user_label);
        panel.add(userName_text);
        panel.add(password_label);
        panel.add(password_text);

        message = new JLabel();
        panel.add(message);
        panel.add(submit);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
  
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Ingrese!");
        setSize(300, 100);
        setVisible(true);

    }

    public static void main(String[] args) {
        new LOGIN();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String userName = userName_text.getText();
        String password = password_text.getText();
        Usuario usr;        
        try{
        usr= new Usuario(userName, password);
        if (usr.getExiste()) {
            Principal prin = new Principal(usr.getId());
            
            this.dispose();
            prin.setVisible(true);
        } else {
            message.setText(" Usuario Invalido");
            JOptionPane.showConfirmDialog(this, "Usuario incorrecto", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }}
        catch(SQLException e){
            JOptionPane.showConfirmDialog(this, e, "Hubo un problema con la vase de datos", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }

    }

}