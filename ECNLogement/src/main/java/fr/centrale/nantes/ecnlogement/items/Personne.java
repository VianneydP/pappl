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
@Table(name = "personne")
@NamedQueries({
    @NamedQuery(name = "Personne.findAll", query = "SELECT p FROM Personne p"),
    @NamedQuery(name = "Personne.findByPersonneId", query = "SELECT p FROM Personne p WHERE p.personneId = :personneId"),
    @NamedQuery(name = "Personne.findByPersonneNom", query = "SELECT p FROM Personne p WHERE p.personneNom = :personneNom"),
    @NamedQuery(name = "Personne.findByPersonnePrenom", query = "SELECT p FROM Personne p WHERE p.personnePrenom = :personnePrenom"),
    @NamedQuery(name = "Personne.findByPersonneLogin", query = "SELECT p FROM Personne p WHERE p.personneLogin = :personneLogin"),
    @NamedQuery(name = "Personne.findByPersonnePassword", query = "SELECT p FROM Personne p WHERE p.personnePassword = :personnePassword")})
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "personne_id")
    private Integer personneId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "personne_nom")
    private String personneNom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "personne_prenom")
    private String personnePrenom;
    @Size(max = 256)
    @Column(name = "personne_login")
    private String personneLogin;
    @Size(max = 3000)
    @Column(name = "personne_password")
    private String personnePassword;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Role roleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personne")
    private Collection<Connexion> connexionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personne")
    private Collection<Eleve> eleveCollection;

    public Personne() {
    }

    public Personne(Integer personneId) {
        this.personneId = personneId;
    }

    public Personne(Integer personneId, String personneNom, String personnePrenom) {
        this.personneId = personneId;
        this.personneNom = personneNom;
        this.personnePrenom = personnePrenom;
    }

    public Integer getPersonneId() {
        return personneId;
    }

    public void setPersonneId(Integer personneId) {
        this.personneId = personneId;
    }

    public String getPersonneNom() {
        return personneNom;
    }

    public void setPersonneNom(String personneNom) {
        this.personneNom = personneNom;
    }

    public String getPersonnePrenom() {
        return personnePrenom;
    }

    public void setPersonnePrenom(String personnePrenom) {
        this.personnePrenom = personnePrenom;
    }

    public String getPersonneLogin() {
        return personneLogin;
    }

    public void setPersonneLogin(String personneLogin) {
        this.personneLogin = personneLogin;
    }

    public String getPersonnePassword() {
        return personnePassword;
    }

    public void setPersonnePassword(String personnePassword) {
        this.personnePassword = personnePassword;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public Collection<Connexion> getConnexionCollection() {
        return connexionCollection;
    }

    public void setConnexionCollection(Collection<Connexion> connexionCollection) {
        this.connexionCollection = connexionCollection;
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
        hash += (personneId != null ? personneId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personne)) {
            return false;
        }
        Personne other = (Personne) object;
        if ((this.personneId == null && other.personneId != null) || (this.personneId != null && !this.personneId.equals(other.personneId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.ecnlogement.items.Personne[ personneId=" + personneId + " ]";
    }
    
}
