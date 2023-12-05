package vue;

import equipements.Arme;
import equipements.Armure;
import equipements.Casque;
import joueur.Joueur;
import creature.AbstractCreature;
import creature.Araigne;
import creature.Minotaure;
import creature.Dragon;
import observer.MonObserver;
import modele.GestionnaireCombat;
import vue.PanneauPrincipal;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Définition de la fenetre de combat
 *
 * @author Antoine Bolduc et Stanley Vu
 */
public class FenetreCombat extends JFrame implements MonObserver {

    //Pas nécessaire d'après intellij, mais mentionner dans l'énoncer
    private Joueur joueur;
    private AbstractCreature creature;

    private final GestionnaireCombat gestionnaireCombat;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JPanel panneauPrincipal;
    public static final String SRC_HERO = "images/hero.png";
    public final String SRC_ARAIGNE = "images/spider.png";
    public final String SRC_MINOTAURE = "images/minotaur.png";
    public final String SRC_DRAGON = "images/dragon.png";

    public FenetreCombat(Joueur hero, AbstractCreature creature,
                         GestionnaireCombat gestionnaireCombat)
    {
        this.gestionnaireCombat = gestionnaireCombat;
        this.joueur = hero;
        this.creature = creature;

        try {
            configFrame();
            configImgHero();
            configMsgBox();
            configImgCreature(creature);

        } catch (IOException e) {
            e.printStackTrace();
        }
        requestFocus();
        this.setVisible(true);
    }

    /**
     * Configuration du Frame
     */
    private void configFrame(){
        panneauPrincipal = (JPanel) getContentPane();
        setFrame();
        this.addWindowListener(new WindowAdapter() {
            // gestionnaire d'événement
            public void windowClosing(WindowEvent we) {
                gestionnaireCombat.combatTermine();
            }
        });
    }

    private void configImgHero() throws IOException {
        ajouterImageALafenetre(SRC_HERO);
    }

    private void configMsgBox(){
        setTextArea();
        setScrollPane();
        panneauPrincipal.add(scrollPane);
    }

    private void configImgCreature(AbstractCreature creature) throws IOException {
        if(creature instanceof Araigne){
            ajouterImageALafenetre(SRC_ARAIGNE);

        } else if(creature instanceof Minotaure){
            ajouterImageALafenetre(SRC_MINOTAURE);

        } else if(creature instanceof Dragon){
            ajouterImageALafenetre(SRC_DRAGON);
        }
    }

    @Override
    public void avertir() {
        textArea.append(gestionnaireCombat.getMsg());
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private void ajouterImageALafenetre(String src) throws IOException {
        Image image = ImageIO.read(new File(src));
        add(new JLabel(new ImageIcon(image)));
    }

    private void setTextArea(){
        textArea = new JTextArea();
        textArea.setSize(16,20);
        textArea.setEditable(false);
    }
    private void setScrollPane(){
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    private void setFrame(){
        setLocation(600,300);
        setSize(800,400);
        setLayout(new GridLayout(0,3));
    }

}
