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
public interface LogementRepository extends JpaRepository<Logement, String>, LogementRepositoryCustom {

    public Collection<Logement> findByLogementNumero(@Param("logementNumero")String logementNumero);
    public Collection<Logement> findByLogementGenreRequis(@Param("logementGenreRequis")String logementGenreRequis);
    public Collection<Logement> findByLogementPlacesDispo(@Param("logementPlacesDispo")int logementPlacesDispo);

}
