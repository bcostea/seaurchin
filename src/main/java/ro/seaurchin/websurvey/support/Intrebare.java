package ro.seaurchin.websurvey.support;

import java.util.Set;

/**
 * User: BogdanCo
 * Date: Aug 23, 2006
 * Time: 6:03:17 PM
 */
public class Intrebare
{
    private Long idIntrebare;
    private String enunt=null;
    private Long idSectiune;
    private int areDetalii=0;
    private String detalii;
    private int areClearText;
    private int order;

    private Set optiuni;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getIdIntrebare() {
        return idIntrebare;
    }

    public void setIdIntrebare(Long idIntrebare) {
        this.idIntrebare = idIntrebare;
    }

    public String getEnunt() {
        return enunt;
    }

    public void setEnunt(String enunt) {
        this.enunt = enunt;
    }

    public Long getIdSectiune() {
        return idSectiune;
    }

    public void setIdSectiune(Long idSectiune) {
        this.idSectiune = idSectiune;
    }

    public int getAreDetalii() {
        return areDetalii;
    }

    public void setAreDetalii(int areDetalii) {
        this.areDetalii = areDetalii;
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public int getAreClearText() {
        return areClearText;
    }

    public void setAreClearText(int areClearText) {
        this.areClearText = areClearText;
    }

    public Set getOptiuni() {
        return optiuni;
    }

    public void setOptiuni(Set optiuni) {
        this.optiuni = optiuni;
    }

}
