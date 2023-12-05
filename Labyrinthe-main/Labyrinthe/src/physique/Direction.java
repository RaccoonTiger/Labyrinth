/**
 * Cette classe implémente le module utilitaire qui est utilisé pour effectuer des opérations décrites
 * sous forme de direction. Elle offre également certains services pour travailler avec des
 * positions.
 *
 * @author Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */
package physique;

import java.util.Random;

public class Direction {

    //Constantes publiques
    public static final int HAUT = 0;
    public static final int BAS = 1;
    public static final int GAUCHE = 2;
    public static final int DROITE = 3;
    public static final int DIRECTION_MAX = 4;


    //Champs membres
    private static Random rand = new Random();

    /**
     * Ce service permet d’obtenir la direction opposée à celle reçus en paramètre
     * @param dir est une direction
     * @return une direction opposee
     */
    public static int directionOpposee(int dir){

        //Initialiser la variable
        int  dirOpposee = -1;

        switch (dir){

            case HAUT:
                dirOpposee = BAS;
                break;
            case BAS:
                dirOpposee = HAUT;
                break;
            case GAUCHE:
                dirOpposee = DROITE;
                break;
            case DROITE:
                dirOpposee = GAUCHE;
                break;
            default:
                System.out.println("Valeur non valide!!!");
        }

        return dirOpposee;
    }

    /**
     * Ce service permet de convertir une direction en sa représentation Position
     * @param dir direction
     */
    public static Position directionAPosition(int dir){

        Position positionConvertie = new Position(0,0);

        switch (dir){

            case HAUT:
                positionConvertie.setI(-1);
                positionConvertie.setJ(0);
                break;
            case BAS:
                positionConvertie.setI(1);
                positionConvertie.setJ(0);
                break;
            case GAUCHE:
                positionConvertie.setI(0);
                positionConvertie.setJ(-1);
                break;
            case DROITE:
                positionConvertie.setI(0);
                positionConvertie.setJ(1);
                break;
            default:
                System.out.println("Valeur non valide!!!");
        }
        return  positionConvertie;
    }

    /**
     * Ce service permet d’effectuer la conversion inverse
     * @param pos position
     */
    public static int positionADirection(Position pos){

        int dir = -1;
        int i = pos.getI();
        int j = pos.getJ();

        if (j == 0) {
            if (i == -1) { dir = HAUT; }
            if (i == 1) { dir = BAS; }
        }
        else if (i == 0){
            if (j == -1) { dir = GAUCHE; }
            if (j == 1) { dir = DROITE; }
        }

        return dir;
    }

    /**
     * Ce service permet d’obtenir une direction aléatoire
     * @return une direction aléatoire
     */
    public static int obtenirDirAlea(){

        return rand.nextInt(DIRECTION_MAX);
    }
}


