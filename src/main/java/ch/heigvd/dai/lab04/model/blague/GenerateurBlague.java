package ch.heigvd.dai.lab04.model.blague;

import ch.heigvd.dai.lab04.config.GestionnaireConfiguration;
import ch.heigvd.dai.lab04.model.mail.Groupe;
import ch.heigvd.dai.lab04.model.mail.Personne;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Cette classe permet de générer des blagues.
 * Elle va implémenter les règles suivantes :
 * - L'utilisateur définit une liste de victimes
 * <p>
 * - L'utilisateur définit combien de groupes de victimes doivent être formés.
 * Un groupe est composé au minimum de 3 personnes, un qui envoie la blague et 2 qui la reçoivent.
 * <p>
 * - L'utilisateur définit une liste de messages à envoyer par email. Quand une blague est faite sur
 * un groupe de victimes, un des messages est choisi et est envoyé à chaque personne du groupe depuis
 * l'adresse de la victime choisie comme expéditeur. (Les victimes vont donc croire que l'expéditeur
 * est bien celui dont l'adresse est indiquée).
 * <p>
 * Elle va utiliser la classe GestionConfiguration pour récupérer les informations de configuration
 * depuis le fichier de configuration.
 * Ensuite, elle va générer les blagues en utilisant les informations de configuration et générer les groupes de victimes.
 * </p>
 *
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public class GenerateurBlague {
    /* Attributs */
    // Le gestionnaire de configuration va contenir une liste de victimes et une liste de messages
    // ainsi que le nombre de groupes à créer, l'adresse du serveur SMTP et le port du serveur SMTP
    private final GestionnaireConfiguration gestionnaireConfiguration;
    private static final Logger LOG = Logger.getLogger(GenerateurBlague.class.getName());

    /**
     // Groupes de victimes
     private List<Groupe> listeGroupes;
     // Liste des messages à envoyer par mail (blagues) pour chaque groupe
     private List<Message> listeMessages;
     private final List<String> messages = new ArrayList<>();
     // Groupe de victimes qui vont recevoir la blague (1 expéditeur et 2 destinataires minimum)
     private final Groupe groupeVictimes = new Groupe();

     static final int NB_GROUPES_MIN = 3;
     static private final String FIN_MSG = "$fin$";
     **/

    /**
     * Constructeur de la classe GenerateurBlague
     *
     * @param gestionnaireConfiguration qui va permettre de récupérer les informations de configuration
     */
    public GenerateurBlague(GestionnaireConfiguration gestionnaireConfiguration) {
        // Le gestionnaire de configuration contient les informations de configuration nécessaires
        // pour générer les blagues
        this.gestionnaireConfiguration = gestionnaireConfiguration;
    }

    public List<Groupe> genererGroupes(List<String> victimsMail, int nombreGroupes) {

        List<String> victimes = new ArrayList<>(victimsMail);

        Collections.shuffle(victimes);
        List<Groupe> groupes = new ArrayList<>();

        for (int i = 0; i < nombreGroupes; i++) {
            Groupe groupe = new Groupe();
            groupes.add(groupe);
        }

        int tour = 0;
        Groupe groupeCible;

        while (victimes.size() > 0) {
            groupeCible = groupes.get(tour);
            tour = (tour + 1) % groupes.size();
            Personne victime = new Personne(victimes.remove(0));
            LOG.warning(victime.getAdresseEmail());
            groupeCible.ajouterPersonne(victime);
        }
        return groupes;
    }


    public List<Blague> genererBlagues(){

        List<Blague> blagues = new ArrayList<>();
        List<String> messages = gestionnaireConfiguration.getListeMessages();

        int numberVictims = gestionnaireConfiguration.getPersonnes().size();
        int numberGroups = gestionnaireConfiguration.getNombreGroupes();

        LOG.warning(String.valueOf(numberGroups));
        LOG.warning(String.valueOf(numberVictims));

        if(numberVictims / numberGroups < 3){
            numberGroups = numberVictims / 3;
            LOG.warning("There are not enough people to generate a prank");
        }

        List<Groupe> groupes = genererGroupes(gestionnaireConfiguration.getAdressesEmail(), numberGroups);
        for(Groupe groupe : groupes){
            Blague blague = new Blague();

            List<Personne> victimes = groupe.getPersonnes();

            // Shuffle to make the sender random
            Collections.shuffle(victimes);
            Personne expediteur = victimes.remove(0);
            blague.setEnvoyeur(expediteur);
            blague.ajouterDestinataire(victimes);

            Random rand = new Random();
            String message = messages.get(rand.nextInt(messages.size()));
            blague.setMessage(message);

            blagues.add(blague);
        }
        return blagues;
    }

}
