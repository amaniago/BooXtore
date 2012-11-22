package Jpa.Classes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CONTENIR")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Contenir.findAll", query = "SELECT c FROM Contenir c"),
    @NamedQuery(name = "Contenir.findByIdCommande", query = "SELECT c FROM Contenir c WHERE c.contenirPK.idCommande = :idCommande"),
    @NamedQuery(name = "Contenir.findByIdLivre", query = "SELECT c FROM Contenir c WHERE c.contenirPK.idLivre = :idLivre"),
    @NamedQuery(name = "Contenir.findByQuantiteCommander", query = "SELECT c FROM Contenir c WHERE c.quantiteCommander = :quantiteCommander")
})
public class Contenir implements Serializable
{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ContenirPK contenirPK;
    @Column(name = "QUANTITE_COMMANDER")
    private Integer quantiteCommander;
    @JoinColumn(name = "ID_LIVRE", referencedColumnName = "ID_LIVRE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Livre livre;
    @JoinColumn(name = "ID_COMMANDE", referencedColumnName = "ID_COMMANDE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Commande commande;

    public Contenir()
    {
    }

    public Contenir(ContenirPK contenirPK)
    {
        this.contenirPK = contenirPK;
    }

    public Contenir(int idCommande, int idLivre)
    {
        this.contenirPK = new ContenirPK(idCommande, idLivre);
    }

    public ContenirPK getContenirPK()
    {
        return contenirPK;
    }

    public void setContenirPK(ContenirPK contenirPK)
    {
        this.contenirPK = contenirPK;
    }

    public Integer getQuantiteCommander()
    {
        return quantiteCommander;
    }

    public void setQuantiteCommander(Integer quantiteCommander)
    {
        this.quantiteCommander = quantiteCommander;
    }

    public Livre getLivre()
    {
        return livre;
    }

    public void setLivre(Livre livre)
    {
        this.livre = livre;
    }

    public Commande getCommande()
    {
        return commande;
    }

    public void setCommande(Commande commande)
    {
        this.commande = commande;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (contenirPK != null ? contenirPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contenir))
        {
            return false;
        }
        Contenir other = (Contenir) object;
        if ((this.contenirPK == null && other.contenirPK != null) || (this.contenirPK != null && !this.contenirPK.equals(other.contenirPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Jpa.Classes.Contenir[ contenirPK=" + contenirPK + " ]";
    }
}