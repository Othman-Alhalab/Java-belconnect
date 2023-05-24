/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slutupg;

import java.util.Calendar;


public class Motorcykel extends PrivataFordon {

    public Motorcykel(Kund Ägare, String Fordonsnummer, int maxAntalPassagerare, int antalHjul, Calendar Köpdatum, double Inköpspris, double Värde, double Garantitid, String Adress) {
        super(Ägare, Fordonsnummer, maxAntalPassagerare, antalHjul, Köpdatum, Inköpspris, Värde, Garantitid, Adress);
    }

    //Standard garantitid
    public Motorcykel(Kund Ägare, String Fordonsnummer, int maxAntalPassagerare, Calendar Köpdatum, double Inköpspris, double Värde, String Adress) {
        super(Ägare, Fordonsnummer, maxAntalPassagerare, 2, Köpdatum, Inköpspris, Värde, 2, Adress);
    }


}

