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
public class TexteRepositoryCustomImpl  implements TexteRepositoryCustom{
    
    @Autowired
    @Lazy
    private TexteRepository repository;

    @Override
    public Texte create(Texte item) {
        if (item != null) {
            repository.saveAndFlush(item);

            Optional<Texte> result = repository.findById(item.getTexteNom());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }

    @Override
    public Texte create() {
        Texte item = new Texte();
        return create(item);
    }

    @Override
    public void remove(Texte item) {
          if (item != null) {
            repository.delete(item);
          }
    }

    @Override
    public Texte update(String texteNom, Texte value) {
          Texte item = repository.getByTexteNom(texteNom);
          if ((item != null) && (value != null)) {
              item.setTexteContenu(value.getTexteContenu());
              repository.saveAndFlush(item);
          }
          return item;
    }

    @Override
    public Texte getByTexteNom(String texteNom) {
          Collection<Texte> result = repository.findByTexteNom(texteNom);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }

}
