/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ulpgc.dis.practica7;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


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
            this.setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
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
                this.setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
                revalidate();
            } catch (IOException ex) {
                Logger.getLogger(Lienzo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public BufferedImage getImagen(){
        return imagen;
    }
    
    public Mat filtrarImagen(){
        Mat m = Imgcodecs.imread(fileName);
        return m;
    }    
}
