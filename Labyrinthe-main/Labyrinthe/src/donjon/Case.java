/**
 * La case est l’objet de construction du donjon.
 *
 * @author Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */
package donjon;

import java.util.Arrays;
import physique.Position;


public class Case {

    //Champs membres 
    private Position pos;
    private boolean decouverte;
    private boolean fin;
    private boolean developpe;
    private Case[] voisins ;

    private final int NB_VOISIN = 4;

    //Constructeur par défaut
    public Case(){
        initChampsMembres();
    }

    //Constructeur par paramètre
    public Case(Position pos){
        initChampsMembres();
        this.pos = pos;
    }

    public Case clone(){ return new Case(this.pos); }

    //Accesseurs et mutateurs 
    public boolean getDecouverte() {
        return decouverte;
    }

    public void setDecouverte(boolean decouverte) {
        this.decouverte = decouverte;
    }

    public boolean getFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public boolean getDeveloppe() {
        return developpe;
    }

    public void setDeveloppe(boolean developpe) {
        this.developpe = developpe;
    }

    public Case getVoisin(int dir) { return voisins[dir]; }

    public void setVoisin(int dir,Case voisin) {
        voisins[dir] = voisin;
    }

    public Position getPosition() {return new Position(pos);}


    @Override
    public String toString() {
        return "donjon.donjon.Case{" +
                "pos=" + pos +
                ", decouverte=" + decouverte +
                ", fin=" + fin +
                ", developpe=" + developpe +
                ", voisin=" + Arrays.toString(voisins) +
                '}';
    }

    private void initChampsMembres() {
        decouverte = false;
        fin = false;
        developpe = false;
        voisins = new Case[NB_VOISIN];
        pos = new Position(0,0);
    }

}
