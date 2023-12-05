package vue;

import equipements.*;
import joueur.Joueur;
import modele.PlanDeJeu;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Définition de PanneauStatusMilieu
 *
 * @author Antoine Bolduc et Stanley Vu
 */
public class PanneauStatusMilieu extends JPanel implements ItemListener {

    private final Joueur joueur;
    private final JPanel panneauGauche;
    private final JPanel panneauDroit;
    private final PlanDeJeu planDeJeu = PlanDeJeu.getInstance();

    //elements de defense
    private JLabel defenseTotale;
    private JLabel casque;
    private JComboBox<Casque> boxCasque;
    private JLabel armure;
    private JComboBox<Armure> boxArmure;

    //elements d'attaque
    private JLabel attaqueTotale;
    private JLabel arme;
    private JComboBox<Arme> boxArme;

    //elements de soin
    private JLabel nbPotions;
    private JButton buttonPotion;

    public PanneauStatusMilieu(){

        joueur = planDeJeu.getJoueur();
        panneauDroit = new JPanel();
        panneauGauche = new JPanel();
        configPan();
    }

    /**
     * Configuration du panneau
     */
    private void configPan(){

        setLayout(new GridLayout(0,2));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        try{
            configPannneauGauche();
            configPannneauDroit();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        add(panneauGauche);
        add(panneauDroit);
    }

    /**
     * Configuration du panneau Gauche qui contient une image du hero
     * @throws IOException
     */
    private void configPannneauGauche() throws IOException {
        ajouterImageALafenetre();
    }

    /**
     * Ajouter une image au panneau de gauche
     * @throws IOException
     */
    private void ajouterImageALafenetre() throws IOException {
        Image image = ImageIO.read(new File(FenetreCombat.SRC_HERO));
        ImageIcon icon = new ImageIcon(image);
        panneauGauche.add(new JLabel(icon));
    }

    /**
     * Configuration du panneau droit
     */
    private void configPannneauDroit(){

        panneauDroit.setLayout(new GridLayout (0,1));

        defenseTotale = new JLabel("Defense Totale = "+0, SwingConstants.LEFT);
        casque = new JLabel("Casque:", SwingConstants.LEFT);
        boxCasque = new JComboBox<>();
        boxCasque.addItemListener(this);
        armure = new JLabel("Armure:", SwingConstants.LEFT);
        boxArmure = new JComboBox<>();
        boxArmure.addItemListener(this);

        attaqueTotale = new JLabel("Attaque Totale = "+0, SwingConstants.LEFT);
        arme = new JLabel("Arme:", SwingConstants.LEFT);
        boxArme = new JComboBox<>();
        boxArme.addItemListener(this);

        nbPotions = new JLabel("Nb Potions = "+0, SwingConstants.LEFT);
        buttonPotion = new JButton("Utiliser Potion");
        buttonPotion.setEnabled(false);
        buttonPotion.addActionListener(e -> joueur.utiliserPotion());

        ajouterElementAuPanneauDroit();
    }

    private void ajouterElementAuPanneauDroit(){

        panneauDroit.add(defenseTotale);
        panneauDroit.add(casque);
        panneauDroit.add(boxCasque);
        panneauDroit.add(armure);
        panneauDroit.add(boxArmure);
        panneauDroit.add(attaqueTotale);
        panneauDroit.add(arme);
        panneauDroit.add(boxArme);
        panneauDroit.add(nbPotions);
        panneauDroit.add(buttonPotion);
    }

    /**
     * Mise à jour les elements du panneau
     */
    public void mettreAJoursInfo(){

        attaqueTotale.setText("Attaque Totale = "+joueur.getForce());
        defenseTotale.setText("Defense Totale = "+joueur.getArmure());

        viderComboBox();

        int compteurPotion = 0;

        miseAJourDesComboBox(compteurPotion);

        setComboBoxParDefaut();

    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            Object item = event.getItem();
            joueur.equiper((AbstractEquipement) item);
        }
    }

    /**
     * Pour chaque ComboBox (Casque, Armure, Arme), ajoute d’abord une référence à
     * l’Équipement équipé, s’il y en a un
     */
    private void setComboBoxParDefaut(){

        if (joueur.getCasqueEquipe() != null){
            boxCasque.addItem(joueur.getCasqueEquipe());
        }
        if (joueur.getArmeEquipe() != null){
            boxArmure.addItem(joueur.getArmureEquipe());
        }
        if (joueur.getArmeEquipe() != null){
            boxArme.addItem(joueur.getArmeEquipe());
        }
    }

    /**
     * Vider les ComboBox
     */
    private void viderComboBox(){
        boxCasque.removeAllItems();
        boxArmure.removeAllItems();
        boxArme.removeAllItems();
    }

    private void miseAJourDesComboBox(int compteurPotion){

        //Obtient ensuite une référence à la liste d’équipement: joueur.getEquipements()
        ArrayList<AbstractEquipement> equipementArraylist = joueur.getEquipements();

        for (AbstractEquipement equipement : equipementArraylist) {

            if (equipement instanceof Casque) {
                boxCasque.addItem((Casque) equipement);
            }
            if (equipement instanceof Armure) {
                boxArmure.addItem((Armure) equipement);
            }
            if (equipement instanceof Arme) {
                boxArme.addItem((Arme) equipement);
            }
            if (equipement instanceof Potion) {
                compteurPotion++;
                nbPotions.setText("Nb Potions = " + compteurPotion);
            }

            nbPotions.setText("Nb Potions = " + compteurPotion);
            buttonPotion.setEnabled(compteurPotion > 0);
        }
    }

}
