/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import java.util.Date;
import java.util.Optional;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class EleveRepositoryCustomImpl implements EleveRepositoryCustom {

    @Autowired
    @Lazy
    private EleveRepository repository;

    @Autowired
    private PersonneRepository personneRepository;

    @Override
    public Eleve create(Eleve item) {
        if (item != null) {
            repository.saveAndFlush(item);

            Optional<Eleve> result = repository.findById(item.getEleveId());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }
    
    @Override
    public Eleve create(int numscei, Personne personne){
        if (numscei != 0 && personne != null){
            Eleve item = new Eleve();
            item.setNumscei(numscei);
            item.setPersonne(personne);
            return create(item);
        }
        return null;
    }
    
    @Override
    public Eleve create(int numscei, Date eleveDateNaissance, String genre, String elevePayshab, String eleveVillehab, int eleveCodepostal, Personne personne) {
        if ((eleveDateNaissance != null) && (genre != null) && (elevePayshab != null) && (eleveVillehab != null) && (personne != null)) {
            Eleve item = new Eleve();
            item.setNumscei(numscei);
            item.setEleveDateNaissance(eleveDateNaissance);
            item.setGenre(genre);
            item.setElevePayshab(elevePayshab);
            item.setEleveVillehab(eleveVillehab);
            item.setEleveCodepostal(eleveCodepostal);
            item.setPersonne(personne);
            return create(item);
        }
        return null;
    }

    @Override
    public void remove(Eleve item) {
        if (item != null) {
            repository.delete(item);
        }
    }

    @Override
    public Eleve update(Integer eleveId, Eleve value) {
        Eleve item = repository.getByEleveId(eleveId);
        if ((item != null) && (value != null)) {
            item.setEleveDateNaissance(value.getEleveDateNaissance());
            item.setGenre(value.getGenre());
            item.setNumscei(value.getNumscei());
            item.setElevePayshab(value.getElevePayshab());
            item.setEleveVillehab(value.getEleveVillehab());
            item.setEleveCodepostal(value.getEleveCodepostal());
            item.setEleveMail(value.getEleveMail());
            item.setEleveNumtel(value.getEleveNumtel());
            item.setEleveBoursier(value.getEleveBoursier());
            item.setEleveInfosup(value.getEleveInfosup());
            item.setCodeCommune(value.getCodeCommune());
            item.setLogementNumero(value.getLogementNumero());
            item.setPersonne(value.getPersonne());
            item.setTypeSouhait(value.getTypeSouhait());
            repository.saveAndFlush(item);
        }
        return item;
    }

    @Override
    public Eleve getByEleveId(Integer eleveId) {
        Collection<Eleve> result = repository.findByEleveId(eleveId);
        if (result.size() == 1) {
            return result.iterator().next();
        }
        return null;
    }

    @Override
    public Eleve getByPersonNomPrenomNaissance(String personneNom, String personnePrenom, Date eleveNaissance) {
        if ((personneNom != null) && (personnePrenom != null) && (eleveNaissance != null)
                && (!personneNom.isEmpty()) && (!personnePrenom.isEmpty())) {
            Collection<Personne> list = personneRepository.findByPersonFirstAndLastName(personneNom, personnePrenom);
            for (Personne p : list) {
                for (Eleve e : p.getEleveCollection()) {
                    if (e.getEleveDateNaissance().equals(eleveNaissance)) {
                        return e;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Eleve getByPersonNomPrenomNumscei(String personneNom, String personnePrenom,int numscei){
        if ((personneNom != null) && (personnePrenom != null) && (numscei != 0)
                && (!personneNom.isEmpty()) && (!personnePrenom.isEmpty())) {
            Collection<Personne> list = personneRepository.findByPersonFirstAndLastName(personneNom, personnePrenom);
            for (Personne p : list) {
                for (Eleve e : p.getEleveCollection()) {
                    if (e.getNumscei()==numscei) {
                        return e;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public void setPersonne(Eleve item, Personne toSet, PersonneRepository altRepository) {
        if ((item != null) && (toSet != null)) {
            
            if (item.getPersonne()== null) {
                item.setPersonne(toSet);
                repository.save(item);
            }
            if ((toSet.getEleveCollection()!= null) && (! toSet.getEleveCollection().contains(item))) {
                toSet.getEleveCollection().add(item);
                altRepository.save(toSet);
            }
            repository.flush();
            altRepository.flush();
        }
    }
    
  //Ajoute par moi  
  /**  
    @Override
    public Eleve getByPersonneId(int personneId){
        Personne pers=personneRepository.getByPersonneId(personneId);
        if ((personneId != -1) && (pers != null) && (pers.getRoleId().getRoleId()==2)) {
            Collection<Personne> list = personneRepository.findByPersonneId(personneId);
            for (Personne p : list) {
                for (Eleve e : p.getEleveCollection()) {
                    if (e.getPersonne().getPersonneId()==personneId) {
                        return e;
                    }
                }
            }
        }
        return null;
    }
    @Override
public Eleve getByPersonneId(int personneId) {
    Personne personne = personneRepository.getByPersonneId(personneId);

    if (personne != null) {
        return personne.getEleveCollection()
                .stream()
                .filter(eleve -> eleve.getPersonne().getPersonneId() == personneId)
                .findFirst()
                .orElse(null);
    }

    return null;
}**/
}
