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
public interface CommuneRepository extends JpaRepository<Commune, Integer>, CommuneRepositoryCustom {

    public Collection<Commune> findByCodeCommune(@Param("codeCommune")Integer codeCommune);
    public Collection<Commune> findByNomCommune(@Param("nomCommune")String nomCommune);
    public Collection<Commune> findByCodePostal(@Param("codePostal")int codePostal);
    public Collection<Commune> findByLatitude(@Param("latitude")float latitude);
    public Collection<Commune> findByLongitude(@Param("longitude")float longitude);
    public Collection<Commune> findByDansMetropoleNantes(@Param("dansMetropoleNantes")boolean dansMetropoleNantes);

}
