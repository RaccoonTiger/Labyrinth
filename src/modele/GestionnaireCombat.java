package modele;

/**
 * Gestionnaire des combats prenant place entre le hero et les
 * creatures du donjon.
 *
 * Le gestionnaire lance un pop-up window qui affiche les détails du combat.
 * Le combat s'exécute dans le gestionnaire qui génère une liste de messages.
 * La fenêtre agit comme observer et affiche les messages générés.
 *
 * @author Fred Simard | ETS
 * @version ETE 2018 - TP3
 */


import java.util.ArrayList;

import creature.AbstractCreature;
import joueur.Joueur;
import observer.MonObservable;
import personnage.AbstractPersonnage;
import vue.FenetreCombat;

import static modele.PlanDeJeu.messagesPanneauBas;

public class GestionnaireCombat extends MonObservable implements Runnable{

	private final int TEMP_DELAI = 500;
	private AbstractPersonnage hero, creature;
	private boolean combatEnCours = false;
	private ArrayList<String> messages = new ArrayList<String>();
	private int compteurMonstreTuees = 0;

	/**
	 * Méthode qui lance la fenetre pop-up, et la tâche qui exécute le combat
	 * @param hero, le hero du donjon
	 * @param creature, la creature avec qui il y a un combat
	 */
	public void executerCombat(Joueur hero, AbstractCreature creature){

		// efface les messages
		messages.clear();
		messagesPanneauBas.add("Debut combat");

		// indique qu'un combat est en cours (suspend l'exécution du donjon)
		combatEnCours = true;

		// copie les références
		this.hero = hero;
		this.creature = creature;

		// lance le pop-up window
		// (à compléter)
		FenetreCombat fenetreCombat = new FenetreCombat(hero,creature,this);
		attacherObserver(fenetreCombat);

		// lance la tâche qui gère le combat
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Tâche qui exécute le combat
	 * Boucle tant que les deux protagoniste sont vivant. Alterne les coups
	 * données entre le hero et la creature.
	 */
	public void run() {

		do{
			// hero donne le premier coup
			int forceCoupDonne = hero.getForce();
			creature.recoitCoup(forceCoupDonne);

			// ajoute les messages et mets à jours la fenetre
			messages.add("Joueur donne un coup: " + forceCoupDonne);
			messages.add("Point de vie creature: " + creature.getPointDeVie());
			//espace pour mieux voir
			messages.add(" ");
			this.avertirLesObservers();

			// attend un peu
			try {
				Thread.sleep(TEMP_DELAI);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// si la creature est toujours vivante
			if(creature.estVivant()){

				// creature donne un coup
				forceCoupDonne = creature.getForce();

				hero.recoitCoup(forceCoupDonne);

				// ajoute les messages
				messages.add("Creature donne un coup: " + forceCoupDonne);
				messages.add("Point de vie joueur: " + hero.getPointDeVie());
				messages.add(" ");
				this.avertirLesObservers();
			}else{

				// si la creature est morte, c'est la fin du combat
				messages.add("Creature vaincu");
				messagesPanneauBas.add("Combat gagne");
				// affichage resultat combat, exp obtenu
				combatResults();
				this.avertirLesObservers();
			}

			// attend un peu
			try {
				Thread.sleep(TEMP_DELAI);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// si le hero est mort
			if(!hero.estVivant()){

				// ajoute un message
				compteurMonstreTuees = 0;
				messages.add("Joueur vaincu");
				messagesPanneauBas.add("Combat perdu");
				this.avertirLesObservers();
			}

			// boucle tant que le hero et la creature sont vivants
		}while(hero.estVivant() && creature.estVivant());

	}

	/**
	 * informatrice pour savoir s'il y a un combat en cours.
	 * @return true/false, indiquant si un combat est en cours
	 */
	public boolean combatEstEnCours(){
		return combatEnCours;
	}

	/**
	 * mutatrice pour indiquer que le combat est termine
	 */
	public void combatTermine(){
		combatEnCours = false;
	}

	/**
	 * methode pour obtenir la liste des messages du combat
	 * @return chaine de caracteres contenant tous les messages avec des sauts de lignes
	 */
	public String getMsg(){

		String str = "";
		for (int i = 0; i < messages.size(); ++i){
			str += messages.get(i) + "\n";
		}
		return str;
	}

	public void combatResults(){
		compteurMonstreTuees++;
		hero.expGet(creature.getExperienceCurrent());
		messages.add("exp get :" + creature.getExperienceCurrent());
		// on verifie level up
		if(hero.canLevelUp()){
			hero.levelUp();
			messages.add("Level up!");
			messagesPanneauBas.add("Level up!");
		}
		expRequiredNextLevel();
	}

	public void expRequiredNextLevel(){
		int currentExp = hero.getExperienceCurrent();
		int toNextLvl = hero.getExperienceToLevel();
		messagesPanneauBas.add("Exp: " + currentExp + "/ " + toNextLvl);
	}

	public int getCompteurMonstreTuees() {
		return compteurMonstreTuees;
	}
}

