package ch.heigvd.dai.lab04;

import ch.heigvd.dai.lab04.config.GestionnaireConfiguration;
import ch.heigvd.dai.lab04.model.blague.Blague;
import ch.heigvd.dai.lab04.model.blague.GenerateurBlague;
import ch.heigvd.dai.lab04.smtp.ClientSmtp;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

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

        Logger logger = Logger.getLogger(App.class.getName());

        // On récupère les configurations
        GestionnaireConfiguration gestionnaireConfiguration = new GestionnaireConfiguration();
        GenerateurBlague generateurBlague                   = new GenerateurBlague(gestionnaireConfiguration);

        // Création d'un client SMTP
        ClientSmtp clientSmtp = new ClientSmtp(gestionnaireConfiguration.getAdresseServeurSmtp(),
                                               gestionnaireConfiguration.getPortServeurSmtp());

        // On génère les blagues et le groupes de victimes à partir des configurations
        List<Blague> blagues = generateurBlague.genererBlagues();

        // Envoie des blagues à la liste de groupes de victimes
        for(Blague blague : blagues){
            logger.info(blague.getEnvoyeur().getAdresseEmail() + " " + blague.getDestinataires().get(0).getAdresseEmail());
            clientSmtp.envoyerMessage(blague.genererMessage());
        }

        System.out.println("Fin de l'envoi des mails");
    }
}
