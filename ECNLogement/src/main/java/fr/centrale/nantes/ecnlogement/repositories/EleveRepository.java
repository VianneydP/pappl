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
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface EleveRepository extends JpaRepository<Eleve, Integer>, EleveRepositoryCustom {

    public Collection<Eleve> findByEleveId(@Param("eleveId")Integer eleveId);
    public Collection<Eleve> findByEleveDateNaissance(@Param("eleveDateNaissance")Date eleveDateNaissance);
    public Collection<Eleve> findByGenre(@Param("genre")String genre);
    public Collection<Eleve> findByElevePayshab(@Param("elevePayshab")String elevePayshab);
    public Collection<Eleve> findByEleveVillehab(@Param("eleveVillehab")String eleveVillehab);
    public Collection<Eleve> findByEleveCodepostal(@Param("eleveCodepostal")int eleveCodepostal);
    public Collection<Eleve> findByEleveMail(@Param("eleveMail")String eleveMail);
    public Collection<Eleve> findByEleveNumtel(@Param("eleveNumtel")String eleveNumtel);
    public Collection<Eleve> findByEleveBoursier(@Param("eleveBoursier")Boolean eleveBoursier);
    public Collection<Eleve> findByEleveInfosup(@Param("eleveInfosup")String eleveInfosup);

}
