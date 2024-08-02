package co.edu.uptc.management.library.rest;

import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.edu.uptc.management.library.dto.UserDTO;
import co.edu.uptc.management.library.utils.ManagementListUtils;
import co.edu.uptc.management.persistence.ManagementPersistenceUser;

@Path("/ManagementUser")
public class ManagementUser {
	/* Atributo que determina la instancia para manejar la persistencia de usuarios */
	public static ManagementPersistenceUser managementPersistenceUser = new ManagementPersistenceUser();
	
	/* Atributo que determina la clase utilitaria para operaciones a listas */
	public static ManagementListUtils<UserDTO> managementListUtils;
	
	static {
		/* Hacemos el cargue de la información */
		managementPersistenceUser.loadFilePlain("/data/users.txt");
		
		/* Enviamos la información cargada de los archivos a la clase utilitaria */
		managementListUtils = new ManagementListUtils<UserDTO>(
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
	@Consumes({MediaType.TEXT_PLAIN})
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
}
