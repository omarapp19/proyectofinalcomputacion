package proyecto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import proyecto.SeleccionarRepresentanteView;
import proyecto.AgregarExamenView;
import proyecto.AgregarCertificadoView; 

public class EstudiantesView extends javax.swing.JInternalFrame {


    private boolean modoEdicion = false; 
    private String cedulaRepresentanteSeleccionado = null;
    

    private DefaultListModel<String> modeloListaExamenes;
    private DefaultListModel<String> modeloListaCertificados; 
    private TableRowSorter<DefaultTableModel> sorter;
    
   

    public EstudiantesView() {
        initComponents();
        
  
        modeloListaExamenes = new DefaultListModel<>();
        listaExamenes.setModel(modeloListaExamenes);
        modeloListaCertificados = new DefaultListModel<>();
        listaCertificados.setModel(modeloListaCertificados);
        

        cargarTablaEstudiantes();
        
    
        DefaultTableModel modelo = (DefaultTableModel) tablaEstudiantes.getModel();
        sorter = new TableRowSorter<>(modelo);
        tablaEstudiantes.setRowSorter(sorter);

        
        

        tablaEstudiantes.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {

        });
        
        

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
           
                filtrarTablaEstudiantes();
            }
        });

        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        
   
        habilitarCampos(false);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNuevo.setEnabled(true);
        

        modeloListaExamenes = new DefaultListModel<>();
        listaExamenes.setModel(modeloListaExamenes);
        
        modeloListaCertificados = new DefaultListModel<>(); 
        listaCertificados.setModel(modeloListaCertificados); 
        
    
        cargarTablaEstudiantes();
        
       
        tablaEstudiantes.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && tablaEstudiantes.getSelectedRow() != -1) {
                String cedula = tablaEstudiantes.getValueAt(tablaEstudiantes.getSelectedRow(), 0).toString();
                
    
                cargarExamenes(cedula);
                cargarCertificados(cedula); 
            }
        });
    }
    

    
    private void filtrarTablaEstudiantes() {
        String texto = txtBuscar.getText().trim();
        
        if (texto.isEmpty()) {
  
            sorter.setRowFilter(null);
        } else {
          

            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }
    
    private void habilitarCampos(boolean enabled) {
        txtCedula.setEnabled(enabled);
        txtNombre.setEnabled(enabled);
        cmbNivel.setEnabled(enabled);
        btnBuscarRepresentante.setEnabled(enabled);
    }
    
   
    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        cmbNivel.setSelectedIndex(0);
        txtRepresentanteNombre.setText(""); 
        this.cedulaRepresentanteSeleccionado = null; 
        
        modeloListaExamenes.clear();
        modeloListaCertificados.clear(); 
    }
    
    private void cargarTablaEstudiantes() {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaEstudiantes.getModel();
        modeloTabla.setRowCount(0); 

        Connection conn = Conexion.getConexion();
        String sql = "SELECT e.cedula, e.nombre, e.nivel, r.nombre as nombre_representante " +
                     "FROM estudiantes e " +
                     "LEFT JOIN representantes r ON e.id_representante_fk = r.cedula";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            modeloTabla.setColumnIdentifiers(new Object[]{"Cédula", "Nombre", "Nivel", "Representante"});
            
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getString("cedula");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("nivel");
                fila[3] = rs.getString("nombre_representante");
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estudiantes: " + e.getMessage());
        }
    }
    
    private boolean estudianteYaExiste(String cedula) {
        Connection conn = Conexion.getConexion();
        String sql = "SELECT COUNT(*) FROM estudiantes WHERE cedula = ?";
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
    
    private void cargarExamenes(String cedulaEstudiante) {
        modeloListaExamenes.clear();
        Connection conn = Conexion.getConexion();
        String sql = "SELECT fecha, nivel_evaluado, resultado FROM examenes " +
                     "WHERE id_estudiante_fk = ? ORDER BY fecha DESC";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cedulaEstudiante);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String linea = String.format("Fecha: %s | Nivel: %s | Resultado: %s", 
                                     rs.getString("fecha"), 
                                     rs.getString("nivel_evaluado"), 
                                     rs.getString("resultado"));
                    modeloListaExamenes.addElement(linea);
                }
            }
            if (modeloListaExamenes.isEmpty()) {
                modeloListaExamenes.addElement("Sin exámenes registrados.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar exámenes: " + e.getMessage());
        }
    }
    
    
 
    private void cargarCertificados(String cedulaEstudiante) {
        modeloListaCertificados.clear();
        Connection conn = Conexion.getConexion();
        String sql = "SELECT fecha_emision, nivel_certificado, codigo_unico FROM certificados " +
                     "WHERE id_estudiante_fk = ? ORDER BY fecha_emision DESC";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cedulaEstudiante);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String codigo = (rs.getString("codigo_unico") != null) ? rs.getString("codigo_unico") : "N/A";
                    String linea = String.format("Fecha: %s | Nivel: %s | Código: %s", 
                                     rs.getString("fecha_emision"), 
                                     rs.getString("nivel_certificado"), 
                                     codigo);
                    modeloListaCertificados.addElement(linea);
                }
            }
            if (modeloListaCertificados.isEmpty()) {
                modeloListaCertificados.addElement("Sin certificados registrados.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar certificados: " + e.getMessage());
        }
    }


  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelConsulta = new javax.swing.JPanel();
        lblBuscar = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEstudiantes = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        panelDetalle = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        tabbedPaneEstudiante = new javax.swing.JTabbedPane();
        panelDatos = new javax.swing.JPanel();
        lblCedula = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblNivel = new javax.swing.JLabel();
        cmbNivel = new javax.swing.JComboBox<>();
        lblRepresentante = new javax.swing.JLabel();
        txtRepresentanteNombre = new javax.swing.JTextField();
        btnBuscarRepresentante = new javax.swing.JButton();
        panelExamenes = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaExamenes = new javax.swing.JList<>();
        btnAgregarExamen = new javax.swing.JButton();
        panelCertificados = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaCertificados = new javax.swing.JList<>();
        btnAgregarCertificado = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gestión de Estudiantes");
        setVisible(true);

        panelConsulta.setBackground(new java.awt.Color(245, 245, 245));
        panelConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consultar Estudiantes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        lblBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblBuscar.setText("Buscar:");

        txtBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tablaEstudiantes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tablaEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Nombre", "Nivel", "Representante"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaEstudiantes);

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar))
                .addContainerGap())
        );

        panelDetalle.setBackground(new java.awt.Color(255, 255, 255));
        panelDetalle.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle del Estudiante", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnNuevo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNuevo.setText("Nuevo (Afiliar)");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setText("Guardar (Modificar)");
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

        tabbedPaneEstudiante.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        panelDatos.setBackground(new java.awt.Color(255, 255, 255));

        lblCedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCedula.setText("Cédula:");

        txtCedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNombre.setText("Nombre Completo:");

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNivel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNivel.setText("Nivel:");

        cmbNivel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbNivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kyu 10", "Kyu 9", "Kyu 8", "Kyu 7", "Kyu 6", "Kyu 5", "Kyu 4", "Kyu 3", "Kyu 2", "Kyu 1", "Shodan (Dan 1)", "Nidan (Dan 2)" }));

        lblRepresentante.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblRepresentante.setText("Representante:");

        txtRepresentanteNombre.setEditable(false);
        txtRepresentanteNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnBuscarRepresentante.setText("Relacionar...");
        btnBuscarRepresentante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarRepresentanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRepresentante)
                    .addComponent(lblNivel)
                    .addComponent(lblNombre)
                    .addComponent(lblCedula))
                .addGap(18, 18, 18)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCedula)
                    .addComponent(txtNombre)
                    .addComponent(cmbNivel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addComponent(txtRepresentanteNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarRepresentante)))
                .addContainerGap())
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNivel)
                    .addComponent(cmbNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRepresentante)
                    .addComponent(txtRepresentanteNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarRepresentante))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        tabbedPaneEstudiante.addTab("Datos Personales", panelDatos);

        jScrollPane2.setViewportView(listaExamenes);

        btnAgregarExamen.setText("Añadir Examen...");
        // --- ¡¡¡CONECTANDO EL BOTÓN!!! ---
        btnAgregarExamen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarExamenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelExamenesLayout = new javax.swing.GroupLayout(panelExamenes);
        panelExamenes.setLayout(panelExamenesLayout);
        panelExamenesLayout.setHorizontalGroup(
            panelExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExamenesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelExamenesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAgregarExamen)))
                .addContainerGap())
        );
        panelExamenesLayout.setVerticalGroup(
            panelExamenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExamenesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarExamen)
                .addContainerGap())
        );

        tabbedPaneEstudiante.addTab("Exámenes", panelExamenes);

        jScrollPane3.setViewportView(listaCertificados);

        btnAgregarCertificado.setText("Añadir Certificado...");
        // --- ¡¡¡CONECTANDO EL BOTÓN!!! ---
        btnAgregarCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCertificadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCertificadosLayout = new javax.swing.GroupLayout(panelCertificados);
        panelCertificados.setLayout(panelCertificadosLayout);
        panelCertificadosLayout.setHorizontalGroup(
            panelCertificadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCertificadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCertificadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCertificadosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAgregarCertificado)))
                .addContainerGap())
        );
        panelCertificadosLayout.setVerticalGroup(
            panelCertificadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCertificadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarCertificado)
                .addContainerGap())
        );

        tabbedPaneEstudiante.addTab("Certificados", panelCertificados);

        javax.swing.GroupLayout panelDetalleLayout = new javax.swing.GroupLayout(panelDetalle);
        panelDetalle.setLayout(panelDetalleLayout);
        panelDetalleLayout.setHorizontalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPaneEstudiante)
                    .addGroup(panelDetalleLayout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelDetalleLayout.setVerticalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPaneEstudiante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setText("Módulo de Gestión de Estudiantes");

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
        tablaEstudiantes.clearSelection();
    }                                           

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String nivel = cmbNivel.getSelectedItem().toString();

        if (cedula.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La Cédula y el Nombre son obligatorios.");
            return;
        }
        
        Connection conn = Conexion.getConexion();
        
        if (this.modoEdicion) {
            String sql = "UPDATE estudiantes SET nombre = ?, nivel = ?, id_representante_fk = ? WHERE cedula = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, nivel);
                pstmt.setString(3, this.cedulaRepresentanteSeleccionado);
                pstmt.setString(4, cedula); 
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "¡Estudiante modificado con éxito!");
                cargarTablaEstudiantes();
                btnCancelarActionPerformed(null); 
            } catch (SQLException e) {
                 JOptionPane.showMessageDialog(this, "Error al modificar: " + e.getMessage());
            }
        } else {
            if (estudianteYaExiste(cedula)) {
                JOptionPane.showMessageDialog(this, "La Cédula '" + cedula + "' ya está registrada.");
                return;
            }
            String sql = "INSERT INTO estudiantes (cedula, nombre, nivel, id_representante_fk) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, cedula);
                pstmt.setString(2, nombre);
                pstmt.setString(3, nivel);
                pstmt.setString(4, this.cedulaRepresentanteSeleccionado); 
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "¡Estudiante afiliado con éxito!");
                cargarTablaEstudiantes();
                btnCancelarActionPerformed(null); 
            } catch (SQLException e) {
                 JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
            }
        }
    }                                          

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante.");
            return;
        }
        
        String cedula = tablaEstudiantes.getValueAt(fila, 0).toString();
        String nombre = tablaEstudiantes.getValueAt(fila, 1).toString();
        String nivel = tablaEstudiantes.getValueAt(fila, 2).toString();
        String nombreRep = (tablaEstudiantes.getValueAt(fila, 3) != null) ? tablaEstudiantes.getValueAt(fila, 3).toString() : "";
        
        txtCedula.setText(cedula);
        txtNombre.setText(nombre);
        cmbNivel.setSelectedItem(nivel);
        txtRepresentanteNombre.setText(nombreRep); 
        
        this.cedulaRepresentanteSeleccionado = null; 
        if (!nombreRep.isEmpty()) {
            Connection conn = Conexion.getConexion();
            String sql = "SELECT id_representante_fk FROM estudiantes WHERE cedula = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, cedula);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        this.cedulaRepresentanteSeleccionado = rs.getString("id_representante_fk");
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar ID del representante: " + e.getMessage());
            }
        }
        
        habilitarCampos(true);
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        txtCedula.setEnabled(false); 
        this.modoEdicion = true; 
        
     
        cargarExamenes(cedula);
        cargarCertificados(cedula); 
    }                                            

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        int filaSeleccionada = tablaEstudiantes.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante.");
            return;
        }
        
        String cedula = tablaEstudiantes.getValueAt(filaSeleccionada, 0).toString();
        String nombre = tablaEstudiantes.getValueAt(filaSeleccionada, 1).toString();
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea eliminar a:\n" + nombre + " (C.I: " + cedula + ")?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            Connection conn = Conexion.getConexion();
           
            String sql = "DELETE FROM estudiantes WHERE cedula = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, cedula);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Estudiante eliminado con éxito.");
                cargarTablaEstudiantes(); 
                limpiarCampos();
                btnCancelarActionPerformed(null); 
            } catch (SQLException e) {
                 JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage() + "\nPrimero debe eliminar los exámenes y certificados asociados.");
            }
        }
    }                                           

    private void btnBuscarRepresentanteActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        SeleccionarRepresentanteView selector = new SeleccionarRepresentanteView(this, true);
        selector.setVisible(true);
        
        String cedula = selector.getCedulaSeleccionada();
        String nombre = selector.getNombreSeleccionado();
        
        if (cedula != null && nombre != null) {
            this.cedulaRepresentanteSeleccionado = cedula; 
            this.txtRepresentanteNombre.setText(nombre); 
        }
    }                                                      
    
    private void btnAgregarExamenActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Primero debe seleccionar un estudiante.");
            return;
        }
        String cedulaEstudiante = tablaEstudiantes.getValueAt(fila, 0).toString();
        AgregarExamenView dialog = new AgregarExamenView(this, true, cedulaEstudiante);
        dialog.setVisible(true);
        if (dialog.isGuardadoExitoso()) {
            cargarExamenes(cedulaEstudiante);
        }
    }
    
    

    private void btnAgregarCertificadoActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Primero debe seleccionar un estudiante.");
            return;
        }
        String cedulaEstudiante = tablaEstudiantes.getValueAt(fila, 0).toString();
        

        AgregarCertificadoView dialog = new AgregarCertificadoView(this, true, cedulaEstudiante);
        
    
        dialog.setVisible(true);
        
  
        if (dialog.isGuardadoExitoso()) {
            cargarCertificados(cedulaEstudiante);
        }
    }
    
                   
    private javax.swing.JButton btnAgregarCertificado;
    private javax.swing.JButton btnAgregarExamen;
    private javax.swing.JButton btnBuscarRepresentante;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbNivel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblRepresentante;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList<String> listaCertificados;
    private javax.swing.JList<String> listaExamenes;
    private javax.swing.JPanel panelCertificados;
    private javax.swing.JPanel panelConsulta;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelDetalle;
    private javax.swing.JPanel panelExamenes;
    private javax.swing.JTable tablaEstudiantes;
    private javax.swing.JTabbedPane tabbedPaneEstudiante;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRepresentanteNombre;
             
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
