package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaProveedores;
import bodega_myc_.Modelo.ClaseProveedor;
import bodega_myc_.Vista.PROVEEDORES;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProveedorController implements ActionListener {
    private ClaseProveedor mod;
    private ClaseConsultaProveedores modConsulta;
    private PROVEEDORES frm;
    
    public ProveedorController(ClaseProveedor mod, ClaseConsultaProveedores modConsulta, PROVEEDORES frm) {
        this.mod = mod;
        this.modConsulta = modConsulta;
        this.frm = frm;
        
        // Asignar listeners a los botones
        this.frm.btnNuevoProveedor.addActionListener(this);
        this.frm.btnLeerProveedor.addActionListener(this);
        this.frm.btnModificarProveedor.addActionListener(this);
        this.frm.btnSalirProveedor.addActionListener(this);
        
        // Cargar proveedores al iniciar
        cargarProveedores();
    }
    
    public void iniciar() {
        frm.setTitle("Gestión de Proveedores");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }
    
    private void limpiarCampos() {
        frm.txtIdProvedor.setText("");
        frm.txtRucProveedor.setText("");
        frm.txtNombreProveedor.setText("");
        frm.txtTelefonoProveedor.setText("");
        frm.txtEmailProveedor.setText("");
    }
    
    private void cargarProveedores() {
        try {
            List<ClaseProveedor> proveedores = modConsulta.obtenerTodos();
            DefaultTableModel modelo = (DefaultTableModel) frm.tblProveedor.getModel();
            modelo.setRowCount(0); // Limpiar tabla
            
            for (ClaseProveedor proveedor : proveedores) {
                modelo.addRow(new Object[]{
                    proveedor.getId_proveedores(),
                    proveedor.getRuc_proveedores(),
                    proveedor.getNombre_proveedores(),
                    proveedor.getTelefono_proveedores(),
                    proveedor.getEmail_proveedores()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar proveedores: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void seleccionarProveedorDeTabla() {
        int filaSeleccionada = frm.tblProveedor.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) frm.tblProveedor.getModel();
            
            frm.txtIdProvedor.setText(modelo.getValueAt(filaSeleccionada, 0).toString());
            frm.txtRucProveedor.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
            frm.txtNombreProveedor.setText(modelo.getValueAt(filaSeleccionada, 2).toString());
            frm.txtTelefonoProveedor.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
            frm.txtEmailProveedor.setText(modelo.getValueAt(filaSeleccionada, 4).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor de la tabla");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón Nuevo: Guarda un nuevo proveedor
        if (e.getSource() == frm.btnNuevoProveedor) {
            guardarNuevoProveedor();
        }
        
        // Botón Leer: Selecciona un proveedor de la tabla
        else if (e.getSource() == frm.btnLeerProveedor) {
            seleccionarProveedorDeTabla();
        }
        
        // Botón Modificar: Actualiza un proveedor existente
        else if (e.getSource() == frm.btnModificarProveedor) {
            actualizarProveedor();
        }
        
        // Botón Salir (Eliminar): Elimina un proveedor
        else if (e.getSource() == frm.btnSalirProveedor) {
            eliminarProveedor();
        }
    }
    
    private void guardarNuevoProveedor() {
        // Validar campos obligatorios
        if (frm.txtRucProveedor.getText().isEmpty() || 
            frm.txtNombreProveedor.getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "RUC y Nombre son obligatorios");
            return;
        }
        
        try {
            // Configurar modelo con datos de la vista
            mod.setRuc_proveedores(frm.txtRucProveedor.getText());
            mod.setNombre_proveedores(frm.txtNombreProveedor.getText());
            mod.setTelefono_proveedores(frm.txtTelefonoProveedor.getText());
            mod.setEmail_proveedores(frm.txtEmailProveedor.getText());
            
            // Registrar nuevo proveedor
            if (modConsulta.registrar(mod)) {
                JOptionPane.showMessageDialog(null, "Proveedor registrado correctamente");
                limpiarCampos();
                cargarProveedores(); // Actualizar tabla
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar proveedor");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void actualizarProveedor() {
        if (frm.txtIdProvedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor para actualizar");
            return;
        }
        
        // Validar campos obligatorios
        if (frm.txtRucProveedor.getText().isEmpty() || 
            frm.txtNombreProveedor.getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "RUC y Nombre son obligatorios");
            return;
        }
        
        try {
            // Configurar modelo con datos de la vista
            mod.setId_proveedores(Integer.parseInt(frm.txtIdProvedor.getText()));
            mod.setRuc_proveedores(frm.txtRucProveedor.getText());
            mod.setNombre_proveedores(frm.txtNombreProveedor.getText());
            mod.setTelefono_proveedores(frm.txtTelefonoProveedor.getText());
            mod.setEmail_proveedores(frm.txtEmailProveedor.getText());
            
            // Actualizar proveedor
            if (modConsulta.modificar(mod)) {
                JOptionPane.showMessageDialog(null, "Proveedor actualizado correctamente");
                cargarProveedores(); // Actualizar tabla
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar proveedor");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID de proveedor inválido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void eliminarProveedor() {
        if (frm.txtRucProveedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor para eliminar");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            null, 
            "¿Está seguro de eliminar este proveedor?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String ruc = frm.txtRucProveedor.getText();
                if (modConsulta.eliminar(ruc)) {
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado correctamente");
                    limpiarCampos();
                    cargarProveedores(); // Actualizar tabla
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar proveedor");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}