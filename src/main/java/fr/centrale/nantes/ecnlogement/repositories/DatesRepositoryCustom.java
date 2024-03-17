/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */

package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author viann
 */
public interface DatesRepositoryCustom {
    
    /**
     * Create new Dates
     * @param item
     * @return Dates
     */
    public Dates create(Dates item);
    
    /**
     * Créer un objet Dates avec tous les attributs
     * @param annee Année de Dates
     * @param debut Date de début du formulaire
     * @param fin Date de fin du formulaire
     * @param resultat Date de publication des résultats
     * @return Dates Dates créé
     */
    public Dates create (int annee, Date debut, Date fin, Date resultat);
    
    /**
     * Supprimer un objet Dates
     * @param item
     */
    public void remove(Dates item);
    
    /**
     * Getter pour obtenir l'objet Dates d'une année
     * @param annee Annee dont on souhaite obtenir les dates
     * @return Dates
     */
    public Dates getByAnnee(int annee);
}
