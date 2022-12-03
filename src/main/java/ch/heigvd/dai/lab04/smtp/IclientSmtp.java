package ch.heigvd.dai.lab04.smtp;

import ch.heigvd.dai.lab04.model.mail.Message;
import java.io.IOException;


/**
 * Interface ClientSmtp
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public interface IclientSmtp {
      /**
      * Méthode qui permet d'envoyer un message
      * @param message message à envoyer
      */
      void envoyerMessage(Message message) throws IOException;
}
