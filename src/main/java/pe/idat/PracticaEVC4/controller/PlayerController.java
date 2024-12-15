package pe.idat.PracticaEVC4.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.idat.PracticaEVC4.entity.Player;
import pe.idat.PracticaEVC4.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@Service
@Path("/player")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayer() {
        try {
            List<Player> players = playerRepository.findAll();
            String json = objectMapper.writeValueAsString(players);
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al convertir a JSON")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayerById(@PathParam("id") Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player != null) {
            try {
                String json = objectMapper.writeValueAsString(player);
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            } catch (JsonProcessingException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al convertir a JSON")
                        .build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\":\"Jugador no encontrado\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePlayer(@PathParam("id") Long id, String json) {
        try {
            Player updatePlayer = objectMapper.readValue(json, Player.class);
            Player player = playerRepository.findById(id).orElse(null);
            if (player != null) {
                player.setPlayerName(updatePlayer.getPlayerName());
                player.setPlayerClass(updatePlayer.getPlayerClass());
                player.setPlayerLevel(updatePlayer.getPlayerLevel());
                playerRepository.save(player);
                String responseMessage = "{\"message\":\"Jugador actualizado correctamente\"}";
                return Response.status(Response.Status.OK)
                        .entity(responseMessage)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Jugador no encontrado").build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlayer(@PathParam("id") Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        if (player != null) {
            playerRepository.delete(player);
            String responseMessage = "{\"message\":\"Juagador eliminado correctamente\"}";
            return Response.status(Response.Status.NO_CONTENT)
                    .entity(responseMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\":\"Juagador no encontrada\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlayer(String json) {
        try {
            Player newPlayer = objectMapper.readValue(json, Player.class);
            playerRepository.save(newPlayer);
            String createdJson = objectMapper.writeValueAsString(newPlayer);
            return Response.status(Response.Status.CREATED)
                    .entity(createdJson)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud")
                    .build();
        }
    }

}
