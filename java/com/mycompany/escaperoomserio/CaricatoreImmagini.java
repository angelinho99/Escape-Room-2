/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.escaperoomserio;

/**
 *
 * @author Utente01
 */
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class CaricatoreImmagini  {

    BufferedImage image;

    public BufferedImage caricaImmagine(String posizione) {
        try {
            image = ImageIO.read(getClass().getResource(posizione));
        } catch (IOException ex) {
            System.out.print("Immagine alla posizione:" + posizione + " caricata correttamente");
            Logger.getLogger(CaricatoreImmagini.class.getName()).log(Level.SEVERE, null, ex);
        }

        return image;
    }
}
