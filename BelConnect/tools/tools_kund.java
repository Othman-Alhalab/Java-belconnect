package Slutupg.tools;

import Slutupg.Kund;

import java.io.*;

public class tools_kund extends Tool{

    /**
     * Skriver en kund till en textfil.
     * @param kund kunden som ska skrivas till filen
     * @return true om kunden skrevs, annars false
     */
    public static boolean mataKundTextFil(Kund kund) {
        String data = kund.toString();
        return mataTextFil(KundPath, data);
    }

    /**
     * Kontrollerar om en kund har en specifik email.
     * @param email e-postadressen som ska kontrolleras
     * @return true om emailn matchar en kund i filen annars false
     */
    public static boolean isKundVisaEmail(String email) {
        BufferedReader bufferedReader;
        try {
            //Läsa textfilen kund.txt
            bufferedReader = new BufferedReader(new FileReader(KundPath));
            String s;
            String line = bufferedReader.readLine();
            //Kollar om det finns ett mail som matchar med input, om så ge fel meddelande
            while (line != null) {
                s = line;
                if (s.equals("E-post:" + email)) {
                    return true;
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException ex) {
            return false;
        }

        return false;
    }

    /**
     * Läser in en kund från en textfil baserat på e-postadressen.
     * @param kundEmail email för kunden som ska läsas in
     * @return kundobjektet som läses in från filen, eller null om ingen matchning hittades
     * @throws IOException om det uppstår något fel vid läsning av filen
     */
    public static Kund readKundFromFile(String kundEmail) throws IOException {
        Kund kund = null;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(KundPath));
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.equals("E-post:" + kundEmail)) {
                    String password = bufferedReader.readLine().replaceAll("Lösenord:","");
                    int kundNr = Integer.parseInt(bufferedReader.readLine().substring(11));
                    String namn = bufferedReader.readLine().substring(5);
                    String[] namnParts = namn.split(",");
                    String fornamn = namnParts[0];
                    String efternamn = namnParts[1];
                    String adress = bufferedReader.readLine().substring(7);
                    String postOrt = bufferedReader.readLine().substring(11);
                    String telefonNr = bufferedReader.readLine().substring(14);
                    kund = new Kund(kundNr, fornamn, efternamn, adress, postOrt, telefonNr, kundEmail, password);
                    break;
                }
                line = bufferedReader.readLine();
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return kund;
    }

}
