/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.escaperoomserio;

/**
 *
 * @author Utente01
 */
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class EscapeRoomSerio extends Canvas implements KeyListener {

    private static final int larghezza = 1280;
    private static final int altezza = 720;
    private static final String nome_gioco = "The Void Train";
    
    BufferedImage vagone1=null;
    BufferedImage mappa=null;
    BufferedImage vagone2=null;
    BufferedImage valigiaAperta=null;
    
    public EscapeRoomSerio(){
        caricaRisorse();
    }
 
   

    public static void main(String[] args) {
        
        EscapeRoomSerio treno= new EscapeRoomSerio();

        JFrame finestra_gioco = new JFrame(nome_gioco);

        Dimension dimensione_finestra = new Dimension(larghezza, altezza);
        finestra_gioco.setPreferredSize(dimensione_finestra);
        finestra_gioco.setMaximumSize(dimensione_finestra);
        finestra_gioco.setResizable(false);

        finestra_gioco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        finestra_gioco.add(treno);
        finestra_gioco.addKeyListener(treno);

        finestra_gioco.pack();
        finestra_gioco.setVisible(true);
    }
    private void caricaRisorse() {
        CaricatoreImmagini loader = new CaricatoreImmagini();
        //C:\Users\Utente01\Documents\NetBeansProjects\EscapeRoomSerio\src\main\java\escapeimgs\vagone1.png
        vagone1 = loader.caricaImmagine("src/main/java/escapeimgs/vagone1.png");
        mappa= loader.caricaImmagine("src/main/java/escapeimgs/mappa.png");
        vagone2 = loader.caricaImmagine("src/main/java/escapeimgs/vagone2.png");
        valigiaAperta = loader.caricaImmagine("src/main/java/escapeimgs/valigiaAperta.png");
        System.out.println("Risorse caricate!");
    }

    private void disegna() {
        Graphics g = this.getGraphics();

        g.drawImage(vagone1, 0, 0, larghezza, altezza, this);

        g.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
    disegna();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
