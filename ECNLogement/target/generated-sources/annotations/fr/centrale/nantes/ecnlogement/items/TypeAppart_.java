package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Logement;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-01-22T11:38:18", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(TypeAppart.class)
public class TypeAppart_ { 

    public static volatile CollectionAttribute<TypeAppart, Logement> logementCollection;
    public static volatile SingularAttribute<TypeAppart, String> typeAppartNom;

}