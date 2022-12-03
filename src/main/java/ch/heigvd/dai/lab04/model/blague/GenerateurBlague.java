package ch.heigvd.dai.lab04.model.blague;

import ch.heigvd.dai.lab04.model.mail.Groupe;
import ch.heigvd.dai.lab04.model.mail.Message;
import ch.heigvd.dai.lab04.model.mail.Personne;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
 *
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public class GenerateurBlague {
   /* Attributs */

   // Groupes de victimes
   private List<Groupe> listeGroupes;
   // Liste des messages à envoyer par mail (blagues) pour chaque groupe
   private List<Message> listeMessages;
   private final List<String> messages = new ArrayList<>();
   // Groupe de victimes qui vont recevoir la blague (1 expéditeur et 2 destinataires minimum)
   private final Groupe groupeVictimes = new Groupe();

   static final int NB_GROUPES_MIN = 3;
   static private final String FIN_MSG = "$fin$";

   /**
    * Méthode qui va permettre de créer des groupes de victimes à partir d'un fichier texte contenant
    * les adresses mail des victimes.
    *
    * @param fileInputStream Fichier texte contenant les adresses mail des victimes
    * @param nbGroupes       Nombre de groupes de victimes à créer
    *                        (ex: 3)
    * @return Liste des groupes de victimes
    */
   public List<Groupe> creerGroupes(FileInputStream fileInputStream, int nbGroupes) throws IOException {
      listeGroupes = new ArrayList<>(nbGroupes);

      // Buffered reader qui va lire le fichier texte en UTF-8
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));

      String email;
      while ((email = bufferedReader.readLine()) != null) {
         groupeVictimes.ajouterPersonne(new Personne(email));
      }

      // Si la taille de la liste de victimes est inférieures au nombre minimum de groupe * le nombre à créer
      // de groupes, on ne peut pas créer les groupes
      if (groupeVictimes.getPersonnes().size() < NB_GROUPES_MIN * nbGroupes) {
         throw new IllegalArgumentException("Le nombre de groupes est trop grand par rapport au nombre de victimes");
      }

      // On crée les groupes de victimes
      for (int i = 0; i < nbGroupes; i++) {
         listeGroupes.add(new Groupe());
      }

      // Tant qu'il reste des victimes qui ne sont pas encore dans des groupes, on les ajoute
      while (!groupeVictimes.getPersonnes().isEmpty()) {
         for (Groupe groupe : listeGroupes) {
            // On ajoute une victime au groupe
            groupe.ajouterPersonne(groupeVictimes.getPersonnes().get(0));
            // On supprime la victime du groupe de victimes
            groupeVictimes.getPersonnes().remove(0);
            // S'il n'y a plus de victimes, on arrête la boucle
            if (groupeVictimes.getPersonnes().isEmpty()) {
               break;
            }
         }
      }
      return listeGroupes;
   }

   /**
    * Méthode qui va permettre de créer des messages à envoyer par mail (blagues) à partir d'un fichier texte.
    *
    * @param fileInputStream Fichier texte contenant les messages à envoyer par mail (blagues)
    * @return Liste des messages à envoyer par mail (blagues)
    */
   public List<Message> creerMessages(FileInputStream fileInputStream) throws IOException {

      // Buffered reader qui va lire le fichier texte en UTF-8
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));

      String ligne;
      StringBuilder message = new StringBuilder();
      while ((ligne = bufferedReader.readLine()) != null) {
         while (!ligne.equals(FIN_MSG)) {
            message.append(ligne).append("\r\n");
            ligne = bufferedReader.readLine();
         }
         messages.add(message.toString());
         message = new StringBuilder();
      }
      Collections.shuffle(listeMessages);
      listeMessages = new ArrayList<>(groupeVictimes.taille());
      for (int i = 0; i < groupeVictimes.taille(); i++) {
         listeMessages.add(new Message(groupeVictimes.getPersonnes().get(i).getAdresseEmail(), messages.get(i)));
      }
      return listeMessages;
   }
}
