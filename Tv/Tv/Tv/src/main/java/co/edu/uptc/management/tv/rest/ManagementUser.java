package co.edu.uptc.management.tv.rest;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import co.edu.uptc.management.persistence.ManagementPersistenceUser;
import co.edu.uptc.management.tv.dto.UserDTO;
import co.edu.uptc.management.tv.utils.ManagementListUtils;

@Path("/ManagementUser")
public class ManagementUser {
    public static ManagementPersistenceUser managementPersistenceUser = new ManagementPersistenceUser();
    public static ManagementListUtils<UserDTO> managementListUtils;

    static {
        managementListUtils = new ManagementListUtils<>(
                managementPersistenceUser.getListUserDTO());
        try {
            /* Asignamos el nombre del atributo por los atributos que deseamos ordenar */
            managementListUtils.sortList("nameUser", "password");
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            System.out.println("No se encontró el nombre del atributo en la clase");
            e.printStackTrace();
        }
    }

    @GET
    @Path("/validateUser")
    @Consumes({ MediaType.TEXT_PLAIN })
    public Boolean validateUser(@QueryParam("nameUser") String nameUser,
            @QueryParam("password") String password) {
        UserDTO userDTO = new UserDTO(nameUser, password);
        UserDTO usuarioEncontrado = null;
        try {
            usuarioEncontrado = managementListUtils.findObjectBinary(userDTO, "nameUser", "password");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return !Objects.isNull(usuarioEncontrado);
    }

    @POST
    @Path("/createUser")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response createUser(UserDTO userDTO) {
        // Verificar si el nombre de usuario ya existe en el mapa
        if (managementPersistenceUser.getUserMap().containsKey(userDTO.getNameUser())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El nombre de usuario ya existe.")
                    .build();
        }

        // Agregar el nuevo usuario
        if (managementPersistenceUser.insertUser(userDTO)) {
            return Response.ok(userDTO).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al agregar el usuario.")
                .build();
    }

    @GET
    @Path("/getUsers")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<UserDTO> getUsers() {
        return managementPersistenceUser.getListUserDTO();
    }

    @DELETE
    @Path("/deleteUser")
    public Response deleteUser(@QueryParam("nameUser") String nameUser) {
        // Lógica para eliminar el usuario
        boolean deleted = managementPersistenceUser.deleteUser(nameUser);
        if (deleted) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    

}
