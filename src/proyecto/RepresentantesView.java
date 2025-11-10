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

public class RepresentantesView extends javax.swing.JInternalFrame {


    private boolean modoEdicion = false; 
    private TableRowSorter<DefaultTableModel> sorter;
  
    public RepresentantesView() {
        initComponents();
        
        cargarTablaRepresentantes();

 
    DefaultTableModel modelo = (DefaultTableModel) tablaRepresentantes.getModel();
    sorter = new TableRowSorter<>(modelo);
    tablaRepresentantes.setRowSorter(sorter);
  
    txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
   
            filtrarTablaRepresentantes();
        }
    });
 
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        

        habilitarCampos(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevo.setEnabled(true);
        
    
        cargarTablaRepresentantes();
    }
    

    
    private void habilitarCampos(boolean enabled) {
        txtCedula.setEnabled(enabled);
        txtNombre.setEnabled(enabled);
        txtTelefono.setEnabled(enabled);
        txtEmail.setEnabled(enabled);
    }
    
    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
    }
    
 
private void filtrarTablaRepresentantes() {
    String texto = txtBuscar.getText().trim();

    if (texto.isEmpty()) {
        sorter.setRowFilter(null);
    } else {
       
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }
}

    private void cargarTablaRepresentantes() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaRepresentantes.getModel();
        modeloTabla.setRowCount(0); 

        Connection conn = Conexion.getConexion();

        String sql = "SELECT cedula, nombre, telefono, email FROM representantes";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
           
                Object[] fila = new Object[4];
                fila[0] = rs.getString("cedula");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("telefono");
                fila[3] = rs.getString("email");
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar representantes: " + e.getMessage());
        }
    }
    
    
    private boolean representanteYaExiste(String cedula) {
        Connection conn = Conexion.getConexion();
        String sql = "SELECT COUNT(*) FROM representantes WHERE cedula = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cedula);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al verificar cédula: " + e.getMessage());
        }
        return false;
    }


 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelConsulta = new javax.swing.JPanel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRepresentantes = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        panelDetalle = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblCedula = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Representantes");
        setVisible(true);

        panelConsulta.setBackground(new java.awt.Color(245, 245, 245));
        panelConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consultar Representantes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lblBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBuscar.setText("Buscar:");

        txtBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tablaRepresentantes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tablaRepresentantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Nombre", "Teléfono", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaRepresentantes);

        btnModificar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnModificar.setText("Seleccionar para Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(204, 0, 0));
        btnEliminar.setText("Eliminar Seleccionado");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelConsultaLayout = new javax.swing.GroupLayout(panelConsulta);
        panelConsulta.setLayout(panelConsultaLayout);
        panelConsultaLayout.setHorizontalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addComponent(lblBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar))
                    .addGroup(panelConsultaLayout.createSequentialGroup()
                        .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelConsultaLayout.setVerticalGroup(
            panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
                .addContainerGap())
        );

        panelDetalle.setBackground(new java.awt.Color(255, 255, 255));
        panelDetalle.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle del Representante", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnNuevo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNuevo.setText("Nuevo Registro");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setText("Guardar Cambios");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblCedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCedula.setText("Cédula:");

        txtCedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNombre.setText("Nombre Completo:");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTelefono.setText("Teléfono:");

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEmail.setText("Email (Opcional):");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout panelDetalleLayout = new javax.swing.GroupLayout(panelDetalle);
        panelDetalle.setLayout(panelDetalleLayout);
        panelDetalleLayout.setHorizontalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetalleLayout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                    .addGroup(panelDetalleLayout.createSequentialGroup()
                        .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(lblCedula)
                            .addComponent(lblTelefono)
                            .addComponent(lblEmail))
                        .addGap(18, 18, 18)
                        .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCedula)
                            .addComponent(txtNombre)
                            .addComponent(txtTelefono)
                            .addComponent(txtEmail))))
                .addContainerGap())
        );
        panelDetalleLayout.setVerticalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetalleLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefono)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setText("Módulo de Gestión de Representantes");

        btnCerrar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(153, 0, 0));
        btnCerrar.setText("X");
        btnCerrar.setToolTipText("Cerrar esta ventana");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCerrar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(btnCerrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

  
    
    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        this.dispose();
    }                                         

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        limpiarCampos();
        habilitarCampos(true);
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        txtCedula.requestFocus();
        this.modoEdicion = false; 
    }                                        

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        limpiarCampos();
        habilitarCampos(false);
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        this.modoEdicion = false; 
    }                                           

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           

        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

  
        if (cedula.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La Cédula y el Nombre son obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Connection conn = Conexion.getConexion();
        
        if (this.modoEdicion) {

            String sql = "UPDATE representantes SET nombre = ?, telefono = ?, email = ? WHERE cedula = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, telefono);
                pstmt.setString(3, email);
                pstmt.setString(4, cedula); 
                
                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "¡Representante modificado con éxito!");
                    cargarTablaRepresentantes();
                    btnCancelarActionPerformed(null); 
                }
            } catch (SQLException e) {
                 JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage());
            }

        } else {
       
            if (representanteYaExiste(cedula)) {
                JOptionPane.showMessageDialog(this, "La Cédula '" + cedula + "' ya está registrada.", "Cédula Duplicada", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO representantes (cedula, nombre, telefono, email) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, cedula);
                pstmt.setString(2, nombre);
                pstmt.setString(3, telefono);
                pstmt.setString(4, email);
                
                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "¡Representante registrado con éxito!");
                    cargarTablaRepresentantes();
                    btnCancelarActionPerformed(null); 
                }
            } catch (SQLException e) {
                 JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
            }
        }
    }                                          

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int fila = tablaRepresentantes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un representante de la tabla.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
      
        String cedula = tablaRepresentantes.getValueAt(fila, 0).toString();
        String nombre = tablaRepresentantes.getValueAt(fila, 1).toString();
        String telefono = (tablaRepresentantes.getValueAt(fila, 2) != null) ? tablaRepresentantes.getValueAt(fila, 2).toString() : "";
        String email = (tablaRepresentantes.getValueAt(fila, 3) != null) ? tablaRepresentantes.getValueAt(fila, 3).toString() : "";
        
        txtCedula.setText(cedula);
        txtNombre.setText(nombre);
        txtTelefono.setText(telefono);
        txtEmail.setText(email);
        

        habilitarCampos(true);
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        txtCedula.setEnabled(false); //
        
        this.modoEdicion = true; 
    }                                            

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        int filaSeleccionada = tablaRepresentantes.getSelectedRow();
        
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un representante para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String cedula = tablaRepresentantes.getValueAt(filaSeleccionada, 0).toString();
        String nombre = tablaRepresentantes.getValueAt(filaSeleccionada, 1).toString();
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea eliminar a:\n" + nombre + " (C.I: " + cedula + ")?\n\nADVERTENCIA: Si este representante tiene estudiantes asignados, podría causar un error.",
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            Connection conn = Conexion.getConexion();
            String sql = "DELETE FROM representantes WHERE cedula = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, cedula);
                int filasAfectadas = pstmt.executeUpdate();
                
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(this, "Representante eliminado con éxito.");
                    cargarTablaRepresentantes();
                    limpiarCampos();
                    btnCancelarActionPerformed(null);
                }
            } catch (SQLException e) {
              
                 JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage() + "\nAsegúrese de que no tenga estudiantes asignados.", "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                           

                  
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JPanel panelDetalle;
    private javax.swing.JTable tablaRepresentantes;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
               
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
    }//GEN-LAST:event_exitForm

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
