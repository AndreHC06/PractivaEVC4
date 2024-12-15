package pe.idat.PracticaEVC4.ServerConfig;


import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import pe.idat.PracticaEVC4.controller.PlayerController;
import pe.idat.PracticaEVC4.security.apiKeyAuthFilter;

@Configuration
public class ServerConfig extends ResourceConfig {
    public ServerConfig() {
        register(apiKeyAuthFilter.class);
        register(PlayerController.class);
    }
}
