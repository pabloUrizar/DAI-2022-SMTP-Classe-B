package ch.heigvd.dai.lab04.model.blague;

import ch.heigvd.dai.lab04.model.mail.Message;
import ch.heigvd.dai.lab04.model.mail.Personne;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe blague qui va permettre de mettre en place les blagues qui seront envoyées par mail
 * En utilisant la classe Message. Elle va mettre l'expéditeur, le sujet, le corps du message et
 * les destinataires (qui seront des groupes de personnes).
 *
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public class Blague {
    /* Attributs */
    // Victime choisie comme expéditeur
    private Personne expediteur;
    // Victimes choisies comme destinataires
    private final List<Personne> destinataires = new ArrayList<>();

    private final List<String> enCopies = new ArrayList<>();
    // Contient le Sujet et le corps du message.
    private String message;


    /**
     * Getter de l'expéditeur
     *
     * @return envoyeur
     */
    public Personne getEnvoyeur() {
        return expediteur;
    }

    /**
     * Setter de l'expéditeur
     *
     * @param expediteur Personne qui va envoyer le mail
     */
    public void setExpediteur(Personne expediteur) {
        this.expediteur = expediteur;
    }

    /**
     * Getter des destinataires
     *
     * @return destinataires
     */
    public List<Personne> getDestinataires() {
        return destinataires;
    }

    /**
     * Setter du message de la blague
     *
     * @param message Message de la blague
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public void ajouterDestinataire(List<Personne> listeDestinataire) {
        destinataires.addAll(listeDestinataire);
    }

    public Message genererMessage() {
        Message msg = new Message();

        msg.setCorps(message + "\r\n" + "Cordialement,\r\n" + expediteur.getPseudonyme());
        msg.setListCopies(enCopies);
        String[] destinataires = this.destinataires.stream().map(Personne::getAdresseEmail).toArray(String[]::new);
        msg.setDestinataires(destinataires);

        msg.setExpediteur(expediteur.getAdresseEmail());
        return msg;
    }

    public void setEnCopie(List<String> enCopie) {
        enCopies.addAll(enCopie);
    }
}
