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
public class SouhaitRepositoryCustomImpl implements SouhaitRepositoryCustom {

    @Autowired
    @Lazy
    private SouhaitRepository repository;

    @Override
    public Souhait create(Souhait item) {
        if (item != null) {
            repository.saveAndFlush(item);

            Optional<Souhait> result = repository.findById(item.getTypeSouhait());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }

    @Override
    public Souhait create() {
        Souhait item = new Souhait();
        return create(item);
    }

    @Override
    public void remove(Souhait item) {
          if (item != null) {
            repository.delete(item);
          }
    }

    @Override
    public Souhait update(String typeSouhait, Souhait value) {
          Souhait item = repository.getByTypeSouhait(typeSouhait);
          if ((item != null) && (value != null)) {
              repository.saveAndFlush(item);
          }
          return item;
    }

    @Override
    public Souhait getByTypeSouhait(String typeSouhait) {
          Collection<Souhait> result = repository.findByTypeSouhait(typeSouhait);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }

}
