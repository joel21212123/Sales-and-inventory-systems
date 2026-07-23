package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaCliente;
import bodega_myc_.Modelo.ClaseCliente;
import bodega_myc_.Vista.Cliente;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClienteController implements ActionListener {
    private ClaseCliente mod;
    private ClaseConsultaCliente modConsulta;
    private Cliente frm;
    
    public ClienteController(ClaseCliente mod, ClaseConsultaCliente modConsulta, Cliente frm) {
        this.mod = mod;
        this.modConsulta = modConsulta;
        this.frm = frm;
        
        this.frm.btnNuevoClientes.addActionListener(this);
        this.frm.btnActualizarClientes.addActionListener(this);
        this.frm.btnEliminarClientes.addActionListener(this);
        this.frm.btnLeerClientes.addActionListener(this);
        
        cargarClientes();
    }
    
    public void iniciar() {
        frm.setTitle("Gestión de Clientes");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }
    
    private void limpiarCampos() {
        frm.txtCodigo.setText("");
        frm.txtNombreCliente.setText("");
        frm.txtDni.setText("");
        frm.txtTelefono.setText("");
        frm.txtFechaRegistro.setText("");
    }
    
    private void cargarClientes() {
        try {
            List<ClaseCliente> clientes = modConsulta.obtenerTodos();
            DefaultTableModel modelo = (DefaultTableModel) frm.tblClientes.getModel();
            modelo.setRowCount(0);
            
            for (ClaseCliente cliente : clientes) {
                Object[] fila = {
                    cliente.getId_clientes(),
                    cliente.getNombre_cliente(),
                    cliente.getDni(),
                    cliente.getTelefono()
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void seleccionarClienteTabla() {
        int filaSeleccionada = frm.tblClientes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) frm.tblClientes.getModel();
            frm.txtCodigo.setText(modelo.getValueAt(filaSeleccionada, 0).toString());
            frm.txtNombreCliente.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
            frm.txtDni.setText(modelo.getValueAt(filaSeleccionada, 2).toString());
            frm.txtTelefono.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón NUEVO: Guarda un nuevo cliente
        if (e.getSource() == frm.btnNuevoClientes) {
            if (frm.txtNombreCliente.getText().isEmpty() || frm.txtDni.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre y DNI son obligatorios");
                return;
            }
            
            mod.setNombre_cliente(frm.txtNombreCliente.getText());
            mod.setDni(frm.txtDni.getText());
            mod.setTelefono(frm.txtTelefono.getText());
            
            if (modConsulta.registrar(mod)) {
                JOptionPane.showMessageDialog(null, "Nuevo cliente registrado correctamente");
                limpiarCampos();
                cargarClientes(); // Actualizar tabla
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar nuevo cliente");
            }
        }
        
        // Botón ACTUALIZAR: Modifica un cliente existente
        else if (e.getSource() == frm.btnActualizarClientes) {
            if (frm.txtCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleccione un cliente para actualizar");
                return;
            }
            
            if (frm.txtNombreCliente.getText().isEmpty() || frm.txtDni.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre y DNI son obligatorios");
                return;
            }
            
            mod.setId_clientes(Integer.parseInt(frm.txtCodigo.getText()));
            mod.setNombre_cliente(frm.txtNombreCliente.getText());
            mod.setDni(frm.txtDni.getText());
            mod.setTelefono(frm.txtTelefono.getText());
            
            if (modConsulta.modificar(mod)) {
                JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente");
                cargarClientes(); // Actualizar tabla
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar cliente");
            }
        }
        
        // Botón ELIMINAR
        else if (e.getSource() == frm.btnEliminarClientes) {
            if (frm.txtCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleccione un cliente para eliminar");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(
                null, 
                "¿Está seguro de eliminar este cliente?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(frm.txtCodigo.getText());
                if (modConsulta.eliminar(id)) {
                    JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente");
                    limpiarCampos();
                    cargarClientes(); // Actualizar tabla
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar cliente");
                }
            }
        }
        
        // Botón LEER: Carga los datos del cliente seleccionado
        else if (e.getSource() == frm.btnLeerClientes) {
            seleccionarClienteTabla();
        }
    }
}