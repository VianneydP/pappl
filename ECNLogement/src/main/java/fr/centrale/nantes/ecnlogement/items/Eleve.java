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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "eleve")
@NamedQueries({
    @NamedQuery(name = "Eleve.findAll", query = "SELECT e FROM Eleve e"),
    @NamedQuery(name = "Eleve.findByEleveId", query = "SELECT e FROM Eleve e WHERE e.eleveId = :eleveId"),
    @NamedQuery(name = "Eleve.findByEleveDateNaissance", query = "SELECT e FROM Eleve e WHERE e.eleveDateNaissance = :eleveDateNaissance"),
    @NamedQuery(name = "Eleve.findByGenre", query = "SELECT e FROM Eleve e WHERE e.genre = :genre"),
    @NamedQuery(name = "Eleve.findByElevePayshab", query = "SELECT e FROM Eleve e WHERE e.elevePayshab = :elevePayshab"),
    @NamedQuery(name = "Eleve.findByEleveVillehab", query = "SELECT e FROM Eleve e WHERE e.eleveVillehab = :eleveVillehab"),
    @NamedQuery(name = "Eleve.findByEleveCodepostal", query = "SELECT e FROM Eleve e WHERE e.eleveCodepostal = :eleveCodepostal"),
    @NamedQuery(name = "Eleve.findByEleveMail", query = "SELECT e FROM Eleve e WHERE e.eleveMail = :eleveMail"),
    @NamedQuery(name = "Eleve.findByEleveNumtel", query = "SELECT e FROM Eleve e WHERE e.eleveNumtel = :eleveNumtel"),
    @NamedQuery(name = "Eleve.findByEleveBoursier", query = "SELECT e FROM Eleve e WHERE e.eleveBoursier = :eleveBoursier"),
    @NamedQuery(name = "Eleve.findByEleveInfosup", query = "SELECT e FROM Eleve e WHERE e.eleveInfosup = :eleveInfosup")})
public class Eleve implements Serializable {

