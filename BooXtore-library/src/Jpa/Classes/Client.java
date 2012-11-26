package Jpa.Classes;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "CLIENT")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findByLogin", query = "SELECT c FROM Client c WHERE c.login = :login"),
    @NamedQuery(name = "Client.findByMotDePasse", query = "SELECT c FROM Client c WHERE c.motDePasse = :motDePasse"),
    @NamedQuery(name = "Client.findByNom", query = "SELECT c FROM Client c WHERE c.nom = :nom"),
    @NamedQuery(name = "Client.findByPrenom", query = "SELECT c FROM Client c WHERE c.prenom = :prenom"),
    @NamedQuery(name = "Client.findByEmail", query = "SELECT c FROM Client c WHERE c.email = :email"),
    @NamedQuery(name = "Client.findByAdresse", query = "SELECT c FROM Client c WHERE c.adresse = :adresse"),
    @NamedQuery(name = "Client.findByCodePostal", query = "SELECT c FROM Client c WHERE c.codePostal = :codePostal"),
    @NamedQuery(name = "Client.findByVille", query = "SELECT c FROM Client c WHERE c.ville = :ville")
})
public class Client implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "LOGIN")
    private String login;
    @Size(max = 50)
    @Column(name = "MOT_DE_PASSE")
    private String motDePasse;
    @Size(max = 50)
    @Column(name = "NOM")
    private String nom;
    @Size(max = 50)
    @Column(name = "PRENOM")
    private String prenom;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 200)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 250)
    @Column(name = "ADRESSE")
    private String adresse;
    @Size(max = 5)
    @Column(name = "CODE_POSTAL")
    private String codePostal;
    @Size(max = 100)
    @Column(name = "VILLE")
    private String ville;
    @JoinColumn(name = "ID_COMPTE", referencedColumnName = "ID_COMPTE")
    @ManyToOne
    private Compte compte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "login")
    private List<Commande> commandeList;

    public Client()
    {
    }

    public Client(String login)
    {
        this.login = login;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getMotDePasse()
    {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse)
    {
        this.motDePasse = motDePasse;   //TODO : Déplacer SHA1 ici
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public String getCodePostal()
    {
        return codePostal;
    }

    public void setCodePostal(String codePostal)
    {
        this.codePostal = codePostal;
    }

    public String getVille()
    {
        return ville;
    }

    public void setVille(String ville)
    {
        this.ville = ville;
    }

    public Compte getCompte()
    {
        return compte;
    }

    public void setCompte(Compte compte)
    {
        this.compte = compte;
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
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client))
        {
            return false;
        }
        Client other = (Client) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Jpa.Classes.Client[ login=" + login + " ]";
    }
}