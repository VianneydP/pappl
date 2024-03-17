/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;

/**
 *
 * @author viann
 */
public interface TexteRepositoryCustom {
    
    /**
     * Creer un nouveau texte
     * @param item
     * @return Texte
     */
    public Texte create(Texte item);

    /**
     * Create new Texte
     * @return Texte
     */
    public Texte create();

    /**
     * Remove Texte
     * @param item
     */
    public void remove(Texte item);

    /**
     * Update a Texte
     * @param texteNom
     * @param value
     * @return Texte
     */
    public Texte update(String texteNom, Texte value);

    /**
     * Get a Texte
     * @param texteNom
     * @return texteNom
     */
    public Texte getByTexteNom(String texteNom);
    
}
