package co.edu.uptc.management.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import co.edu.uptc.management.constants.CommonConstants;
import co.edu.uptc.management.enums.EtypeFile;
import co.edu.uptc.management.interfaces.IActionFile;
import co.edu.uptc.management.tv.dto.TvDTO;

public class ManagementPersistenceTv extends FilePlain implements IActionFile {

    private final String NAME_TAG_TV = "tv";
    private List<TvDTO> listTv;

    public ManagementPersistenceTv() {
        this.listTv = new ArrayList<>();
        loadAllFiles();
    }

    public TvDTO findTvBySerialNumber(String serialNumber) {
        for (TvDTO tv : listTv) {
            if (tv.getSerialNumber().equals(serialNumber)) {
                return tv;
            }
        }
        return null;
    }

    public boolean insertTv(TvDTO tv) {
        if (!isDuplicateTv(tv)) {
            boolean success = listTv.add(tv);
            if (success) {
                synchronizeAllFiles(); // Sincronizar todos los archivos después de la inserción
            }
            return success;
        }
        return false;

    }

    public boolean isDuplicateTv(TvDTO tv) {
        for (TvDTO existingTv : listTv) {
            if (existingTv.getSerialNumber().equals(tv.getSerialNumber())) {
                return true;
            }
        }
        return false;
    }

    public List<TvDTO> listAllTvs() {
        return new ArrayList<>(listTv);
    }

    public boolean deleteTv(String serialNumber) {
        boolean success = false;
        Iterator<TvDTO> iterator = listTv.iterator();

        while (iterator.hasNext()) {
            TvDTO tv = iterator.next();
            if (tv.getSerialNumber().equals(serialNumber)) {
                iterator.remove();
                success = true;
            }
        }

        // Si la eliminación fue exitosa, guardar los cambios en el almacenamiento
        // persistente
        if (success) {
            synchronizeAllFiles();
        }
        return success;
    }

    public boolean updateTv(TvDTO updatedTv) {
        for (int i = 0; i < listTv.size(); i++) {
            TvDTO tv = listTv.get(i);
            if (tv.getSerialNumber().equals(updatedTv.getSerialNumber())) {
                listTv.set(i, updatedTv);
                synchronizeAllFiles(); // Sincronizar todos los archivos después de la actualización
                return true;
            }
        }
        return false;
    }

    public TvDTO getTv(String serialNumber) {
        for (TvDTO tv : listTv) {
            if (tv.getSerialNumber().equals(serialNumber)) {
                return tv;
            }
        }
        return null;
    }

    public void synchronizeAllFiles() {
        dumpFile(EtypeFile.TXT);
        dumpFile(EtypeFile.XML);
        dumpFile(EtypeFile.JSON);
        dumpFile(EtypeFile.SERIALIZATE);
        dumpFile(EtypeFile.CSV);
    }

