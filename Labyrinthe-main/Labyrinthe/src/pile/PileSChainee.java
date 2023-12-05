/**
 * Cette classe implémente l’objet PileSChainee qui est utilisé pour faire une pile d'object.
 * Elle offre plusieurs services comme empiler,depiler et regarder.
 *
 * @author Antoine Bolduc et  Stanley Vu| ETS
 * @version Hiver 2022 - TP2
 */
package pile;

public class PileSChainee<E> {

    private class Noeud{

        Noeud suivant;
        E element;

        public Noeud(E element, Noeud suivant) {
            this.element = element;
            this.suivant = suivant;
        }

    }

    Noeud tete;
    int nbElements = 0;

    public int getElement (){return nbElements;}

    /**
     * Ajoute un élément à la pile
     * @param element
     */
    public void empiler(E element) {
        tete = new Noeud(element, tete);
        nbElements++;
    }

    /**
     * Enlève un élément de la pile
     * @throws Exception
     */
    public void  depiler()throws Exception {

        if(estVide()) {
            throw new Exception("[ListeSChainee::depiler] liste vide");
        }

        tete = tete.suivant;
        nbElements--;
    }

    /**
     * Retourne une référence sur le prochain élément de la pile sans l’enlever
     * @param index position de l'element de la pile recherchée
     * @return
     * @throws Exception
     */
    public E regarder(int index) throws Exception{

        if(estVide()) {
            throw new Exception("[ListeSChainee::regarder] liste vide");
        }else if(index < 0) {
            throw new Exception("[ListeSChainee::regarder] index négatif");
        }else if(index>nbElements-1) {
            throw new Exception("[Liste::regarder] index trop grand");
        }

        int compteur = 0;
        Noeud ceNoeud = tete;

        while(compteur < index) {
            ceNoeud = ceNoeud.suivant;
            compteur++;
        }

        return ceNoeud.element;
    }

    /**
     * Indique si la pile est vide
     * @return
     */
    public boolean estVide(){

        if(nbElements == 0) {
            return true;
        }

        return false;
    }
}

