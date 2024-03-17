/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
package fr.centrale.nantes.ecnlogement.items;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author viann
 */
@Entity
@Table(name = "texte")
@NamedQueries({
    @NamedQuery(name = "Texte.findAll", query = "SELECT t FROM Texte t"),
    @NamedQuery(name = "Texte.findByTexteNom", query = "SELECT t FROM Texte t WHERE t.texteNom = :texteNom"),
    @NamedQuery(name = "Texte.findByTexteContenu", query = "SELECT t FROM Texte t WHERE t.texteContenu = :texteContenu")})
public class Texte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "texte_nom")
    private String texteNom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "texte_contenu")
    private String texteContenu;

    public Texte() {
    }

    public Texte(String texteNom) {
        this.texteNom = texteNom;
    }

    public Texte(String texteNom, String texteContenu) {
        this.texteNom = texteNom;
        this.texteContenu = texteContenu;
    }

    public String getTexteNom() {
        return texteNom;
    }

    public void setTexteNom(String texteNom) {
        this.texteNom = texteNom;
    }

    public String getTexteContenu() {
        return texteContenu;
    }

    public void setTexteContenu(String texteContenu) {
        this.texteContenu = texteContenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (texteNom != null ? texteNom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Texte)) {
            return false;
        }
        Texte other = (Texte) object;
        if ((this.texteNom == null && other.texteNom != null) || (this.texteNom != null && !this.texteNom.equals(other.texteNom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.ecnlogement.items.Texte[ texteNom=" + texteNom + " ]";
    }
    
}
