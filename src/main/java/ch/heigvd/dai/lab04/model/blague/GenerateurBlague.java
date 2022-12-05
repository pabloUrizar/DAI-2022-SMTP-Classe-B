package ch.heigvd.dai.lab04.model.blague;

import ch.heigvd.dai.lab04.config.GestionnaireConfiguration;
import ch.heigvd.dai.lab04.model.mail.Groupe;
import ch.heigvd.dai.lab04.model.mail.Personne;

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
            LOG.info(victime.getAdresseEmail());
            groupeCible.ajouterPersonne(victime);
        }
        return groupes;
    }


    public List<Blague> genererBlagues(){

        List<Blague> blagues  = new ArrayList<>();
        List<String> messages = gestionnaireConfiguration.getListeMessages();

        int nombreDeVictimes = gestionnaireConfiguration.getPersonnes().size();
        int nombreDeGroupes  = gestionnaireConfiguration.getNombreGroupes();
        // On place le ou les gens en copie dans une liste de personnes
        List<Personne> enCopie = gestionnaireConfiguration.getListeEnCopie();
        // On place l'adresse des gens en copie dans un tableau de String
        String[] enCopieString = new String[enCopie.size()];
        for (int i = 0; i < enCopie.size(); i++) {
            enCopieString[i] = enCopie.get(i).getAdresseEmail();
        }

        for (Personne personne : enCopie) {
            LOG.info("Cc : " + personne.getAdresseEmail());
        }

        LOG.info(String.valueOf(nombreDeGroupes));
        LOG.info(String.valueOf(nombreDeVictimes));

        if(nombreDeVictimes / nombreDeGroupes < 3){
            nombreDeGroupes = nombreDeVictimes / 3;
            LOG.warning("Pas assez de victimes pour former les groupes demandés, diminution du nombre de groupes");
        }

        List<Groupe> groupes = genererGroupes(gestionnaireConfiguration.getAdressesEmail(), nombreDeGroupes);
        for(Groupe groupe : groupes){
            Blague blague = new Blague();

            List<Personne> victimes = groupe.getPersonnes();

            // On choisit une victime au hasard pour être l'expéditeur de la blague
            Collections.shuffle(victimes);
            Personne expediteur = victimes.remove(0);
            blague.setExpediteur(expediteur);
            blague.ajouterDestinataire(victimes);

            blague.setEnCopie(List.of(enCopieString));

            Random rand    = new Random();
            String message = messages.get(rand.nextInt(messages.size()));

            blague.setMessage(message);
            blagues.add(blague);
        }
        return blagues;
    }

}
