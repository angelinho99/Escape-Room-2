/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.escaperoom1; // Cambia con il nome del tuo pacchetto reale se necessario

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class EscapeRoom1 extends JFrame {

    private BufferedImage sfondo;

    public EscapeRoom1() {
        // 1. IMPOSTAZIONI DELLA FINESTRA PRINCIPALE
        setTitle("Escape Room 1 - Il Vagone");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra sullo schermo
        
        // Layout null fondamentale per posizionare i bottoni usando le coordinate X e Y pixel per pixel
        setLayout(null); 

        // 2. CARICAMENTO DELL'IMMAGINE DI SFONDO PRINCIPALE
        try {
            URL urlSfondo = getClass().getResource("/escapeimgs/vagone1.png");
            if (urlSfondo == null) {
                throw new IOException("Sfondo 'vagone1.png' non trovato!");
            }
            sfondo = ImageIO.read(urlSfondo);
            System.out.println("Sfondo principale caricato con successo!");
        } catch (IOException e) {
            System.err.println("ERRORE CRITICO: Impossibile caricare lo sfondo del vagone!");
            e.printStackTrace();
        }

        // 3. CREAZIONE DEL BOTTONE-IMMAGINE (L'elemento cliccabile nel vagone)
        JButton bottoneInterattivo = new JButton();
        
        URL urlBottoneImg = getClass().getResource("/escapeimgs/bottoneValigia.png"); // <--- CAMBIA CON IL TUO FILE DI INPUT
        if (urlBottoneImg != null) {
            int larghezzaBottone = 120; 
            int altezzaBottone = 60;   
            
            ImageIcon iconaOrig = new ImageIcon(urlBottoneImg);
            Image imgScalata = iconaOrig.getImage().getScaledInstance(larghezzaBottone, altezzaBottone, Image.SCALE_SMOOTH);
            bottoneInterattivo.setIcon(new ImageIcon(imgScalata));
        } else {
            System.err.println("Avviso: Immagine del bottone non trovata, verrà mostrato un pulsante vuoto.");
        }

        // Rendiamo il bottone completamente trasparente (si vedrà SOLO la tua immagine)
        bottoneInterattivo.setBorderPainted(false);  
        bottoneInterattivo.setContentAreaFilled(false); 
        bottoneInterattivo.setFocusPainted(false);     
        bottoneInterattivo.setOpaque(false);           

        // Posizioniamo il bottone sul vagone: X=350, Y=420, Larghezza=120, Altezza=60
        // Modifica X e Y per spostarlo sopra l'oggetto corretto del tuo sfondo
        bottoneInterattivo.setBounds(350, 420, 120, 60); 

        // 4. LOGICA DEL CLICK: APERTURA DELL'OGGETTO POP-UP SENZA SFONDO
        bottoneInterattivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                URL urlDettaglio = getClass().getResource("/escapeimgs/valigiaAperta.png"); // <--- CAMBIA CON IL TUO FILE DI INPUT (PNG Trasparente)
                
                if (urlDettaglio != null) {
                    // Creiamo una finestra di dialogo modale (blocca il gioco finché è aperta)
                    JDialog finestraDettaglio = new JDialog(EscapeRoom1.this, true);
                    
                    // Rimuoviamo la barra del titolo e i bordi del sistema operativo
                    finestraDettaglio.setUndecorated(true);
                    
                    // Rendiamo la finestra invisibile così si vedrà solo il PNG ritagliato
                    finestraDettaglio.setBackground(new Color(0, 0, 0, 0)); 

                    // Carichiamo e ridimensioniamo l'oggetto da mostrare in primo piano
                    int larghezzaImg = 400; 
                    int altezzaImg = 300;   
                    ImageIcon iconaOriginale = new ImageIcon(urlDettaglio);
                    Image imgScalata = iconaOriginale.getImage().getScaledInstance(larghezzaImg, altezzaImg, Image.SCALE_SMOOTH);
                    
                    JLabel labelImmagine = new JLabel(new ImageIcon(imgScalata));
                    finestraDettaglio.add(labelImmagine);
                    
                    // Impacchettiamo la finestra attorno all'immagine e centriamola rispetto al gioco
                    finestraDettaglio.pack();
                    finestraDettaglio.setLocationRelativeTo(EscapeRoom1.this);

                    // Chiudiamo il pop-up quando il giocatore clicca in un punto qualsiasi dell'oggetto
                    labelImmagine.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            finestraDettaglio.dispose(); // Chiude il dettaglio e torna al vagone
                        }
                    });

                    // Mostriamo l'oggetto a schermo
                    finestraDettaglio.setVisible(true);
                } else {
                    System.err.println("Errore: Impossibile trovare l'immagine del dettaglio pop-up!");
                    JOptionPane.showMessageDialog(EscapeRoom1.this, "Immagine di dettaglio non trovata!");
                }
            }
        });

        // Aggiungiamo il bottone grafico alla finestra del gioco
        add(bottoneInterattivo);
    }

    // 5. DISEGNO DELLO SFONDO PRINCIPALE
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (sfondo != null) {
            // Usiamo gli Insets per evitare che i bordi della finestra o la barra del titolo coprano l'immagine
            Insets insets = getInsets();
            int x = insets.left;
            int y = insets.top;
            int larghezza = getWidth() - insets.left - insets.right;
            int altezza = getHeight() - insets.top - insets.bottom;
            
            g.drawImage(sfondo, x, y, larghezza, altezza, this);
        }
    }

    // 6. AVVIO DEL GIOCO
    public static void main(String[] args) {
        // Eseguiamo l'interfaccia grafica nel thread corretto di Swing
        SwingUtilities.invokeLater(() -> {
            new EscapeRoom1().setVisible(true);
        });
    }
}

