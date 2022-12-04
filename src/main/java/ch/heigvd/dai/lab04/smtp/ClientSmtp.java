package ch.heigvd.dai.lab04.smtp;

import ch.heigvd.dai.lab04.model.mail.Message;
//import jdk.jpackage.internal.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
    private final String adresseServeurSmtp;
    private int portServeurSmtp = 25;
    Socket socket;
    private PrintWriter ecriture;
    private BufferedReader lecture;

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
        LOG.info("Envoi du message via le protocole SMTP");
        // Création du socket
        socket = new Socket(adresseServeurSmtp, portServeurSmtp);
        // Création des flux d'entrée et de sortie
        ecriture = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        lecture = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        // On récupère la réponse du serveur
        String reponse = lecture.readLine();
        LOG.info(reponse);
        // On envoie le nom de l'hôte
        ecriture.printf("EHLO localhost\r\n");
        // On récupère la réponse du serveur
        reponse = lecture.readLine();
        LOG.info(reponse);

        if (!reponse.startsWith("250")) {
            throw new IOException("Erreur lors de l'envoi du nom de l'hôte");
        }

        while(reponse.startsWith("250-")) {
            reponse = lecture.readLine();
            LOG.info(reponse);
        }

        // On envoie l'expéditeur
        ecriture.printf("MAIL FROM: %s\r\n", message.getExpediteur());
        // On récupère la réponse du serveur
        reponse = lecture.readLine();
        LOG.info(reponse);

        for (String destinataire : message.getDestinataires()) {
            // On envoie le destinataire
            ecriture.printf("RCPT TO: %s\r\n", destinataire);
            // On récupère la réponse du serveur
            reponse = lecture.readLine();
            LOG.info(reponse);
        }

        for (String destinataire : message.getCopies()) {
            // On envoie le destinataire
            ecriture.printf("RCPT TO: %s\r\n", destinataire);
            // On récupère la réponse du serveur
            reponse = lecture.readLine();
            LOG.info(reponse);
        }

        for (String destinataire : message.getCopiesMasquees()) {
            // On envoie le destinataire
            ecriture.printf("RCPT TO: %s\r\n", destinataire);
            // On récupère la réponse du serveur
            reponse = lecture.readLine();
            LOG.info(reponse);
        }

        // On envoie la commande DATA
        ecriture.printf("DATA\r\n");
        // On récupère la réponse du serveur
        reponse = lecture.readLine();
        LOG.info(reponse);

        // On envoie le message
        ecriture.printf("From: %s\r\n", message.getExpediteur());
        for (String destinataire : message.getDestinataires()) {
            ecriture.printf("To: %s\r\n", destinataire);
        }
        for (String destinataire : message.getCopies()) {
            ecriture.printf("Cc: %s\r\n", destinataire);
        }


        for (String destinataire : message.getCopiesMasquees()) {
            ecriture.printf("Bcc: %s\r\n", destinataire);
        }
        //ecriture.printf("Subject: %s\r\n", message.getSujet());
        LOG.info(message.getCorps());
        ecriture.printf("%s\r\n", message.getCorps());
        ecriture.printf(".\r\n");
        // On récupère la réponse du serveur
        reponse = lecture.readLine();
        LOG.info(reponse);

        // On envoie la commande QUIT
        ecriture.printf("QUIT\r\n");
        // On récupère la réponse du serveur
        reponse = lecture.readLine();
        LOG.info(reponse);

        // On ferme le socket et les flux
        socket.close();
        ecriture.close();
        lecture.close();

        LOG.info("Message envoyé");

    }
}
