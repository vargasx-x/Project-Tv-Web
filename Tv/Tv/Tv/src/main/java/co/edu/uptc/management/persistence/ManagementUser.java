package co.edu.uptc.management.persistence;

import co.edu.uptc.management.enums.EtypeFile;
import co.edu.uptc.management.interfaces.IActionFile;

public class ManagementUser extends FilePlain implements IActionFile {
    private Map<String, String> userMap;

    public ManagementUser() {
        this.userMap = new HashMap<>();
        loadFileSerializate(); // Cargar usuarios al inicializar la clase
    }

    // Método para insertar un usuario
    public boolean insertUser(User user) {
        if (userMap.containsKey(user.getUserName())) {
            return false; // El usuario ya existe
        }
        userMap.put(user.getUserName(), user.getPassword());
        dumpFile(EtypeFile.USER_SER); // Guardar cambios en el almacenamiento persistente
        return true;
    }

    // Método para eliminar un usuario por nombre de usuario
    public boolean deleteUser(String userName) {
        if (userMap.remove(userName) != null) {
            dumpFile(EtypeFile.USER_SER); // Guardar cambios en el almacenamiento persistente
            return true; // El usuario fue eliminado correctamente
        }
        return false; // El usuario no existe
    }

    // Método para obtener un usuario por nombre de usuario
    public User getUser(String userName) {
        String password = userMap.get(userName);
        if (password != null) {
            return new User(userName, password);
        }
        return null; // El usuario no existe
    }

    private void dumpFileSerializate() {
        try (FileOutputStream fileOut = new FileOutputStream(
                this.confValue.getPath().concat(this.confValue.getNameFileUSER_SER()));
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this.userMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFileSerializate() {
        try (FileInputStream fileIn = new FileInputStream(
                this.confValue.getPath().concat(this.confValue.getNameFileUSER_SER()));
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
            this.userMap = (Map<String, String>) in.readObject();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
    }

    // Método para autenticar un usuario
    public boolean authenticateUser(String userName, String password) {
        String storedPassword = userMap.get(userName);
        return storedPassword != null && storedPassword.equals(password);
    }

    public boolean userExists(String username) {
        return userMap.containsKey(username);
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

    public Map<String, String> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, String> userMap) {
        this.userMap = userMap;
    }

}
