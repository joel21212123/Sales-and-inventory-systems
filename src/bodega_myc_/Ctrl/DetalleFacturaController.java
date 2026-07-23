package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaDetalleFactura;
import bodega_myc_.Modelo.ClaseDetalleFactura;
import bodega_myc_.Vista.DETALLE_FACTURA;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DetalleFacturaController implements ActionListener {
    private ClaseDetalleFactura mod;
    private ClaseConsultaDetalleFactura modConsulta;
    private DETALLE_FACTURA frm;

    public DetalleFacturaController(ClaseDetalleFactura mod,
            ClaseConsultaDetalleFactura modConsulta,
            DETALLE_FACTURA frm) {

        this.mod = mod;
        this.modConsulta = modConsulta;
        this.frm = frm;

        this.frm.btnNuevoDetalleFactura.addActionListener(this);
        this.frm.btnLeerDetalleFactura.addActionListener(this);
        this.frm.btnModificarDetalleFactura.addActionListener(this);
        this.frm.btnEliminarDetalleFactura.addActionListener(this);

        frm.tblDetalleFactura.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarDetalleDeTabla();
            }
        });
    }

    public void iniciar() {
        frm.setTitle("Detalle de Facturas");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.btnNuevoDetalleFactura) {
            guardarDetalleFactura();
        } else if (e.getSource() == frm.btnLeerDetalleFactura) {
            cargarDetallesFactura();
        } else if (e.getSource() == frm.btnModificarDetalleFactura) {
            actualizarDetalleFactura();
        } else if (e.getSource() == frm.btnEliminarDetalleFactura) {
            eliminarDetalleFactura();
        }
    }

    private void guardarDetalleFactura() {
        try {
            if (frm.txtFacturaId.getText().isEmpty() ||
                frm.txtProductoId.getText().isEmpty() ||
                frm.txtCantidadDetalle.getText().isEmpty()) {

                JOptionPane.showMessageDialog(frm, "Factura ID, Producto ID y Cantidad son obligatorios");
                return;
            }

            mod.setFactura_id(Integer.parseInt(frm.txtFacturaId.getText()));
            mod.setProducto_id(Integer.parseInt(frm.txtProductoId.getText()));
            mod.setCantidad_detalle(Integer.parseInt(frm.txtCantidadDetalle.getText()));
            mod.setPrecio_unitario(Double.parseDouble(frm.txtPrecioUnitario.getText()));
            mod.setSubtotal(mod.getCantidad_detalle() * mod.getPrecio_unitario());

            if (modConsulta.registrar(mod)) {
                JOptionPane.showMessageDialog(frm, "Detalle registrado correctamente");
                limpiarCampos();
                cargarDetallesFactura();
            } else {
                JOptionPane.showMessageDialog(frm, "Error al registrar detalle");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frm, "Error en formato numérico: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, "Error crítico: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void cargarDetallesFactura() {
        try {
            if (frm.txtFacturaId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frm, "Ingrese un ID de factura primero");
                return;
            }

            int facturaId = Integer.parseInt(frm.txtFacturaId.getText());
            List<ClaseDetalleFactura> detalles = modConsulta.obtenerPorFacturaId(facturaId);

            if (detalles == null || detalles.isEmpty()) {
                JOptionPane.showMessageDialog(frm, "No se encontraron detalles para esta factura");
                return;
            }

            DefaultTableModel modelo = (DefaultTableModel) frm.tblDetalleFactura.getModel();
            modelo.setRowCount(0);

            for (ClaseDetalleFactura detalle : detalles) {
                modelo.addRow(new Object[]{
                    detalle.getId_detalle_factura(),
                    detalle.getFactura_id(),
                    detalle.getProducto_id(),
                    detalle.getCantidad_detalle(),
                    detalle.getPrecio_unitario(),
                    detalle.getSubtotal()
                });
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frm, "ID de factura inválido");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, "Error al cargar detalles: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void seleccionarDetalleDeTabla() {
        int fila = frm.tblDetalleFactura.getSelectedRow();
        if (fila >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) frm.tblDetalleFactura.getModel();

            frm.txtDetalleFactura.setText(modelo.getValueAt(fila, 0).toString());
            frm.txtFacturaId.setText(modelo.getValueAt(fila, 1).toString());
            frm.txtProductoId.setText(modelo.getValueAt(fila, 2).toString());
            frm.txtCantidadDetalle.setText(modelo.getValueAt(fila, 3).toString());
            frm.txtPrecioUnitario.setText(modelo.getValueAt(fila, 4).toString());
            frm.txtSubTotal.setText(modelo.getValueAt(fila, 5).toString());
        }
    }

    private void actualizarDetalleFactura() {
        try {
            if (frm.txtDetalleFactura.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frm, "Seleccione un detalle para modificar");
                return;
            }

            mod.setId_detalle_factura(Integer.parseInt(frm.txtDetalleFactura.getText()));
            mod.setFactura_id(Integer.parseInt(frm.txtFacturaId.getText()));
            mod.setProducto_id(Integer.parseInt(frm.txtProductoId.getText()));
            mod.setCantidad_detalle(Integer.parseInt(frm.txtCantidadDetalle.getText()));
            mod.setPrecio_unitario(Double.parseDouble(frm.txtPrecioUnitario.getText()));
            mod.setSubtotal(mod.getCantidad_detalle() * mod.getPrecio_unitario());

            if (modConsulta.modificar(mod)) {
                JOptionPane.showMessageDialog(frm, "Detalle actualizado correctamente");
                cargarDetallesFactura();
            } else {
                JOptionPane.showMessageDialog(frm, "Error al actualizar detalle");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frm, "Error en formato numérico: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, "Error crítico: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void eliminarDetalleFactura() {
        try {
            if (frm.txtDetalleFactura.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frm, "Seleccione un detalle para eliminar");
                return;
            }

            int idDetalle = Integer.parseInt(frm.txtDetalleFactura.getText());

            int confirm = JOptionPane.showConfirmDialog(
                frm,
                "¿Está seguro de eliminar este detalle?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (modConsulta.eliminar(idDetalle)) {
                    JOptionPane.showMessageDialog(frm, "Detalle eliminado correctamente");
                    limpiarCampos();
                    cargarDetallesFactura();
                } else {
                    JOptionPane.showMessageDialog(frm, "Error al eliminar detalle");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frm, "ID de detalle inválido");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frm, "Error crítico: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void limpiarCampos() {
        frm.txtDetalleFactura.setText("");
        frm.txtProductoId.setText("");
        frm.txtCantidadDetalle.setText("");
        frm.txtPrecioUnitario.setText("");
        frm.txtSubTotal.setText("");
    }
}