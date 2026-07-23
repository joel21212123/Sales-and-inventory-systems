package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaFactura;
import bodega_myc_.Vista.ReportesVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReportesVentasController implements ActionListener {
    private final ClaseConsultaFactura modConsulta;
    private final ReportesVentas frm;
    
    public ReportesVentasController(ClaseConsultaFactura modConsulta, ReportesVentas frm) {
        this.modConsulta = modConsulta;
        this.frm = frm;
        this.frm.BotonReportesVentasBuscar.addActionListener(this);
    }
    
    public void iniciar() {
        frm.setTitle("Reportes de Ventas");
        frm.setLocationRelativeTo(null);
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio clave
        cargarDatosIniciales();
    }
    
    private void cargarDatosIniciales() {
        try {
            // Cargar ventas por categoría
            List<Map<String, Object>> ventasCategoria = modConsulta.obtenerVentasPorCategoria();
            DefaultTableModel modelo = (DefaultTableModel) frm.TablaReportesVentas.getModel();
            modelo.setRowCount(0);
            
            for (Map<String, Object> venta : ventasCategoria) {
                modelo.addRow(new Object[]{
                    null, // Producto mas comprado
                    null, // Producto menos comprado
                    null, // Usuario
                    venta.get("monto_total_vendido"), // Monto por cliente
                    venta.get("categoria") // Categoría
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frm, "Error al cargar datos iniciales: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.BotonReportesVentasBuscar) {
            // Implementar lógica de búsqueda específica
            JOptionPane.showMessageDialog(frm, "Búsqueda de ventas implementada");
        }
    }
}