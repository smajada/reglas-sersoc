package org.arteco.sersoc.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Objects;


public class SersocTokenAuthentication extends UsernamePasswordAuthenticationToken {
    private static final long serialVersionUID = 1L;

    private String tipaut;

    public SersocTokenAuthentication(Object principal, Object credentials, String tipaut) {
        super(principal, credentials);
        this.tipaut = tipaut;
    }

    public String getTipaut() { return tipaut; }
    public void setTipaut(String tipaut) { this.tipaut = tipaut; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(tipaut);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        SersocTokenAuthentication other = (SersocTokenAuthentication) obj;
        return Objects.equals(tipaut, other.tipaut);
    }
}