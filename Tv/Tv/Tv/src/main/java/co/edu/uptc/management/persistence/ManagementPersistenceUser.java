package co.edu.uptc.management.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.uptc.management.tv.dto.UserDTO;

public class ManagementPersistenceUser extends FilePlain {
    private Map<String, String> userMap = new HashMap<>();
    private String filePath;

    public ManagementPersistenceUser() {
        this.filePath = "C:/Users/Juan Vargas/Downloads/Tv/Tv/Tv/Tv/src/main/resources/data/User.ser";
;
        loadFileSerializate();
    }
    public boolean insertUser(UserDTO user) {
        if (userMap.containsKey(user.getNameUser())) {
            System.out.println("El usuario ya existe: " + user.getNameUser());
            return false; // The user already exists
        }
        userMap.put(user.getNameUser(), user.getPassword());
        System.out.println("Usuario agregado: " + user.getNameUser());
        dumpFileSerializate(); // Save changes to the persistent storage
        return true;
    }
    

    private void dumpFileSerializate() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(userMap);
            System.out.println("Archivo de usuarios guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @SuppressWarnings("unchecked")
  
private void loadFileSerializate() {
    File file = new File(filePath);
    if (!file.exists()) {
        System.out.println("Archivo de usuarios no encontrado.");
        return;
    }
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
        userMap = (Map<String, String>) in.readObject();
        System.out.println("Usuarios cargados correctamente: " + userMap);
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}


    public List<UserDTO> getListUserDTO() {
        List<UserDTO> userList = new ArrayList<>();
        for (Map.Entry<String, String> entry : userMap.entrySet()) {
            userList.add(new UserDTO(entry.getKey(), entry.getValue()));
        }
        return userList;
    }

    public Map<String, String> getUserMap() {
        return userMap;
    }
}
