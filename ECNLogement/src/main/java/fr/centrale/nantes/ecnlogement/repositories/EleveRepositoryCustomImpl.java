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
    public Eleve create(Date eleveDateNaissance, String genre, String elevePayshab, String eleveVillehab, int eleveCodepostal, Personne personneId) {
        if ((eleveDateNaissance != null) && (genre != null) && (elevePayshab != null) && (eleveVillehab != null) && (personneId != null)) {
            Eleve item = new Eleve();
            item.setEleveDateNaissance(eleveDateNaissance);
            item.setGenre(genre);
            item.setElevePayshab(elevePayshab);
            item.setEleveVillehab(eleveVillehab);
            item.setEleveCodepostal(eleveCodepostal);
            item.setPersonneId(personneId);
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
            item.setElevePayshab(value.getElevePayshab());
            item.setEleveVillehab(value.getEleveVillehab());
            item.setEleveCodepostal(value.getEleveCodepostal());
            item.setEleveMail(value.getEleveMail());
            item.setEleveNumtel(value.getEleveNumtel());
            item.setEleveBoursier(value.getEleveBoursier());
            item.setEleveInfosup(value.getEleveInfosup());
            item.setCodeCommune(value.getCodeCommune());
            item.setLogementNumero(value.getLogementNumero());
            item.setPersonneId(value.getPersonneId());
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
    public void setPersonneId(Eleve item, Personne toSet, PersonneRepository altRepository) {
        if ((item != null) && (toSet != null)) {
            
            if (item.getPersonneId()== null) {
                item.setPersonneId(toSet);
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
}
