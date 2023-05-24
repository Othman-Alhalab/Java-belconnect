
package Slutupg;

import java.util.Calendar;

public abstract class KommunalaFordon extends Fordon {

    private String BeställandeKommun;
    
    public KommunalaFordon(Kund Ägare, String Fordonsnummer, int maxAntalPassagerare, int antalHjul, Calendar Köpdatum, double Inköpspris, double Värde, double Garantitid, String BeställandeKommun) {
        super(Ägare, Fordonsnummer, maxAntalPassagerare, antalHjul, Köpdatum, Inköpspris, Värde, Garantitid);
        this.BeställandeKommun = BeställandeKommun;
    }

    public String getBeställandeKommun() {
        return BeställandeKommun;
    }

    public void setBeställandeKommun(String beställandeKommun) {
        BeställandeKommun = beställandeKommun;
    }
}

