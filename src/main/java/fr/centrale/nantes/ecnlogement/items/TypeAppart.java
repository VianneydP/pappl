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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "type_appart")
@NamedQueries({
    @NamedQuery(name = "TypeAppart.findAll", query = "SELECT t FROM TypeAppart t"),
    @NamedQuery(name = "TypeAppart.findByTypeAppartNom", query = "SELECT t FROM TypeAppart t WHERE t.typeAppartNom = :typeAppartNom")})
public class TypeAppart implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static String APPARTSTUDIO = "STUDIO";
    public static String APPARTCOLOC = "COLOCATION";
    public static String APPARTPMR = "PMR";
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "type_appart_nom")
    private String typeAppartNom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typeAppartNom")
    private Collection<Logement> logementCollection;

    public TypeAppart() {
    }

    public TypeAppart(String typeAppartNom) {
        this.typeAppartNom = typeAppartNom;
    }

    public String getTypeAppartNom() {
        return typeAppartNom;
    }

    public void setTypeAppartNom(String typeAppartNom) {
        this.typeAppartNom = typeAppartNom;
    }

    public Collection<Logement> getLogementCollection() {
        return logementCollection;
    }

    public void setLogementCollection(Collection<Logement> logementCollection) {
        this.logementCollection = logementCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeAppartNom != null ? typeAppartNom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeAppart)) {
            return false;
        }
        TypeAppart other = (TypeAppart) object;
        if ((this.typeAppartNom == null && other.typeAppartNom != null) || (this.typeAppartNom != null && !this.typeAppartNom.equals(other.typeAppartNom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.ecnlogement.items.TypeAppart[ typeAppartNom=" + typeAppartNom + " ]";
    }
    
}
