package Jpa.Classes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "LIVRE")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Livre.findAll", query = "SELECT l FROM Livre l"),
    @NamedQuery(name = "Livre.findByIdLivre", query = "SELECT l FROM Livre l WHERE l.idLivre = :idLivre"),
    @NamedQuery(name = "Livre.findByTitre", query = "SELECT l FROM Livre l WHERE l.titre = :titre"),
    @NamedQuery(name = "Livre.findByDateParution", query = "SELECT l FROM Livre l WHERE l.dateParution = :dateParution"),
    @NamedQuery(name = "Livre.findByResume", query = "SELECT l FROM Livre l WHERE l.resume = :resume"),
    @NamedQuery(name = "Livre.findBySommaire", query = "SELECT l FROM Livre l WHERE l.sommaire = :sommaire"),
    @NamedQuery(name = "Livre.findByQuantiterDisponible", query = "SELECT l FROM Livre l WHERE l.quantiterDisponible = :quantiterDisponible"),
    @NamedQuery(name = "Livre.findByAuteur", query = "SELECT l FROM Livre l WHERE l.auteur = :auteur"),
    @NamedQuery(name = "Livre.findByEditeur", query = "SELECT l FROM Livre l WHERE l.editeur = :editeur"),
    @NamedQuery(name = "Livre.findByPrix", query = "SELECT l FROM Livre l WHERE l.prix = :prix"),
    @NamedQuery(name = "Livre.findByEtat", query = "SELECT l FROM Livre l WHERE l.etat = :etat")})
public class Livre implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID_LIVRE")
    private Integer idLivre;
    @Size(max = 200)
    @Column(name = "TITRE")
    private String titre;
    @Column(name = "DATE_PARUTION")
    @Temporal(TemporalType.DATE)
    private Date dateParution;
    @Size(max = 10000)
    @Column(name = "RESUME")
    private String resume;
    @Size(max = 5000)
    @Column(name = "SOMMAIRE")
    private String sommaire;
    @Column(name = "QUANTITER_DISPONIBLE")
    private Integer quantiterDisponible;
    @Size(max = 50)
    @Column(name = "AUTEUR")
    private String auteur;
    @Size(max = 100)
    @Column(name = "EDITEUR")
    private String editeur;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRIX")
    private BigDecimal prix;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "livre")
    private List<Contenir> contenirList;
    @JoinColumn(name = "ID_ETAT_LIVRE", referencedColumnName = "ID_ETAT_LIVRE")
    @ManyToOne
    private EtatLivre idEtatLivre;
    @JoinColumn(name = "ID_CATEGORIE", referencedColumnName = "ID_CATEGORIE")
    @ManyToOne(optional = false)
    private Categorie idCategorie;

    public Livre()
    {
    }

    public Livre(Integer idLivre)
    {
        this.idLivre = idLivre;
    }

    public Integer getIdLivre()
    {
        return idLivre;
    }

    public void setIdLivre(Integer idLivre)
    {
        this.idLivre = idLivre;
    }

    public String getTitre()
    {
        return titre;
    }

    public void setTitre(String titre)
    {
        this.titre = titre;
    }

    public Date getDateParution()
    {
        return dateParution;
    }

    public void setDateParution(Date dateParution)
    {
        this.dateParution = dateParution;
    }

    public String getResume()
    {
        return resume;
    }

    public void setResume(String resume)
    {
        this.resume = resume;
    }

    public String getSommaire()
    {
        return sommaire;
    }

    public void setSommaire(String sommaire)
    {
        this.sommaire = sommaire;
    }

    public Integer getQuantiterDisponible()
    {
        return quantiterDisponible;
    }

    public void setQuantiterDisponible(Integer quantiterDisponible)
    {
        this.quantiterDisponible = quantiterDisponible;
    }

    public String getAuteur()
    {
        return auteur;
    }

    public void setAuteur(String auteur)
    {
        this.auteur = auteur;
    }

    public String getEditeur()
    {
        return editeur;
    }

    public void setEditeur(String editeur)
    {
        this.editeur = editeur;
    }

    public BigDecimal getPrix()
    {
        return prix;
    }

    public void setPrix(BigDecimal prix)
    {
        this.prix = prix;
    }
    public EtatLivre getIdEtatLivre() {
        return idEtatLivre;
    }

    public void setIdEtatLivre(EtatLivre idEtatLivre) {
        this.idEtatLivre = idEtatLivre;
    }

    public Categorie getIdCategorie() {
        return idCategorie;
    }

    public void setCategorie(Categorie categorie)
    {
        this.idCategorie = categorie;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idLivre != null ? idLivre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livre))
        {
            return false;
        }
        Livre other = (Livre) object;
        if ((this.idLivre == null && other.idLivre != null) || (this.idLivre != null && !this.idLivre.equals(other.idLivre)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Jpa.Classes.Livre[ idLivre=" + idLivre + " ]";
    }
}
