/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;

public interface PersonneRepositoryCustom {

    /**
     * Create new Personne
     * @param item
     * @return Personne
     */
    public Personne create(Personne item);

    /**
     * Create new Personne
     * @param personneNom
     * @param personnePrenom
     * @param roleId
     * @return Personne
     */
    public Personne create(String personneNom, String personnePrenom, Role roleId);

    /**
     * Remove Personne
     * @param item
     */
    public void remove(Personne item);

    /**
     * Update a Personne
     * @param personneId
     * @param value
     * @return Personne
     */
    public Personne update(Integer personneId, Personne value);

    /**
     * Get a Personne
     * @param personneId
     * @return personneId
     */
    public Personne getByPersonneId(Integer personneId);

    /**
     * Get a Personne
     * @param personneLogin
     * @return personneLogin
     */
    public Personne getByPersonneLogin(String personneLogin);

}
