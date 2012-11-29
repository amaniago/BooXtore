package Jpa.Classes;

import java.io.Serializable;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "ETAT_COMMANDE")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "EtatCommande.findAll", query = "SELECT e FROM EtatCommande e"),
    @NamedQuery(name = "EtatCommande.findByIdEtatCommande", query = "SELECT e FROM EtatCommande e WHERE e.idEtatCommande = :idEtatCommande"),
    @NamedQuery(name = "EtatCommande.findByTypeEtat", query = "SELECT e FROM EtatCommande e WHERE e.typeEtat = :typeEtat")
})
public class EtatCommande implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ID_ETAT_COMMANDE")
    private String idEtatCommande;
    @Size(max = 20)
    @Column(name = "TYPE_ETAT")
    private String typeEtat;
    @OneToMany(mappedBy = "etatCommande")
    private List<Commande> commandeList;

    public EtatCommande()
    {
    }

    public EtatCommande(String idEtatCommande)
    {
        this.idEtatCommande = idEtatCommande;
    }

    public String getIdEtatCommande()
    {
        return idEtatCommande;
    }

    public void setIdEtatCommande(String idEtatCommande)
    {
        this.idEtatCommande = idEtatCommande;
    }

    public String getTypeEtat()
    {
        return typeEtat;
    }

    public void setTypeEtat(String typeEtat)
    {
        this.typeEtat = typeEtat;
    }

    @XmlTransient
    public List<Commande> getCommandeList()
    {
        return commandeList;
    }

    public void setCommandeList(List<Commande> commandeList)
    {
        this.commandeList = commandeList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idEtatCommande != null ? idEtatCommande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtatCommande))
        {
            return false;
        }
        EtatCommande other = (EtatCommande) object;
        if ((this.idEtatCommande == null && other.idEtatCommande != null) || (this.idEtatCommande != null && !this.idEtatCommande.equals(other.idEtatCommande)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return typeEtat;
    }
}