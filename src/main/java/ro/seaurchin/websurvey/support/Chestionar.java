package ro.seaurchin.websurvey.support;

import java.util.Set;

/**
 * User: BogdanCo
 * Date: Aug 23, 2006
 * Time: 6:01:02 PM
 */
public class Chestionar
{
    private Long idChestionar;
    private String nume;
    private String descriere;
    private Set sectiuni;

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
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

    public Set getSectiuni() {
        return sectiuni;
    }

    public void setSectiuni(Set sectiuni) {
        this.sectiuni = sectiuni;
    }

}
