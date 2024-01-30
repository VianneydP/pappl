/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface DatesRepository extends JpaRepository<Dates, Integer>, DatesRepositoryCustom {

    public Collection<Dates> findByDatesAnnee(@Param("datesAnnee")Integer datesAnnee);
    public Collection<Dates> findByDatesDebut(@Param("datesDebut")String datesDebut);
    public Collection<Dates> findByDatesFin(@Param("datesFin")int datesFin);
    public Collection<Dates> findByDatesResultats(@Param("datesResultats")float datesResultats);
    
    
    //PAS LA PEINE DE MODIFIER !!!
    //Mais si l'on veut, on peut ajouter des Native Query... MAIS elles ne doivent retourner qu'un objet
}
