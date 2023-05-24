package Slutupg.tools;

import Slutupg.Fordon;
import Slutupg.Reperering;

import java.io.*;

public class tools_fordon extends Tool{

    /**
     * Skriver ett fordon till en textfil.
     * @param fordon fordonet som ska skrivas till filen
     * @return true om fordonet skrevs, annars false
     */
    public static boolean mataFordonTextFIl(Fordon fordon) {
        String data = fordon.toString();
        return mataTextFil(FordonPath, data);
    }

    /**
     * Skriver en reparation till reparation.txt.
     * @param reparation reparationen som ska skrivas till filen
     * @return true om reparationen skrevs , annars false
     */
    public static boolean mataReparationTextFIl(Reperering reparation) {
        String data = reparation.toString();
        return mataTextFil(ReparationPath, data);
    }

    /**
     * Kollar om ett fordon finns i textfilen med samma regplåt som den givna
     * @param regPlåt registreringsskylten för fordonet
     * @return true om fordonet hittas i filen, annars false
     */
    public static boolean isFordonViaRegplåt(String regPlåt) {
        BufferedReader bufferedReader;
        try {
            //Läsa textfilen kund.txt
            bufferedReader = new BufferedReader(new FileReader(FordonPath));
            String s;
            String line = bufferedReader.readLine();
            //Kollar om det finns ett mail som matchar med input, om så ge fel meddelande
            while (line != null) {
                s = line;
                if (s.equals("Fordonsnummer:" + regPlåt)) {
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
}
