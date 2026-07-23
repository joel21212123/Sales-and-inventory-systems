package bodega_myc_;

import bodega_myc_.ConexionMysql.ClaseConsultaUsuario;
import bodega_myc_.Ctrl.LoginController;
import bodega_myc_.Vista.InicioSesion;
import bodega_myc_.Vista.MENU_PRINCIPAL;

public class Bodega_myc_ {

    public static void main(String[] args) {
        // 1. Crear instancias principales
        InicioSesion frmLogin = new InicioSesion(null, true);
        MENU_PRINCIPAL frmMenu = new MENU_PRINCIPAL();
        ClaseConsultaUsuario consultaUsuario = new ClaseConsultaUsuario();
        
        // 2. Iniciar controlador de login
        LoginController loginController = new LoginController(consultaUsuario, frmLogin, frmMenu);
        loginController.iniciar();
    }
}