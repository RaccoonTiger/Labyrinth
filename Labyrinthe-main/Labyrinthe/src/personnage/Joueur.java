/**
 *  Classe Araigne qui herite de la classe AbstractPersonnage
 *
 * @author Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */
package personnage;

import physique.Position;

public class Joueur extends AbstractPersonnage{

    public Joueur(Position pos) {
        super(pos);
    }

    public Position getPos(){
        return this.pos;
    }
}
