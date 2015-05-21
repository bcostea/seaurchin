package ro.seaurchin.websurvey.support;

import java.util.Set;

/**
 * User: BogdanCo
 * Date: Aug 23, 2006
 * Time: 5:59:53 PM
 */
public class Sectiune
{

    private String nume= "";
    private Long idSectiune=null;
    private Long idChestionar=null;
    private String detalii = "";
    private Set intrebari;

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public Sectiune(String nume, Long idSectiune, Set intrebari) {
        this.nume = nume;
        this.idSectiune = idSectiune;
        this.intrebari = intrebari;
    }

    public Sectiune() {
    }

    public Long getIdChestionar() {
        return idChestionar;
    }

    public void setIdChestionar(Long idChestionar) {
        this.idChestionar = idChestionar;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Long getIdSectiune() {
        return idSectiune;
    }

    public void setIdSectiune(Long idSectiune) {
        this.idSectiune = idSectiune;
    }

    public Set getIntrebari() {
        return intrebari;
    }

    public void setIntrebari(Set intrebari) {
        this.intrebari = intrebari;
    }
}