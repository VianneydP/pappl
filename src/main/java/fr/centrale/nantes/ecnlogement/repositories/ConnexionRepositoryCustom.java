/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;

public interface ConnexionRepositoryCustom {

    /**
     * Create new Connexion
     * @param item
     * @return Connexion
     */
    public Connexion create(Connexion item);

    /**
     * Create new Connexion
     * @param personneId
     * @return Connexion
     */
    public Connexion create(Personne personneId);

    /**
     * Remove Connexion
     * @param item
     */
    public void remove(Connexion item);

    /**
     * Update a Connexion
     * @param connectionId
     * @param value
     * @return Connexion
     */
    public Connexion update(String connectionId, Connexion value);

    /**
     * Get a Connexion
     * @param connectionId
     * @return connectionId
     */
    public Connexion getByConnectionId(String connectionId);

    /**
     * Remove old connections Connexion
     */
    public void removeOld();

    /**
     * Expand connection duration Connexion
     * @param item
     */
    public void expand(Connexion item);
    
    /**
     * Check the unicity of the connection
     * @param personne
     * @return boolean 
     */
    public boolean checkUnicity(Personne personne);
    
    /**
     * Supprimer une connexion à partir de la personne
     * @param personne Personne dont on ssouhaite supprimer la connexion 
     */
    public void deleteByPerson(Personne personne);
    
}
