package ro.seaurchin.websurvey.securitate;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;

/**
 * User: BogdanCo
 * Date: Sep 26, 2006
 * Time: 7:16:59 PM
 */
public class UtilizatorSistem implements UserDetails {
    Long idUtilizatorSistem;
    String numeUtilizator;
    String parola;
    String email;
    Boolean activ;
    GrantedAuthority[] grantedAuthorities;

    public Long getIdUtilizatorSistem()
    {
        return idUtilizatorSistem;
    }

    public void setIdUtilizatorSistem(Long idUtilizatorSistem) {
        this.idUtilizatorSistem = idUtilizatorSistem;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActiv() {
        return activ;
    }

    public void setActiv(Boolean activ) {
        this.activ = activ;
    }

    @Override
    public GrantedAuthority[] getAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(GrantedAuthority[] grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return getParola();
    }

    @Override
    public String getUsername() {
        return getNumeUtilizator();
    }

    @Override
    public boolean isAccountNonExpired() {
        return getActiv();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getActiv();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getActiv();
    }

    @Override
    public boolean isEnabled() {
        return getActiv();
    }
}
