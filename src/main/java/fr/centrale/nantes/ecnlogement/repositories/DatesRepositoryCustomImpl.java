/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */

package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.Collection;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import org.springframework.stereotype.Repository;

@Repository
public class DatesRepositoryCustomImpl implements DatesRepositoryCustom{
    
    @Autowired
    @Lazy
    private DatesRepository repository;
    
    @Override
    public Dates create(Dates item){
        if (item != null) {
            repository.saveAndFlush(item);

            Optional<Dates> result = repository.findById(item.getDatesAnnee());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }
    
    @Override
    public Dates create(int annee, Date debut, Date fin, Date resultat){
        if ((debut!=null) && (fin!=null) && (resultat!=null)) {
            Dates item = new Dates();
            item.setDatesAnnee(annee);
            item.setDatesDebut(debut);
            item.setDatesFin(fin);
            item.setDatesResultats(resultat);
            return create(item);
        }
        return null;
    }
    
    @Override
    public void remove(Dates item){
        if (item != null) {
            repository.delete(item);
        }
    }
    
    @Override
    public Dates getByAnnee(int annee){
        System.out.println(annee);
        Collection<Dates> resul = repository.findByDatesAnnee(annee);
        System.out.println(resul.size());
        if (resul.size()==1){
            return resul.iterator().next();
        }
        return null;
    }
}
