/**
 * Le donjon est un tableau 2D de cases
 *
 * @author Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */
package donjon;

import physique.Position;
import physique.Direction;
import pile.PileSChainee;

import java.util.Random;

public class Donjon {

    //Champs membres
    private Case depart;
    private Case fin;
    private Case[][] casesDonjon;
    private Random rand = new Random();
    Configuration config = Configuration.getInstance();
    int ligne;
    int colonne;
    final int NB_DIRECTION_MAX = 4;

    //Constructeur par defaut
    public Donjon(){
        //référence aux configurations
        //initialisation du tableau 2D
        //définir une case départ choisie au hasard
        this.ligne = this.config.getConfig(0);
        this.colonne = this.config.getConfig(1);
        this.casesDonjon = new Case[this.ligne][this.colonne];
        this.depart = new Case(getPositionAlea());

        try{
            this.produireLabyrinthe();
        }
        catch(Exception e){
            System.out.println("[Donjon::produireLabyrinthe] "+e.getMessage());
        }
        this.fin.setFin(true);
    }

    //Accesseurs et mutateurs
    public Case getDepart() {
        return this.depart;
    }

    public Case getFin() {
        return this.fin;
    }

    public Case[][] getCasesDonjon() {
        return this.casesDonjon;
    }

    /**
     * Retourne une position, choisie aléatoirement à l’intérieur du donjon
     * @return  une position aléatoire
     */
    public Position getPositionAlea(){

        int ligneDonjonAlea = rand.nextInt(casesDonjon.length);
        int colonneDonjonAlea = rand.nextInt(casesDonjon[0].length);
        return new Position(ligneDonjonAlea,colonneDonjonAlea);
    }

    /**
     * Cette méthode reçoit en paramètre la position de la case
     * présentement évaluée et compte le nombre de voisins non développé d’une case
     * @param pos
     * @return nbVoisinsNonDeveloppe
     */
    public int getNbVoisinsNonDeveloppe(Position pos){

         int nbVoisinsNonDeveloppe = 0;

        for(int i = 0; i < NB_DIRECTION_MAX; ++i) {
            Case caseAlea = this.getVoisinLibreAlea(pos);
            boolean positionDeveloppe = caseAlea.getDeveloppe();
            if (this.positionEstDansLeLabyrinthe(pos) || !positionDeveloppe) {
                ++nbVoisinsNonDeveloppe;
            }
        }
        return nbVoisinsNonDeveloppe;
    }

    /**
     * Cette méthode reçoit en paramètre la position de la case présentement
     * évaluée et retourne une référence vers un voisin choisi aléatoirement
     * @param pos
     * @return posCourante
     */
    public Case getVoisinAlea(Position pos){

        Position positionReference = pos.clone();

        Position positionAleatoire;
        do {
            int directionAleatoire = Direction.obtenirDirAlea();
            positionAleatoire = Direction.directionAPosition(directionAleatoire);
            positionAleatoire.additionnerPos(positionReference);
        } while(!this.positionValide(positionAleatoire));

        //Cases du donjon toujours Null
        return this.casesDonjon[positionAleatoire.getI()][positionAleatoire.getJ()];

    }

    /**
     * Cette méthode reçoit en paramètre la position et vérifie si elle est valide.
     * @param pos
     * @return la reponse avec une boolean
     */
    private boolean positionValide(Position pos) {
        boolean valide = false;
        int i = pos.getI();
        int j = pos.getJ();
        int ligne = 10;
        int colonne = 10;
        if (i < ligne && i >= 0 && j < colonne && j >= 0) {
            valide = true;
        }

        return valide;
    }

    /**
     * Cette méthode reçoit en paramètre la position de la case présentement
     * évaluée et regarde si elle est dans le donjon ou non.
     * @param pos
     * @return la reponse avec une boolean
     */
    private boolean positionEstDansLeLabyrinthe(Position pos){

        boolean positionPossible = false;

        for (int i = 0; i < casesDonjon.length; i++) {
            for (int j = 0; j < casesDonjon[0].length; j++) {
                if (pos.getJ() == j && pos.getI() == i) {
                    positionPossible = true;
                    break;
                }
            }
        }
        return positionPossible;
    }

    /**
     * Cette méthode reçoit en paramètre la position de la case
     * présentement évaluée et retourne une référence vers un voisin libre qui n’a pas été
     * développé
     * @param pos
     * @return une référence d un voisin
     */
    public Case getVoisinLibreAlea(Position pos){

        Case caseVoisine;
        do {
            caseVoisine = this.getVoisinAlea(pos);
        } while(caseVoisine.getDeveloppe());

        return caseVoisine;

    }

    /**
     * Creation du plan de jeu à l'aide de l'objet PileSChainee
     * @throws Exception
     */
    private void produireLabyrinthe() throws Exception {

        int compteurBoucleInfinie = 900;
        int hautDePile = 0;
        PileSChainee<Case> labyrinthe = new PileSChainee();
        labyrinthe.empiler(this.depart);

        while(!labyrinthe.estVide() && compteurBoucleInfinie-- > 0) {

            Case caseDuHaut = (Case)labyrinthe.regarder(hautDePile);
            Position positionCase = caseDuHaut.getPosition();
            caseDuHaut.setDeveloppe(true);

            if (this.getNbVoisinsNonDeveloppe(positionCase) > 0) {

                Case caseVoisinLibre = this.getVoisinLibreAlea(positionCase);
                Position positionVoisinLibre = caseVoisinLibre.getPosition();
                positionVoisinLibre.soustrairePos(positionCase);

                int direction = Direction.positionADirection(positionVoisinLibre);

                caseDuHaut.setVoisin(direction, caseVoisinLibre);
                caseVoisinLibre.setVoisin(Direction.directionOpposee(direction), caseVoisinLibre);

                labyrinthe.empiler(caseVoisinLibre);

                this.fin = (Case)labyrinthe.regarder(hautDePile);

            } else {
                labyrinthe.depiler();
            }
        }

        //boucle infinie
        if (compteurBoucleInfinie <= 1) {
            throw new RuntimeException("attention : Boucle infinie");
        }

    }

}
