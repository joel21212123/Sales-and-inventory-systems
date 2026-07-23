package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaUsuario;
import bodega_myc_.Modelo.ClaseUsuario;
import bodega_myc_.Vista.REGISTRO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class RegistroController implements ActionListener {
    private ClaseConsultaUsuario modConsulta;
    private REGISTRO frmRegistro;
    private ClaseUsuario modUsuario;
    
    public RegistroController(ClaseConsultaUsuario modConsulta, REGISTRO frmRegistro, ClaseUsuario modUsuario) {
        this.modConsulta = modConsulta;
        this.frmRegistro = frmRegistro;
        this.modUsuario = modUsuario;
        
        // Asignar listeners
        this.frmRegistro.btnRegistrarUser.addActionListener(this);
        this.frmRegistro.btnCancelarRegistrar.addActionListener(this);
    }
    
    public void iniciar() {
        frmRegistro.setLocationRelativeTo(null);
        frmRegistro.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmRegistro.btnRegistrarUser) {
            registrarUsuario();
        } 
        else if (e.getSource() == frmRegistro.btnCancelarRegistrar) {
            frmRegistro.dispose();
        }
    }
    
    private void registrarUsuario() {
        String nombre = frmRegistro.txusername.getText().trim();
        String username = frmRegistro.txusername.getText().trim();
        String password = new String(frmRegistro.jPasswordField1.getPassword());
        
        if (nombre.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frmRegistro, "Todos los campos son obligatorios");
            return;
        }
        
        modUsuario.setNombre_usuario(nombre);
        modUsuario.setUsername(username);
        modUsuario.setPassword(password);
        
        if (modConsulta.registrar(modUsuario)) {
            JOptionPane.showMessageDialog(frmRegistro, "Usuario registrado exitosamente");
            frmRegistro.dispose();
        } else {
            JOptionPane.showMessageDialog(frmRegistro, "Error al registrar usuario");
        }
    }
}