package proyecto;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.net.URL; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import proyecto.MainView; 

public class LoginView extends javax.swing.JFrame {


    private javax.swing.JLabel lblLogo;


    public LoginView() {
        initComponents();
        
    
        getLayeredPane().add(panelLogin, JLayeredPane.MODAL_LAYER);
        
    
        setSize(800, 800);
        setLocationRelativeTo(null); 
        configurarFondo();
        configurarLogo();
        panelLogin.setBackground(new Color(255, 255, 255, 220)); 
    }
    
    
   
    private void configurarFondo() {
       
        String rutaImagen = "/assets/fondo.jpg"; 

        try {
            URL urlFondo = getClass().getResource(rutaImagen);
            if (urlFondo == null) {
                throw new Exception("Recurso de fondo no encontrado: " + rutaImagen);
            }
            ImageIcon icono = new ImageIcon(urlFondo);
            Image img = icono.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(img);
            JLabel fondoLabel = new JLabel(iconoEscalado);
            fondoLabel.setBounds(0, 0, this.getWidth(), this.getHeight());
            
       
            getLayeredPane().add(fondoLabel, JLayeredPane.DEFAULT_LAYER);
            
            ((JPanel)getContentPane()).setOpaque(false);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
             getContentPane().setBackground(Color.DARK_GRAY);
        }
    }


    private void configurarLogo() {
   
        String rutaLogo = "/assets/dojo.png";
        int logoAncho = 128; 
        int logoAlto = 128; 

        try {
            URL urlLogo = getClass().getResource(rutaLogo);
            if (urlLogo == null) {
                throw new Exception("Recurso de logo no encontrado: " + rutaLogo);
            }
            ImageIcon iconoLogo = new ImageIcon(urlLogo);
            Image img = iconoLogo.getImage().getScaledInstance(logoAncho, logoAlto, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("Error al cargar el logo: " + e.getMessage());
            lblLogo.setText("[ LOGO NO ENCONTRADO ]"); 
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelLogin = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnIniciarSesion = new javax.swing.JButton();
        btnRegistrar = new javax.swing.JButton();

        // ESTE ES EL IMPORTANTE. Si cierras el login, se cierra todo.
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Dojo - Login");
        setResizable(false);

        panelLogin.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("ADMINISTRACIÓN DE DOJO");

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblUsuario.setText("Usuario:");

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtUsuario.setText("admin");

        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblPassword.setText("Contraseña:");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtPassword.setText("123");

        btnIniciarSesion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnIniciarSesion.setText("Iniciar Sesión");
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addComponent(btnIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPassword)
                            .addComponent(lblUsuario))
                        .addGap(18, 18, 18)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuario)
                            .addComponent(txtPassword))))
                .addGap(40, 40, 40))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitulo)
                .addGap(20, 20, 20)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(177, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(183, Short.MAX_VALUE)
                .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    

   private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        String usuario = txtUsuario.getText();
        String pass = new String(txtPassword.getPassword());

     
        Connection conn = Conexion.getConexion(); 
        
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "No se pudo establecer conexión con la base de datos.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";

    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
         
            pstmt.setString(1, usuario);
            pstmt.setString(2, pass);

     
            try (ResultSet rs = pstmt.executeQuery()) {
                
               
                if (rs.next()) {
              
                    MainView menuPrincipal = new MainView(this); 
                    menuPrincipal.setVisible(true);

         
                    this.setVisible(false); 
                    
                } else {
                
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(this, 
                "Error al consultar la base de datos: " + e.getMessage(),
                "Error de Base de Datos", 
                JOptionPane.ERROR_MESSAGE);
        }
        

    }                                          

   private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {                                             

        RegistroView dialog = new RegistroView(this, true);
        

        dialog.setVisible(true);
        

        txtUsuario.requestFocus();
    }                                         

    

    public static void main(String args[]) {
        

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            
            // --- ¡¡¡ ESTA ES LA LÍNEA MÁGICA !!! ---
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

     
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }

                    
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
                 
}
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

