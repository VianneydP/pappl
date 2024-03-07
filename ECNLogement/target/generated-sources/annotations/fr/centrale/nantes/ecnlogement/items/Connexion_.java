package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Personne;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-02-19T11:57:27", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Connexion.class)
public class Connexion_ { 

    public static volatile SingularAttribute<Connexion, Personne> personne;
    public static volatile SingularAttribute<Connexion, String> connectionId;
    public static volatile SingularAttribute<Connexion, Date> expiration;

}