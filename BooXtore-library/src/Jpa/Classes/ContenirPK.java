/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa.Classes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author MANIAGO
 */
@Embeddable
public class ContenirPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_COMMANDE")
    private int idCommande;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_LIVRE")
    private int idLivre;

    public ContenirPK() {
    }

    public ContenirPK(int idCommande, int idLivre) {
        this.idCommande = idCommande;
        this.idLivre = idLivre;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCommande;
        hash += (int) idLivre;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContenirPK)) {
            return false;
        }
        ContenirPK other = (ContenirPK) object;
        if (this.idCommande != other.idCommande) {
            return false;
        }
        if (this.idLivre != other.idLivre) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Jpa.Classes.ContenirPK[ idCommande=" + idCommande + ", idLivre=" + idLivre + " ]";
    }
    
}
