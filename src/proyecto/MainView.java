package proyecto;

import proyecto.LoginView;
import proyecto.EstudiantesView;
import proyecto.RepresentantesView;
import proyecto.HistorialView;
import java.awt.Image; 
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane; 
import java.awt.BorderLayout; 
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import javax.swing.JPanel;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Box; 
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import java.net.URL; 


public class MainView extends javax.swing.JFrame {

    private LoginView vistaLoginDeOrigen;
    
    private SimpleUniverse simpleU;
    
    public MainView() {
        initComponents();
        configurarVentana();
        crearToolBarYEscena3D(); 
    }
    
    public MainView(LoginView login) {
        initComponents();
        this.vistaLoginDeOrigen = login; 
        configurarVentana();
        crearToolBarYEscena3D(); 
    }
    
    private void configurarVentana() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH); 
    }
    
    private void crearToolBarYEscena3D() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); 
        
        int iconAncho = 50;
        int iconAlto = 50;
        
        JButton btnEstudiantes = new JButton("Estudiantes");
        btnEstudiantes.setToolTipText("Gestionar Estudiantes");
        btnEstudiantes.setIcon(cargarIconoEscalado("estudiante.png", iconAncho, iconAlto));
        btnEstudiantes.addActionListener(this::itemGestionEstudiantesActionPerformed);
        
        JButton btnRepresentantes = new JButton("Representantes");
        btnRepresentantes.setToolTipText("Gestionar Representantes");
        btnRepresentantes.setIcon(cargarIconoEscalado("padre.png", iconAncho, iconAlto));
        btnRepresentantes.addActionListener(this::itemGestionRepresentantesActionPerformed);

        JButton btnHistorial = new JButton("Historial");
        btnHistorial.setToolTipText("Ver Historial General");
        btnHistorial.setIcon(cargarIconoEscalado("historial.png", iconAncho, iconAlto));
        btnHistorial.addActionListener(this::itemVerHistorialActionPerformed);

        JButton btnCerrar = new JButton("Cerrar Sesión");
        btnCerrar.setToolTipText("Volver a la pantalla de Login");
        btnCerrar.setIcon(cargarIconoEscalado("cerrar.png", iconAncho, iconAlto));
        btnCerrar.addActionListener(this::itemCerrarSesionActionPerformed);
        
        JButton btnSalir = new JButton("Salir");
        btnSalir.setToolTipText("Cerrar la aplicación por completo");
         btnSalir.setIcon(cargarIconoEscalado("salir.png", iconAncho, iconAlto));
        btnSalir.addActionListener(this::itemSalirActionPerformed);
        
        toolBar.add(btnEstudiantes);
        toolBar.add(btnRepresentantes);
        toolBar.add(btnHistorial);
        toolBar.add(new JToolBar.Separator()); 
        toolBar.add(btnCerrar);
        toolBar.add(btnSalir);
        
        
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        
        JPanel panel3D = new JPanel(new BorderLayout());
        panel3D.setPreferredSize(new Dimension(300, 300)); 
        panel3D.add(canvas3D, BorderLayout.CENTER);

        BranchGroup escena = crearGrafoEscena();
        
        simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(escena);

        
        this.getContentPane().setLayout(new java.awt.BorderLayout());
        this.getContentPane().add(toolBar, java.awt.BorderLayout.NORTH);
        this.getContentPane().add(panel3D, java.awt.BorderLayout.WEST);
        this.getContentPane().add(escritorioPrincipal, java.awt.BorderLayout.CENTER);
    }
    
    
    public BranchGroup crearGrafoEscena() {
        BranchGroup root = new BranchGroup();
        
        TransformGroup tgPosicion = new TransformGroup();

        TransformGroup tgRotacion = new TransformGroup();
        tgRotacion.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgRotacion.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 

        TransformGroup tgModelo = new TransformGroup();
        
        
        Appearance appRojo = new Appearance();
        Material matRojo = new Material(
                new Color3f(0.8f, 0.1f, 0.1f), new Color3f(0.1f, 0.1f, 0.1f),
                new Color3f(0.8f, 0.1f, 0.1f), new Color3f(1.0f, 1.0f, 1.0f), 64.0f);
        appRojo.setMaterial(matRojo);

        Appearance appNegro = new Appearance();
        Material matNegro = new Material(
                new Color3f(0.05f, 0.05f, 0.05f), new Color3f(0.05f, 0.05f, 0.05f),
                new Color3f(0.1f, 0.1f, 0.1f), new Color3f(0.8f, 0.8f, 0.8f), 32.0f);
        appNegro.setMaterial(matNegro);


        float pilarAlto = 0.5f;
        float pilarAncho = 0.1f;
        float pilarDistancia = 0.5f;
        float pilarSlant = 0.05f;

        Box pilar1 = new Box(pilarAncho, pilarAlto, pilarAncho, appRojo);
        Transform3D t3d_pilar1 = new Transform3D();
        t3d_pilar1.rotZ(pilarSlant);
        t3d_pilar1.setTranslation(new Vector3d(-pilarDistancia, 0.0, 0.0));
        TransformGroup tg_pilar1 = new TransformGroup(t3d_pilar1);
        tg_pilar1.addChild(pilar1);
        
        Box pilar2 = new Box(pilarAncho, pilarAlto, pilarAncho, appRojo);
        Transform3D t3d_pilar2 = new Transform3D();
        t3d_pilar2.rotZ(-pilarSlant);
        t3d_pilar2.setTranslation(new Vector3d(pilarDistancia, 0.0, 0.0));
        TransformGroup tg_pilar2 = new TransformGroup(t3d_pilar2);
        tg_pilar2.addChild(pilar2);
        
        float nukiAncho = 0.7f;
        float nukiAlto = 0.08f;
        float nukiPosV = 0.25f;
        Box vigaNuki = new Box(nukiAncho, nukiAlto, pilarAncho, appRojo);
        Transform3D t3d_vigaNuki = new Transform3D();
        t3d_vigaNuki.setTranslation(new Vector3d(0.0, nukiPosV, 0.0));
        TransformGroup tg_vigaNuki = new TransformGroup(t3d_vigaNuki);
        tg_vigaNuki.addChild(vigaNuki);

        float shimakiAncho = 0.75f;
        float shimakiAlto = 0.1f;
        float shimakiPosV = pilarAlto - 0.05f;
        Box vigaShimaki = new Box(shimakiAncho, shimakiAlto, pilarAncho, appRojo);
        Transform3D t3d_vigaShimaki = new Transform3D();
        t3d_vigaShimaki.setTranslation(new Vector3d(0.0, shimakiPosV, 0.0));
        TransformGroup tg_vigaShimaki = new TransformGroup(t3d_vigaShimaki);
        tg_vigaShimaki.addChild(vigaShimaki);

        float kasagiAncho1 = 0.9f;
        float kasagiAlto1 = 0.05f;
        float kasagiPosV1 = shimakiPosV + shimakiAlto;
        Box vigaKasagi1 = new Box(kasagiAncho1, kasagiAlto1, pilarAncho + 0.05f, appNegro);
        Transform3D t3d_vigaKasagi1 = new Transform3D();
        t3d_vigaKasagi1.setTranslation(new Vector3d(0.0, kasagiPosV1, 0.0));
        TransformGroup tg_vigaKasagi1 = new TransformGroup(t3d_vigaKasagi1);
        tg_vigaKasagi1.addChild(vigaKasagi1);

        float kasagiAncho2 = 0.88f;
        float kasagiAlto2 = 0.08f;
        float kasagiPosV2 = kasagiPosV1 + kasagiAlto1;
        Box vigaKasagi2 = new Box(kasagiAncho2, kasagiAlto2, pilarAncho + 0.02f, appNegro);
        Transform3D t3d_vigaKasagi2 = new Transform3D();
        t3d_vigaKasagi2.setTranslation(new Vector3d(0.0, kasagiPosV2, 0.0));
        TransformGroup tg_vigaKasagi2 = new TransformGroup(t3d_vigaKasagi2);
        tg_vigaKasagi2.addChild(vigaKasagi2);

        tgModelo.addChild(tg_pilar1);
        tgModelo.addChild(tg_pilar2);
        tgModelo.addChild(tg_vigaNuki);
        tgModelo.addChild(tg_vigaShimaki);
        tgModelo.addChild(tg_vigaKasagi1);
        tgModelo.addChild(tg_vigaKasagi2);
        
        
        float y_max = kasagiPosV2 + (kasagiAlto2 / 2.0f);
        float y_min = -pilarAlto;
        float centroVertical = (y_max + y_min) / 2.0f; 
        
        Transform3D tCentrar = new Transform3D();
        tCentrar.setTranslation(new Vector3d(0.0, -centroVertical, 0.0));
        tgModelo.setTransform(tCentrar);
        
        
        Transform3D tScale = new Transform3D();
        tScale.setScale(0.8);
        
        Transform3D tTranslate = new Transform3D();
        tTranslate.setTranslation(new Vector3d(0.0, -0.1, 0.0)); 
        
        Transform3D initialTransform = new Transform3D(tTranslate);
        initialTransform.mul(tScale); 
        
        tgPosicion.setTransform(initialTransform);

        
        tgPosicion.addChild(tgRotacion);
        tgRotacion.addChild(tgModelo);
        root.addChild(tgPosicion);

        MouseRotate mr = new MouseRotate(tgRotacion); 
        mr.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
        root.addChild(mr); 

        AmbientLight ambient = new AmbientLight(new Color3f(0.5f, 0.5f, 0.5f));
        ambient.setInfluencingBounds(new BoundingSphere());
        root.addChild(ambient);
        
        DirectionalLight dirLight = new DirectionalLight(
                new Color3f(1.0f, 1.0f, 1.0f), 
                new Vector3f(-1.0f, -1.0f, -1.0f)
        );
        dirLight.setInfluencingBounds(new BoundingSphere());
        root.addChild(dirLight);

        Color3f colorFondo = new Color3f(0.9f, 0.9f, 0.9f); 
        Background fondo = new Background(colorFondo);
        BoundingSphere boundsFondo = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
        fondo.setApplicationBounds(boundsFondo);
        root.addChild(fondo);

        return root;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        escritorioPrincipal = new proyecto.JDesktopPaneConFondo();
        menuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        itemCerrarSesion = new javax.swing.JMenuItem();
        itemSalir = new javax.swing.JMenuItem();
        menuGestion = new javax.swing.JMenu();
        itemGestionEstudiantes = new javax.swing.JMenuItem();
        itemGestionRepresentantes = new javax.swing.JMenuItem();
        menuHistorial = new javax.swing.JMenu();
        itemVerHistorial = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menú Principal - Sistema de Administración de Dojo");

        escritorioPrincipal.setBackground(new java.awt.Color(45, 45, 45));

        javax.swing.GroupLayout escritorioPrincipalLayout = new javax.swing.GroupLayout(escritorioPrincipal);
        escritorioPrincipal.setLayout(escritorioPrincipalLayout);
        escritorioPrincipalLayout.setHorizontalGroup(
            escritorioPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        escritorioPrincipalLayout.setVerticalGroup(
            escritorioPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 579, Short.MAX_VALUE)
        );

        menuArchivo.setText("Archivo");
        menuArchivo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        itemCerrarSesion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        itemCerrarSesion.setText("Cerrar Sesión");
        itemCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCerrarSesionActionPerformed(evt);
            }
        });
        menuArchivo.add(itemCerrarSesion);

        itemSalir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        itemSalir.setText("Salir");
        itemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(itemSalir);

        menuBar.add(menuArchivo);

        menuGestion.setText("Gestión");
        menuGestion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        itemGestionEstudiantes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        itemGestionEstudiantes.setText("Estudiantes");
        itemGestionEstudiantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionEstudiantesActionPerformed(evt);
            }
        });
        menuGestion.add(itemGestionEstudiantes);

        itemGestionRepresentantes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        itemGestionRepresentantes.setText("Representantes");
        itemGestionRepresentantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemGestionRepresentantesActionPerformed(evt);
            }
        });
        menuGestion.add(itemGestionRepresentantes);

        menuBar.add(menuGestion);

        menuHistorial.setText("Historial");
        menuHistorial.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        itemVerHistorial.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        itemVerHistorial.setText("Exámenes y Certificados");
        itemVerHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVerHistorialActionPerformed(evt);
            }
        });
        menuHistorial.add(itemVerHistorial);

        menuBar.add(menuHistorial);

        setJMenuBar(menuBar);

        
        pack();
    }// </editor-fold>                        

   private ImageIcon cargarIconoEscalado(String nombreIcono, int ancho, int alto) {
        try {
            URL url = getClass().getResource("/assets/" + nombreIcono);
            
            if (url != null) {
                ImageIcon originalIcon = new ImageIcon(url);
                Image img = originalIcon.getImage();
                Image scaledImg = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            } else {
                 System.err.println("Icono no encontrado: /assets/" + nombreIcono);
            }
        } catch (Exception e) {
            System.err.println("Error al escalar icono: " + nombreIcono);
        }
        return null; 
    }
    
    private void itemCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        this.dispose();
        if (vistaLoginDeOrigen != null) {
            vistaLoginDeOrigen.setVisible(true);
        } else {
            new LoginView().setVisible(true);
        }
    }                                                

    private void itemSalirActionPerformed(java.awt.event.ActionEvent evt) {                                          
        System.exit(0);
    }                                         

    private void itemGestionEstudiantesActionPerformed(java.awt.event.ActionEvent evt) {                                                       
        EstudiantesView vistaEstudiantes = new EstudiantesView();
        escritorioPrincipal.add(vistaEstudiantes);
        vistaEstudiantes.setVisible(true);
        try {
            vistaEstudiantes.setMaximum(true); 
            vistaEstudiantes.setSelected(true); 
        } catch (java.beans.PropertyVetoException e) {}
    }                                                      

    private void itemGestionRepresentantesActionPerformed(java.awt.event.ActionEvent evt) {                                                          
        RepresentantesView vistaRepresentantes = new RepresentantesView();
        escritorioPrincipal.add(vistaRepresentantes);

        vistaRepresentantes.setVisible(true);
        try {
            vistaRepresentantes.setMaximum(true); 
            vistaRepresentantes.setSelected(true); 
        } catch (java.beans.PropertyVetoException e) {}
    }                                                         

    private void itemVerHistorialActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        HistorialView vistaHistorial = new HistorialView();
        escritorioPrincipal.add(vistaHistorial);
        vistaHistorial.setVisible(true);
        try {
            vistaHistorial.setMaximum(true); 
            vistaHistorial.setSelected(true); 
        } catch (java.beans.PropertyVetoException e) {}
    }                                                

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JDesktopPane escritorioPrincipal;
    private javax.swing.JMenuItem itemCerrarSesion;
    private javax.swing.JMenuItem itemGestionEstudiantes;
    private javax.swing.JMenuItem itemGestionRepresentantes;
    private javax.swing.JMenuItem itemSalir;
    private javax.swing.JMenuItem itemVerHistorial;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuGestion;
    private javax.swing.JMenu menuHistorial;
    // End of variables declaration                   

}