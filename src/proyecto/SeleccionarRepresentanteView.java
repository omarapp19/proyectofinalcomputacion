package proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class SeleccionarRepresentanteView extends javax.swing.JDialog {

 
    private String cedulaSeleccionada = null;
    private String nombreSeleccionado = null;
    
 
    public SeleccionarRepresentanteView(javax.swing.JInternalFrame parent, boolean modal) {
    
        super(javax.swing.JOptionPane.getFrameForComponent(parent), modal);
        
        initComponents();
        
 
        this.setLocationRelativeTo(parent);
        

        cargarTablaRepresentantes();
    }
    

    private void cargarTablaRepresentantes() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaRepes.getModel();
        modeloTabla.setRowCount(0); 

        Connection conn = Conexion.getConexion();
        String sql = "SELECT cedula, nombre, telefono FROM representantes";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getString("cedula");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("telefono");
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar representantes: " + e.getMessage());
        }
    }
    

    public String getCedulaSeleccionada() {
        return cedulaSeleccionada;
    }

    public String getNombreSeleccionado() {
        return nombreSeleccionado;
    }
    
 

    private void seleccionarYcerrar() {
        int fila = tablaRepes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un representante.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
  
        this.cedulaSeleccionada = tablaRepes.getValueAt(fila, 0).toString();
        this.nombreSeleccionado = tablaRepes.getValueAt(fila, 1).toString();
        
 
        this.dispose();
    }
    

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRepes = new javax.swing.JTable();
        btnSeleccionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Seleccionar Representante");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Seleccione un Representante");

        tablaRepes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Nombre", "Teléfono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        // --- Evento de Doble Clic ---
        tablaRepes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaRepesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaRepes);

        btnSeleccionar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSeleccionar.setText("Seleccionar");
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBuscar.setText("Buscar:");

        txtBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
  
        this.cedulaSeleccionada = null;
        this.nombreSeleccionado = null;
        this.dispose();
    }                                           

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {                                               
  
        seleccionarYcerrar();
    }                                              

    private void tablaRepesMouseClicked(java.awt.event.MouseEvent evt) {                                        
  
        if (evt.getClickCount() == 2) {
            seleccionarYcerrar();
        }
    }                                       


                  
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tablaRepes;
    private javax.swing.JTextField txtBuscar;
                   
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

