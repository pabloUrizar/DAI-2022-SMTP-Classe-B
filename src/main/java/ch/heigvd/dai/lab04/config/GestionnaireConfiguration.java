package ch.heigvd.dai.lab04.config;

import ch.heigvd.dai.lab04.model.mail.Personne;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.nio.charset.StandardCharsets;
public class GestionnaireConfiguration {

    private String adresseServeurSmtp;
    private int portServeurSmtp;
    private final List<Personne> listePersonnes;
    private final List<String> listeMessages;
    private int nombreGroupes;
    private List<Personne> listeEnCopie;

    /**
     * Constructeur de la classe GestionConfiguration
     * Va permettre de récupérer les informations de configuration depuis le fichier de configuration
     */
    public GestionnaireConfiguration() throws IOException {
        // Récupération des informations de configuration depuis les fichiers respectifs
        listePersonnes = recupererAdressesFichier("../src/main/java/ch/heigvd/dai/lab04/config/victimes.utf8");
        listeMessages  = recupererMessagesFichier("../src/main/java/ch/heigvd/dai/lab04/config/messages.utf8");
        recupererProprieteFichier("../src/main/java/ch/heigvd/dai/lab04/config/config.properties");
    }


    /**
     * Méthode permettant de récuperer des adresses email depuis un fichier et les mettre dans une liste de
     * personnes
     *
     * @param nomFichier contenant les adresses email
     * @return Liste de personnes
     */

    private List<Personne> recupererAdressesFichier(String nomFichier) {
        List<Personne> listePersonnes = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(nomFichier, StandardCharsets.UTF_8));
            String ligne;
            while ((ligne = bufferedReader.readLine()) != null) {
                listePersonnes.add(new Personne(ligne));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listePersonnes;
    }
    /**
     * Méthode permettant de récuperer des blagues depuis un fichier et les mettre dans une liste de
     * messages.
     * @param nomFichier Nom du fichier contenant les messages
     * @return Liste de messages sous forme de String
     */
    private List<String> recupererMessagesFichier(String nomFichier) {
        List<String> listeMessages = new ArrayList<>();
        // Un message se termine par la ligne $fin$
        StringBuilder message = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(nomFichier, StandardCharsets.UTF_8));
            String ligne;
            while ((ligne = bufferedReader.readLine()) != null) {
                if (ligne.equals("$fin$")) {
                    // Quand un message est terminé, on l'ajoute à la liste et on réinitialise la variable.
                    listeMessages.add(message.toString());
                    message = new StringBuilder();
                } else {
                    message.append(ligne).append("\r\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listeMessages;
    }

    /**
     * Méthode permettant de récupérer les propriétés du fichier de configuration en utilisant la classe
     * Properties. Cette méthode est privée, car elle est utilisée uniquement dans le constructeur.
     * @param nomFichier Nom du fichier de configuration
     */
    private void recupererProprieteFichier(String nomFichier) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(nomFichier);
        Properties properties           = new Properties();

        properties.load(fileInputStream);

        adresseServeurSmtp = properties.getProperty("adresseServeurSmtp");
        portServeurSmtp    = Integer.parseInt(properties.getProperty("portServeurSmtp"));
        nombreGroupes      = Integer.parseInt(properties.getProperty("nombreDeGroupes"));

        // On récupère les adresses email éventuelles personnes en copie
        listeEnCopie = new ArrayList<>();
        String listeEnCopieString = properties.getProperty("temoinEnCopie");

        // S'il y a effectivement des personnes à mettre en copie, on les ajoute à la liste
        if (listeEnCopieString != null) {
            String[] listeEnCopieArray = listeEnCopieString.split(",");
            for (String adresseMail : listeEnCopieArray) {
                listeEnCopie.add(new Personne(adresseMail));
            }
        }
    }


    /* Getters */

    public String getAdresseServeurSmtp() {
        return adresseServeurSmtp;
    }

    public int getPortServeurSmtp() {
        return portServeurSmtp;
    }

    public int getNombreGroupes() {
        return nombreGroupes;
    }

    public List<Personne> getPersonnes() {
        return listePersonnes;
    }

    public List<String> getListeMessages() {
        return listeMessages;
    }

    // Méthode qui retourne l'adresse email d'une liste de personnes
    public List<String> getAdressesEmail() {
        List<String> listeAdressesEmail = new ArrayList<>();
        for (Personne personne : listePersonnes) {
            listeAdressesEmail.add(personne.getAdresseEmail());
        }
        return listeAdressesEmail;
    }

    public List<Personne> getListeEnCopie() {
        return listeEnCopie;
    }
}
