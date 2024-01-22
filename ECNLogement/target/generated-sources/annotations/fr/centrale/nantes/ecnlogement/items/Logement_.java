package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Eleve;
import fr.centrale.nantes.ecnlogement.items.TypeAppart;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-01-22T11:38:18", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Logement.class)
public class Logement_ { 

    public static volatile SingularAttribute<Logement, String> logementNumero;
    public static volatile SingularAttribute<Logement, Integer> logementPlacesDispo;
    public static volatile CollectionAttribute<Logement, Eleve> eleveCollection;
    public static volatile SingularAttribute<Logement, String> logementGenreRequis;
    public static volatile SingularAttribute<Logement, TypeAppart> typeAppartNom;

}