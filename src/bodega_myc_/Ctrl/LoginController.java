package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaUsuario;
import bodega_myc_.Modelo.ClaseUsuario;
import bodega_myc_.Vista.InicioSesion;
import bodega_myc_.Vista.MENU_PRINCIPAL;
import bodega_myc_.Vista.REGISTRO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginController implements ActionListener {
    private final ClaseConsultaUsuario modConsulta;
    private final InicioSesion frmLogin;
    private final MENU_PRINCIPAL frmMenu;
    private int usuarioId;

    public LoginController(ClaseConsultaUsuario modConsulta, InicioSesion frmLogin, MENU_PRINCIPAL frmMenu) {
        this.modConsulta = modConsulta;
        this.frmLogin = frmLogin;
        this.frmMenu = frmMenu;
        
        this.frmLogin.btnLogin.addActionListener(this);
        this.frmLogin.btnRegistrar.addActionListener(this);
        this.frmLogin.jButton2.addActionListener(this);
    }
    
    public void iniciar() {
        frmLogin.setLocationRelativeTo(null);
        frmLogin.setVisible(true);
    }
    
    public int getUsuarioId() {
        return usuarioId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmLogin.btnLogin) {
            iniciarSesion();
        } 
        else if (e.getSource() == frmLogin.btnRegistrar) {
            abrirRegistro();
        }
        else if (e.getSource() == frmLogin.jButton2) {
            System.exit(0);
        }
    }
    
    private void iniciarSesion() {
        String username = frmLogin.txtusername.getText().trim();
        String password = new String(frmLogin.txtpassword.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frmLogin, "Usuario y contraseña son obligatorios");
            return;
        }
        
        if (modConsulta.autenticar(username, password)) {
            ClaseUsuario usuario = modConsulta.obtenerPorUsername(username);
            if (usuario != null) {
                this.usuarioId = usuario.getId_usuario();
                frmMenu.setVisible(true);
                frmLogin.dispose();
                
                // Iniciar controlador del menú principal
                MenuPrincipalController menuController = new MenuPrincipalController(frmMenu, usuarioId);
                menuController.iniciar();
            }
        } else {
            JOptionPane.showMessageDialog(frmLogin, "Credenciales incorrectas");
        }
    }
    
    private void abrirRegistro() {

     // Crea un frame temporal como padre
    javax.swing.JFrame parentFrame = new javax.swing.JFrame();
    REGISTRO frmRegistro = new REGISTRO(parentFrame, true);
    ClaseUsuario modUsuario = new ClaseUsuario();
    RegistroController registroController = new RegistroController(modConsulta, frmRegistro, modUsuario);
    registroController.iniciar();
    }
}