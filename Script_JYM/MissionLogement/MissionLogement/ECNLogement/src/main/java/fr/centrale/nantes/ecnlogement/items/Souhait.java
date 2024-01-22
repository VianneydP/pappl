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
@Table(name = "souhait")
@NamedQueries({
    @NamedQuery(name = "Souhait.findAll", query = "SELECT s FROM Souhait s"),
    @NamedQuery(name = "Souhait.findByTypeSouhait", query = "SELECT s FROM Souhait s WHERE s.typeSouhait = :typeSouhait")})
public class Souhait implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "type_souhait")
    private String typeSouhait;
    @OneToMany(mappedBy = "typeSouhait")
    private Collection<Eleve> eleveCollection;

    public Souhait() {
    }

    public Souhait(String typeSouhait) {
        this.typeSouhait = typeSouhait;
    }

    public String getTypeSouhait() {
        return typeSouhait;
    }

    public void setTypeSouhait(String typeSouhait) {
        this.typeSouhait = typeSouhait;
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
        hash += (typeSouhait != null ? typeSouhait.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Souhait)) {
            return false;
        }
        Souhait other = (Souhait) object;
        if ((this.typeSouhait == null && other.typeSouhait != null) || (this.typeSouhait != null && !this.typeSouhait.equals(other.typeSouhait))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.ecnlogement.items.Souhait[ typeSouhait=" + typeSouhait + " ]";
    }
    
}
