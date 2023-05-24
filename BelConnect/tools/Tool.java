package Slutupg.tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//en abstrakt klass som har en fil skrivare som används i andra "tool" klasser
public abstract class Tool {
    static String KundPath = "info/Kund.txt";
    static String FordonPath = "info/fordon.txt";
    static String ReparationPath = "info/Reperering.txt";
    static FileWriter fileWriter;
    static BufferedWriter bufferedWriter;

    /**
     * Appendar text till en befintlig textfil.
     * @param filePath sökvägen till textfilen
     * @param data själva datan som skrevs till filen
     * @return true om det gick att skriva in datan i filen annars false
     */
    public static boolean mataTextFil(String filePath, String data) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data + "\n");
            bufferedWriter.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

}
