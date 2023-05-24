/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slutupg;

import java.util.Calendar;

public class Personbil extends PrivataFordon {
    private double BagageUtrymmeVolym;

    public Personbil(Kund Ägare, String Fordonsnummer, int maxAntalPassagerare, int antalHjul, Calendar Köpdatum, double Inköpspris, double Värde, double Garantitid, String Adress, double BagageUtrymmeVolym) {
        super(Ägare, Fordonsnummer, maxAntalPassagerare, antalHjul, Köpdatum, Inköpspris, Värde, Garantitid, Adress);
        this.BagageUtrymmeVolym = BagageUtrymmeVolym;
    }

    //Standard konsturkor, om garantid inte anges samt om standrad bil med 4 hjul
    public Personbil(Kund Ägare, String Fordonsnummer, int maxAntalPassagerare, Calendar Köpdatum, double Inköpspris, double Värde, String Adress, double BagageUtrymmeVolym) {
        super(Ägare, Fordonsnummer, maxAntalPassagerare, 4, Köpdatum, Inköpspris, Värde, 10, Adress);
        this.BagageUtrymmeVolym = BagageUtrymmeVolym;
    }
    @Override
    public String toString() {
        return super.toString()+"BagageUtrymmeVolym:"+ BagageUtrymmeVolym+"\n";
    }


}
