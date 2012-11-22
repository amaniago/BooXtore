package Jpa.Classes;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "COMPTE")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Compte.findAll", query = "SELECT c FROM Compte c"),
    @NamedQuery(name = "Compte.findByIdCompte", query = "SELECT c FROM Compte c WHERE c.idCompte = :idCompte"),
    @NamedQuery(name = "Compte.findByTypeCompte", query = "SELECT c FROM Compte c WHERE c.typeCompte = :typeCompte")
})
public class Compte implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID_COMPTE")
    private Integer idCompte;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TYPE_COMPTE")
    private String typeCompte;
    @OneToMany(mappedBy = "compte")
    private List<Client> clientList;

    public Compte()
    {
    }

    public Compte(Integer idCompte)
    {
        this.idCompte = idCompte;
    }

    public Compte(Integer idCompte, String typeCompte)
    {
        this.idCompte = idCompte;
        this.typeCompte = typeCompte;
    }

    public Integer getIdCompte()
    {
        return idCompte;
    }

    public void setIdCompte(Integer idCompte)
    {
        this.idCompte = idCompte;
    }

    public String getTypeCompte()
    {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte)
    {
        this.typeCompte = typeCompte;
    }

    @XmlTransient
    public List<Client> getClientList()
    {
        return clientList;
    }

    public void setClientList(List<Client> clientList)
    {
        this.clientList = clientList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idCompte != null ? idCompte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compte))
        {
            return false;
        }
        Compte other = (Compte) object;
        if ((this.idCompte == null && other.idCompte != null) || (this.idCompte != null && !this.idCompte.equals(other.idCompte)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Jpa.Classes.Compte[ idCompte=" + idCompte + " ]";
    }
}