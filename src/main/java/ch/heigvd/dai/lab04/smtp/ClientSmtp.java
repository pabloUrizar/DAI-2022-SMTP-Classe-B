package ch.heigvd.dai.lab04.smtp;

import ch.heigvd.dai.lab04.model.mail.Message;
import jdk.jpackage.internal.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Classe clientSmtp qui permet d'envoyer des mails à une liste de destinataires
 * Cette classe implémente l'interface IClientSmtp et surcharge la méthode envoyerMessage.
 *
 * @author Grégoire Guyot
 * @author Pablo Urizar
 * @version 1.0
 */
public class ClientSmtp implements IClientSmtp {

   // Logger
   private static final Logger LOG = Logger.getLogger(ClientSmtp.class.getName());

   /* Attributs */
   // Informations du serveur SMTP
   private String adresseServeurSmtp;
   private int portServeurSmtp = 25;

   /**
    * Constructeur de la classe clientSmtp
    *
    * @param adresseServeurSmtp adresse du serveur SMTP
    * @param portServeurSmtp    port du serveur SMTP
    */
   public ClientSmtp(String adresseServeurSmtp, int portServeurSmtp) {
      this.adresseServeurSmtp = adresseServeurSmtp;
      this.portServeurSmtp = portServeurSmtp;
   }

   /**
    * @param message message à envoyer
    * @throws IOException
    */
   @Override
   public void envoyerMessage(Message message) throws IOException {
      Log.info("Envoi du message via le protocole SMTP");

      try (Socket socket          = new Socket(adresseServeurSmtp, portServeurSmtp);
           BufferedReader lecture = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           PrintWriter ecriture   = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

         // On attend la réponse du serveur
         String reponse = lecture.readLine();
         LOG.info(reponse);

         // On envoie le nom de l'hôte
         ecriture.println("EHLO " + InetAddress.getLocalHost().getHostName());
         reponse = lecture.readLine();
         LOG.info(reponse);

         // Si la ligne ne commence par 250, c'est que le serveur n'a pas accepté la commande
         if (!reponse.startsWith("250")) {
            throw new IOException("Erreur smtp : " + reponse);
         }
         // Tant que la ligne commence par 250, on continue à lire
         while (reponse.startsWith("250")) {
            reponse = lecture.readLine();
            LOG.info(reponse);
         }

         // On envoie l'expéditeur
         ecriture.println("MAIL FROM: " + message.getExpediteur());
         reponse = lecture.readLine();
         LOG.info(reponse);

         // On envoie les destinataires
         for (String to : message.getDestinataires()) {
            ecriture.println("RCPT TO: " + to);
            reponse = lecture.readLine();
            LOG.info(reponse);
         }

         // On envoie les gens en copie
         for (String cc : message.getCopies()) {
            ecriture.println("RCPT TO: " + cc);
            reponse = lecture.readLine();
            LOG.info(reponse);
         }

         // On envoie les gens en copie cachée
         for (String bcc : message.getCopiesMasquees()) {
            ecriture.println("RCPT TO: " + bcc);
            reponse = lecture.readLine();
            LOG.info(reponse);
         }

         // On envoie le corps du message
         ecriture.println("DATA");
         reponse = lecture.readLine();
         LOG.info(reponse);

         // On envoie l'expéditeur
         ecriture.println("From: " + message.getExpediteur());
         // On envoie aux destinataires
         ecriture.println("To: " + message.getDestinataires()[0]);
         // S'il y en a plus d'un, on les sépare par des virgules
         if (message.getDestinataires().length > 1) {
            for (int i = 1; i < message.getDestinataires().length; i++) {
               ecriture.println(", " + message.getDestinataires()[i]);
            }
         }
         // On envoie aux gens en copie
         ecriture.println("Cc: " + message.getCopies()[0]);
         // S'il y en a plus d'un, on les sépare par des virgules
         if (message.getCopies().length > 1) {
            for (int i = 1; i < message.getCopies().length; i++) {
               ecriture.println(", " + message.getCopies()[i]);
            }
         }
         // TODO : voir si il faut faire ca
         /*
         // On envoie aux gens en copie cachée
         ecriture.println("Bcc: " + message.getCopiesMasquees()[0]);
         // S'il y en a plus d'un, on les sépare par des virgules
         if (message.getCopiesMasquees().length > 1) {
            for (int i = 1; i < message.getCopiesMasquees().length; i++) {
               ecriture.println(", " + message.getCopiesMasquees()[i]);
            }
         }
         */
         Log.info(message.getCorps());
         ecriture.println(message.getCorps());
         // Terminaison du message
         ecriture.println(".");
         reponse = lecture.readLine();
         LOG.info(reponse);
         // Fin de la connexion
         ecriture.println("QUIT");
         reponse = lecture.readLine();
         LOG.info(reponse);

         // La fermeture des flux et du socket se fait automatiquement grâce au try-with-resources
      }
   }
}