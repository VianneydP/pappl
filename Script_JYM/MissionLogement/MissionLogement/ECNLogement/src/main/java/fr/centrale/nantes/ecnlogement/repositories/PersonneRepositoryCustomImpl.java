/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import java.util.Optional;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class PersonneRepositoryCustomImpl implements PersonneRepositoryCustom {

    @Autowired
    @Lazy
    private PersonneRepository repository;

    @Override
    public Personne create(Personne item) {
        if (item != null) {
            repository.saveAndFlush(item);

            Optional<Personne> result = repository.findById(item.getPersonneId());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }

    @Override
    public Personne create(String personneNom, String personnePrenom, Role roleId) {
        if ((personneNom != null) && (personnePrenom != null) && (roleId != null)) {
            Personne item = new Personne();
            item.setPersonneNom(personneNom);
            item.setPersonnePrenom(personnePrenom);
            item.setRoleId(roleId);
            return create(item);
        }
        return null;
    }

    @Override
    public void remove(Personne item) {
          if (item != null) {
            repository.delete(item);
          }
    }

    @Override
    public Personne update(Integer personneId, Personne value) {
          Personne item = repository.getByPersonneId(personneId);
          if ((item != null) && (value != null)) {
              item.setPersonneNom(value.getPersonneNom());
              item.setPersonnePrenom(value.getPersonnePrenom());
              item.setPersonneLogin(value.getPersonneLogin());
              item.setPersonnePassword(value.getPersonnePassword());
              item.setRoleId(value.getRoleId());
              repository.saveAndFlush(item);
          }
          return item;
    }

    @Override
    public Personne getByPersonneId(Integer personneId) {
          Collection<Personne> result = repository.findByPersonneId(personneId);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }

    @Override
    public Personne getByPersonneLogin(String personneLogin) {
          Collection<Personne> result = repository.findByPersonneLogin(personneLogin);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }

}
