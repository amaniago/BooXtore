package Jpa.Classes;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CATEGORIE")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Categorie.findAll", query = "SELECT c FROM Categorie c"),
    @NamedQuery(name = "Categorie.findByIdCategorie", query = "SELECT c FROM Categorie c WHERE c.idCategorie = :idCategorie"),
    @NamedQuery(name = "Categorie.findByNomCategorie", query = "SELECT c FROM Categorie c WHERE c.nomCategorie = :nomCategorie")
})
public class Categorie implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CATEGORIE")
    private Integer idCategorie;
    @Size(max = 50)
    @Column(name = "NOM_CATEGORIE")
    private String nomCategorie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorie")
    private List<Livre> livreList;

    public Categorie()
    {
    }

    public Categorie(Integer idCategorie)
    {
        this.idCategorie = idCategorie;
    }

    public Integer getIdCategorie()
    {
        return idCategorie;
    }

    public void setIdCategorie(Integer idCategorie)
    {
        this.idCategorie = idCategorie;
    }

    public String getNomCategorie()
    {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie)
    {
        this.nomCategorie = nomCategorie;
    }

    @XmlTransient
    public List<Livre> getLivreList()
    {
        return livreList;
    }

    public void setLivreList(List<Livre> livreList)
    {
        this.livreList = livreList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idCategorie != null ? idCategorie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categorie))
        {
            return false;
        }
        Categorie other = (Categorie) object;
        if ((this.idCategorie == null && other.idCategorie != null) || (this.idCategorie != null && !this.idCategorie.equals(other.idCategorie)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return nomCategorie;
    }
}