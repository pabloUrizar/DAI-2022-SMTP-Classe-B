package ch.heigvd.dai.lab04.smtp;

import ch.heigvd.dai.lab04.model.mail.Message;

import java.io.*;

/**
 * Classe clientSmtp qui permet d'envoyer des mails à une liste de destinataires
 * Cette classe implémente l'interface IclientSmtp et surcharge la méthode envoyerMessage
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public class clientSmtp implements IclientSmtp{

   @Override
   public void envoyerMessage(Message message) throws IOException {

   }
}