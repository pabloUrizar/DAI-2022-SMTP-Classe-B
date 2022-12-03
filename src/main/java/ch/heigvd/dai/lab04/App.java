package ch.heigvd.dai.lab04;

import ch.heigvd.dai.lab04.config.GestionnaireConfiguration;
import ch.heigvd.dai.lab04.model.blague.Blague;
import ch.heigvd.dai.lab04.model.blague.GenerateurBlague;
import ch.heigvd.dai.lab04.model.mail.Groupe;
import ch.heigvd.dai.lab04.smtp.ClientSmtp;
import sun.net.smtp.SmtpClient;

import java.io.IOException;
import java.util.List;

/**
 * Application principale du générateur de blagues.
 * Cette application va utiliser tous les composants du générateur de blagues.
 * Elle va générer une blague, choisir un groupe de victimes et envoyer le mail.
 *
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public class App {
    public static void main(String[] args) throws IOException {
        /**

        // Recuperation des propriétés dans le fichier config.properties
        final String fichierVictimes = "src/main/java/ch/heigvd/dai/lab04/config/victimes.UTF8";
        final String fichierMessages = "src/main/java/ch/heigvd/dai/lab04/config/messages.UTF8";
        final String fichierConfig   = "src/main/java/ch/heigvd/dai/lab04/config/config.properties";
        Properties properties = null;


        try (InputStream input = Files.newInputStream(Paths.get(fichierConfig))) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        assert properties != null;
        final String adresseServeurSmtp = properties.getProperty("adresseServeurSmtp");
        final int portServeurSmtp = Integer.parseInt(properties.getProperty("portServeurSmtp"));
        final int nbGroupes = Integer.parseInt(properties.getProperty("nombreDeGroupes"));

        // Envoi des messages avec la méthode envoyerMessage de la classe clientSmtp
        clientSmtp client = new clientSmtp(adresseServeurSmtp, portServeurSmtp);
        GenerateurBlague generateur = new GenerateurBlague();
        // Utiliser le client smtp et le générateur de blagues pour envoyer les messages

        // ...

        System.out.println("Fin de l'envoi des messages");
         **/

        // Création du client SMTP en utilisant le générateur de blague et le gestionnaire de configuration qui
        // contient les informations de connexion au serveur SMTP.

        GestionnaireConfiguration gestionnaireConfiguration = new GestionnaireConfiguration();
        GenerateurBlague generateurBlague = new GenerateurBlague(gestionnaireConfiguration);
        ClientSmtp clientSmtp             = new ClientSmtp(gestionnaireConfiguration.getAdresseServeurSmtp(),
                                                           gestionnaireConfiguration.getPortServeurSmtp());

        // Génération des blagues
        List<Blague> blagues = generateurBlague.genererBlagues();

        // Envoie des blagues à la liste de groupes de victimes
        for(Blague blague : blagues){
            clientSmtp.envoyerMessage(blague.genererMessage());
        }

        System.out.println("Fin de l'envoi des messages");

    }
}
