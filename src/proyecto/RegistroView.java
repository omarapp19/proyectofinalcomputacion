package proyecto;


import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class RegistroView extends javax.swing.JDialog {


    private javax.swing.JLabel lblLogo;

 
    public RegistroView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        initComponents();
        

        getLayeredPane().add(panelRegistro, JLayeredPane.MODAL_LAYER);
        
  
        setSize(550, 650); 
        

        setLocationRelativeTo(parent); 

        configurarFondo();
        configurarLogo();
        

        panelRegistro.setBackground(new Color(255, 255, 255, 220)); 
    }
    

    
    private void configurarFondo() {
  
        String rutaImagen = "/assets/fondo.jpg"; 
        try {
            URL urlFondo = getClass().getResource(rutaImagen);
            if (urlFondo == null) { throw new Exception("Fondo no encontrado"); }
            
            ImageIcon icono = new ImageIcon(urlFondo);
     
            Image img = icono.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(img);
            
            JLabel fondoLabel = new JLabel(iconoEscalado);
            fondoLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
            
            getLayeredPane().add(fondoLabel, JLayeredPane.DEFAULT_LAYER);
            ((JPanel)getContentPane()).setOpaque(false);
            
        } catch (Exception e) {
            System.err.println("Error al cargar fondo: " + e.getMessage());
             getContentPane().setBackground(Color.DARK_GRAY);
        }
    }

    private void configurarLogo() {
        String rutaLogo = "/assets/dojo.png";
        int logoAncho = 128, logoAlto = 128; 
        try {
            URL urlLogo = getClass().getResource(rutaLogo);
            if (urlLogo == null) { throw new Exception("Logo no encontrado"); }
            ImageIcon iconoLogo = new ImageIcon(urlLogo);
            Image img = iconoLogo.getImage().getScaledInstance(logoAncho, logoAlto, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Error al cargar logo: " + e.getMessage());
            lblLogo.setText("[ LOGO ]"); 
        }
    }
    


    private boolean usuarioYaExiste(String usuario) {
        Connection conn = Conexion.getConexion();
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al verificar usuario: " + e.getMessage());
        }
        return false;
    }


  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelRegistro = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblClave1 = new javax.swing.JLabel();
        txtClave1 = new javax.swing.JPasswordField();
        btnConfirmarRegistro = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblClave2 = new javax.swing.JLabel();
        txtClave2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Nuevo Usuario");
        setResizable(false); // No dejamos que cambie de tamaño

        panelRegistro.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("REGISTRAR USUARIO");

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblUsuario.setText("Nuevo Usuario:");

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        lblClave1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblClave1.setText("Contraseña:");

        txtClave1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnConfirmarRegistro.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnConfirmarRegistro.setText("Confirmar Registro");
        btnConfirmarRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarRegistroActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblClave2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblClave2.setText("Confirmar Contraseña:");

        txtClave2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        // --- Layout del Panel (sin cambios) ---
        javax.swing.GroupLayout panelRegistroLayout = new javax.swing.GroupLayout(panelRegistro);
        panelRegistro.setLayout(panelRegistroLayout);
        panelRegistroLayout.setHorizontalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addComponent(btnConfirmarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRegistroLayout.createSequentialGroup()
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClave1)
                            .addComponent(lblUsuario)
                            .addComponent(lblClave2))
                        .addGap(18, 18, 18)
                        .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuario)
                            .addComponent(txtClave1)
                            .addComponent(txtClave2))))
                .addGap(40, 40, 40))
        );
        panelRegistroLayout.setVerticalGroup(
            panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitulo)
                .addGap(20, 20, 20)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClave1)
                    .addComponent(txtClave1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClave2)
                    .addComponent(txtClave2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(panelRegistroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmarRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        // --- CAMBIO CLAVE: Layout del JDialog (el contenedor) ---
        // Esto centra el 'panelRegistro' dentro del JDialog
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(panelRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(panelRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        pack(); // Este pack() ahora está "desactivado" por el setSize()
    }// </editor-fold>                        

    

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        this.dispose();
    }                                           

    private void btnConfirmarRegistroActionPerformed(java.awt.event.ActionEvent evt) {                                                     

        String usuario = txtUsuario.getText().trim();
        String clave1 = new String(txtClave1.getPassword());
        String clave2 = new String(txtClave2.getPassword());


        if (usuario.isEmpty() || clave1.isEmpty() || clave2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!clave1.equals(clave2)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (usuarioYaExiste(usuario)) {
            JOptionPane.showMessageDialog(this, "El nombre de usuario '" + usuario + "' ya existe. Por favor, elija otro.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }


        Connection conn = Conexion.getConexion();
        String sql = "INSERT INTO usuarios (usuario, clave) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, clave1);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "¡Usuario '" + usuario + "' registrado con éxito!", "Registro Completo", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }                                                    


    public static void main(String args[]) {
 
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistroView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }



        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegistroView dialog = new RegistroView(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

                    
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmarRegistro;
    private javax.swing.JLabel lblClave1;
    private javax.swing.JLabel lblClave2;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel panelRegistro;
    private javax.swing.JPasswordField txtClave1;
    private javax.swing.JPasswordField txtClave2;
    private javax.swing.JTextField txtUsuario;
                  
}
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

