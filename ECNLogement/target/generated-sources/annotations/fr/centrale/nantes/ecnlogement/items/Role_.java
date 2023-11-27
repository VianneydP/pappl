package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Personne;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-11-27T15:57:16")
@StaticMetamodel(Role.class)
public class Role_ { 

    public static volatile CollectionAttribute<Role, Personne> personneCollection;
    public static volatile SingularAttribute<Role, Integer> roleId;
    public static volatile SingularAttribute<Role, String> roleNom;

}