package Slutupg.tools;

import java.io.*;


public class tools_file extends Tool{
    /**
     * Hittar det högsta kundnumret från en given textfil.
     * @param filename sökvägen till textfilen
     * @return det högsta kundnumret i filen
     * @throws IOException om det inte gick att läsa filen
     */
    public static int HögstaNummer(String filename) throws IOException {
        //läser in filnamnet som ges in
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        int highestKundnummer = Integer.MIN_VALUE;
        //läser filen tills det inte finns något kvar
        while ((line = reader.readLine()) != null) {
            //när det hittar en rad där det finns "Kundnummer"
            if (line.startsWith("Kundnummer:")) {
                int kundnummer = Integer.parseInt(line.substring(11));
                if (kundnummer > highestKundnummer) {
                    highestKundnummer = kundnummer;
                }
            }
        }
        reader.close();
        return highestKundnummer;
    }
}
