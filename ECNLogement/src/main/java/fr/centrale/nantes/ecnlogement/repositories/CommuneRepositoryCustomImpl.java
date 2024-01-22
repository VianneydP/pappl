/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.repositories;

import fr.centrale.nantes.ecnlogement.items.*;
import static java.lang.Math.*;
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
    
    public int rangCommune(Integer codeCommune){
        int rg=0;
        Commune com=getByCodeCommune(codeCommune);
        if (com!=null){
            if (com.getDansMetropoleNantes()){
                rg=4;
            }else{
                double distance=500;
                Commune nantes=getByCodeCommune(44109);
                double lat_a=nantes.getLatitude();
                double lon_a=nantes.getLongitude();
                double lat_b=com.getLatitude();
                double lon_b=com.getLongitude();
                int R = 6378; //Rayon de la terre en km
                lat_a = PI*lat_a/180;
                lon_a = PI*lon_a/180;
                lat_b = PI*lat_b/180;
                lon_b = PI*lon_b/180;
                distance = R * (PI/2 - Math.asin( Math.sin(lat_b) * Math.sin(lat_a) + Math.cos(lon_b - lon_a) * Math.cos(lat_b) * Math.cos(lat_a)));
                if (distance<200){
                    rg=1;
                }if (distance<400 && distance >=200){
                    rg=2;
                }if (distance>=400){
                    rg=3;
                }
            }
        }
        return rg;
    }
}
