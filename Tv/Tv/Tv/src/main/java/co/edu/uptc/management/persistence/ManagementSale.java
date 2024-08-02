package co.edu.uptc.management.persistence;

import javax.xml.bind.Element;

import co.edu.uptc.management.constants.CommonConstants;
import co.edu.uptc.management.enums.EtypeFile;
import co.edu.uptc.management.interfaces.IActionFile;

public class ManagementSale extends FilePlain implements IActionFile {
    private final String NAME_TAG_SALE = "sale";
    private List<Sale> listSale;
    private ManagementTv managementTv; // Instancia de ManagementTv

    public ManagementSale(ManagementTv managementTv) {
        this.listSale = new ArrayList<>();
        this.managementTv = managementTv; // Inicializar ManagementTv

        loadAllFiles();
    }

    public Tv findTvBySerialNumber(String serialNumber) {
        return managementTv.findTvBySerialNumber(serialNumber);
    }

    public boolean insertSale(Sale sale) {
        if (!isDuplicateSale(sale)) {
            boolean success = listSale.add(sale);
            if (success) {
                synchronizeAllFiles();
            }
            return success;
        }
        return false;
    }

    public List<Sale> listAllSales() {
        return new ArrayList<>(listSale);
    }

    public boolean deleteSale(String serialNumber) {
        boolean success = false;
        Iterator<Sale> iterator = listSale.iterator();

        while (iterator.hasNext()) {
            Sale sale = iterator.next();
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

    public Sale getSale(String serialNumber) {
        for (Sale sale : listSale) {
            if (sale.getTelevisor().getSerialNumber().equals(serialNumber)) {
                return sale;
            }
        }
        return null;
    }

    private void synchronizeAllFiles() {
        dumpFile(EtypeFile.SALE_TXT);
        dumpFile(EtypeFile.SALE_XML);
        dumpFile(EtypeFile.SALE_JSON);
        dumpFile(EtypeFile.SALE_SERIALIZATE);
        dumpFile(EtypeFile.SALE_CSV);
    }

    @Override
    public void dumpFile(EtypeFile etypefile) {
        switch (etypefile) {
            case SALE_TXT -> dumpFilePlain();
            case SALE_XML -> dumpFileXML();
            case SALE_JSON -> dumpFileJSON();
            case SALE_SERIALIZATE -> dumpFileSerializate();
            case SALE_CSV -> dumpFileCSV();
        }
    }

    @Override
    public void loadFile(EtypeFile etypefile) {
        switch (etypefile) {
            case SALE_TXT -> loadFilePlain();
            case SALE_XML -> loadFileXML();
            case SALE_JSON -> loadFileJSON();
            case SALE_SERIALIZATE -> loadFileSerializate();
            case SALE_CSV -> loadFileCSV();
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

    private boolean isDuplicateSale(Sale sale) {
        for (Sale existingSale : listSale) {
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
        for (Sale sale : listSale) {
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
        listSale.clear(); // Asegúrate de que la lista esté vacía antes de cargar nuevos datos

        try {
            File file = new File(confValue.getPath().concat(confValue.getNameFileSALE_XML()));
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize(); // Normalizar el documento XML

            NodeList list = document.getElementsByTagName(NAME_TAG_SALE);
            for (int i = 0; i < list.getLength(); i++) {
                Element saleElement = (Element) list.item(i);

                String idSale = saleElement.getElementsByTagName("idSale").item(0).getTextContent().trim();
                String serialNumber = saleElement.getElementsByTagName("serialNumber").item(0).getTextContent().trim();
                String saleDate = saleElement.getElementsByTagName("saleDate").item(0).getTextContent().trim();
                String salePrice = saleElement.getElementsByTagName("salePrice").item(0).getTextContent().trim();
                String paymentMethod = saleElement.getElementsByTagName("paymentMethod").item(0).getTextContent()
                        .trim();

                Tv televisor = findTvBySerialNumber(serialNumber);

                if (televisor != null) {
                    Sale newSale = new Sale(idSale, televisor, saleDate, salePrice, paymentMethod);
                    if (!isDuplicateSale(newSale)) {
                        listSale.add(newSale);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Televisor con número de serie " + serialNumber + " no encontrado.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Continuar con el siguiente objeto en el NodeList
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Se presentó un error en el cargue del archivo XML: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Manejo de excepciones específicas según tu necesidad
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

                Tv televisor = findTvBySerialNumber(serialNumber);

                if (televisor != null) {
                    Sale newSale = new Sale(idSale, televisor, saleDate, salePrice, paymentMethod);
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
        List<Sale> sales = this.listSale.stream().collect(Collectors.toList());
        lines.append("<XML version=\"1.0\" encoding=\"UTF-8\"> \n");
        for (Sale sale : sales) {
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
        if (content.isEmpty() || !(content.startsWith("[") && content.endsWith("]"))) {
            System.out.println("El archivo JSON está vacío o no está en el formato esperado.");
            return;
        }

        // Parsear el contenido JSON
        JSONArray jsonArray = new JSONArray(content);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String idSale = jsonObject.optString("idSale").trim();
            String serialNumber = jsonObject.optString("serialNumber").trim();
            String saleDate = jsonObject.optString("saleDate").trim();
            String salePrice = jsonObject.optString("salePrice").trim();
            String paymentMethod = jsonObject.optString("paymentMethod").trim();

            Tv televisor = findTvBySerialNumber(serialNumber);

            if (televisor != null) {
                Sale newSale = new Sale(idSale, televisor, saleDate, salePrice, paymentMethod);
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
                this.listSale = (List<Sale>) in.readObject();
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
        for (Sale sale : listSale) {
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

                Tv televisor = findTvBySerialNumber(serialNumber);

                if (televisor != null) {
                    Sale newSale = new Sale(idSale, televisor, saleDate, salePrice, paymentMethod);
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

    public List<Sale> getListSale() {
        return listSale;
    }

    public void setListSale(List<Sale> listSale) {
        this.listSale = listSale;
    }
}
