import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interfaz extends JFrame {

    private PanelCaja panelCaja;

    private Digital digital;

    public Interfaz(){
        setTitle("Digital 57");
        setSize(500,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout( new BorderLayout());
        setLocationRelativeTo(null);

        digital = new Digital();

        panelCaja = new PanelCaja(this);
        add(panelCaja, BorderLayout.CENTER);
    }

    public void crearRegistro(String telefono, String email){
        String uid = UUID.randomUUID().toString();
        try {
            digital.aniadirRegistro(uid,telefono,email);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"El registro ha sido aniadido, aniada mas registros");
    }


    public static void main(String[] args){
        try{
            Interfaz ventana = new Interfaz();
            ventana.setVisible(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}

class PanelCaja extends JPanel implements ActionListener {
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JButton btnSend;
    private Interfaz interfaz;



    private static final String emailRegex = "^[A-Za-z0-9]+@[A-za-z]+.[A-Za-z]";


    public PanelCaja(Interfaz interfaz){
        this.interfaz = interfaz;
        setLayout(new GridLayout(3,1));

        JPanel fields = new JPanel();
        fields.setLayout(new GridLayout(2,2));

        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtEmail.setEditable(true);
        txtPhone.setEditable(true);

        btnSend = new JButton("Enviar");
        btnSend.setActionCommand("ENVIAR");
        btnSend.addActionListener(this);
        btnSend.setSize(500,100);

        fields.add(new JLabel("Telefono:"));
        fields.add(txtPhone);
        fields.add(new JLabel("Email:"));
        fields.add(txtEmail);

        JPanel empty = new JPanel();
        empty.setLayout(new GridLayout(2,2));

        add(fields);
        add(empty);
        add(btnSend);
    }

    private void limpiar(){
        txtPhone.setText("");
        txtEmail.setText("");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        boolean show = true;
        String phoneRegex = "^[0-9]{1,10}";
        String emailRegex = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9.]+$";
        if(command.equals("ENVIAR")){

            Pattern phonePattern = Pattern.compile(phoneRegex);
            Pattern emailPattern = Pattern.compile(emailRegex);

            if(!phonePattern.matcher(phone).matches()){
                show = false;
                JOptionPane.showMessageDialog(null, "The phone number should contain no more than 10 digits");
            }
            if(!emailPattern.matcher(email).matches()){
                show = false;
                JOptionPane.showMessageDialog(null, "Invalid email");
            }
            if(show){
                interfaz.crearRegistro(phone,email);
                limpiar();
            }


        }

    }
}
