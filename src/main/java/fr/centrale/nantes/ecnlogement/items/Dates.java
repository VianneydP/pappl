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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author viann
 */
@Entity
@Table(name = "date")
@NamedQueries({
    @NamedQuery(name = "Dates.findAll", query = "SELECT d FROM Dates d"),
    @NamedQuery(name = "Dates.findByDatesAnnee", query = "SELECT d FROM Dates d WHERE d.datesAnnee = :datesAnnee"),
    @NamedQuery(name = "Dates.findByDatesDebut", query = "SELECT d FROM Dates d WHERE d.datesDebut = :datesDebut"),
    @NamedQuery(name = "Dates.findByDatesFin", query = "SELECT d FROM Dates d WHERE d.datesFin = :datesFin"),
    @NamedQuery(name = "Dates.findByDatesResultats", query = "SELECT d FROM Dates d WHERE d.datesResultats = :datesResultats")})
public class Dates implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "date_debut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datesDebut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datesFin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_resultats")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datesResultats;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_annee")
    private Integer datesAnnee;

    public Dates() {
    }

    public Dates(Integer datesAnnee) {
        this.datesAnnee = datesAnnee;
    }

    public Dates(Integer datesAnnee, Date datesDebut, Date datesFin, Date datesResultats) {
        this.datesAnnee = datesAnnee;
        this.datesDebut = datesDebut;
        this.datesFin = datesFin;
        this.datesResultats = datesResultats;
    }

    public Integer getDatesAnnee() {
        return datesAnnee;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datesAnnee != null ? datesAnnee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dates)) {
            return false;
        }
        Dates other = (Dates) object;
        if ((this.datesAnnee == null && other.datesAnnee != null) || (this.datesAnnee != null && !this.datesAnnee.equals(other.datesAnnee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.ecnlogement.items.Dates[ datesAnnee=" + datesAnnee + " ]";
    }

    public void setDatesAnnee(Integer datesAnnee) {
        this.datesAnnee = datesAnnee;
    }
    
    public Date getDatesDebut() {
        return datesDebut;
    }

    public void setDatesDebut(Date dateDebut) {
        this.datesDebut = dateDebut;
    }

    public Date getDatesFin() {
        return datesFin;
    }

    public void setDatesFin(Date dateFin) {
        this.datesFin = dateFin;
    }

    public Date getDatesResultats() {
        return datesResultats;
    }

    public void setDatesResultats(Date dateResultats) {
        this.datesResultats = dateResultats;
    }    
}
