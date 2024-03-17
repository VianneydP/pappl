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
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Integer>, PersonneRepositoryCustom {

    public Collection<Personne> findByPersonneId(@Param("personneId")Integer personneId);
    public Collection<Personne> findByPersonneNom(@Param("personneNom")String personneNom);
    public Collection<Personne> findByPersonnePrenom(@Param("personnePrenom")String personnePrenom);
    public Collection<Personne> findByPersonneLogin(@Param("personneLogin")String personneLogin);
    public Collection<Personne> findByPersonnePassword(@Param("personnePassword")String personnePassword);

    /**
     * find personne
     * @param personneNom
     * @param personnePrenom
     * @return 
     */
    @Query("SELECT p FROM Personne p WHERE p.personneNom = :personneNom AND p.personnePrenom = :personnePrenom")
    public Collection<Personne> findByPersonFirstAndLastName(@Param("personneNom")String personneNom, @Param("personnePrenom")String personnePrenom);
    
}
