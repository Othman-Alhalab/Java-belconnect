/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slutupg;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class Reperering {
    
    private int reperationsID;
    private Calendar datum;
    private String beskriving;
    private int KundNr;
    private String Fordonsnummer;


    public Reperering(int reperationsID, Calendar datum, String beskriving, int KundNr, String Fordonsnummer) {
        this.reperationsID = reperationsID;
        this.datum = datum;
        this.beskriving = beskriving;
        this.KundNr = KundNr;
        this.Fordonsnummer = Fordonsnummer;
    }

    public int getReperationsID() {
        return reperationsID;
    }

    public void setReperationsID(int reperationsID) {
        this.reperationsID = reperationsID;
    }

    public String getDatum() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(datum.getTime());
    }

    public void setDatum(Calendar datum) {
        this.datum = datum;
    }

    public String getBeskriving() {
        return beskriving;
    }

    public void setBeskriving(String beskriving) {
        this.beskriving = beskriving;
    }

    public int getKundNr() {
        return KundNr;
    }

    public void setKundNr(int kundNr) {
        KundNr = kundNr;
    }

    public String getFordonsnummer() {
        return Fordonsnummer;
    }

    public void setFordonsnummer(String fordonsnummer) {
        Fordonsnummer = fordonsnummer;
    }



    @Override
    public String toString() {
        return "ReperationsID:" + getReperationsID() + "\n" +
                "Fordonsnummer:" + getFordonsnummer() + "\n" +
                "Datum:" + getDatum() + "\n" +
                "Beskriving:" + getBeskriving() + "\n" +
                "KundNr:" + getKundNr() + "\n";
    }
}


