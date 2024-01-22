/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import java.util.Date;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface ConnexionRepository extends JpaRepository<Connexion, String>, ConnexionRepositoryCustom {

    public Collection<Connexion> findByConnectionId(@Param("connectionId")String connectionId);
    public Collection<Connexion> findByExpiration(@Param("expiration")Date expiration);

    /**
     * Get all expired connections
     * @param expireDate
     * @return
     */
    @Query("SELECT c FROM Connexion c WHERE c.expiration < :expireDate")
    public Collection<Connexion> findAllExpireBefore(@Param("expireDate")Date expireDate);

}
