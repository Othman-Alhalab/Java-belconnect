
package Slutupg;

import java.util.Calendar;
public abstract class PrivataFordon extends Fordon  {
    private String Adress;

    public PrivataFordon(Kund Ägare, String Fordonsnummer, int maxAntalPassagerare, int antalHjul, Calendar Köpdatum, double Inköpspris, double Värde, double Garantitid, String Adress) {
    super(Ägare, Fordonsnummer, maxAntalPassagerare, antalHjul, Köpdatum, Inköpspris, Värde, Garantitid);

        this.Adress = Adress;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }


}

    

