package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Connexion;
import fr.centrale.nantes.ecnlogement.items.Eleve;
import fr.centrale.nantes.ecnlogement.items.Role;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-02-19T11:57:27", comments="EclipseLink-2.7.10.v20211216-rNA")
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