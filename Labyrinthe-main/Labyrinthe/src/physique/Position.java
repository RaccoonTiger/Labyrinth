/**
 * Cette classe implémente l’objet Position qui est utilisé pour garder en mémoire une position
 * dans le donjon, ou pour garder en mémoire une position donnée en Pixel (vue::*). La
 * convention utilisée dans le projet est qu’une position est toujours donnée en coordonnées i, j.
 * La coordonnée i se développe dans l’axe vertical et la coordonnée j, dans l’axe horizontal.
 * Prenez note qu’une paire de coordonnée: {1,0} indiquera i=1, j=0, ce qui donne un vecteur qui
 * pointe directement vers le haut. Cette convention est différente de la norme {x,y} par contre elle
 * à l’avantage de fonctionner partout dans le programme, de la même façon.
 *
 * @author Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */
package physique;

public class Position {

    //Champs membres
    private int  i;
    private int j;

    //Constructeur par parametre
    public Position(int i, int j){
        this.i = i;
        this.j = j;
    }

    //Constructeur par copie
    public Position(Position pos){
        this.i = pos.i;
        this.j = pos.j;
    }

    public Position clone(){
        return new Position(this);
    }

    public boolean equals(Position pos){
        return this.i == pos.i && this.j == pos.j;
    }

    //Accesseurs et mutateurs

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    /**
     * Methode qui additionne  la position courante avec une position recu
     * @param pos est une position
     */
    public void additionnerPos(Position pos){
        this.i += pos.i;
        this.j += pos.j;
    }

    /**
     * Methode qui soustrait la position courante avec une position recu
     * @param pos est une position
     */
    public void soustrairePos(Position pos){
        this.i -= pos.i;
        this.j -= pos.j;
    }

    /**
     * Methode qui multiplie la position courante avec une position recu
     * @param pos est une position
     */
    public void multiplierPos(Position pos){
        this.i *= pos.i;
        this.j *= pos.j;
    }
}

