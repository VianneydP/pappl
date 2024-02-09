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
public class TypeAppartRepositoryCustomImpl implements TypeAppartRepositoryCustom {

    @Autowired
    @Lazy
    private TypeAppartRepository repository;

    @Override
    public TypeAppart create(TypeAppart item) {
        if (item != null) {
            repository.saveAndFlush(item);

            Collection<TypeAppart> result = repository.findByTypeAppartNom(item.getTypeAppartNom());
            if (!result.isEmpty()) {
                return result.iterator().next();
            }
        }
        return null;
    }

    @Override
    public TypeAppart create() {
        TypeAppart item = new TypeAppart();
        return create(item);
    }

    @Override
    public TypeAppart create(String typeAppartNom) {
        if ((typeAppartNom != null)) {
            TypeAppart item = new TypeAppart();
            item.setTypeAppartNom(typeAppartNom);
            return create(item);
        }
        return null;
    }
    
    @Override
    public void remove(TypeAppart item) {
          if (item != null) {
            repository.delete(item);
          }
    }

    @Override
    public TypeAppart update(String typeAppartNom, TypeAppart value) {
          TypeAppart item = repository.getByTypeAppartNom(typeAppartNom);
          if ((item != null) && (value != null)) {
              repository.saveAndFlush(item);
          }
          return item;
    }

    @Override
    public TypeAppart getByTypeAppartNom(String typeAppartNom) {
          Collection<TypeAppart> result = repository.findByTypeAppartNom(typeAppartNom);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }


}
