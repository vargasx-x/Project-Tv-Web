package co.edu.uptc.management.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
    try (FileInputStream fileIn = new FileInputStream("C:/Users/Juan Vargas/Downloads/Tv/Tv/Tv/Tv/src/main/resources/data/User.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn)) {
        Map<String, String> userMap = (Map<String, String>) in.readObject();
        System.out.println("Contenido del archivo User.ser: " + userMap);
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}

}
