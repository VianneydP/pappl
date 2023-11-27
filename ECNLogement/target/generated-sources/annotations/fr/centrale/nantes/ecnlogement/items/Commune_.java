package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Eleve;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-11-27T15:57:16")
@StaticMetamodel(Commune.class)
public class Commune_ { 

    public static volatile SingularAttribute<Commune, Boolean> dansMetropoleNantes;
    public static volatile SingularAttribute<Commune, Float> latitude;
    public static volatile SingularAttribute<Commune, Integer> codeCommune;
    public static volatile SingularAttribute<Commune, Integer> codePostal;
    public static volatile CollectionAttribute<Commune, Eleve> eleveCollection;
    public static volatile SingularAttribute<Commune, String> nomCommune;
    public static volatile SingularAttribute<Commune, Float> longitude;

}