package co.edu.uptc.management.tv.rest;

import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
    public UserDTO createUser(UserDTO userDTO) {
        if (managementPersistenceUser.insertUser(userDTO)) {
            return userDTO; // Return the user if added successfully
        }
        return null; // Return null if user already exists or could not be added
    }
}
