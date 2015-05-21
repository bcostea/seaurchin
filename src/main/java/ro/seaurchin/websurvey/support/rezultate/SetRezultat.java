package ro.seaurchin.websurvey.support.rezultate;

import java.util.Set;

/**
 * User: BogdanCo
 * Date: Sep 13, 2006
 * Time: 9:15:34 PM
 */
public class SetRezultat
{
    private Long idSetRezultat;
    private Long idChestionar;
    private Set rezultate;
    private String judet;
    private String localitate;
    private String unitate;
    private String serie;
    private String etapa;
    private String utilizator;

    public String getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(String utilizator) {
        this.utilizator = utilizator;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public Long getIdSetRezultat() {
        return idSetRezultat;
    }

    public void setIdSetRezultat(Long idSetRezultat) {
        this.idSetRezultat = idSetRezultat;
    }

    public Long getIdChestionar() {
        return idChestionar;
    }

    public void setIdChestionar(Long idChestionar) {
        this.idChestionar = idChestionar;
    }

    public Set getRezultate() {
        return rezultate;
    }

    public void setRezultate(Set rezultate) {
        this.rezultate = rezultate;
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

}
