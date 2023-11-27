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
public class CommuneRepositoryCustomImpl implements CommuneRepositoryCustom {

    @Autowired
    @Lazy
    private CommuneRepository repository;

    @Override
    public Commune create(Commune item) {
        if (item != null) {
            repository.saveAndFlush(item);

            Optional<Commune> result = repository.findById(item.getCodeCommune());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }

    @Override
    public Commune create(String nomCommune, int codePostal, float latitude, float longitude, boolean dansMetropoleNantes) {
        if ((nomCommune != null)) {
            Commune item = new Commune();
            item.setNomCommune(nomCommune);
            item.setCodePostal(codePostal);
            item.setLatitude(latitude);
            item.setLongitude(longitude);
            item.setDansMetropoleNantes(dansMetropoleNantes);
            return create(item);
        }
        return null;
    }

    @Override
    public void remove(Commune item) {
          if (item != null) {
            repository.delete(item);
          }
    }

    @Override
    public Commune update(Integer codeCommune, Commune value) {
          Commune item = repository.getByCodeCommune(codeCommune);
          if ((item != null) && (value != null)) {
              item.setNomCommune(value.getNomCommune());
              item.setCodePostal(value.getCodePostal());
              item.setLatitude(value.getLatitude());
              item.setLongitude(value.getLongitude());
              item.setDansMetropoleNantes(value.getDansMetropoleNantes());
              repository.saveAndFlush(item);
          }
          return item;
    }

    @Override
    public Commune getByCodeCommune(Integer codeCommune) {
          Collection<Commune> result = repository.findByCodeCommune(codeCommune);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }

}
