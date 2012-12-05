package Jpa.Classes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "COMMANDE")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Commande.findAll", query = "SELECT c FROM Commande c"),
    @NamedQuery(name = "Commande.findByIdCommande", query = "SELECT c FROM Commande c WHERE c.idCommande = :idCommande"),
    @NamedQuery(name = "Commande.findByTotal", query = "SELECT c FROM Commande c WHERE c.total = :total"),
    @NamedQuery(name = "Commande.findByClient", query = "SELECT c FROM Commande c WHERE c.login = :client")
})
public class Commande implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_COMMANDE")
    private Integer idCommande;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTAL")
    private BigDecimal total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commande")
    private List<Contenir> contenirList;
    @JoinColumn(name = "ID_ETAT_COMMANDE", referencedColumnName = "ID_ETAT_COMMANDE")
    @ManyToOne
    private EtatCommande etatCommande;
    @JoinColumn(name = "LOGIN", referencedColumnName = "LOGIN")
    @ManyToOne(optional = false)
    private Client login;

    public Commande()
    {
    }

    public Commande(Integer idCommande)
    {
        this.idCommande = idCommande;
    }

    public Integer getIdCommande()
    {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande)
    {
        this.idCommande = idCommande;
    }

    public BigDecimal getTotal()
    {
        return total;
    }

    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

    @XmlTransient
    public List<Contenir> getContenirList()
    {
        return contenirList;
    }

    public void setContenirList(List<Contenir> contenirList)
    {
        this.contenirList = contenirList;
    }

    public EtatCommande getEtatCommande()
    {
        return etatCommande;
    }

    public void setEtatCommande(EtatCommande etatCommande)
    {
        this.etatCommande = etatCommande;
    }

    public Client getLogin()
    {
        return login;
    }

    public void setLogin(Client login)
    {
        this.login = login;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idCommande != null ? idCommande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commande))
        {
            return false;
        }
        Commande other = (Commande) object;
        if ((this.idCommande == null && other.idCommande != null) || (this.idCommande != null && !this.idCommande.equals(other.idCommande)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Jpa.Classes.Commande[ idCommande=" + idCommande + " ]";
    }
}