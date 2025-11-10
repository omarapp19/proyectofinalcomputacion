
package proyecto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class HistorialView extends javax.swing.JInternalFrame {
private TableRowSorter<DefaultTableModel> sorterExamenes;
private TableRowSorter<DefaultTableModel> sorterCertificados;
  
    public HistorialView() {
        initComponents();
        
        cargarTablaExamenes();
    cargarTablaCertificados();


    DefaultTableModel modeloExa = (DefaultTableModel) tablaExamenes.getModel();
    sorterExamenes = new TableRowSorter<>(modeloExa);
    tablaExamenes.setRowSorter(sorterExamenes);

    txtBuscarExamen.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
            filtrarTablaExamenes();
        }
    });



    DefaultTableModel modeloCert = (DefaultTableModel) tablaCertificados.getModel();
    sorterCertificados = new TableRowSorter<>(modeloCert);
    tablaCertificados.setRowSorter(sorterCertificados);

    txtBuscarCertificado.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
            filtrarTablaCertificados();
        }
    });

        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        

        cargarTablaExamenes();
        cargarTablaCertificados();
    }
    
    
 
private void filtrarTablaExamenes() {
    String texto = txtBuscarExamen.getText().trim();
    if (texto.isEmpty()) {
        sorterExamenes.setRowFilter(null);
    } else {
        sorterExamenes.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }
}


private void filtrarTablaCertificados() {
    String texto = txtBuscarCertificado.getText().trim();
    if (texto.isEmpty()) {
        sorterCertificados.setRowFilter(null);
    } else {
        sorterCertificados.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }
}
    
    private void cargarTablaExamenes() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaExamenes.getModel();
        modeloTabla.setRowCount(0); 

        Connection conn = Conexion.getConexion();
 
        String sql = "SELECT e.nombre, ex.fecha, ex.nivel_evaluado, ex.resultado " +
                     "FROM examenes ex " +
                     "JOIN estudiantes e ON ex.id_estudiante_fk = e.cedula " +
                     "ORDER BY ex.fecha DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getString("fecha");
                fila[1] = rs.getString("nombre"); 
                fila[2] = rs.getString("nivel_evaluado");
                fila[3] = rs.getString("resultado");
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el historial de exámenes: " + e.getMessage());
        }
    }
    
  
    private void cargarTablaCertificados() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaCertificados.getModel();
        modeloTabla.setRowCount(0); 

        Connection conn = Conexion.getConexion();
        String sql = "SELECT e.nombre, c.fecha_emision, c.nivel_certificado, c.codigo_unico " +
                     "FROM certificados c " +
                     "JOIN estudiantes e ON c.id_estudiante_fk = e.cedula " +
                     "ORDER BY c.fecha_emision DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getString("fecha_emision");
                fila[1] = rs.getString("nombre"); // Nombre del estudiante
                fila[2] = rs.getString("nivel_certificado");
                fila[3] = rs.getString("codigo_unico");
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el historial de certificados: " + e.getMessage());
        }
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        tabbedPaneHistorial = new javax.swing.JTabbedPane();
        panelExamenes = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaExamenes = new javax.swing.JTable();
        txtBuscarExamen = new javax.swing.JTextField();
        lblBuscarExamen = new javax.swing.JLabel();
        panelCertificados = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCertificados = new javax.swing.JTable();
        lblBuscarCertificado = new javax.swing.JLabel();
        txtBuscarCertificado = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Historial General del Dojo");
        setVisible(true);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setText("Historial General del Dojo");

        btnCerrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(153, 0, 0));
        btnCerrar.setText("X");
        btnCerrar.setToolTipText("Cerrar esta ventana");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        tabbedPaneHistorial.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tablaExamenes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Estudiante", "Nivel Evaluado", "Resultado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaExamenes);

        txtBuscarExamen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblBuscarExamen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBuscarExamen.setText("Buscar:");

        javax.swing.GroupLayout panelExamenesLayout = new javax.swing.GroupLayout(panelExamenes);
        panelExamenes.setLayout(panelExamenesLayout);
        panelExamenesLayout.setHorizontalGroup(
            panelExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExamenesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                    .addGroup(panelExamenesLayout.createSequentialGroup()
                        .addComponent(lblBuscarExamen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarExamen, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelExamenesLayout.setVerticalGroup(
            panelExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelExamenesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarExamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarExamen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneHistorial.addTab("Listado de Exámenes", panelExamenes);

        tablaCertificados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha Emisión", "Estudiante", "Nivel Certificado", "Código"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaCertificados);

        lblBuscarCertificado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBuscarCertificado.setText("Buscar:");

        txtBuscarCertificado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout panelCertificadosLayout = new javax.swing.GroupLayout(panelCertificados);
        panelCertificados.setLayout(panelCertificadosLayout);
        panelCertificadosLayout.setHorizontalGroup(
            panelCertificadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCertificadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCertificadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                    .addGroup(panelCertificadosLayout.createSequentialGroup()
                        .addComponent(lblBuscarCertificado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCertificadosLayout.setVerticalGroup(
            panelCertificadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCertificadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCertificadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBuscarCertificado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneHistorial.addTab("Listado de Certificados", panelCertificados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPaneHistorial)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(btnCerrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPaneHistorial)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        this.dispose();
    }                                         


                 
    private javax.swing.JButton btnCerrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBuscarCertificado;
    private javax.swing.JLabel lblBuscarExamen;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelCertificados;
    private javax.swing.JPanel panelExamenes;
    private javax.swing.JTabbedPane tabbedPaneHistorial;
    private javax.swing.JTable tablaCertificados;
    private javax.swing.JTable tablaExamenes;
    private javax.swing.JTextField txtBuscarCertificado;
    private javax.swing.JTextField txtBuscarExamen;
                  
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
