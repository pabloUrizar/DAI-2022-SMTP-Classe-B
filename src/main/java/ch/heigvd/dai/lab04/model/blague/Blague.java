package ch.heigvd.dai.lab04.model.blague;

import ch.heigvd.dai.lab04.model.mail.Message;
import ch.heigvd.dai.lab04.model.mail.Personne;

import java.io.File;
import java.util.List;

/**
 * Classe blague qui va permettre de mettre en place les blagues qui seront envoyées par mail
 * En utilisant la classe Message. Elle va mettre l'expéditeur, le sujet, le corps du message et
 * les destinataires (qui seront des groupes de personnes).
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public class Blague {
   /* Attributs */
   // Victime choisie comme expéditeur
   private Personne envoyeur;
   // Victimes choisies comme destinataires
   private List<Personne> destinataires;
   private String message;

   /**
    * Getter de l'expéditeur
    * @return envoyeur
    */
   public Personne getEnvoyeur() {
      return envoyeur;
   }

   /**
    * Setter de l'expéditeur
    *
    * @param envoyeur Personne qui va envoyer le mail
    */
   public void setEnvoyeur(Personne envoyeur) {
      this.envoyeur = envoyeur;
   }

   /**
    * Getter des destinataires
    * @return destinataires
    */
   public List<Personne> getDestinataires() {
      return destinataires;
   }

   /**
    * Getter du message de la blague
    * @return message
    */
   public String getMessage() {
      return message;
   }

   /**
    * Setter du message de la blague
    * @param message Message de la blague
    */
   public void setMessage(String message) {
      this.message = message;
   }

   public Message genererMessage() {
      Message msg = new Message();
      msg.setCorps(message + "\r\n\r\n" + "Cordialement,\r\n" + envoyeur.getPrenom() + " " + envoyeur.getNom());
      // Placer les victimes dans la liste des destinataires depuis
      // le fichier victimes.UTF8 du paquet config

      return msg;
   }


}
