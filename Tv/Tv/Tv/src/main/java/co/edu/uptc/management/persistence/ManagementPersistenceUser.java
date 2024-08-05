package co.edu.uptc.management.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import co.edu.uptc.management.enums.EtypeFile;
import co.edu.uptc.management.interfaces.IActionFile;
import co.edu.uptc.management.tv.dto.UserDTO;

public class ManagementPersistenceUser extends FilePlain implements IActionFile {
    private Map<String, String> userMap;

    public ManagementPersistenceUser() {
        this.userMap = new HashMap<>();
        loadFile(EtypeFile.USER_SER); // Cargar usuarios al inicializar la clase
    }

    // Método para insertar un usuario
    public boolean insertUser(UserDTO user) {
        if (userMap.containsKey(user.getUserName())) {
            return false; // El usuario ya existe
        }
        userMap.put(user.getUserName(), user.getPassword());
        dumpFile(EtypeFile.USER_SER); // Guardar cambios en el almacenamiento persistente
        return true;
    }

    // Método para eliminar un usuario por nombre de usuario
    public boolean deleteUser(String nameUser) {
        if (userMap.remove(nameUser) != null) {
            dumpFile(EtypeFile.USER_SER); // Guardar cambios en el almacenamiento persistente
            return true; // El usuario fue eliminado correctamente
        }
        return false; // El usuario no existe
    }

    // Método para obtener un usuario por nombre de usuario
    public UserDTO getUser(String nameUser) {
        String password = userMap.get(nameUser);
        if (password != null) {
            return new UserDTO(nameUser, password);
        }
        return null; // El usuario no existe
    }

    // Método para obtener la lista de usuarios como UserDTO
    public List<UserDTO> getListUserDTO() {
        return userMap.entrySet().stream()
                .map(entry -> new UserDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public void dumpFile(EtypeFile etypefile) {
        if (EtypeFile.USER_SER.equals(etypefile)) {
            dumpFileSerializate();
        }
    }

    @Override
    public void loadFile(EtypeFile etypefile) {
        if (EtypeFile.USER_SER.equals(etypefile)) {
            loadFileSerializate();
        }
    }

    public void dumpFileSerializate() {
        try (FileOutputStream fileOut = new FileOutputStream(
                this.confValue.getPath().concat(this.confValue.getNameFileUSER_SER()));
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this.userMap);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFileSerializate() {
        File file = new File("resource/data/User.ser");
            // Imprime la ruta absoluta del archivo para depuración
            System.out.println("File path: " + file.getAbsolutePath());

           
        try (FileInputStream fileIn = new FileInputStream(
                this.confValue.getPath().concat(this.confValue.getNameFileUSER_SER()));
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            this.userMap = (Map<String, String>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el archivo de usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para autenticar un usuario
    public boolean authenticateUser(String nameUser, String password) {
        String storedPassword = userMap.get(nameUser);
        return storedPassword != null && storedPassword.equals(password);
    }

    public boolean userExists(String nameUser) {
        return userMap.containsKey(nameUser);
    }

    public Map<String, String> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, String> userMap) {
        this.userMap = userMap;
    }
}
