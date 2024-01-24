package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Commune;
import fr.centrale.nantes.ecnlogement.items.Logement;
import fr.centrale.nantes.ecnlogement.items.Personne;
import fr.centrale.nantes.ecnlogement.items.Souhait;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-01-24T14:15:43", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Eleve.class)
public class Eleve_ { 

    public static volatile SingularAttribute<Eleve, Boolean> eleveBoursier;
    public static volatile SingularAttribute<Eleve, Personne> personne;
    public static volatile SingularAttribute<Eleve, String> eleveMail;
    public static volatile SingularAttribute<Eleve, String> eleveInfosup;
    public static volatile SingularAttribute<Eleve, Commune> codeCommune;
    public static volatile SingularAttribute<Eleve, Souhait> typeSouhait;
    public static volatile SingularAttribute<Eleve, Integer> eleveId;
    public static volatile SingularAttribute<Eleve, Integer> numscei;
    public static volatile SingularAttribute<Eleve, Logement> logementNumero;
    public static volatile SingularAttribute<Eleve, Date> eleveDateNaissance;
    public static volatile SingularAttribute<Eleve, String> eleveVillehab;
    public static volatile SingularAttribute<Eleve, String> elevePayshab;
    public static volatile SingularAttribute<Eleve, Integer> eleveCodepostal;
    public static volatile SingularAttribute<Eleve, String> eleveNumtel;
    public static volatile SingularAttribute<Eleve, String> genre;

}