package org.arteco.sersoc.config;

import es.palma.imicorpservices.common.dto.usuari.GenPerfilDto;
import es.palma.imicorpservices.common.dto.usuari.GenUsuariDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class SersocAuthenticationProvider implements AuthenticationProvider {
    public static final String[] PRFAPL  = {"FUE_USUARI_OPFUE"};

    private static final String ERR_LOGIN = "Login error";

    @Autowired
    private ImicorpService imiService;

    public SersocAuthenticationProvider() { /* Constructor buit. */ }

    @Override
    public Authentication authenticate(Authentication pAuth) throws AuthenticationException {
        if (pAuth.isAuthenticated()) { return pAuth; }

        SersocTokenAuthentication auth = (SersocTokenAuthentication) pAuth;

        GenUsuariDto usuari =
                new GenUsuariDto((String) auth.getPrincipal(), (String) auth.getCredentials(), auth.getTipaut());

        LoginResultDto result = imiService.login(usuari);
        if (result == null) {
            throw new BadCredentialsException("Login error: sense resposta");
        }
        String token = result.getToken();
        if(Constants.CON_ERROR_CANVI_PASSWORD.equals(result.getErrCon())) {
            throw new CanviarPasswordException("Canvi password");
        } else if (Constants.CON_ERROR_USUARI_DEHABILITAT.equals(result.getErrCon())) {
            throw new UsuariDeshabilitatException("Usuari deshabilitat");
        }
        if (result.hasErrors() || token == null) { throw new BadCredentialsException(ERR_LOGIN); }
        UsuariDto pusuari = imiService.getPublicUserDto(token);
        // Obtenir els perfils de l'aplicacio
        List<GenPerfilDto> perfilsAplicacio = new ArrayList<>(); //imicorpClient.getPerfilsSubaplicacionsByAplicacioHbl(APPLICATION, SiNo.S)
        perfilsAplicacio.add(new GenPerfilDto("FUE_USUARI_OPFUE"));
        if (pusuari != null) {
            boolean autenticat = false; // No es equivalent a !roles.isEmpty() al final del if?
            Collection<GrantedAuthority> roles = new HashSet<>();
            for (GenPerfilDto perfilApl : perfilsAplicacio) {
                if (pusuari.getPerfils().containsKey(perfilApl.getPrfCoa())) {
                    roles.add(new SimpleGrantedAuthority("ROLE_" + perfilApl.getPrfCoa()));
                    autenticat = true;
                }
            }

            if (autenticat) {
                PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(
                        pusuari, null, roles);
                authToken.setAuthenticated(true);
                return authToken;
            }

            throw new FaltaRolCredentialsException(ERR_LOGIN);
        } else {
            throw new BadCredentialsException(ERR_LOGIN);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) { return true; }
}