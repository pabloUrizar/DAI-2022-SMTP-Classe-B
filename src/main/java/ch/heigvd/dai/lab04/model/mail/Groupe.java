package ch.heigvd.dai.lab04.model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Groupe qui va choisir plusieurs personnes dans une liste de personnes et en faire un groupe
 * de destinataires
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public class Groupe {
   // Attributs
   private List<Personne> listePersonnes;

   /**
    * Permet d'ajouter un utilisateur à la liste des utilisateurs
    * @param personne Utilisateur à ajouter
    */
   public void ajouterUtilisateur(Personne personne) {
      listePersonnes.add(personne);
   }

   /**
    * Getter de la liste des utilisateurs
    * @return listeUtilisateurs
    */
   public List<Personne> getUtilisateurs() {
      return new ArrayList<>(listePersonnes);
   }
}
