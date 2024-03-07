package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Eleve;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-02-19T11:57:27", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Commune.class)
public class Commune_ { 

    public static volatile SingularAttribute<Commune, Boolean> dansMetropoleNantes;
    public static volatile SingularAttribute<Commune, Float> latitude;
    public static volatile SingularAttribute<Commune, String> codeCommune;
    public static volatile SingularAttribute<Commune, Integer> codePostal;
    public static volatile CollectionAttribute<Commune, Eleve> eleveCollection;
    public static volatile SingularAttribute<Commune, String> nomCommune;
    public static volatile SingularAttribute<Commune, Integer> communeId;
    public static volatile SingularAttribute<Commune, Float> longitude;

}