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
public class LogementRepositoryCustomImpl implements LogementRepositoryCustom {

    @Autowired
    @Lazy
    private LogementRepository repository;

    @Override
    public Logement create(Logement item) {
        if (item != null) {
            repository.saveAndFlush(item);

            Optional<Logement> result = repository.findById(item.getLogementNumero());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }

    @Override
    public Logement create(int logementPlacesDispo, TypeAppart typeAppartNom) {
        if ((typeAppartNom != null)) {
            Logement item = new Logement();
            item.setLogementPlacesDispo(logementPlacesDispo);
            item.setTypeAppartNom(typeAppartNom);
            return create(item);
        }
        return null;
    }

    @Override
    public void remove(Logement item) {
          if (item != null) {
            repository.delete(item);
          }
    }

    @Override
    public Logement update(String logementNumero, Logement value) {
          Logement item = repository.getByLogementNumero(logementNumero);
          if ((item != null) && (value != null)) {
              item.setLogementGenreRequis(value.getLogementGenreRequis());
              item.setLogementPlacesDispo(value.getLogementPlacesDispo());
              item.setTypeAppartNom(value.getTypeAppartNom());
              repository.saveAndFlush(item);
          }
          return item;
    }

    @Override
    public Logement getByLogementNumero(String logementNumero) {
          Collection<Logement> result = repository.findByLogementNumero(logementNumero);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }

}
