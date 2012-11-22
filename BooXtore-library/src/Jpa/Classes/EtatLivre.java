package Jpa.Classes;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "ETAT_LIVRE")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "EtatLivre.findAll", query = "SELECT e FROM EtatLivre e"),
    @NamedQuery(name = "EtatLivre.findByIdEtatLivre", query = "SELECT e FROM EtatLivre e WHERE e.idEtatLivre = :idEtatLivre"),
    @NamedQuery(name = "EtatLivre.findByValeurEtat", query = "SELECT e FROM EtatLivre e WHERE e.valeurEtat = :valeurEtat")
})
public class EtatLivre implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "ID_ETAT_LIVRE")
    private String idEtatLivre;
    @Size(max = 50)
    @Column(name = "VALEUR_ETAT")
    private String valeurEtat;
    @OneToMany(mappedBy = "etatLivre")
    private List<Livre> livreList;

    public EtatLivre()
    {
    }

    public EtatLivre(String idEtatLivre)
    {
        this.idEtatLivre = idEtatLivre;
    }

    public String getIdEtatLivre()
    {
        return idEtatLivre;
    }

    public void setIdEtatLivre(String idEtatLivre)
    {
        this.idEtatLivre = idEtatLivre;
    }

    public String getValeurEtat()
    {
        return valeurEtat;
    }

    public void setValeurEtat(String valeurEtat)
    {
        this.valeurEtat = valeurEtat;
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
        hash += (idEtatLivre != null ? idEtatLivre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EtatLivre))
        {
            return false;
        }
        EtatLivre other = (EtatLivre) object;
        if ((this.idEtatLivre == null && other.idEtatLivre != null) || (this.idEtatLivre != null && !this.idEtatLivre.equals(other.idEtatLivre)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Jpa.Classes.EtatLivre[ idEtatLivre=" + idEtatLivre + " ]";
    }
}