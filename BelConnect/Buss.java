/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Slutupg;

import java.util.Calendar;

public class Buss extends KommunalaFordon {

    /**
     * Standard konsturkor
     * @param Ägare kunden som äger bussen
     * @param Fordonsnummer fordonsnumret för bussen
     * @param maxAntalPassagerare maximalt antal passagerare som bussen kan rymma
     * @param antalHjul antalet hjul på bussen
     * @param Köpdatum datumet då bussen köptes
     * @param Inköpspris inköpspriset för bussen
     * @param Värde nuvarande värde på bussen
     * @param BeställandeKommun kommunen som beställde bussen
     */
    public Buss(Kund Ägare, String Fordonsnummer, int maxAntalPassagerare, int antalHjul, Calendar Köpdatum, double Inköpspris, double Värde, String BeställandeKommun) {
        super(Ägare, Fordonsnummer, maxAntalPassagerare, antalHjul, Köpdatum, Inköpspris, Värde, 15, BeställandeKommun);
    }

    /**
     * En buss konstuktor där man själv får välja garantitiden
     * @param Ägare kunden som äger bussen
     * @param Fordonsnummer fordonsnumret för bussen
     * @param maxAntalPassagerare maximalt antal passagerare som bussen kan rymma
     * @param antalHjul antalet hjul på bussen
     * @param Köpdatum datumet då bussen köptes
     * @param Inköpspris inköpspriset för bussen
     * @param Värde nuvarande värde på bussen
     * @param BeställandeKommun kommunen som beställde bussen
     */
    public Buss(Kund Ägare, String Fordonsnummer, int maxAntalPassagerare, int antalHjul, Calendar Köpdatum, double Inköpspris, double Värde, double Garantitid,String BeställandeKommun) {
        super(Ägare, Fordonsnummer, maxAntalPassagerare, antalHjul, Köpdatum, Inköpspris, Värde, Garantitid, BeställandeKommun);
    }


}
