/**
 * Classe abstraite d'une créature d'un jeu
 *
 * @author Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */
package personnage;
import physique.Position;

public class AbstractCreature extends AbstractPersonnage{

    //Constructeur par paramètres
    public AbstractCreature(Position pos) {
        super(pos);
    }

    public boolean getVivant() {
        return true;
    }
}
