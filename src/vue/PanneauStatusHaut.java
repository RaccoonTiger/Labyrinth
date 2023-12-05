package vue;

import creature.AbstractCreature;
import joueur.Joueur;
import modele.GestionnaireCombat;
import modele.PlanDeJeu;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.Timer;

/**
 * Définition du PanneauStatusHaut
 *
 * @author Antoine Bolduc et Stanley Vu
 */
public class PanneauStatusHaut extends JPanel{

    private JLabel labelNumeroNiveau,labelNbEnnemiTue,tempsDeJeu,labelHero;
    private JProgressBar progressBar;
    private final PlanDeJeu planDeJeu;
    Joueur joueur;
    Vector<AbstractCreature> creatures;
    int compteurEnnemiTue=0;

    public PanneauStatusHaut(){
        planDeJeu = PlanDeJeu.getInstance();
        configPan();
        joueur = planDeJeu.getJoueur();
        creatures = planDeJeu.getCreatures();
    }

    /**
     * Configuration du panneau
     */
    private void configPan(){

        setLayout(new GridLayout(0,1));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        confLabelHero();
        confProgressBar();
        confLabelNumeroNiveau();
        confLabelNbEnnemiTue();
        confTempsDeJeu();

        ajouterElementAuPanneau();

        setVisible(true);
    }

    /**
     * Mise à jour les elements du panneau
     */
    public void mettreAJoursInfo(){

        progressBar.setValue(joueur.getPointDeVie()*100/joueur.getPointDeVieMax());

        labelNumeroNiveau.setText("Niveau: "+planDeJeu.getNiveau());

        if(joueur.estVivant()){
            compteurEnnemiTue = planDeJeu.getGestionnaireCombat().getCompteurMonstreTuees();
            labelNbEnnemiTue.setText("Nb Ennemis Tues: "+compteurEnnemiTue);
        }

        if(!joueur.estVivant()){
            compteurEnnemiTue=0;
        }

        tempsDeJeu.setText("Temps de jeu: "+planDeJeu.getTimer()+" secondes");
    }

    private void confLabelHero(){
        String NOM_HERO = "Leeroy Jenkins";
        labelHero = new JLabel(NOM_HERO,SwingConstants.CENTER);
        labelHero.setFont(new Font(labelHero.getFont().getName(), Font.BOLD+Font.ITALIC,24));
    }

    private void confLabelNumeroNiveau(){
        labelNumeroNiveau = new JLabel("Niveau: "+0,SwingConstants.CENTER);
    }

    private void confLabelNbEnnemiTue(){
        labelNbEnnemiTue = new JLabel("Nb Ennemis Tues: "+0,SwingConstants.CENTER);
    }

    private void confTempsDeJeu(){
        tempsDeJeu = new JLabel("Temps de jeu: "+0+" secondes",SwingConstants.CENTER);
    }

    private void confProgressBar(){
        progressBar = new JProgressBar();
        progressBar.setValue(100);
        progressBar.setForeground(Color.GREEN);
        progressBar.setBackground(Color.RED);
    }

    private void ajouterElementAuPanneau(){
        add(labelHero);
        add(progressBar);
        add(labelNumeroNiveau);
        add(labelNbEnnemiTue);
        add(tempsDeJeu);
    }
}
