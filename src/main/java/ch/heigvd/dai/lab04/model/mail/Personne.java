package ch.heigvd.dai.lab04.model.mail;

/**
 * Classe Personne qui va permettre de créer une personne qui deviendra une victime (expéditeur ou destinataire)
 *
 * @author Grégoire Guyot
 * @author Pablo Urizar
 */
public class Personne {
   /* Attributs */
   private final String adresseEmail;

   /**
    * Constructeur de la classe Utilisateur
    *
    * @param adresseEmail adrese email de l'utilisateur
    * @throws IllegalArgumentException si l'adresse email n'est pas valide (pas de @ ou nulle)
    */
   public Personne(String adresseEmail) {
      // On vérifie que l'adresse email est valide donc sous la forme "nom@domaine.xx"
      if (!adresseEmail.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,6}$"))
         throw new IllegalArgumentException("L'adresse email n'est pas valide");

      this.adresseEmail = adresseEmail;

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
