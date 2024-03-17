/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
package fr.centrale.nantes.ecnlogement.items;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kwyhr
 */
@Entity
@Table(name = "connexion")
@NamedQueries({
    @NamedQuery(name = "Connexion.findAll", query = "SELECT c FROM Connexion c"),
    @NamedQuery(name = "Connexion.findByConnectionId", query = "SELECT c FROM Connexion c WHERE c.connectionId = :connectionId"),
    @NamedQuery(name = "Connexion.findByExpiration", query = "SELECT c FROM Connexion c WHERE c.expiration = :expiration")})
public class Connexion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "connection_id")
    private String connectionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;
    @JoinColumn(name = "personne_id", referencedColumnName = "personne_id")
    @ManyToOne(optional = false)
    private Personne personne;

    public Connexion() {
    }

    public Connexion(String connectionId) {
        this.connectionId = connectionId;
    }

    public Connexion(String connectionId, Date expiration) {
        this.connectionId = connectionId;
        this.expiration = expiration;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (connectionId != null ? connectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Connexion)) {
            return false;
        }
        Connexion other = (Connexion) object;
        if ((this.connectionId == null && other.connectionId != null) || (this.connectionId != null && !this.connectionId.equals(other.connectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.ecnlogement.items.Connexion[ connectionId=" + connectionId + " ]";
    }
    
}
