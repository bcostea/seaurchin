package ro.seaurchin.websurvey.securitate;

import org.acegisecurity.GrantedAuthority;

/**
 * User: BogdanCo
 * Date: Sep 26, 2006
 * Time: 7:57:46 PM
 */
public class RolUtilizator implements GrantedAuthority
{
    Long idRolUtilizator;
    String numeUtilizator;
    String denumire;

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public Long getIdRolUtilizator() {
        return idRolUtilizator;
    }

    public void setIdRolUtilizator(Long idRolUtilizator) {
        this.idRolUtilizator = idRolUtilizator;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String getAuthority() {
        return denumire;
    }
}
