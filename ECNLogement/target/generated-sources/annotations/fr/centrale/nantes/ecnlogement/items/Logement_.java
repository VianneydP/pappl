package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Eleve;
import fr.centrale.nantes.ecnlogement.items.TypeAppart;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-11-27T15:57:16")
@StaticMetamodel(Logement.class)
public class Logement_ { 

    public static volatile SingularAttribute<Logement, String> logementNumero;
    public static volatile SingularAttribute<Logement, Integer> logementPlacesDispo;
    public static volatile CollectionAttribute<Logement, Eleve> eleveCollection;
    public static volatile SingularAttribute<Logement, String> logementGenreRequis;
    public static volatile SingularAttribute<Logement, TypeAppart> typeAppartNom;

}