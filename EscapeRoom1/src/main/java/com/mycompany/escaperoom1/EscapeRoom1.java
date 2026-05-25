/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.escaperoom1;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class EscapeRoom1 extends JFrame {

    // Password richiesta per la valigia
    private final int FORZA_CODICE_1 = 5;
    private final int FORZA_CODICE_2 = 2;
    private final int FORZA_CODICE_3 = 3;
    private final int FORZA_CODICE_4 = 1;

    // Variabili per tracciare lo stato dei 4 rulli numerici
    private int num1 = 0;
    private int num2 = 0;
    private int num3 = 0;
    private int num4 = 0; 

    // STATI DEL GIOCO (Inventario e Progresso)
    private boolean valigiaAperta = false;
    private boolean haChiave = false; 
    private int statoSchermo = 0; // 0 = Vagone, 1 = Valigia, 2 = Mappa

    // Componenti Grafici Principali
    private JLabel labelSfondo;
    private JPanel pannelloDettaglio; // Il pannello centrale che non occupa tutto lo schermo
    
    private JButton bottoneValigietta, bottoneMappa, bottonePorta, btnIndietro;
    private JButton btnNum1, btnNum2, btnNum3, btnNum4, btnApri, bottoneChiaveInvisibile;

    // Icone salvate
    private ImageIcon imgVagone, imgValigiaChiusa, imgValigiaAperta, imgMappaGrande;

    public EscapeRoom1() {
        setTitle("Escape Room 1 - Il Vagone");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(null); 

        // Caricamento immagini di sfondo (Dimensione del pannello di dettaglio: 500x380)
        imgVagone = caricaIcona("/escapeimgs/vagone1.png", 800, 560);
        imgValigiaChiusa = caricaIcona("/escapeimgs/valigiaChiusa.png", 500, 380);
        imgValigiaAperta = caricaIcona("/escapeimgs/valigiaAperta.png", 500, 380);
        imgMappaGrande = caricaIcona("/escapeimgs/mappa.png", 500, 380);

        // 1. CREAZIONE DEL CONTENITORE DELLO SFONDO GENERALE (Sempre visibile sotto)
        labelSfondo = new JLabel(imgVagone);
        labelSfondo.setBounds(0, 0, 800, 560);
        labelSfondo.setLayout(null); 
        add(labelSfondo);

        // 2. CREAZIONE BOTTONI INTERATTIVI NEL VAGONE
        bottoneValigietta = creaBottoneTrasparente(260, 450, 120, 60);
        URL urlValigia = getClass().getResource("/escapeimgs/valigietta.png");
        if(urlValigia != null) {
            bottoneValigietta.setIcon(caricaIcona("/escapeimgs/valigietta.png", 120, 60));
        }

        bottoneMappa = creaBottoneTrasparente(150, 250, 80, 60);
        URL urlMappaIcona = getClass().getResource("/escapeimgs/mappa1.png");
        if (urlMappaIcona != null) {
            bottoneMappa.setIcon(caricaIcona("/escapeimgs/mappa1.png", 80, 60));
        }

        bottonePorta = creaBottoneTrasparente(680, 150, 100, 300);

        // 🛠️ 3. CREAZIONE DEL PANNELLO DI DETTAGLIO OVERLAY (CENTRATO, 500x430)
        // Usiamo un JPanel personalizzato per disegnare lo sfondo dell'oggetto zoomato
        pannelloDettaglio = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (statoSchermo == 1) {
                    g.drawImage(valigiaAperta ? imgValigiaAperta.getImage() : imgValigiaChiusa.getImage(), 0, 0, this);
                } else if (statoSchermo == 2) {
                    g.drawImage(imgMappaGrande.getImage(), 0, 0, this);
                }
            }
        };
        pannelloDettaglio.setBounds(150, 40, 500, 430); // Posizionato al centro dello schermo 800x600
        pannelloDettaglio.setLayout(null);
        pannelloDettaglio.setOpaque(false); // Sfondo trasparente per mostrare solo il disegno paintComponent
        pannelloDettaglio.setVisible(false);

        // 4. COMPONENTI INTERNI AL PANNELLO (RULLI GRIGI ORIGINARI)
        btnNum1 = new JButton(String.valueOf(num1));
        btnNum2 = new JButton(String.valueOf(num2));
        btnNum3 = new JButton(String.valueOf(num3));
        btnNum4 = new JButton(String.valueOf(num4)); 

        Font fontNumeri = new Font("Arial", Font.BOLD, 20);
        JButton[] rulli = {btnNum1, btnNum2, btnNum3, btnNum4};
        
        // Coordinate dei rulli relative all'interno del pannello di dettaglio
        int xIniziale = 140; 
        for (int i = 0; i < rulli.length; i++) {
            rulli[i].setFont(fontNumeri);
            rulli[i].setFocusPainted(false);
            rulli[i].setBackground(Color.DARK_GRAY);
            rulli[i].setForeground(Color.WHITE);
            rulli[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            rulli[i].setBounds(xIniziale + (i * 55), 150, 45, 40);
            pannelloDettaglio.add(rulli[i]);
        }

        // Tasto Apri/Sblocca valigia
        btnApri = new JButton("APRI");
        btnApri.setFont(new Font("Arial", Font.BOLD, 12));
        btnApri.setBounds(190, 210, 100, 30);
        btnApri.setBackground(new Color(34, 139, 34)); 
        btnApri.setForeground(Color.WHITE);
        btnApri.setFocusPainted(false);
        pannelloDettaglio.add(btnApri);

        // 🔑 BOTTONE CHIAVE COMPLETAMENTE INVISIBILE
        bottoneChiaveInvisibile = creaBottoneTrasparente(190, 140, 100, 60);
        pannelloDettaglio.add(bottoneChiaveInvisibile);

        // Bottone "Indietro" per chiudere il focus ed è posizionato sotto l'immagine (Y=390)
        btnIndietro = new JButton("⬅ Torna al Vagone");
        btnIndietro.setFont(new Font("Arial", Font.BOLD, 11));
        btnIndietro.setBounds(165, 390, 160, 25);
        btnIndietro.setBackground(Color.DARK_GRAY);
        btnIndietro.setForeground(Color.WHITE);
        btnIndietro.setFocusPainted(false);
        pannelloDettaglio.add(btnIndietro);

        // ==========================================
        //             LOGICHE DI GIOCO
        // ==========================================

        bottoneValigietta.addActionListener(e -> cambiaSchermo(1));
        bottoneMappa.addActionListener(e -> cambiaSchermo(2));
        btnIndietro.addActionListener(e -> cambiaSchermo(0));

        // Listener dei rulli numerici
        btnNum1.addActionListener(ev -> { num1 = (num1 + 1) % 10; btnNum1.setText(String.valueOf(num1)); });
        btnNum2.addActionListener(ev -> { num2 = (num2 + 1) % 10; btnNum2.setText(String.valueOf(num2)); });
        btnNum3.addActionListener(ev -> { num3 = (num3 + 1) % 10; btnNum3.setText(String.valueOf(num3)); });
        btnNum4.addActionListener(ev -> { num4 = (num4 + 1) % 10; btnNum4.setText(String.valueOf(num4)); });

        // Logica pulsante APRI
        btnApri.addActionListener(ev -> {
            if (num1 == FORZA_CODICE_1 && num2 == FORZA_CODICE_2 && num3 == FORZA_CODICE_3 && num4 == FORZA_CODICE_4) {
                valigiaAperta = true;
                aggiornaVisibilitaDettaglioValigia();
                pannelloDettaglio.repaint();
                JOptionPane.showMessageDialog(this, "Click! La valigia si è aperta.", "Serratura", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "Codice Errato!", "Serratura", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Logica click sulla chiave invisibile
        bottoneChiaveInvisibile.addActionListener(ev -> {
            haChiave = true;
            bottoneChiaveInvisibile.setVisible(false); 
            JOptionPane.showMessageDialog(this, "Hai raccolto: CHIAVE DELLA PORTA!", "Inventario", JOptionPane.INFORMATION_MESSAGE);
            pannelloDettaglio.repaint();
        });

        // Logica click sulla porta d'uscita
        bottonePorta.addActionListener(e -> {
            if (haChiave) {
                JOptionPane.showMessageDialog(this, "Usi la chiave dorata sulla serratura...\nLa porta si apre cigolando!\n\nCOMPLIMENTI, SEI FUGGITO DAL VAGONE!", "Vittoria!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0); 
            } else {
                JOptionPane.showMessageDialog(this, "La porta del vagone è bloccata dall'esterno.\nServe una chiave pesante per aprirla.", "Porta Chiusa", JOptionPane.WARNING_MESSAGE);
            }
        });

        // COMPOSIZIONE FINALE NELLO SFONDO
        labelSfondo.add(bottoneValigietta);
        labelSfondo.add(bottoneMappa); 
        labelSfondo.add(bottonePorta); 
        labelSfondo.add(pannelloDettaglio); // Aggiunto sopra al vagone

        cambiaSchermo(0);
    }

    // Gestione degli stati visivi della stanza
    private void cambiaSchermo(int nuovoStato) {
        statoSchermo = nuovoStato;

        if (statoSchermo == 0) { // VISUALE GENERALE VAGONE
            pannelloDettaglio.setVisible(false);
            bottoneValigietta.setVisible(true);
            bottoneMappa.setVisible(true);
            bottonePorta.setVisible(true);
        } 
        else if (statoSchermo == 1) { // FOCUS VALIGIA
            bottoneValigietta.setVisible(false);
            bottoneMappa.setVisible(false);
            bottonePorta.setVisible(false);
            pannelloDettaglio.setVisible(true);
            aggiornaVisibilitaDettaglioValigia();
        } 
        else if (statoSchermo == 2) { // FOCUS MAPPA
            bottoneValigietta.setVisible(false);
            bottoneMappa.setVisible(false);
            bottonePorta.setVisible(false);
            pannelloDettaglio.setVisible(true);
            
            // Nascondi elementi della valigia quando guardiamo la mappa
            btnNum1.setVisible(false); btnNum2.setVisible(false); btnNum3.setVisible(false); btnNum4.setVisible(false);
            btnApri.setVisible(false);
            bottoneChiaveInvisibile.setVisible(false);
        }
        pannelloDettaglio.repaint();
        labelSfondo.repaint();
    }

    private void aggiornaVisibilitaDettaglioValigia() {
        if (!valigiaAperta) {
            btnNum1.setVisible(true); btnNum2.setVisible(true); btnNum3.setVisible(true); btnNum4.setVisible(true);
            btnApri.setVisible(true);
            bottoneChiaveInvisibile.setVisible(false);
        } else {
            btnNum1.setVisible(false); btnNum2.setVisible(false); btnNum3.setVisible(false); btnNum4.setVisible(false);
            btnApri.setVisible(false);
            bottoneChiaveInvisibile.setVisible(!haChiave); // Attivo e invisibile solo se non hai preso la chiave
        }
    }

    private JButton creaBottoneTrasparente(int x, int y, int largh, int alt) {
        JButton btn = new JButton();
        btn.setBounds(x, y, largh, alt);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        return btn;
    }

    private ImageIcon caricaIcona(String percorso, int l, int a) {
        URL url = getClass().getResource(percorso);
        if (url != null) {
            return new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(l, a, Image.SCALE_SMOOTH));
        }
        System.err.println("ERRORE: Impossibile trovare il file '" + percorso + "'");
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EscapeRoom1().setVisible(true));
    }
}