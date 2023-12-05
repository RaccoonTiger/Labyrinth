package joueur;

/**
 * Définition du Joueur.
 * 
 * @author Fred Simard | ETS  && Antoine Bolduc et Stanley Vu
 * @version ETE 2018 - TP2
 */

import java.util.Vector;
import java.util.ArrayList;

/**
 * Cette classe représente le joueur humain. Elle surcharge le
 * personnage abstrait pour le déplacement et propose une méthode
 * pour mettre à jours la visibilité des cases en fonction de la vision.
 * 
 * @author Fred Simard | ETS
 * @version ETE 2018 - TP2
 */


import dongon.Case;
import equipements.AbstractEquipement;
import equipements.Arme;
import equipements.Armure;
import equipements.Casque;
import equipements.Potion;
import personnage.AbstractPersonnage;
import physique.Direction;
import physique.Position;

import static modele.PlanDeJeu.messagesPanneauBas;

public class Joueur extends AbstractPersonnage {

	private boolean mouvement = true;
	private final ArrayList<AbstractEquipement> equipementRamasses = new ArrayList<>();
	private Arme armeEquipe = null;
	private Casque casqueEquipe = null;
	private Armure armureEquipe = null;


	/**
	 * Construceur par paramètre
	 * @param , position du joueur
	 */
	public Joueur() {
		pointDeVie=100;
		pointDeVieMax=100;
	}

	public ArrayList<AbstractEquipement> getEquipements(){
		return this.equipementRamasses;
	}

	public Arme getArmeEquipe() {
		return armeEquipe;
	}

	public Casque getCasqueEquipe() {
		return casqueEquipe;
	}

	public Armure getArmureEquipe() {
		return armureEquipe;
	}

	/**
	 * surcharge de la méthode pour déplacer le joueur dans la direction donnée
	 * @param direction(int), direction du mouvement
	 */
	public void seDeplacer(int direction){
		
		if(mouvement) {
			// se déplacer
			super.seDeplacer(direction);
			
			// mise à jour de la vision
			mettreAJourVision();
		}
	}
	

	/**
	 * surcharge de la méthode pour placer le joueur à sa case de départ
	 * @param caseCourante(Case), case courante
	 */
	public void setCase(Case caseCourante){
		
		// assigne la case
		super.setCase(caseCourante);

		// mise à jour de la vision
		mettreAJourVision();
	}
	
	/**
	 * méthode qui mets à jour la vision
	 */
	private void mettreAJourVision(){
		
		// rend visible la case courante
		super.caseCourante.setDecouverte(true);
		
		// dans toutes les directions
		for(int i=0;i<Direction.NB_DIRECTIONS;i++){
			
			// dévoile les voisins jusqu'à la profondeur de la vision
			Case voisin = super.caseCourante.getVoisin(i);
			int PROFONDEUR_VISION = 2;
			for(int j = 0; j< PROFONDEUR_VISION; j++){
				if(voisin!=null){
					voisin.setDecouverte(true);
					voisin = voisin.getVoisin(i);
				}
			}
		}
	}

	public void setMouvement(boolean etat){
		this.mouvement = etat;
	}
	

	/**
	 * Remise à zéro du joueur
	 * - remet les points de vie à max
	 * - vide équipement
	 */
	public void remiseAZero(){
		this.pointDeVie = this.pointDeVieMax;
		equipementRamasses.clear();
		ajustementPoints();
		resetEquipement();

	}

	/**
	 * Permet de ramasser un objet au sol et de l'ajouter dans la liste
	 * d'équipement du joueur
	 * @param equipement equipement courant
	 */
	public void ramasser(AbstractEquipement equipement){
		equipement.setAuSol(false);
		equipementRamasses.add(equipement);
	}

	/**
	 * Permet de detecter un objet
	 * @param item item courant
	 */
	public boolean itemDetecte(AbstractEquipement item){
		boolean detecte = false;

		if(pos.equals(item.getPos())){
			detecte = true;

		}
		return detecte;
	}

	/**
	 * Permet au joueur d'équiper un equipement et fait l'ajustement
	 * de c'est nouveau points
	 * @param equipement equipement choisi
	 */
	public void equiper(AbstractEquipement equipement){

		enregistreEquipement(equipement);
		ajustementPoints();

	}

	/**
	 * Enregistrer un equipement au arme,casque ou armure courant
	 * @param equipement equipement choisi
	 */
	private void enregistreEquipement(AbstractEquipement equipement) {

		if(equipement instanceof Arme){
			armeEquipe = (Arme)equipement;
		} else if(equipement instanceof Casque){
			casqueEquipe = (Casque)equipement;

		} else if(equipement instanceof Armure){
			armureEquipe = (Armure)equipement;
		}

	}

	/**
	 * Fonction qui permet d'utiliser une potion
	 */
	public void utiliserPotion(){
		for(int i = 0;i<equipementRamasses.size();i++){
			if(equipementRamasses.get(i) instanceof Potion){
				equipementRamasses.remove(i);
				this.pointDeVie = pointDeVieMax;
			}
		}
	}

	/**
	 * Ajustement des points de l'armure
	 */
	private void ajustementPoints(){

		resetPointage();

		if(armureEquipe != null){
			armure += armureEquipe.getValeur();
		}

		if(casqueEquipe != null){
			armure += casqueEquipe.getValeur();
		}

		if(armeEquipe != null){
			bonusAttaque = armeEquipe.getValeur();
		}

	}

	private void resetPointage(){
		armure = 0;
		bonusAttaque = 0;
	}

	private void resetEquipement(){
		armeEquipe = null;
		casqueEquipe = null;
		armureEquipe = null;
	}

	public void toStringEquipements(){
		for (AbstractEquipement equipementRamass : equipementRamasses) {
			System.out.println("item" + equipementRamass);
		}
	}
}
