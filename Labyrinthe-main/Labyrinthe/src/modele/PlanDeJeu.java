package modele;

/**
 * Le plan de jeu est la classe qui supporte le modèle du programme.
 * Il contient:
 * 	- le donjon
 *  - le joueur
 *  - les créatures
 *  
 * et actionne les mécaniques du jeu.
 * 
 * Le plan de jeu est implémenté en Lazy Singleton
 * 
 * @author Fred Simard | ETS
 * @modifier Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */


import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import donjon.Case;
import donjon.Configuration;
import donjon.Donjon;
import observer.MonObservable;
import observer.MonObserver;
import personnage.*;
import physique.Direction;
import physique.Position;

import javax.swing.*;

public class PlanDeJeu extends MonObservable implements MonObserver, Runnable {

	private Donjon donjon;
	private boolean partieEnCours;
	private int niveauCourant;
	private Random rand = new Random(System.currentTimeMillis());
	
	private static final PlanDeJeu instance = new PlanDeJeu();
	private static Thread t;

	private Vector<AbstractCreature> abstractCreature = new Vector<>();

	//Constantes des monstres
	final int ARAIGNEE = 0;
	final int MINOTAURE = 1;
	final int DRAGON = 2;

	/**
	 * constructeur du plan de jeu
	 */
	public PlanDeJeu() {
		partieEnCours = true;
		nouveauNiveau();
	}

	/**
	 * Initialisation d'une creature
	 */
	private void initCreature(){
		abstractCreature.removeAllElements();

		for( int i = 0; i < Configuration.NB_CREATURES ;i++){
			int creatureAleatoire = rand.nextInt(Configuration.NB_CREATURES);
			Position position = donjon.getPositionAlea();

			Case caseAlea = new Case(position);

			AbstractCreature creature = choisirTypeCreature(creatureAleatoire,position);
			creature.attacherObserver(this);
			creature.setCase(caseAlea);
			abstractCreature.add(creature);
		}
	}

	/**
	 * Choisir un type de creature en fonction d un numero et instancie la creature avec une
	 * position en parametre.
	 * @param numeroDeSerieCreature
	 * @param pos
	 * @return une creature
	 */
	public AbstractCreature choisirTypeCreature(int numeroDeSerieCreature, Position pos){

		switch(numeroDeSerieCreature){
			case ARAIGNEE:
				return new Araigne(pos);
			case MINOTAURE:
				return new Minotaure(pos);
			case DRAGON:
				return new Dragon(pos);
			default:
				throw new IllegalStateException("Unexpected value: " + numeroDeSerieCreature);
		}
	}
	/**
	 * méthode pour obtenir une référence au plan de jeu
	 * @return l'instance
	 */
	public static PlanDeJeu getInstance(){
		return instance;
	}
	
	/**
	 * méthode pour obtenir une référence au donjon
	 * @return référence au donjon
	 */
	public Donjon getDonjon(){
		return this.donjon;
	}

	@Override
	/**
	 * callback implémenté par l'observer
	 */
	public void avertir() {
		
		// vérifie les règles du jeu
		validerEtatJeu();
		
		// avertie les observers du plan de jeu
		this.avertirLesObservers();
	}

	@Override
	/**
	 * implémente la méthode run de Runnable
	 */
	public void run() {

		// tant qu'une partie est en cours
		while(partieEnCours){
			
			// déplace toutes les créatures à compléter
			for(int i = 0; i<abstractCreature.size(); i++){
				abstractCreature.get(i).seDeplacer(Direction.obtenirDirAlea());
			}
			
			// attend X nombre de secondes
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * méthode qui valide les règles du jeu
	 */
	private void  validerEtatJeu(){
		

		// verifie si le joueur vient de trouver de l'équipement
			// oui, ajoute à l'inventaire
		
		// verifie s'il y a un combat
			// oui, fais la résolution du combat

		// verifie si le joueur est mort...
			// oui, partie perdu

	
	}
	
	/**
	 * méthode qui lance un nouveau niveau
	 */
	private void  nouveauNiveau() {
		
		// la partie est toujours en cours
		partieEnCours = true;
		// crée un nouveau donjon
		this.donjon = new Donjon();
		
		initCreature();
		
		// si la tâche qui gère les créature
		// n'a pas encore été lancé, la lance.
		if(t ==null){
			t = new Thread(this);
			t.start();
		}
		
	}

	/**
	 * méthode qui gère une partie gagné
	 */
	private void partieGagne() throws Exception {
		
		// incrémente le compteur de niveau
		niveauCourant++;
		
		// obtient les configs
		Configuration config = Configuration.getInstance();
		int nbCols = (int)config.getConfig(Configuration.NB_COLONNES);
		int nbLignes = (int)config.getConfig(Configuration.NB_LIGNES);
		int nbCreatures = (int)config.getConfig(Configuration.NB_CREATURES);
		// mets à jours les configs
		config.setConfig(Configuration.NB_COLONNES,nbCols+1);
		config.setConfig(Configuration.NB_LIGNES,nbLignes+1);
		config.setConfig(Configuration.NB_CREATURES,nbCreatures+2);
		
		// lance un nouveau niveau
		nouveauNiveau();
	}

	public Vector<AbstractCreature> getCreatures(){
		return new Vector<>(abstractCreature);
	}

	/**
	 * gestion d'une partie perdu
	 */
	private void partiePerdu(){
		
		// plus de partie en cours
		partieEnCours = false;
		
		// attend la fin de la tâche
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// remise à zéro du jeu
		Configuration.remiseAZero();
	}

	public Joueur getJoueur() {
		return new Joueur(new Position(0,0));
	}

}
