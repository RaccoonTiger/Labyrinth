package vue;

import joueur.Joueur;
import modele.PlanDeJeu;
import observer.MonObserver;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
/**
 * Définition du PanneauStatus qui s'occupe de faire la gestion des autres panneaux
 *
 * @author Antoine Bolduc et Stanley Vu
 */
public class PanneauStatus extends JPanel implements MonObserver {

    private final PanneauStatusHaut panneauStatusHaut;
    private final PanneauStatusMilieu panneauStatusMilieu;
    private final PanneauStatusBas panneauStatusBas;
    private final Dimension taillePanneau;

    public PanneauStatus(Dimension tailleEcran){

        //instanciation des panneaux
        panneauStatusHaut = new PanneauStatusHaut();
        panneauStatusMilieu = new PanneauStatusMilieu();
        panneauStatusBas = new PanneauStatusBas();

        PlanDeJeu planDeJeu = PlanDeJeu.getInstance();
        planDeJeu.attacherObserver(this);

        //instanciation de la taille du panneau
        taillePanneau = new Dimension((tailleEcran.width/3), tailleEcran.height);

        configPanneaux();
    }

    /**
     * Configuration du panneau
     */
    private void configPanneaux() {

        setSize(taillePanneau);
        setPreferredSize(taillePanneau);
        setLayout(new GridLayout(3,1));

        add(panneauStatusHaut);
        add(panneauStatusMilieu);
        add(panneauStatusBas);

        this.setVisible(true);
    }

    @Override
    public void avertir() {
        mettreAJoursInfoDeToutLesPan();
    }

    private void mettreAJoursInfoDeToutLesPan(){
        panneauStatusHaut.mettreAJoursInfo();
        panneauStatusMilieu.mettreAJoursInfo();
        panneauStatusBas.mettreAJoursInfo();
    }
}
