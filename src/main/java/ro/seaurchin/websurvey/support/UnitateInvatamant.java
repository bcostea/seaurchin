package ro.seaurchin.websurvey.support;

/**
 * User: BogdanCo
 * Date: Sep 13, 2006
 * Time: 10:00:31 PM
 */
public class UnitateInvatamant
{
    private String judet;
    private String localitate;
    private String unitate;
    private String serie;
    private String etapa;
    private Long idUnitate;

    public Long getIdUnitate() {
        return idUnitate;
    }

    public void setIdUnitate(Long idUnitate) {
        this.idUnitate = idUnitate;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getUnitate() {
        return unitate;
    }

    public void setUnitate(String unitate) {
        this.unitate = unitate;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }


}
