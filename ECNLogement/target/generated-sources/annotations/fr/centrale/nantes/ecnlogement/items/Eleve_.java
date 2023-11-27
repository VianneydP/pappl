package fr.centrale.nantes.ecnlogement.items;

import fr.centrale.nantes.ecnlogement.items.Commune;
import fr.centrale.nantes.ecnlogement.items.Logement;
import fr.centrale.nantes.ecnlogement.items.Personne;
import fr.centrale.nantes.ecnlogement.items.Souhait;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2023-11-27T15:57:16")
@StaticMetamodel(Eleve.class)
public class Eleve_ { 

    public static volatile SingularAttribute<Eleve, Boolean> eleveBoursier;
    public static volatile SingularAttribute<Eleve, String> eleveMail;
    public static volatile SingularAttribute<Eleve, String> eleveInfosup;
    public static volatile SingularAttribute<Eleve, Commune> codeCommune;
    public static volatile SingularAttribute<Eleve, Souhait> typeSouhait;
    public static volatile SingularAttribute<Eleve, Integer> eleveId;
    public static volatile SingularAttribute<Eleve, Logement> logementNumero;
    public static volatile SingularAttribute<Eleve, Date> eleveDateNaissance;
    public static volatile SingularAttribute<Eleve, String> eleveVillehab;
    public static volatile SingularAttribute<Eleve, String> elevePayshab;
    public static volatile SingularAttribute<Eleve, Integer> eleveCodepostal;
    public static volatile SingularAttribute<Eleve, String> eleveNumtel;
    public static volatile SingularAttribute<Eleve, String> genre;
    public static volatile SingularAttribute<Eleve, Personne> personneId;

}