package vue;

import modele.PlanDeJeu;


import javax.swing.*;

import static modele.PlanDeJeu.messagesPanneauBas;

/**
 * Définition du PanneauStatusBas
 *
 * @author Antoine Bolduc et Stanley Vu
 */
public class PanneauStatusBas extends JPanel{

    private JTextArea textArea;
    private JScrollPane scrollPane;
    private final PlanDeJeu planDeJeu = PlanDeJeu.getInstance();

    public PanneauStatusBas(){
        configPan();
    }

    /**
     * Configuration du panneau
     */
    private void configPan(){
        setTextArea();
        setScrollPane();
        add(scrollPane);
    }

    private void setTextArea(){
        textArea = new JTextArea(20,50);
        textArea.setEditable(false);
    }
    private void setScrollPane(){
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    /**
     * Mise à jour les elements du panneau
     */
    public void mettreAJoursInfo(){

        int compteur = 0;
        textArea.append(getMsgPanneauBas());
        textArea.setCaretPosition(textArea.getDocument().getLength());
        //clear pour eviter les doublons dans l affichage
        messagesPanneauBas.clear();
    }

    /**
     * Extraire les message de l array list messagesPanneauBas
     * @return un string qui contient les messages
     */
    public String getMsgPanneauBas(){

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < messagesPanneauBas.size(); ++i){
            str.append(messagesPanneauBas.get(i)).append("\n");
        }
        return str.toString();
    }
}
