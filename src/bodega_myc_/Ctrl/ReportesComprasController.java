package bodega_myc_.Ctrl;

import bodega_myc_.ConexionMysql.ClaseConsultaCompras;
import bodega_myc_.Vista.ReportesCompras;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReportesComprasController implements ActionListener {
    private final ClaseConsultaCompras modConsulta;
    private final ReportesCompras frm;
    
    public ReportesComprasController(ClaseConsultaCompras modConsulta, ReportesCompras frm) {
        this.modConsulta = modConsulta;
        this.frm = frm;
        this.frm.ReporteComprasBuscar.addActionListener(this);
    }
    
    public void iniciar() {
        frm.setTitle("Reportes de Compras");
        frm.setLocationRelativeTo(null);
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio clave
        frm.setVisible(true);
    }
    
    private void cargarReporteCompras() {
        try {
            List<Map<String, Object>> compras = modConsulta.obtenerReporteCompras();
            DefaultTableModel modelo = (DefaultTableModel) frm.TablaReportesCompras.getModel();
            modelo.setRowCount(0);
            
            for (Map<String, Object> compra : compras) {
                modelo.addRow(new Object[]{
                    compra.get("proveedor"),
                    compra.get("monto_comprado"),
                    compra.get("producto"),
                    compra.get("fecha")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frm, "Error al cargar reporte: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frm.ReporteComprasBuscar) {
            cargarReporteCompras();
        }
    }
}