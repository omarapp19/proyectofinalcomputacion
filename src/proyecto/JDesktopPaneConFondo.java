package proyecto;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import java.net.URL; 

public class JDesktopPaneConFondo extends JDesktopPane {

    private Image imagenFondo;

    public JDesktopPaneConFondo() {
        
      
        String nombreImagen = "/assets/fondom.jpg"; 


        try {
          
            URL url = getClass().getResource("/assets/fondom.jpg");
            
            if (url == null) {
                System.err.println("No se encontró la imagen de fondo del menú: " + nombreImagen);
                imagenFondo = null;
            } else {
                imagenFondo = new ImageIcon(url).getImage();
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imagen de fondo del menú: " + e.getMessage());
            imagenFondo = null;
        }
    }

    
    @Override
    protected void paintComponent(Graphics g) {
    
        super.paintComponent(g);

 
        if (imagenFondo != null) {
       
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}