    @Override
    public void dumpFile(EtypeFile etypefile) {
        switch (etypefile) {
            case TXT:
                dumpFilePlain();
                break;
            case XML:
                dumpFileXML();
                break;
            case JSON:
                dumpFileJSON();
                break;
            case SERIALIZATE:
                dumpFileSerializate();
                break;
            case CSV:
                dumpFileCSV();
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + etypefile);
        }
    }

    @Override
    public void loadFile(EtypeFile etypefile) {
        switch (etypefile) {
            case TXT:
                loadFilePlain();
                break;
            case XML:
                loadFileXML();
                break;
            case JSON:
                loadFileJSON();
                break;
            case SERIALIZATE:
                loadFileSerializate();
                break;
            case CSV:
                loadFileCSV();
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + etypefile);
        }
    }

    public void loadAllFiles() {
        loadFile(EtypeFile.TXT);
        loadFile(EtypeFile.XML);
        loadFile(EtypeFile.JSON);
        loadFile(EtypeFile.SERIALIZATE);
        loadFile(EtypeFile.CSV);
    }

    public void dumpFilePlain() {
        StringBuilder pathFileName = new StringBuilder();
        pathFileName.append(confValue.getPath());
        pathFileName.append(confValue.getNameFileTXT());

        List<String> records = new ArrayList<>();
        for (TvDTO tv : listTv) {
            StringBuilder contentTv = new StringBuilder();
            contentTv.append(tv.getSerialNumber()).append(CommonConstants.SEMI_COLON);
            contentTv.append(tv.getResolution()).append(CommonConstants.SEMI_COLON);
            contentTv.append(tv.getSizeDisplay()).append(CommonConstants.SEMI_COLON);
            contentTv.append(tv.getTechnologyDisplay()).append(CommonConstants.SEMI_COLON);
            contentTv.append(tv.getSystemOperational());

            records.add(contentTv.toString());
        }
        this.writer(pathFileName.toString(), records);
    }

    public void loadFilePlain() {
        listTv.clear();
        List<String> contentInLine = this.reader(confValue.getPath().concat(confValue.getNameFileTXT()));
        contentInLine.forEach(row -> {
            StringTokenizer tokens = new StringTokenizer(row, CommonConstants.SEMI_COLON);
            while (tokens.hasMoreElements()) {
                String serialNumber = tokens.nextToken().trim();
                String resolution = tokens.nextToken().trim();
                String sizeDisplay = tokens.nextToken().trim();
                String technologyDisplay = tokens.nextToken().trim();
                String systemOperational = tokens.nextToken().trim();
                listTv.add(new TvDTO(serialNumber, resolution, sizeDisplay, technologyDisplay, systemOperational));
            }
        });
    }

    public void dumpFileXML() {
        String filePath = confValue.getPath().concat(confValue.getNameFileXML());
        StringBuilder lines = new StringBuilder();
        List<TvDTO> tvs = this.listTv.stream().collect(Collectors.toList());
        lines.append("<XML version=\"1.0\" encoding=\"UTF-8\"> \n");
        for (TvDTO tv : tvs) {
            lines.append("<tv>\n");
            lines.append("<serialNumber>").append(tv.getSerialNumber()).append("</serialNumber>\n");
            lines.append("<resolution>").append(tv.getResolution()).append("</resolution>\n");
            lines.append("<sizeDisplay>").append(tv.getSizeDisplay()).append("</sizeDisplay>\n");
            lines.append("<technologyDisplay>").append(tv.getTechnologyDisplay()).append("</technologyDisplay>\n");
            lines.append("<systemOperational>").append(tv.getSystemOperational()).append("</systemOperational>\n");
            lines.append("</tv>\n");
        }
        lines.append("</XML>");
        this.writeFile(filePath, lines.toString());
    }

    public void loadFileXML() {
        listTv.clear();
        try {
            File file = new File(confValue.getPath().concat(confValue.getNameFileXML()));
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList list = document.getElementsByTagName(NAME_TAG_TV);
            for (int i = 0; i < list.getLength(); i++) {
                String serialNumber = document.getElementsByTagName("serialNumber").item(i).getTextContent().trim();
                String resolution = document.getElementsByTagName("resolution").item(i).getTextContent().trim();
                String sizeDisplay = document.getElementsByTagName("sizeDisplay").item(i).getTextContent().trim();
                String technologyDisplay = document.getElementsByTagName("technologyDisplay").item(i).getTextContent()
                        .trim();
                String systemOperational = document.getElementsByTagName("systemOperational").item(i).getTextContent()
                        .trim();
                listTv.add(new TvDTO(serialNumber, resolution, sizeDisplay, technologyDisplay, systemOperational));
            }
        } catch (Exception e) {
            System.out.println("Se presentó un error en el cargue del archivo XML");
        }
    }

    public void dumpFileJSON() {
        String filePath = confValue.getPath().concat(confValue.getNameFileJSON());
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            Gson gson = new Gson();
            gson.toJson(listTv, fileWriter);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
        }
    }

    public void loadFileJSON() {
    String filePath = confValue.getPath().concat(confValue.getNameFileJSON());
    try (FileReader fileReader = new FileReader(filePath)) {
        JsonArray jsonArray = JsonParser.parseReader(fileReader).getAsJsonArray();
        listTv.clear(); // Asegúrate de limpiar la lista antes de llenarla

        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            TvDTO tv = new TvDTO(
                    jsonObject.get("serialNumber").getAsString().trim(),
                    jsonObject.get("resolution").getAsString().trim(),
                    jsonObject.get("sizeDisplay").getAsString().trim(),
                    jsonObject.get("technologyDisplay").getAsString().trim(),
                    jsonObject.get("systemOperational").getAsString().trim()
            );
            listTv.add(tv);
        }
    } catch (IOException | JsonParseException e) {
        System.err.println("Error al cargar el archivo JSON: " + e.getMessage());
    }
}

    public void dumpFileSerializate() {
        String filePath = confValue.getPath().concat(confValue.getNameFileSer());
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            objectOutputStream.writeObject(listTv);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo serializado: " + e.getMessage());
        }
    }

    public void loadFileSerializate() {
        String filePath = confValue.getPath().concat(confValue.getNameFileSer());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            listTv = (List<TvDTO>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el archivo serializado: " + e.getMessage());
        }
    }

    public void dumpFileCSV() {
        StringBuilder pathFileName = new StringBuilder();
        pathFileName.append(confValue.getPath());
        pathFileName.append(confValue.getNameFileCSV());

        List<String> records = new ArrayList<>();
        for (TvDTO tv : listTv) {
            StringBuilder contentTv = new StringBuilder();
            contentTv.append(tv.getSerialNumber()).append(CommonConstants.SEMI_COLON);
            contentTv.append(tv.getResolution()).append(CommonConstants.SEMI_COLON);
            contentTv.append(tv.getSizeDisplay()).append(CommonConstants.SEMI_COLON);
            contentTv.append(tv.getTechnologyDisplay()).append(CommonConstants.SEMI_COLON);
            contentTv.append(tv.getSystemOperational());

            records.add(contentTv.toString());

        }
        this.writer(pathFileName.toString(), records);
    }

    public void loadFileCSV() {
        listTv.clear();
        List<String> contentInLine = this.reader(confValue.getPath().concat(confValue.getNameFileCSV()));
        contentInLine.forEach(row -> {
            StringTokenizer tokens = new StringTokenizer(row, CommonConstants.SEMI_COLON);
            while (tokens.hasMoreElements()) {
                String serialNumber = tokens.nextToken().trim();
                String resolution = tokens.nextToken().trim();
                String sizeDisplay = tokens.nextToken().trim();
                String technologyDisplay = tokens.nextToken().trim();
                String systemOperational = tokens.nextToken().trim();
                listTv.add(new TvDTO(serialNumber, resolution, sizeDisplay, technologyDisplay, systemOperational));
            }

        });
    }

    // Bubble Sort for Serial Number Ascending
    public void bubbleSortSerialNumber() {
        for (int i = 0; i < listTv.size() - 1; i++) {
            for (int j = 0; j < listTv.size() - i - 1; j++) {
                if (listTv.get(j).getSerialNumber().compareTo(listTv.get(j + 1).getSerialNumber()) > 0) {
                    Collections.swap(listTv, j, j + 1);
                }
            }
        }
    }

    // Bubble Sort for Serial Number Descending
    public void bubbleSortSerialNumberDesc() {
        for (int i = 0; i < listTv.size() - 1; i++) {
            for (int j = 0; j < listTv.size() - i - 1; j++) {
                if (listTv.get(j).getSerialNumber().compareTo(listTv.get(j + 1).getSerialNumber()) < 0) {
                    Collections.swap(listTv, j, j + 1);
                }
            }
        }
    }

    // Insertion Sort for Technology Display Ascending
    public void insertionSortTechnologyDisplay() {
        for (int i = 1; i < listTv.size(); i++) {
            TvDTO key = listTv.get(i);
            int j = i - 1;
            while (j >= 0 && listTv.get(j).getTechnologyDisplay().compareTo(key.getTechnologyDisplay()) > 0) {
                listTv.set(j + 1, listTv.get(j));
                j = j - 1;
            }
            listTv.set(j + 1, key);
        }
    }

    // Insertion Sort for Technology Display Descending
    public void insertionSortTechnologyDisplayDesc() {
        for (int i = 1; i < listTv.size(); i++) {
            TvDTO key = listTv.get(i);
            int j = i - 1;
            while (j >= 0 && listTv.get(j).getTechnologyDisplay().compareTo(key.getTechnologyDisplay()) < 0) {
                listTv.set(j + 1, listTv.get(j));
                j = j - 1;
            }
            listTv.set(j + 1, key);
        }
    }

    // Selection Sort for System Operational Ascending
    public void selectionSortSystemOperational() {
        for (int i = 0; i < listTv.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < listTv.size(); j++) {
                if (listTv.get(j).getSystemOperational().compareTo(listTv.get(minIndex).getSystemOperational()) < 0) {
                    minIndex = j;
                }
            }
            Collections.swap(listTv, i, minIndex);
        }
    }

    // Selection Sort for System Operational Descending
    public void selectionSortSystemOperationalDesc() {
        for (int i = 0; i < listTv.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < listTv.size(); j++) {
                if (listTv.get(j).getSystemOperational().compareTo(listTv.get(maxIndex).getSystemOperational()) > 0) {
                    maxIndex = j;
                }
            }
            Collections.swap(listTv, i, maxIndex);
        }
    }

    public String getNAME_TAG_TV() {
        return NAME_TAG_TV;
    }

    public List<TvDTO> getListTv() {
        
        return listTv;
    }

    public void setListTv(List<TvDTO> listTv) {
        this.listTv = listTv;
    }

}
