package modele;

import creature.AbstractCreature;
import joueur.Joueur;
import modele.GestionnaireCombat;
import observer.MonObserver;
import vue.PanneauPrincipal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FenetreCombat extends JFrame implements MonObserver {

    Joueur hero;
    AbstractCreature creature;
    GestionnaireCombat gestionnaireCombat;
    JTextArea textArea;
    JScrollPane scrollPane;
    PanneauPrincipal panneauPrincipal;

    public FenetreCombat(Joueur hero, AbstractCreature creature,
                         GestionnaireCombat gestionnaireCombat)
    {

        configFrame();
        //configImgHero();
        //configMsgBox();
        //configImgCreature();
        //this.requestFocus();
        //this.setVisible(true);

    }

    public void configFrame(){
        this.setContentPane(panneauPrincipal);
        //not sure comment utiliser getContentPane();

        this.panneauPrincipal.setLocation(600,300);
        this.panneauPrincipal.setSize(800,400);
        panneauPrincipal.setLayout(new GridLayout(0,3));

        this.addWindowListener(new WindowAdapter() {

            // gestionnaire d'événement
            public void windowClosing(WindowEvent we) {
                gestionnaireCombat.combatTermine();

                int result = JOptionPane.showConfirmDialog(null,
                        "Do you want to Exit ?", "Exit Confirmation : ",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION){
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                else if (result == JOptionPane.NO_OPTION){
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }

        });
    }

    // à demander pour voir si ca va brick le code
    @Override
    public void avertir() {

    }

}
