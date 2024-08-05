package co.edu.uptc.management.tv.rest;

import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.edu.uptc.management.config.Config;
import co.edu.uptc.management.persistence.ManagementPersistenceUser;
import co.edu.uptc.management.tv.dto.UserDTO;
import co.edu.uptc.management.tv.utils.ManagementListUtils;

@Path("/ManagementUser")
public class ManagementUser {
    /* Atributo que determina la instancia para manejar la persistencia de usuarios */
    public static ManagementPersistenceUser managementPersistenceUser;
    
    /* Atributo que determina la clase utilitaria para operaciones a listas */
    public static ManagementListUtils<UserDTO> managementListUtils;
    
    static {
        try {
            // Crea una instancia de Config
            Config config = Config.getInstance();
            
            // Pasa la instancia de Config al constructor de ManagementPersistenceUser
            managementPersistenceUser = new ManagementPersistenceUser(config);
            
            /* Hacemos el cargue de la información */
            managementPersistenceUser.loadFileSerializate(); // Usamos la serialización en lugar de plain text
            System.out.println("Archivo serializado cargado correctamente.");
            
            /* Enviamos la información cargada de los archivos a la clase utilitaria */
            managementListUtils = new ManagementListUtils<UserDTO>(
                    managementPersistenceUser.getListUserDTO());
            
            /* Asignamos el nombre del atributo por los atributos que deseamos ordenar */
            managementListUtils.sortList("nameUser", "password");
            System.out.println("Lista ordenada correctamente.");
            
        } catch (Exception e) {
            System.err.println("Error during ManagementUser class initialization.");
            e.printStackTrace();
        }
    }
    
    @GET
    @Path("/validateUser")
    @Consumes({MediaType.TEXT_PLAIN})
    public Boolean validateUser(@QueryParam("nameUser") String nameUser,
                                @QueryParam("password") String password) {
        if (managementListUtils == null) {
            System.err.println("managementListUtils is null.");
            return false;
        }
        UserDTO userDTO = new UserDTO(nameUser, password);
        UserDTO usuarioEncontrado = null;
        try {
            usuarioEncontrado = managementListUtils.findObjectBinary(userDTO, "nameUser", "password");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return !Objects.isNull(usuarioEncontrado);
    }
}
