package vue;

/**
 * Panneau principal du jeu
 * 
 * contient:
 * - le panneau du donjon
 * 
 * @author Fred Simard | ETS
 * @version ETE 2018 - TP2
 */

import javafx.scene.shape.Box;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

public class PanneauPrincipal extends JPanel{

	Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
	
	// Panneaux
	public PanneauDonjon panDonjon;

	/**
	 * Constructeur
	 */
	public PanneauPrincipal() {
		
		// assigne la tâche
		setSize(tailleEcran);
		setPreferredSize(tailleEcran);
		
		// initialise les composantes
		initComposantes();
	}
	

	/*
	 * Dimensionne et ajoute les differents panneaux e leur place.
	 */
	private void initComposantes() {

		// définit le layout
		setLayout(new BorderLayout());
		
		// définit le panneau de donjon
		panDonjon = new PanneauDonjon(tailleEcran);
		add(panDonjon, BorderLayout.CENTER);

		PanneauStatus panneauStatus = new PanneauStatus(tailleEcran);
		add(panneauStatus, BorderLayout.LINE_END);

		panDonjon.requestFocus();
		
	}


}
