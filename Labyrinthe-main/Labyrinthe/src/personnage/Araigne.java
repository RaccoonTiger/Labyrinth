/**
 * Classe Araigne qui herite de la classe AbstractCreature
 *
 * @author Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */
package personnage;

import physique.Position;

public class Araigne extends AbstractCreature {

    //Constructeur par paramètres
    public Araigne(Position pos) {
        super(pos);
    }

    @Override
    public void seDeplacer(int direction) {
        super.seDeplacer(direction);
        super.seDeplacer(direction);
    }
}
