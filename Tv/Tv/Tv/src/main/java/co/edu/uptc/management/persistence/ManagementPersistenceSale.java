package co.edu.uptc.management.persistence;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;  // Asegúrate de importar de org.w3c.dom
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import co.edu.uptc.management.constants.CommonConstants;
import co.edu.uptc.management.enums.EtypeFile;
import co.edu.uptc.management.interfaces.IActionFile;
import co.edu.uptc.management.tv.dto.SaleDTO;
import co.edu.uptc.management.tv.dto.TvDTO;

public class ManagementPersistenceSale extends FilePlain implements IActionFile {
    private final String NAME_TAG_SALE = "sale";
    private List<SaleDTO> listSale;
    private ManagementPersistenceTv managementPersistenceTv; 

    public ManagementPersistenceSale(ManagementPersistenceTv managementPersistenceTv) {
        this.listSale = new ArrayList<>();
        this.managementPersistenceTv = managementPersistenceTv; 

        loadAllFiles();
    }

    public TvDTO findTvBySerialNumber(String serialNumber) {
        // Implementar la búsqueda de TV según tu lógica
        return managementPersistenceTv.getTv(serialNumber);
    }

    public boolean insertSale(SaleDTO sale) {
        if (!isDuplicateSale(sale)) {
            boolean success = listSale.add(sale);
            if (success) {
                synchronizeAllFiles();
            }
            return success;
        }
        return false;
    }

    public List<SaleDTO> listAllSales() {
        return new ArrayList<>(listSale);
    }

