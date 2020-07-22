package springboot.servicios.oauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.servicios.commons.models.entity.Usuario;
import springboot.servicios.oauth.clients.UsuarioFeingClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioFeingClient client;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = client.finByUsername(s);
        if(usuario == null){
            log.error("Error en el login, no existe el usuario"+ s+ " en el sistema");
            throw  new UsernameNotFoundException("Error en el login, no existe el usuario"+ s+ " en el sistema");
        }
        List<GrantedAuthority> authorities= usuario.getRoles().stream()
                .map( role -> new SimpleGrantedAuthority(role.getNombre()) )
                .peek(authorirty -> log.info("Role: "+ authorirty.getAuthority()))
                .collect(Collectors.toList());
        log.info("Usuario Autenticado: : "+ s);
        return new User(usuario.getUsername(),usuario.getPassword(), usuario.isEnabled(),true,true,true,authorities );
    }
}
