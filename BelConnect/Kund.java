package Slutupg;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Kund{

    private int KundNr;
    private String FörNamn;
    private String EfterNamn;
    private String Adress;
    private String PostOrt;
    private String TelefonNr;
    private String Email;
    private String lösenord;

    //Konstruktorn för klassen Fordon som används för att skapa ett nytt objekt.
    //Tar emot olika attributvärden för att sätta upp fordonsobjektet.
    public Kund(int KundNr, String FörNamn, String EfterNamn, String Adress, String PostOrt, String TelefonNr, String Email, String lösenord) {
        this.KundNr = KundNr;
        this.FörNamn = FörNamn;
        this.EfterNamn = EfterNamn;
        this.Adress = Adress;
        this.PostOrt = PostOrt;
        this.TelefonNr = TelefonNr;
        this.Email = Email;
        this.lösenord = lösenord;
    }
    public Kund(){}
    public int getKundNr() {
        return KundNr;
    }

    public void setKundNr(int kundNr) {
        KundNr = kundNr;
    }

    public String getFörNamn() {
        return FörNamn;
    }

    public void setFörNamn(String förNamn) {
        FörNamn = förNamn;
    }

    public String getEfterNamn() {
        return EfterNamn;
    }

    public void setEfterNamn(String efterNamn) {
        EfterNamn = efterNamn;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getPostOrt() {
        return PostOrt;
    }

    public void setPostOrt(String postOrt) {
        PostOrt = postOrt;
    }

    public String getTelefonNr() {
        return TelefonNr;
    }

    public void setTelefonNr(String telefonNr) {
        TelefonNr = telefonNr;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLösenord() {
        return lösenord;
    }

    public void setLösenord(String lösenord) {
        this.lösenord = lösenord;
    }


    //To String
    @Override
    public String toString() {
        return "E-post:" + getEmail() + "\n"+
                "Lösenord:" + getLösenord() + "\n"+
                "Kundnummer:" + getKundNr() + "\n" +
                "Namn:" + getFörNamn() + "," + getEfterNamn() + "\n" +
                "Adress:" + getAdress() + "\n" +
                "Postnummer:" + getPostOrt() + "\n" +
                "Telefonnummer:" + getTelefonNr() + "\n";
    }


}

