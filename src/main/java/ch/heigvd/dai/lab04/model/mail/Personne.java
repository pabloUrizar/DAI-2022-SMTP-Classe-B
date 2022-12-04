package ch.heigvd.dai.lab04.model.mail;

import java.util.regex.Pattern;

/**
 * Classe Personne qui va permettre de créer une personne qui deviendra une victime (expéditeur ou destinataire)
 *
 * @author Grégoire Guyot
 * @author Pablo Urizar
 */
public class Personne {
   /* Attributs */
   private final String adresseEmail;

   private final String nom;
   private final String prenom;

   /**
    * Constructeur de la classe Utilisateur
    *
    * @param adresseEmail adrese email de l'utilisateur
    * @throws IllegalArgumentException si l'adresse email n'est pas valide (pas de @ ou nulle)
    */
   public Personne(String adresseEmail) {
      // On vérifie que l'adresse email est valide donc sous la forme "nom@domaine.xx"$

      if (!adresseEmail.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,6}$"))
        throw new IllegalArgumentException("L'adresse email n'est pas valide");

      /**
      if (adresseEmail.contains("@")) {
         this.adresseEmail = adresseEmail;

         String[] test = adresseEmail.split(Pattern.quote("."));
         this.prenom = test[0];
         this.nom = test[1];
      } else {
         throw new IllegalArgumentException("Invalid mail address");
      }
       **/
      this.adresseEmail = adresseEmail;
      // Le prenom sera le premier mot avant le . de l'adresse email
      this.prenom = adresseEmail.split("\\.")[0];
      // Le nom sera le premier mot avant le @ de l'adresse email
      this.nom = adresseEmail.split("@")[0];
   }

   /**
    * Getter de l'adresse email de l'utilisateur
    *
    * @return adresse email de l'utilisateur
    */
   public String getAdresseEmail() {
      return adresseEmail;
   }

   /**
    * Getter du nom de l'utilisateur
    *
    * @return nom de l'utilisateur
    */
   public String getNom() {
      return nom;
   }

   /**
    * Getter du prenom de l'utilisateur
    *
    * @return prenom de l'utilisateur
    */
   public String getPrenom() {
      return prenom;
   }

   /**
    * Méthode toString qui permet d'afficher l'adresse email de l'utilisateur
    *
    * @return adresse email de l'utilisateur
    */
   @Override
   public String toString() {
      return "Utilisateur{" +
         "adresseEmail='" + adresseEmail + '\'' +
         '}';
   }
}
