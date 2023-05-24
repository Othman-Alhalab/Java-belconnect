/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slutupg;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;


public abstract class Fordon{

    private Kund Ägare;
    private String Fordonsnummer;
    private int maxAntalPassagerare;
    private int antalHjul;
    private Calendar Köpdatum;
    private double Inköpspris;
    private double Värde;
    private double Garantitid;
    private ArrayList<Reperering> reparationsHistorik;

    /**
     * Skapar en instans av Fordon med angivna attribut.
     * @param Ägare kunden som äger fordonet
     * @param Fordonsnummer fordonsnumret för fordonet
     * @param maxAntalPassagerare maximalt antal passagerare som fordonet kan rymma
     * @param antalHjul antalet hjul på fordonet
     * @param Köpdatum datumet då fordonet köptes
     * @param Inköpspris inköpspriset för fordonet
     * @param Värde nuvarande värde på fordonet
     * @param Garantitid garantitiden för fordonet
     */
    public Fordon(Kund Ägare, String Fordonsnummer, int maxAntalPassagerare, int antalHjul, Calendar Köpdatum, double Inköpspris, double Värde, double Garantitid) {
        this.Ägare = Ägare;
        this.Fordonsnummer = Fordonsnummer;
        this.maxAntalPassagerare = maxAntalPassagerare;
        this.antalHjul = antalHjul;
        this.Köpdatum = Köpdatum;
        this.Inköpspris = Inköpspris;
        this.Värde = Värde;
        this.Garantitid = Garantitid;
        this.reparationsHistorik = new ArrayList<>();
    }


    public Kund getÄgare() {
        return Ägare;
    }

    public void setÄgare(Kund ägare) {
        Ägare = ägare;
    }

    public int getÄgareNummer() {
        return Ägare.getKundNr();
    }

    public String getFordonsnummer() {
        return Fordonsnummer;
    }

    public void setFordonsnummer(String fordonsnummer) {
        Fordonsnummer = fordonsnummer;
    }

    public int getMaxAntalPassagerare() {
        return maxAntalPassagerare;
    }

    public void setMaxAntalPassagerare(int maxAntalPassagerare) {
        this.maxAntalPassagerare = maxAntalPassagerare;
    }

    public int getAntalHjul() {
        return antalHjul;
    }

    public void setAntalHjul(int antalHjul) {
        this.antalHjul = antalHjul;
    }

    public String getKöpdatum() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(Köpdatum.getTime());
    }

    public void setKöpdatum(Calendar köpdatum) {
        Köpdatum = köpdatum;
    }

    public double getInköpspris() {
        return Inköpspris;
    }

    public void setInköpspris(double inköpspris) {
        Inköpspris = inköpspris;
    }

    public ArrayList<Reperering> getReparationsHistorik() {
        return reparationsHistorik;
    }

    public void setReparationsHistorik(ArrayList<Reperering> reparationsHistorik) {
        this.reparationsHistorik = reparationsHistorik;
    }

    public double getGarantitid() {
        return Garantitid;
    }

    public void setGarantitid(double Garantitid) {
        this.Garantitid = Garantitid;
    }

    public double getVärde() {
        return Värde;
    }

    public void setVärde(double värde) {
        Värde = värde;
    }

    /**
     * Returnerar en strängrepresentation av objektet med olika attributvärden.
     * Strängen innehåller information om fordonsnummer, ägarenummer,
     * max antal passagerare, antal hjul, köpdatum, garantitid, inköpspris,
     * värde och fordons typ.
     * @return Strängrepresentation av objektet.
     */
   @Override
   public String toString() {
       return "Fordonsnummer:" + getFordonsnummer() + "\n" +
               "Ägare:" + getÄgareNummer() + "\n" +
               "Max antal passagerare:" + getMaxAntalPassagerare() + "\n" +
               "Antal hjul:" + getAntalHjul() + "\n" +
               "Köpdatum:" + getKöpdatum() + "\n" +
               "Garantitid:" + getGarantitid() + "\n" +
               "Inköpspris:" + getInköpspris() + "\n" +
               "Värde:" + getVärde() + "\n" +
               "FordonsTyp:" + this.getClass().getSimpleName() + "\n";
   }

}
