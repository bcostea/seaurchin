package ro.seaurchin.websurvey.support;

import ro.seaurchin.websurvey.support.rezultate.Rezultat;

/**
 * User: BogdanCo
 * Date: Aug 23, 2006
 * Time: 6:06:59 PM
 */
public class Optiune
{
    private Long idOptiune;
    private Long idIntrebare;
    private String nume;
    private Long idTipOptiune;


    public Long getIdOptiune() {
        return idOptiune;
    }

    public void setIdOptiune(Long idOptiune) {
        this.idOptiune = idOptiune;
    }

    public Long getIdIntrebare() {
        return idIntrebare;
    }

    public void setIdIntrebare(Long idIntrebare) {
        this.idIntrebare = idIntrebare;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Long getIdTipOptiune() {
        return idTipOptiune;
    }

    public void setIdTipOptiune(Long idTipOptiune) {
        this.idTipOptiune = idTipOptiune;
    }

}
