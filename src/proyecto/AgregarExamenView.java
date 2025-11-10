package proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;



public class AgregarExamenView extends javax.swing.JDialog {


    private String cedulaEstudiante; 
    private boolean guardadoExitoso = false; 

   
    public AgregarExamenView(javax.swing.JInternalFrame parent, boolean modal, String cedulaEstudiante) {
        super(javax.swing.JOptionPane.getFrameForComponent(parent), modal);
        initComponents();
        
        this.cedulaEstudiante = cedulaEstudiante;
        
        
        cmbNivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
            "Kyu 10", "Kyu 9", "Kyu 8", "Kyu 7", "Kyu 6", "Kyu 5", 
            "Kyu 4", "Kyu 3", "Kyu 2", "Kyu 1", "Shodan (Dan 1)", "Nidan (Dan 2)" 
        }));
        
   
        this.setLocationRelativeTo(parent);
    }
    
   
    public boolean isGuardadoExitoso() {
        return guardadoExitoso;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblNivel = new javax.swing.JLabel();
        cmbNivel = new javax.swing.JComboBox<>();
        lblResultado = new javax.swing.JLabel();
        cmbResultado = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblFormatoFecha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Añadir Nuevo Examen");
        setResizable(false);

        panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setText("Registrar Examen");

        lblFecha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblFecha.setText("Fecha:");

        txtFecha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblNivel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNivel.setText("Nivel Evaluado:");

        cmbNivel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lblResultado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblResultado.setText("Resultado:");

        cmbResultado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbResultado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aprobado", "Reprobado" }));

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardar.setText("Guardar");
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

        lblFormatoFecha.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblFormatoFecha.setText("(Formato: AAAA-MM-DD)");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitulo)
                            .addComponent(lblNivel)
                            .addComponent(lblResultado)
                            .addComponent(lblFecha))
                        .addGap(18, 18, 18)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbNivel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbResultado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(txtFecha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFormatoFecha)))))
                .addGap(0, 0, 0))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addComponent(lblTitulo)
                .addGap(24, 24, 24)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFormatoFecha))
                .addGap(18, 18, 18)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNivel)
                    .addComponent(cmbNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResultado)
                    .addComponent(cmbResultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        this.guardadoExitoso = false; 
        this.dispose();
    }                                           

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
   
        String fecha = txtFecha.getText().trim();
        String nivel = cmbNivel.getSelectedItem().toString();
        String resultado = cmbResultado.getSelectedItem().toString();

   
        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La fecha es obligatoria.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
       
        Connection conn = Conexion.getConexion();
        String sql = "INSERT INTO examenes (fecha, nivel_evaluado, resultado, id_estudiante_fk) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fecha);
            pstmt.setString(2, nivel);
            pstmt.setString(3, resultado);
            pstmt.setString(4, this.cedulaEstudiante);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Examen registrado con éxito.");
                this.guardadoExitoso = true; 
                this.dispose(); 
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el examen: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            this.guardadoExitoso = false;
        }
    }                                          


                    
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbNivel;
    private javax.swing.JComboBox<String> cmbResultado;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFormatoFecha;
    private javax.swing.JLabel lblNivel;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtFecha;
                 
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

