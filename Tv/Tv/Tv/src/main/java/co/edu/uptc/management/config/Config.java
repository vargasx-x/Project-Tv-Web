package co.edu.uptc.management.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Config config;
    private Properties properties;
    private String path;
    private String nameFileTXT;
    private String nameFileUSER_SER;
    private String nameFileSALE_TXT;
    private String nameFileXML;
    private String nameFileSALE_XML;
    private String nameFileJSON;
    private String nameFileSALE_JSON;
    private String nameFileSer;
    private String nameFileSALE_SER;
    private String nameFileCSV;
    private String nameFileSALE_CSV;

    private Config() {
        this.properties = new Properties();

        try (FileInputStream entrada = new FileInputStream("resources/conf/app.config.properties1")) {
            properties.load(entrada);
            this.path = properties.getProperty("app.file.path.txt");
            this.nameFileTXT = properties.getProperty("app.file.name.txt");
            this.nameFileUSER_SER = properties.getProperty("app.file.name.user.ser");
            this.nameFileSALE_TXT = properties.getProperty("app.file.name.sale.txt");
            this.nameFileXML = properties.getProperty("app.file.name.xml");
            this.nameFileSALE_XML = properties.getProperty("app.file.name.sale.xml");
            this.nameFileJSON = properties.getProperty("app.file.name.json");
            this.nameFileSALE_JSON = properties.getProperty("app.file.name.sale.json");
            this.nameFileSer = properties.getProperty("app.file.name.ser");
            this.nameFileSALE_SER = properties.getProperty("app.file.name.sale.ser");
            this.nameFileCSV = properties.getProperty("app.file.name.csv");
            this.nameFileSALE_CSV = properties.getProperty("app.file.name.sale.csv");
        } catch (IOException ex) {
            System.err.println("Error al cargar el archivo properties de configuración: " + ex.getMessage());
        }
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNameFileTXT() {
        return nameFileTXT;
    }

    public void setNameFileTXT(String nameFileTXT) {
        this.nameFileTXT = nameFileTXT;
    }

    public String getNameFileXML() {
        return nameFileXML;
    }

    public void setNameFileXML(String nameFileXML) {
        this.nameFileXML = nameFileXML;
    }

    public String getNameFileJSON() {
        return nameFileJSON;
    }

    public void setNameFileJSON(String nameFileJSON) {
        this.nameFileJSON = nameFileJSON;
    }

    public static Config getConfig() {
        return config;
    }

    public static void setConfig(Config config) {
        Config.config = config;
    }

    public String getNameFileSer() {
        return nameFileSer;
    }

    public void setNameFileSer(String nameFileSer) {
        this.nameFileSer = nameFileSer;
    }

    public String getNameFileCSV() {
        return nameFileCSV;
    }

    public void setNameFileCSV(String nameFileCSV) {
        this.nameFileCSV = nameFileCSV;
    }

    public String getNameFileUSER_SER() {
        return nameFileUSER_SER;
    }

    public void setNameFileUSER_SER(String nameFileUSER_SER) {
        this.nameFileUSER_SER = nameFileUSER_SER;
    }

    public String getNameFileSALE_TXT() {
        return nameFileSALE_TXT;
    }

    public void setNameFileSALE_TXT(String nameFileSALE_TXT) {
        this.nameFileSALE_TXT = nameFileSALE_TXT;
    }

    public String getNameFileSALE_XML() {
        return nameFileSALE_XML;
    }

    public void setNameFileSALE_XML(String nameFileSALE_XML) {
        this.nameFileSALE_XML = nameFileSALE_XML;
    }

    public String getNameFileSALE_JSON() {
        return nameFileSALE_JSON;
    }

    public void setNameFileSALE_JSON(String nameFileSALE_JSON) {
        this.nameFileSALE_JSON = nameFileSALE_JSON;
    }

    public String getNameFileSALE_SER() {
        return nameFileSALE_SER;
    }

    public void setNameFileSALE_SER(String nameFileSALE_SER) {
        this.nameFileSALE_SER = nameFileSALE_SER;
    }

    public String getNameFileSALE_CSV() {
        return nameFileSALE_CSV;
    }

    public void setNameFileSALE_CSV(String nameFileSALE_CSV) {
        this.nameFileSALE_CSV = nameFileSALE_CSV;
    }
}