    public boolean deleteSale(String serialNumber) {
        boolean success = false;
        Iterator<SaleDTO> iterator = listSale.iterator();

        while (iterator.hasNext()) {
            SaleDTO sale = iterator.next();
            if (sale.getTelevisor().getSerialNumber().equals(serialNumber)) {
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

    public SaleDTO getSale(String serialNumber) {
        for (SaleDTO sale : listSale) {
            if (sale.getTelevisor().getSerialNumber().equals(serialNumber)) {
                return sale;
            }
        }
        return null;
    }

    public void synchronizeAllFiles() {
        dumpFile(EtypeFile.SALE_TXT);
        dumpFile(EtypeFile.SALE_XML);
        dumpFile(EtypeFile.SALE_JSON);
        dumpFile(EtypeFile.SALE_SERIALIZATE);
        dumpFile(EtypeFile.SALE_CSV);
    }

    @Override
public void dumpFile(EtypeFile etypefile) {
    switch (etypefile) {
        case SALE_TXT:
            dumpFilePlain();
            break;
        case SALE_XML:
            dumpFileXML();
            break;
        case SALE_JSON:
            dumpFileJSON();
            break;
        case SALE_SERIALIZATE:
            dumpFileSerializate();
            break;
        case SALE_CSV:
            dumpFileCSV();
            break;
        default:
            throw new IllegalArgumentException("Unsupported file type: " + etypefile);
    }
}

@Override
public void loadFile(EtypeFile etypefile) {
    switch (etypefile) {
        case SALE_TXT:
            loadFilePlain();
            break;
        case SALE_XML:
            loadFileXML();
            break;
        case SALE_JSON:
            loadFileJSON();
            break;
        case SALE_SERIALIZATE:
            loadFileSerializate();
            break;
        case SALE_CSV:
            loadFileCSV();
            break;
        default:
            throw new IllegalArgumentException("Unsupported file type: " + etypefile);
    }
}


    private void loadAllFiles() {
        // Limpiar la lista de ventas antes de cargar desde los archivos
        listSale.clear();

        loadFile(EtypeFile.SALE_TXT);
        loadFile(EtypeFile.SALE_XML);
        loadFile(EtypeFile.SALE_JSON);
        loadFile(EtypeFile.SALE_SERIALIZATE);
        loadFile(EtypeFile.SALE_CSV);
    }

    private boolean isDuplicateSale(SaleDTO sale) {
        for (SaleDTO existingSale : listSale) {
            if (existingSale.getIdSale().equals(sale.getIdSale())) {
                return true;
            }
        }
        return false;
    }

    private void dumpFilePlain() {

        StringBuilder pathFileName = new StringBuilder();
        pathFileName.append(confValue.getPath());
        pathFileName.append(confValue.getNameFileSALE_TXT());

        List<String> records = new ArrayList<>();
        for (SaleDTO sale : listSale) {
            StringBuilder contentSale = new StringBuilder();

            contentSale.append(sale.getIdSale()).append(CommonConstants.SEMI_COLON);
            contentSale.append(sale.getTelevisor().getSerialNumber()).append(CommonConstants.SEMI_COLON);
            contentSale.append(sale.getSaleDate()).append(CommonConstants.SEMI_COLON);
            contentSale.append(sale.getSalePrice()).append(CommonConstants.SEMI_COLON);
            contentSale.append(sale.getPaymentMethod());

            records.add(contentSale.toString());

        }
        this.writer(pathFileName.toString(), records);
    }

    private void loadFileXML() {
        listSale.clear();
        try {
            File file = new File(confValue.getPath().concat(confValue.getNameFileSALE_XML()));
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
    
            NodeList nodeList = document.getElementsByTagName("sale");
    
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
    
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
    
                    String idSale = element.getElementsByTagName("idSale").item(0).getTextContent();
                    String serialNumber = element.getElementsByTagName("serialNumber").item(0).getTextContent();
                    String saleDate = element.getElementsByTagName("saleDate").item(0).getTextContent();
                    String salePrice = element.getElementsByTagName("salePrice").item(0).getTextContent();
                    String paymentMethod = element.getElementsByTagName("paymentMethod").item(0).getTextContent();
    
                    TvDTO televisor = findTvBySerialNumber(serialNumber);
    
                    if (televisor != null) {
                        SaleDTO newSale = new SaleDTO(idSale, televisor, saleDate, salePrice, paymentMethod);
                        if (!isDuplicateSale(newSale)) {
                            listSale.add(newSale);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Televisor con número de serie " + serialNumber + " no encontrado.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Se presentó un error en el cargue del archivo XML: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadFilePlain() {
        listSale.clear(); // Asegúrate de que la lista esté vacía antes de cargar nuevos datos
        List<String> contentInLine = this.reader(confValue.getPath().concat(confValue.getNameFileSALE_TXT()));
        contentInLine.forEach(row -> {
            StringTokenizer tokens = new StringTokenizer(row, CommonConstants.SEMI_COLON);
            while (tokens.hasMoreElements()) {
                String idSale = tokens.nextToken().trim();
                String serialNumber = tokens.nextToken().trim();
                String saleDate = tokens.nextToken().trim();
                String salePrice = tokens.nextToken().trim();
                String paymentMethod = tokens.nextToken().trim();

                TvDTO televisor = findTvBySerialNumber(serialNumber);

                if (televisor != null) {
                    SaleDTO newSale = new SaleDTO(idSale, televisor, saleDate, salePrice, paymentMethod);
                    if (!isDuplicateSale(newSale)) {
                        listSale.add(newSale);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Televisor con número de serie " + serialNumber + " no encontrado.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }
            }
        });
    }

    // volcado
    private void dumpFileXML() {
        String filePath = confValue.getPath().concat(confValue.getNameFileSALE_XML());
        StringBuilder lines = new StringBuilder();
        List<SaleDTO> sales = this.listSale.stream().collect(Collectors.toList());
        lines.append("<XML version=\"1.0\" encoding=\"UTF-8\"> \n");
        for (SaleDTO sale : sales) {
            lines.append("<sale>\n");
            lines.append("<idSale>" + sale.getIdSale() + "</idSale>\n");
            lines.append("<serialNumber>" + sale.getTelevisor().getSerialNumber() + "</serialNumber>\n");
            lines.append("<saleDate>" + sale.getSaleDate() + "</saleDate>\n");
            lines.append("<salePrice>" + sale.getSalePrice() + "</salePrice>\n");
            lines.append("<paymentMethod>" + sale.getPaymentMethod() + "</paymentMethod>\n");
            lines.append("</sale>\n");
        }
        lines.append("</XML>");
        this.writeFile(filePath, lines.toString());
    }

    private void dumpFileJSON() {
        // Obtén la ruta completa del archivo JSON
        String filePath = confValue.getPath().concat(confValue.getNameFileSALE_JSON());
        StringBuilder stringJSON = new StringBuilder();
        try {

            // Inicia el arreglo JSON
            stringJSON.append("[\n");

            // Itera sobre la lista de televisores
            for (int i = 0; i < this.listSale.size(); i++) {
                stringJSON.append("{\n");
                stringJSON.append("\"idSale\" : \"").append(listSale.get(i).getIdSale()).append("\", \n");
                stringJSON.append("\"serialNumber\" : \"").append(listSale.get(i).getTelevisor().getSerialNumber())
                        .append("\", \n");
                stringJSON.append("\"saleDate\" : \"").append(listSale.get(i).getSaleDate()).append("\", \n");
                stringJSON.append("\"salePrice\" : \"").append(listSale.get(i).getSalePrice())
                        .append("\", \n");
                stringJSON.append("\"paymentMethod\" : \"").append(listSale.get(i).getPaymentMethod()).append("\"\n");
                stringJSON.append("}");

                // Agrega una coma después de cada objeto, excepto el último
                if (i < listSale.size() - 1) {
                    stringJSON.append(", \n");
                } else {
                    stringJSON.append("\n");
                }
            }

            // Cierra el arreglo JSON
            stringJSON.append("]");

            // Escribe el archivo utilizando try-with-resources para asegurar el cierre
            // adecuado del recurso
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(stringJSON.toString());
            } catch (IOException e) {
                System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error al generar el JSON: " + e.getMessage());
        }
    }

    public void loadFileJSON() {
    listSale.clear(); // Asegúrate de que la lista esté vacía antes de cargar nuevos datos
    StringBuilder filename = new StringBuilder();
    filename.append(this.confValue.getPath());
    filename.append(this.confValue.getNameFileSALE_JSON());
    String content = this.readFile(filename.toString()).trim();

    // Verificar si el contenido está vacío o no es un array JSON válido
    if (content.isEmpty()) {
        System.out.println("El archivo JSON está vacío.");
        return;
    }

    // Crear un objeto Gson para parsear el contenido
    Gson gson = new Gson();
    try {
        // Parsear el contenido JSON a un JsonArray
        JsonArray jsonArray = gson.fromJson(content, JsonArray.class);

        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();

            String idSale = jsonObject.has("idSale") ? jsonObject.get("idSale").getAsString().trim() : "";
            String serialNumber = jsonObject.has("serialNumber") ? jsonObject.get("serialNumber").getAsString().trim() : "";
            String saleDate = jsonObject.has("saleDate") ? jsonObject.get("saleDate").getAsString().trim() : "";
            String salePrice = jsonObject.has("salePrice") ? jsonObject.get("salePrice").getAsString().trim() : "";
            String paymentMethod = jsonObject.has("paymentMethod") ? jsonObject.get("paymentMethod").getAsString().trim() : "";

            TvDTO televisor = findTvBySerialNumber(serialNumber);

            if (televisor != null) {
                SaleDTO newSale = new SaleDTO(idSale, televisor, saleDate, salePrice, paymentMethod);
                if (!isDuplicateSale(newSale)) {
                    listSale.add(newSale);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Televisor con número de serie " + serialNumber + " no encontrado.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                continue;
            }
        }
    } catch (JsonParseException e) {
        System.out.println("Error al parsear el archivo JSON: " + e.getMessage());
    }
}

    private void dumpFileSerializate() {
        try (FileOutputStream fileOut = new FileOutputStream(
                this.confValue.getPath().concat(this.confValue.getNameFileSALE_SER()));
                ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this.listSale);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")

    private void loadFileSerializate() {
        try (FileInputStream fileIn = new FileInputStream(
                this.confValue.getPath().concat(this.confValue.getNameFileSALE_SER()));
                ObjectInputStream in = new ObjectInputStream(fileIn)) {

            // Verificar si el archivo tiene contenido antes de intentar leer
            if (fileIn.available() > 0) {
                this.listSale = (List<SaleDTO>) in.readObject();
            } else {
                System.out.println("El archivo serializado está vacío.");
                // Aquí puedes manejar el caso de archivo vacío según tu lógica de la aplicación
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
    }

    private void dumpFileCSV() {
        StringBuilder pathFileName = new StringBuilder();
        pathFileName.append(confValue.getPath());
        pathFileName.append(confValue.getNameFileSALE_CSV());

        List<String> records = new ArrayList<>();
        for (SaleDTO sale : listSale) {
            StringBuilder contentSale = new StringBuilder();
            contentSale.append(sale.getIdSale()).append(CommonConstants.SEMI_COLON);
            contentSale.append(sale.getTelevisor().getSerialNumber()).append(CommonConstants.SEMI_COLON);
            contentSale.append(sale.getSaleDate()).append(CommonConstants.SEMI_COLON);
            contentSale.append(sale.getSalePrice()).append(CommonConstants.SEMI_COLON);
            contentSale.append(sale.getPaymentMethod());

            records.add(contentSale.toString());

        }
        this.writer(pathFileName.toString(), records);
    }

    private void loadFileCSV() {
        listSale.clear(); // Asegúrate de que la lista esté vacía antes de cargar nuevos datos
        List<String> contentInLine = this.reader(confValue.getPath().concat(confValue.getNameFileSALE_CSV()));
        contentInLine.forEach(row -> {
            StringTokenizer tokens = new StringTokenizer(row, CommonConstants.SEMI_COLON);
            while (tokens.hasMoreElements()) {
                String idSale = tokens.nextToken().trim();
                String serialNumber = tokens.nextToken().trim();
                String saleDate = tokens.nextToken().trim();
                String salePrice = tokens.nextToken().trim();
                String paymentMethod = tokens.nextToken().trim();

                TvDTO televisor = findTvBySerialNumber(serialNumber);

                if (televisor != null) {
                    SaleDTO newSale = new SaleDTO(idSale, televisor, saleDate, salePrice, paymentMethod);
                    if (!isDuplicateSale(newSale)) {
                        listSale.add(newSale);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Televisor con número de serie " + serialNumber + " no encontrado.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }
            }
        });
    }

    public String getNAME_TAG_SALE() {
        return NAME_TAG_SALE;
    }

    public List<SaleDTO> getListSale() {
        return listSale;
    }

    public void setListSale(List<SaleDTO> listSale) {
        this.listSale = listSale;
    }
}
