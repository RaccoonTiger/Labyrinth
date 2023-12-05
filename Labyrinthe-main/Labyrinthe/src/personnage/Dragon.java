/**
 * Classe Dragon qui herite de la classe AbstractCreature
 *
 * @author Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */
package personnage;

import physique.Position;

public class Dragon extends AbstractCreature{

    //Constructeur par paramètres
    public Dragon(Position pos) {
        super(pos);
    }
}
