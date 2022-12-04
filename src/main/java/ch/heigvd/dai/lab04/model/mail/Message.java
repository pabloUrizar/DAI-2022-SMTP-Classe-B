package ch.heigvd.dai.lab04.model.mail;

import java.util.List;

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
   private String corps;
   // Les destinataires, les gens en copies et les gens en copies cachées sont stockés dans
   // des tableaux de chaînes de caractères comme ils peuvent être plusieurs.
   private String[] destinataires = new String[0];
   private String[] copies        = new String[0];

   // Pas encore implémentée
   private String[] copiesMaquees = new String[0];

   /* Getters */
   public String getExpediteur() {
      return expediteur;
   }
   public void setExpediteur(String expediteur) {
      this.expediteur = expediteur;
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

   public void setListCopies(List<String> copies) {
      this.copies = copies.toArray(new String[0]);
   }


   public String getCorps() {
      return corps;
   }

   public void setCorps(String corps) {
      this.corps = corps;
   }

   public void setDestinataires(String[] destinataires) {
      this.destinataires = destinataires.clone();
   }

}

