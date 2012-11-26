package Ejb;

import Jpa.Classes.Client;
import javax.ejb.Remote;

@Remote
public interface CompteEJBRemote
{
    public boolean authentification(String login, String mdp);

    public Client inscription(String login, String mdp, String nom, String prenom, String mail, String adr, String codePostal, String ville);

    public void modifierCompte(Client client);
}