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

/**
 *
 * @author viann
 */
@Repository
public interface TexteRepository  extends JpaRepository<Texte, String>, TexteRepositoryCustom {
    
    public Collection<Texte> findByTexteNom(@Param("texteNom")String texteNom);
}
