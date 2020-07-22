package springboot.servicios.oauth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.servicios.commons.models.entity.Usuario;

@FeignClient(name="servicio-usuariosNuevo")
public interface UsuarioFeingClient {
    @GetMapping("/usuarios/search/buscar-username")
    public Usuario finByUsername(@RequestParam String username);
}
