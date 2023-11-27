/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;

public interface LogementRepositoryCustom {

    /**
     * Create new Logement
     * @param item
     * @return Logement
     */
    public Logement create(Logement item);

    /**
     * Create new Logement
     * @param logementPlacesDispo
     * @param typeAppartNom
     * @return Logement
     */
    public Logement create(int logementPlacesDispo, TypeAppart typeAppartNom);

    /**
     * Remove Logement
     * @param item
     */
    public void remove(Logement item);

    /**
     * Update a Logement
     * @param logementNumero
     * @param value
     * @return Logement
     */
    public Logement update(String logementNumero, Logement value);

    /**
     * Get a Logement
     * @param logementNumero
     * @return logementNumero
     */
    public Logement getByLogementNumero(String logementNumero);

}
