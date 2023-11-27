/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import java.util.Date;

public interface EleveRepositoryCustom {

    /**
     * Create new Eleve
     * @param item
     * @return Eleve
     */
    public Eleve create(Eleve item);

    /**
     * Create new Eleve
     * @param eleveDateNaissance
     * @param genre
     * @param elevePayshab
     * @param eleveVillehab
     * @param eleveCodepostal
     * @param personneId
     * @return Eleve
     */
    public Eleve create(Date eleveDateNaissance, String genre, String elevePayshab, String eleveVillehab, int eleveCodepostal, Personne personneId);

    /**
     * Remove Eleve
     * @param item
     */
    public void remove(Eleve item);

    /**
     * Update a Eleve
     * @param eleveId
     * @param value
     * @return Eleve
     */
    public Eleve update(Integer eleveId, Eleve value);

    /**
     * Get a Eleve
     * @param eleveId
     * @return eleveId
     */
    public Eleve getByEleveId(Integer eleveId);

    /**
     * Get a Eleve
     * @param personneNom
     * @param personnePrenom
     * @param eleveNaissance
     * @return 
     */
    public Eleve getByPersonNomPrenomNaissance(String personneNom, String personnePrenom, Date eleveNaissance);
    
    /**
     * Set Personne
     * @param item
     * @param toSet
     * @param altRepository 
     */
    public void setPersonneId(Eleve item, Personne toSet, PersonneRepository altRepository);
}
