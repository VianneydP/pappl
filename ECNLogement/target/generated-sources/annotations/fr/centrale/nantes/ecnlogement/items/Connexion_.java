package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Personne;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-11-27T15:57:16")
@StaticMetamodel(Connexion.class)
public class Connexion_ { 

    public static volatile SingularAttribute<Connexion, Personne> personneId;
    public static volatile SingularAttribute<Connexion, String> connectionId;
    public static volatile SingularAttribute<Connexion, Date> expiration;

}