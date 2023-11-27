package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Eleve;
import fr.centrale.nantes.ecnlogement.items.Role;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-11-27T15:57:16")
@StaticMetamodel(Personne.class)
public class Personne_ { 

    public static volatile CollectionAttribute<Personne, Connexion> connexionCollection;
    public static volatile SingularAttribute<Personne, String> personnePrenom;
    public static volatile SingularAttribute<Personne, Role> roleId;
    public static volatile SingularAttribute<Personne, Integer> personneId;
    public static volatile SingularAttribute<Personne, String> personneLogin;
    public static volatile SingularAttribute<Personne, String> personnePassword;
    public static volatile CollectionAttribute<Personne, Eleve> eleveCollection;
    public static volatile SingularAttribute<Personne, String> personneNom;

}