package personnage;

/**
 * Classe abstraite d'un personnage d'un jeu
 * 
 * @author Fred Simard | ETS
 * @version ETE 2018 - TP2
 */

import physique.Direction;
import physique.Position;

import java.util.Observable;

import dongon.AbstractObjet;
import dongon.Case;
import observer.MonObservable;

public abstract class AbstractPersonnage extends AbstractObjet {

	
	protected int armure=0;
	protected int force=10;
	protected int bonusAttaque = 0;
	protected int pointDeVie=100;
	protected int pointDeVieMax=100;
	protected int niveauJoueur = 1;
	protected int experienceCurrent=0;
	protected int experienceToLevel=100;

	/**
	 * constructeur
	 */
	
	public AbstractPersonnage(){}
	
	public AbstractPersonnage(Position pos){
		this.pos = pos;
	}
	
	/**
	 * méthode pour déplacer un personnage
	 * @param direction, direction du mouvement
	 */
	public void seDeplacer(int direction){
		
		// obtient une référence sur le voisin
		Case voisin = caseCourante.getVoisin(direction);
		
		// vérifie que le voisin est valide (ne l'est pas quand il y a un mur)
		if(voisin != null){
			
			// met à jour la position
			caseCourante = voisin;
			pos.additionnerPos(Direction.directionAPosition(direction));
			this.avertirLesObservers();
		}
	}

	public int getArmure() {
		return armure;
	}

	public int getForce() {
		return force + bonusAttaque;
	}

	public int getPointDeVie() {
		return pointDeVie;
	}
	
	public int getPointDeVieMax() {
		return pointDeVieMax;
	}

	public int getNiveauJoueur() {
		return niveauJoueur;
	}

	public int getExperienceCurrent() {
		return experienceCurrent;
	}

	public int getExperienceToLevel() {
		return experienceToLevel;
	}

	public void recoitCoup(int forceCoup) {
		forceCoup -= armure;
		
		if(forceCoup > 0){
			pointDeVie -= forceCoup;
		}
	}

	public boolean estVivant() {
		return (pointDeVie>0);
	}

	public void expGet(int exp){
		experienceCurrent += exp;
	}
	public boolean canLevelUp(){
		boolean canLevelUp = false;
		if (this.experienceCurrent >= this.experienceToLevel){
			levelUp();
			canLevelUp = true;
		}
		return canLevelUp;
	}
	public void levelUp(){
			toNextLevel();
			statLevelUp();
			System.out.println("Level up brah");
	}
	public void statLevelUp(){
		this.pointDeVieMax+=10;
		this.armure+=2;
		this.force+=5;
	}
	public void toNextLevel(){
		this.niveauJoueur++;
		this.experienceCurrent = 0;
		this.experienceToLevel += 10;
	}
}
