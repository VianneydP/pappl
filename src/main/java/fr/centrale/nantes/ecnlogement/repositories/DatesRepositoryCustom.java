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
    
    public Dates create(Dates item);
    
    public Dates create (int annee, Date debut, Date fin, Date resultat);
    
    public void remove(Dates item);
    
    public Dates getByAnnee(int annee);
}