    @Column(name = "eleve_codepostal")
    private Integer eleveCodepostal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eleve_confirm")
    private boolean eleveConfirm;
    @Size(max = 2147483647)
    @Column(name = "eleve_infosup_ve")
    private String eleveInfosupVe;
    @JoinColumn(name = "commune_id", referencedColumnName = "commune_id")
    @ManyToOne
    private Commune communeId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "eleve_id")
    private Integer eleveId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "eleve_numscei")
    private int numscei;
    @Basic(optional = false)
    @Column(name = "eleve_date_naissance")
    @Temporal(TemporalType.DATE)
    private Date eleveDateNaissance;
    @Basic(optional = false)
    @Size(min = 1, max = 2147483647)
    @Column(name = "genre")
    private String genre;
    @Basic(optional = false)
    @Size(min = 1, max = 2147483647)
    @Column(name = "eleve_payshab")
    private String elevePayshab;
    @Basic(optional = false)
    @Size(min = 1, max = 2147483647)
    @Column(name = "eleve_villehab")
    private String eleveVillehab;
    @Size(max = 2147483647)
    @Column(name = "eleve_mail")
    private String eleveMail;
    @Size(max = 2147483647)
    @Column(name = "eleve_numtel")
    private String eleveNumtel;
    @Column(name = "eleve_boursier")
    private Boolean eleveBoursier;
    @Size(max = 2147483647)
    @Column(name = "eleve_infosup")
    private String eleveInfosup;
    @JoinColumn(name = "code_commune", referencedColumnName = "code_commune")
    @ManyToOne
    private Commune codeCommune;
    @JoinColumn(name = "logement_numero", referencedColumnName = "logement_numero")
    @ManyToOne
    private Logement logementNumero;
    @JoinColumn(name = "personne_id", referencedColumnName = "personne_id")
    @ManyToOne(optional = false)
    private Personne personne;
    @JoinColumn(name = "type_souhait", referencedColumnName = "type_souhait")
    @ManyToOne
    private Souhait typeSouhait;

    public Eleve() {
    }

    public Eleve(Integer eleveId) {
        this.eleveId = eleveId;
    }

    public Eleve(Integer eleveId, Date eleveDateNaissance, String genre, String elevePayshab, String eleveVillehab, int eleveCodepostal) {
        this.eleveId = eleveId;
        this.eleveDateNaissance = eleveDateNaissance;
        this.genre = genre;
        this.elevePayshab = elevePayshab;
        this.eleveVillehab = eleveVillehab;
        this.eleveCodepostal = eleveCodepostal;
    }

    public Integer getEleveId() {
        return eleveId;
    }

    public void setEleveId(Integer eleveId) {
        this.eleveId = eleveId;
    }
    
    public Integer getNumscei(){
        return numscei;
    }
    
    public void setNumscei(Integer numscei){
        this.numscei = numscei;
    }
    
    public Date getEleveDateNaissance() {
        return eleveDateNaissance;
    }

    public void setEleveDateNaissance(Date eleveDateNaissance) {
        this.eleveDateNaissance = eleveDateNaissance;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getElevePayshab() {
        return elevePayshab;
    }

    public void setElevePayshab(String elevePayshab) {
        this.elevePayshab = elevePayshab;
    }

    public String getEleveVillehab() {
        return eleveVillehab;
    }

    public void setEleveVillehab(String eleveVillehab) {
        this.eleveVillehab = eleveVillehab;
    }

    public int getEleveCodepostal() {
        return eleveCodepostal;
    }

    public void setEleveCodepostal(int eleveCodepostal) {
        this.eleveCodepostal = eleveCodepostal;
    }

    public String getEleveMail() {
        return eleveMail;
    }

    public void setEleveMail(String eleveMail) {
        this.eleveMail = eleveMail;
    }

    public String getEleveNumtel() {
        return eleveNumtel;
    }

    public void setEleveNumtel(String eleveNumtel) {
        this.eleveNumtel = eleveNumtel;
    }

    public Boolean getEleveBoursier() {
        return eleveBoursier;
    }

    public void setEleveBoursier(Boolean eleveBoursier) {
        this.eleveBoursier = eleveBoursier;
    }

    public String getEleveInfosup() {
        return eleveInfosup;
    }

    public void setEleveInfosup(String eleveInfosup) {
        this.eleveInfosup = eleveInfosup;
    }

    public Commune getCodeCommune() {
        return codeCommune;
    }

    public void setCodeCommune(Commune codeCommune) {
        this.codeCommune = codeCommune;
    }

    public Logement getLogementNumero() {
        return logementNumero;
    }

    public void setLogementNumero(Logement logementNumero) {
        this.logementNumero = logementNumero;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Souhait getTypeSouhait() {
        return typeSouhait;
    }

    public void setTypeSouhait(Souhait typeSouhait) {
        this.typeSouhait = typeSouhait;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eleveId != null ? eleveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eleve)) {
            return false;
        }
        Eleve other = (Eleve) object;
        if ((this.eleveId == null && other.eleveId != null) || (this.eleveId != null && !this.eleveId.equals(other.eleveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.ecnlogement.items.Eleve[ eleveId=" + eleveId + " ]";
    }

    public void setEleveCodepostal(Integer eleveCodepostal) {
        this.eleveCodepostal = eleveCodepostal;
    }

    public boolean getEleveConfirm() {
        return eleveConfirm;
    }

    public void setEleveConfirm(boolean eleveConfirm) {
        this.eleveConfirm = eleveConfirm;
    }

    public String getEleveInfosupVe() {
        return eleveInfosupVe;
    }

    public void setEleveInfosupVe(String eleveInfosupVe) {
        this.eleveInfosupVe = eleveInfosupVe;
    }

    public Commune getCommuneId() {
        return communeId;
    }

    public void setCommuneId(Commune communeId) {
        this.communeId = communeId;
    }
    
}
