package Ejb;

import Jpa.Classes.Client;
import Jpa.Classes.Compte;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB gérant les comptes et permettant la création, la modification et l'authentification des comptes
 */
@Stateless
public class CompteEJB implements CompteEJBRemote
{
    /**
     * Contexte de persistance injecté gérant les transactions, dont le cycle de vie et géré par le container d'EJB.
     */
    @PersistenceContext
    EntityManager em;

    /**
     * Permet de vérifier que le login est valide
     * @param login Login du client
     * @param mdp Mot de passe du client
     * @return Vérifie que les login du client correspondent
     */
    @Override
    public boolean authentification(String login, String mdp)
    {
        Client client = em.find(Client.class, login);
        return client != null ? client.getMotDePasse().equals(sha1(mdp)) : false;
    }

    /**
     * Enregistre un client dans la base de données
     * @param login Login de l'utilisateur
     * @param mdp Mot de passe de l'utilisateur
     * @param nom Nom de l'utilisateur
     * @param prenom Prénom de l'utilisateur
     * @param mail Mail de l'utilisateur
     * @param adr Adresse de l'utilisateur
     * @param codePostal CodePostal
     * @param ville Ville
     */
    @Override
    public Client inscription(String login, String mdp, String nom, String prenom, String mail, String adr, String codePostal, String ville)
    {
        Client client = new Client();
        client.setLogin(login);
        client.setMotDePasse(sha1(mdp));
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(mail);
        client.setAdresse(adr);
        client.setCodePostal(codePostal);
        client.setVille(ville);
        client.setCompte(em.find(Compte.class, 2));
        em.persist(client);
        return client;
    }

    /**
     * Obtient les informations de l'utilisateur en fonction de son login
     * @return login
     */
    @Override
    public Client getLogin(String login)
    {
        return em.find(Client.class, login);
    }

    /**
     * Permet de modifier les informations du comptes
     * @param client Client à modifier
     */
    @Override
    public void modifierCompte(Client client)
    {
        em.merge(client);
    }

    private String sha1(String input)
    {
        MessageDigest mDigest = null;
        try
        {
            mDigest = MessageDigest.getInstance("SHA1");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++)
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        return sb.toString();
    }
}