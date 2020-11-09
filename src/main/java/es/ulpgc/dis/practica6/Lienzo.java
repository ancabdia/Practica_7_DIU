/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ulpgc.dis.practica6;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Andres
 */
public class Lienzo extends JPanel {

    private static BufferedImage imagen = null;
    private static String fileName;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, this);
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
        if (fileName == null) {
            imagen = null;
        } else {
            try {
                File fichero = new File(fileName);
                imagen = ImageIO.read(fichero);
                if(imagen.getWidth() > 1024 || imagen.getHeight() > 768){
                    fichero = null;
                    imagen = null;
                    JOptionPane.showMessageDialog(this, "Image size must be 1024x768 maximum", "Error loading image", JOptionPane.ERROR_MESSAGE);
                }else{
                    this.setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
                }
            } catch (IOException ex) {
                Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public BufferedImage getImagen(){
        return imagen;
    }
    
    public void filtrarImagen(int i){
        Mat m = Imgcodecs.imread(fileName);
        Mat m2 = threshold(m, i);
        imagen = (BufferedImage) HighGui.toBufferedImage(m2);
    }

    private Mat threshold(Mat imagen_original, Integer umbral) {
        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(imagen_original.rows(),
                imagen_original.cols(),
                CvType.CV_8U);
        Mat imagenUmbralizada = new Mat(imagen_original.rows(),
                imagen_original.cols(),
                CvType.CV_8U);
        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(imagen_original,
                imagenGris,
                Imgproc.COLOR_BGR2GRAY);
        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris,
                imagenUmbralizada,
                umbral,
                255,
                Imgproc.THRESH_BINARY);
        // se devuelve la imagen umbralizada
        return imagenUmbralizada;
    }
}
