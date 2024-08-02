package co.edu.uptc.management.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import co.edu.uptc.management.constants.CommonConstants;
import co.edu.uptc.management.library.dto.UserDTO;

public class ManagementPersistenceUser extends FilePlain{
	private List<UserDTO> listUserDTO = new ArrayList<>();
	public void loadFilePlain(String rutaNombreArchivo) {
		List<String> contentInLine = this.reader(rutaNombreArchivo);
		for(String row: contentInLine) {
			StringTokenizer tokens = new StringTokenizer(row, CommonConstants.SEMI_COLON);
			while(tokens.hasMoreElements()){
				String userName = tokens.nextToken();
				String password = tokens.nextToken();
				listUserDTO.add(new UserDTO(userName, password));
			}
		}
	}
	public List<UserDTO> getListUserDTO() {
		return listUserDTO;
	}
	public void setListUserDTO(List<UserDTO> listUserDTO) {
		this.listUserDTO = listUserDTO;
	}
	
}
