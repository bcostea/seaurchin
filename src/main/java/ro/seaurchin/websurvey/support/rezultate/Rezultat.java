package ro.seaurchin.websurvey.support.rezultate;

/**
 * User: BogdanCo
 * Date: Sep 13, 2006
 * Time: 9:15:27 PM
 */
public class Rezultat
{
    private Long idRezultat;
    private Long idSetRezultat;
    private Long idIntrebare;
    private Long idOptiune;
    private String clearText;
    private String UCD;

    public String getUCD() {
        return UCD;
    }

    public void setUCD(String UCD) {
        this.UCD = UCD;
    }

    public Long getIdRezultat() {
        return idRezultat;
    }

    public void setIdRezultat(Long idRezultat) {
        this.idRezultat = idRezultat;
    }

    public Long getIdSetRezultat() {
        return idSetRezultat;
    }

    public void setIdSetRezultat(Long idSetRezultat) {
        this.idSetRezultat = idSetRezultat;
    }

    public Long getIdIntrebare() {
        return idIntrebare;
    }

    public void setIdIntrebare(Long idIntrebare) {
        this.idIntrebare = idIntrebare;
    }

    public Long getIdOptiune() {
        return idOptiune;
    }

    public void setIdOptiune(Long idOptiune) {
        this.idOptiune = idOptiune;
    }

    public String getClearText() {
        return clearText;
    }

    public void setClearText(String clearText) {
        this.clearText = clearText;
    }
}
