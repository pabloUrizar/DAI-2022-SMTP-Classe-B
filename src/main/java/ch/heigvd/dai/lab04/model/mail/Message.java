package ch.heigvd.dai.lab04.model.mail;

/**
 * Classe Message contenant toutes les informations des mails envoyés sous forme de chaîne de caractères
 *
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public class Message {
   /* Attributs */
   private String expediteur;
   private String sujet;
   private String corps;
   // Les destinaitres, les gens en copies et les gens en copies cachées sont stockés dans
   // des tableaux de chaînes de caractères comme ils peuvent être plusieurs.
   private String[] destinataires = new String[0];
   private String[] copies        = new String[0];
   private String[] copiesMaquees = new String[0];

   /* Getters */
   public String getExpediteur() {
      return expediteur;
   }

   public String getSujet() {
      return sujet;
   }

   public String[] getDestinataires() {
      return destinataires;
   }

   public String[] getCopies() {
      return copies;
   }

   public String[] getCopiesMasquees() {
      return copiesMaquees;
   }


   public String getCorps() {
      return corps;
   }
}

