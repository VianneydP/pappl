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
@Table(name = "commune")
@NamedQueries({
    @NamedQuery(name = "Commune.findAll", query = "SELECT c FROM Commune c"),
    @NamedQuery(name = "Commune.findByCodeCommune", query = "SELECT c FROM Commune c WHERE c.codeCommune = :codeCommune"),
    @NamedQuery(name = "Commune.findByNomCommune", query = "SELECT c FROM Commune c WHERE c.nomCommune = :nomCommune"),
    @NamedQuery(name = "Commune.findByCodePostal", query = "SELECT c FROM Commune c WHERE c.codePostal = :codePostal"),
    @NamedQuery(name = "Commune.findByLatitude", query = "SELECT c FROM Commune c WHERE c.latitude = :latitude"),
    @NamedQuery(name = "Commune.findByLongitude", query = "SELECT c FROM Commune c WHERE c.longitude = :longitude"),
    @NamedQuery(name = "Commune.findByDansMetropoleNantes", query = "SELECT c FROM Commune c WHERE c.dansMetropoleNantes = :dansMetropoleNantes")})
public class Commune implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "code_commune")
    private Integer codeCommune;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nom_commune")
    private String nomCommune;
    @Basic(optional = false)
    @NotNull
    @Column(name = "code_postal")
    private int codePostal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private float latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private float longitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dans_metropole_nantes")
    private boolean dansMetropoleNantes;
    @OneToMany(mappedBy = "codeCommune")
    private Collection<Eleve> eleveCollection;

    public Commune() {
    }

    public Commune(Integer codeCommune) {
        this.codeCommune = codeCommune;
    }

    public Commune(Integer codeCommune, String nomCommune, int codePostal, float latitude, float longitude, boolean dansMetropoleNantes) {
        this.codeCommune = codeCommune;
        this.nomCommune = nomCommune;
        this.codePostal = codePostal;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dansMetropoleNantes = dansMetropoleNantes;
    }

    public Integer getCodeCommune() {
        return codeCommune;
    }

    public void setCodeCommune(Integer codeCommune) {
        this.codeCommune = codeCommune;
    }

    public String getNomCommune() {
        return nomCommune;
    }

    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public boolean getDansMetropoleNantes() {
        return dansMetropoleNantes;
    }

    public void setDansMetropoleNantes(boolean dansMetropoleNantes) {
        this.dansMetropoleNantes = dansMetropoleNantes;
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
        hash += (codeCommune != null ? codeCommune.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commune)) {
            return false;
        }
        Commune other = (Commune) object;
        if ((this.codeCommune == null && other.codeCommune != null) || (this.codeCommune != null && !this.codeCommune.equals(other.codeCommune))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.ecnlogement.items.Commune[ codeCommune=" + codeCommune + " ]";
    }
    
}
