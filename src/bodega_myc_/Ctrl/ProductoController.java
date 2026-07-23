package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaProducto;
import bodega_myc_.Modelo.ClaseProducto;
import bodega_myc_.Vista.PRODUCTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductoController implements ActionListener {
    private ClaseProducto mod;
    private ClaseConsultaProducto modConsulta;
    private PRODUCTO frm;
    
    public ProductoController(ClaseProducto mod, ClaseConsultaProducto modConsulta, PRODUCTO frm) {
        this.mod = mod;
        this.modConsulta = modConsulta;
        this.frm = frm;
        
        // Asignar listeners a los botones
        this.frm.btnNuevoProducto.addActionListener(this);
        this.frm.btnLeerProducto.addActionListener(this);
        this.frm.btnActualizarProducto.addActionListener(this);
        this.frm.btnEliminarProducto.addActionListener(this);
        
        // Cargar productos al iniciar
        cargarProductos();
    }
    
    public void iniciar() {
        frm.setTitle("Gestión de Productos");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }
    
    private void limpiarCampos() {
        frm.txtCodigoProducto.setText("");
        frm.txtNombreProducto.setText("");
        frm.txtDescripcionProducto.setText("");
        frm.txtPrecioAdquisicionProducto.setText("");
        frm.txtPrecioVentaProducto.setText("");
        frm.txtStockProducto.setText("");
        frm.txtCategoriaProducto.setText("");
        frm.txtFechaProducto.setText("");
        frm.txtFechaActualizacionProducto.setText("");
    }
    
    private void cargarProductos() {
        try {
            List<ClaseProducto> productos = modConsulta.obtenerTodos();
            DefaultTableModel modelo = (DefaultTableModel) frm.tblProducto.getModel();
            modelo.setRowCount(0); // Limpiar tabla
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            
            for (ClaseProducto producto : productos) {
                modelo.addRow(new Object[]{
                    producto.getIdproductos(),
                    producto.getNombre_Productos(),
                    producto.getDescripcion(),
                    producto.getPrecio_adquisicion(),
                    producto.getPrecio_venta(),
                    producto.getStock(),
                    producto.getCategoria(),
                    producto.getFecha_creacion() != null ? sdf.format(producto.getFecha_creacion()) : "",
                    producto.getFecha_actualizacion() != null ? sdf.format(producto.getFecha_actualizacion()) : ""
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void seleccionarProductoDeTabla() {
        int filaSeleccionada = frm.tblProducto.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) frm.tblProducto.getModel();
            
            frm.txtCodigoProducto.setText(modelo.getValueAt(filaSeleccionada, 0).toString());
            frm.txtNombreProducto.setText(modelo.getValueAt(filaSeleccionada, 1).toString());
            frm.txtDescripcionProducto.setText(modelo.getValueAt(filaSeleccionada, 2).toString());
            frm.txtPrecioAdquisicionProducto.setText(modelo.getValueAt(filaSeleccionada, 3).toString());
            frm.txtPrecioVentaProducto.setText(modelo.getValueAt(filaSeleccionada, 4).toString());
            frm.txtStockProducto.setText(modelo.getValueAt(filaSeleccionada, 5).toString());
            frm.txtCategoriaProducto.setText(modelo.getValueAt(filaSeleccionada, 6).toString());
            frm.txtFechaProducto.setText(modelo.getValueAt(filaSeleccionada, 7).toString());
            frm.txtFechaActualizacionProducto.setText(modelo.getValueAt(filaSeleccionada, 8).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de la tabla");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón Nuevo: Guarda un nuevo producto
        if (e.getSource() == frm.btnNuevoProducto) {
            guardarNuevoProducto();
        }
        
        // Botón Leer: Selecciona un producto de la tabla
        else if (e.getSource() == frm.btnLeerProducto) {
            seleccionarProductoDeTabla();
        }
        
        // Botón Actualizar: Modifica un producto existente
        else if (e.getSource() == frm.btnActualizarProducto) {
            actualizarProducto();
        }
        
        // Botón Eliminar: Elimina un producto
        else if (e.getSource() == frm.btnEliminarProducto) {
            eliminarProducto();
        }
    }
    
    private void guardarNuevoProducto() {
        // Validar campos obligatorios
        if (frm.txtNombreProducto.getText().isEmpty() || 
            frm.txtPrecioAdquisicionProducto.getText().isEmpty() ||
            frm.txtPrecioVentaProducto.getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "Nombre, Precio Adquisición y Precio Venta son obligatorios");
            return;
        }
        
        try {
            // Configurar modelo con datos de la vista
            mod.setNombre_Productos(frm.txtNombreProducto.getText());
            mod.setDescripcion(frm.txtDescripcionProducto.getText());
            mod.setPrecio_adquisicion(Double.parseDouble(frm.txtPrecioAdquisicionProducto.getText()));
            mod.setPrecio_venta(Double.parseDouble(frm.txtPrecioVentaProducto.getText()));
            mod.setStock(frm.txtStockProducto.getText().isEmpty() ? 0 : Integer.parseInt(frm.txtStockProducto.getText()));
            mod.setStock_minimo(5); // Valor por defecto
            mod.setCategoria(frm.txtCategoriaProducto.getText());
            
            // Registrar nuevo producto
            if (modConsulta.registrar(mod)) {
                JOptionPane.showMessageDialog(null, "Producto registrado correctamente");
                limpiarCampos();
                cargarProductos(); // Actualizar tabla
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar producto");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Formato numérico inválido en precios o stock", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void actualizarProducto() {
        if (frm.txtCodigoProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para actualizar");
            return;
        }
        
        // Validar campos obligatorios
        if (frm.txtNombreProducto.getText().isEmpty() || 
            frm.txtPrecioAdquisicionProducto.getText().isEmpty() ||
            frm.txtPrecioVentaProducto.getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "Nombre, Precio Adquisición y Precio Venta son obligatorios");
            return;
        }
        
        try {
            // Configurar modelo con datos de la vista
            mod.setIdproductos(Integer.parseInt(frm.txtCodigoProducto.getText()));
            mod.setNombre_Productos(frm.txtNombreProducto.getText());
            mod.setDescripcion(frm.txtDescripcionProducto.getText());
            mod.setPrecio_adquisicion(Double.parseDouble(frm.txtPrecioAdquisicionProducto.getText()));
            mod.setPrecio_venta(Double.parseDouble(frm.txtPrecioVentaProducto.getText()));
            mod.setStock(frm.txtStockProducto.getText().isEmpty() ? 0 : Integer.parseInt(frm.txtStockProducto.getText()));
            mod.setStock_minimo(5); // Valor por defecto
            mod.setCategoria(frm.txtCategoriaProducto.getText());
            
            // Actualizar producto
            if (modConsulta.actualizar(mod)) {
                JOptionPane.showMessageDialog(null, "Producto actualizado correctamente");
                cargarProductos(); // Actualizar tabla
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar producto");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Formato numérico inválido en precios o stock", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void eliminarProducto() {
        if (frm.txtCodigoProducto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            null, 
            "¿Está seguro de eliminar este producto?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(frm.txtCodigoProducto.getText());
                if (modConsulta.eliminar(id)) {
                    JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
                    limpiarCampos();
                    cargarProductos(); // Actualizar tabla
                } else {
                    JOptionPane.showMessageDialog(null, "Error al eliminar producto");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID de producto inválido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}