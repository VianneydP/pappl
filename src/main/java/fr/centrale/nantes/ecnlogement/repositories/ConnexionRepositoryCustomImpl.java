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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class ConnexionRepositoryCustomImpl implements ConnexionRepositoryCustom {

    @Autowired
    @Lazy
    private ConnexionRepository repository;

    private static final long DELTA = 30 * 60 * 1000L;

    /**
     * Create pseudo-random code
     *
     * @param login
     * @param now
     * @return
     */
    private static String createCode(Date now) {
        StringBuilder newCode = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");

        for (int i = 0; i < 5; i++) {
            char c = (char) ('A' + (int) (Math.random() * 26));
            newCode.append(c);
        }
        newCode.append(sdf.format(now));
        for (int i = 0; i < 3; i++) {
            char c = (char) ('A' + (int) (Math.random() * 26));
            newCode.append(c);
        }
        return newCode.toString();
    }

    @Override
    public Connexion create(Connexion item) {
        if (item != null) {
            repository.saveAndFlush(item);

            Optional<Connexion> result = repository.findById(item.getConnectionId());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }

    @Override
    public Connexion create(Personne personne) {
        if (personne != null) {
            Calendar aCalendar = Calendar.getInstance();
            Date now = aCalendar.getTime();
            String connexionId = createCode(now);
            Date expire = aCalendar.getTime();
            expire.setTime(now.getTime() + DELTA);
            Connexion item = new Connexion();
            item.setConnectionId(connexionId);
            item.setExpiration(expire);
            item.setPersonne(personne);
            return create(item);
        }
        return null;
    }

    @Override
    public void remove(Connexion item) {
          if (item != null) {
            repository.delete(item);
          }
    }

    @Override
    public Connexion update(String connectionId, Connexion value) {
          Connexion item = repository.getByConnectionId(connectionId);
          if ((item != null) && (value != null)) {
              item.setExpiration(value.getExpiration());
              item.setPersonne(value.getPersonne());
              repository.saveAndFlush(item);
          }
          return item;
    }

    @Override
    public Connexion getByConnectionId(String connectionId) {
          Collection<Connexion> result = repository.findByConnectionId(connectionId);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }

    @Override
    public void removeOld() {
        Calendar aCalendar = Calendar.getInstance();
        Date now = aCalendar.getTime();

        Collection<Connexion> oldConnections = repository.findAllExpireBefore(now);
        for (Connexion item : oldConnections) {
            repository.delete(item);
        }
        repository.flush();
    }

    @Override
    public void expand(Connexion item) {
        if (item != null) {
            Calendar aCalendar = Calendar.getInstance();
            Date now = aCalendar.getTime();
            Date expire = aCalendar.getTime();
            expire.setTime(now.getTime() + DELTA);

            item.setExpiration(expire);
            repository.saveAndFlush(item);
        }
    }
    
    public boolean checkUnicity(Personne personne) {
        Collection<Connexion> coRep = repository.findAll();
        for (Connexion co:coRep){
            if (co.getPersonne().getPersonneId()==personne.getPersonneId()){
                return false;
            }
        }
        return true;
    }
    
    public void deleteByPerson(Personne personne) {
        Collection<Connexion> coRep = repository.findAll();
        for (Connexion co:coRep){
            if (co.getPersonne().getPersonneId()==personne.getPersonneId()){
                repository.deleteById(co.getConnectionId());
            }
        }
    }
}
