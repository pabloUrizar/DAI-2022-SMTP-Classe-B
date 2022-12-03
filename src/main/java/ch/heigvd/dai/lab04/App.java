package ch.heigvd.dai.lab04;

import ch.heigvd.dai.lab04.model.blague.GenerateurBlague;
import ch.heigvd.dai.lab04.smtp.clientSmtp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

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
      // parse properties file

      final String fichierVictimes = "src/main/java/ch/heigvd/dai/lab04/config/victimes.UTF8";
      final String fichierMessages = "src/main/java/ch/heigvd/dai/lab04/config/messages.UTF8";
      final String fichierConfig = "src/main/java/ch/heigvd/dai/lab04/config/config.properties";
      Properties properties = null;


      try (InputStream input = Files.newInputStream(Paths.get(fichierConfig)){
         properties = new Properties();
         properties.load(input);
      } catch (IOException ex) {
         ex.printStackTrace();
      }

      assert properties != null;
      final String adresseServeurSmtp = properties.getProperty("adresseServeurSmtp");
      final int portServeurSmtp       = Integer.parseInt(properties.getProperty("portServeurSmtp"));
      final int nbGroupes             = Integer.parseInt(properties.getProperty("nombreDeGroupes"));


      // Envoi des messages avec la méthode envoyerMessage de la classe clientSmtp
      clientSmtp client           = new clientSmtp(adresseServeurSmtp, portServeurSmtp);
      GenerateurBlague generateur = new GenerateurBlague();
      // Utiliser le client smtp et le générateur de blagues pour envoyer les messages

      // ...

      System.out.println("Fin de l'envoi des messages");




   }
}
