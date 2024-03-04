/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
package fr.centrale.nantes.ecnlogement.items;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "logement")
@NamedQueries({
    @NamedQuery(name = "Logement.findAll", query = "SELECT l FROM Logement l"),
    @NamedQuery(name = "Logement.findByLogementNumero", query = "SELECT l FROM Logement l WHERE l.logementNumero = :logementNumero"),
    @NamedQuery(name = "Logement.findByLogementGenreRequis", query = "SELECT l FROM Logement l WHERE l.logementGenreRequis = :logementGenreRequis"),
    @NamedQuery(name = "Logement.findByLogementPlacesDispo", query = "SELECT l FROM Logement l WHERE l.logementPlacesDispo = :logementPlacesDispo")})
public class Logement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "logement_numero")
    private String logementNumero;
    @Size(max = 2147483647)
    @Column(name = "logement_genre_requis")
    private String logementGenreRequis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "logement_places_dispo")
    private int logementPlacesDispo;
    @JoinColumn(name = "type_appart_nom", referencedColumnName = "type_appart_nom")
    @ManyToOne(optional = false)
    private TypeAppart typeAppartNom;
    @OneToMany(mappedBy = "logementNumero")
    private Collection<Eleve> eleveCollection;

    public Logement() {
    }

    public Logement(String logementNumero) {
        this.logementNumero = logementNumero;
    }

    public Logement(String logementNumero, int logementPlacesDispo) {
        this.logementNumero = logementNumero;
        this.logementPlacesDispo = logementPlacesDispo;
    }

    public String getLogementNumero() {
        return logementNumero;
    }

    public void setLogementNumero(String logementNumero) {
        this.logementNumero = logementNumero;
    }

    public String getLogementGenreRequis() {
        return logementGenreRequis;
    }

    public void setLogementGenreRequis(String logementGenreRequis) {
        this.logementGenreRequis = logementGenreRequis;
    }

    public int getLogementPlacesDispo() {
        return logementPlacesDispo;
    }

    public void setLogementPlacesDispo(int logementPlacesDispo) {
        this.logementPlacesDispo = logementPlacesDispo;
    }

    public TypeAppart getTypeAppartNom() {
        return typeAppartNom;
    }

    public void setTypeAppartNom(TypeAppart typeAppartNom) {
        this.typeAppartNom = typeAppartNom;
    }

    public Collection<Eleve> getEleveCollection() {
        return eleveCollection;
    }

    public void setEleveCollection(Collection<Eleve> eleveCollection) {
        this.eleveCollection = eleveCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logementNumero != null ? logementNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logement)) {
            return false;
        }
        Logement other = (Logement) object;
        if ((this.logementNumero == null && other.logementNumero != null) || (this.logementNumero != null && !this.logementNumero.equals(other.logementNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.ecnlogement.items.Logement[ logementNumero=" + logementNumero + " ]";
    }
    
}
