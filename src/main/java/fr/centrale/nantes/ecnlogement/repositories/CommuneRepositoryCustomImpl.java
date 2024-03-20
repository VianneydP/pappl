/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class CommuneRepositoryCustomImpl implements CommuneRepositoryCustom {
    //TOUTES LES IMPLEMENTATIONS ICI
    //Regarder les méthodes en get- pour comprendre l'utilisation
    
    @Autowired
    @Lazy
    private CommuneRepository repository;

    @Override
    public Commune create(Commune item) {
        if (item != null) {
            Collection<Commune> result = repository.findByCodeCommune(item.getCodeCommune());
            if (result.isEmpty()) {
                repository.saveAndFlush(item);
                return item;
            }else{
                Object[] r=result.toArray();
                return (Commune) r[0];
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
    public Commune update(String codeCommune, Commune value) {
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
    public Commune getByCodeCommune(String codeCommune) {
          Collection<Commune> result = repository.findByCodeCommune(codeCommune);
          if (result.size() == 1) {
              return result.iterator().next();
          }
          return null;
    }
    @Override
    public Commune getByDepNom(String dep,String nom) {
        Collection<Commune> result = repository.findByNomCommune(nom);
        for (Commune co:result){
            int cp_form=co.getCodePostal();
            String dep_form=Integer.toString(cp_form);
            dep_form=dep_form.substring(0, 2);
            if (dep_form.equalsIgnoreCase(dep)){
                return co;
            }
        }
        return null;
    }
    
    
    @Override
    public Double distNantesCommune(String codeCommune){
        Double rg=0.;
        Commune com=getByCodeCommune(codeCommune);
        if (com!=null){
            if (com.getDansMetropoleNantes()){
                rg=100000.;
            }else{
                double distance=500;
                Commune nantes=getByCodeCommune("44109");
                double lat_a=nantes.getLatitude();
                double lon_a=nantes.getLongitude();
                double lat_b=com.getLatitude();
                double lon_b=com.getLongitude();
                int R = 6378; //Rayon de la terre en km
                lat_a = PI*lat_a/180;
                lon_a = PI*lon_a/180;
                lat_b = PI*lat_b/180;
                lon_b = PI*lon_b/180;
                rg = R * (PI/2 - Math.asin( Math.sin(lat_b) * Math.sin(lat_a) + Math.cos(lon_b - lon_a) * Math.cos(lat_b) * Math.cos(lat_a)));
            }
        }
        return rg;
    }
    
    
}
