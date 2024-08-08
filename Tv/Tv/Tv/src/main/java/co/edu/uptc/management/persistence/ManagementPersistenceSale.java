package co.edu.uptc.management.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import co.edu.uptc.management.constants.CommonConstants;
import co.edu.uptc.management.tv.dto.SaleDTO;
import co.edu.uptc.management.tv.dto.TvDTO;

public class ManagementPersistenceSale extends FilePlain {

    private List<SaleDTO> listSale = new ArrayList<>();

    public void dumpFilePlain(String rutaArchivo) {
        List<String> records = new ArrayList<>();
        for (SaleDTO saleDTO : listSale) {
            StringBuilder contentObject = new StringBuilder();
            contentObject.append(saleDTO.getIdSale()).append(CommonConstants.SEMI_COLON);
            contentObject.append(saleDTO.getTelevisor().getSerialNumber()).append(CommonConstants.SEMI_COLON);
            contentObject.append(saleDTO.getSaleDate()).append(CommonConstants.SEMI_COLON);
            contentObject.append(saleDTO.getSalePrice()).append(CommonConstants.SEMI_COLON);
            contentObject.append(saleDTO.getPaymentMethod());

            records.add(contentObject.toString());
        }
        this.writer(rutaArchivo, records);
    }

    public void loadFilePlain(String rutaNombreArchivo) {
        List<String> contentInLine = this.reader(rutaNombreArchivo);
        for (String row : contentInLine) {
            StringTokenizer tokens = new StringTokenizer(row, CommonConstants.SEMI_COLON);
            while (tokens.hasMoreElements()) {
                String idSale = tokens.nextToken();
                String serialNumber = tokens.nextToken();
                String saleDate = tokens.nextToken();
                String salePrice = tokens.nextToken();
                String paymentMethod = tokens.nextToken();

                // Create a TvDTO placeholder (assuming TvDTO has a constructor with serial number)
                TvDTO televisor = new TvDTO(serialNumber, null, null, null, null);

                // Add the SaleDTO object to the list
                listSale.add(new SaleDTO(idSale, televisor, saleDate, salePrice, paymentMethod));
            }
        }
    }

    public List<SaleDTO> getListSale() {
        return listSale;
    }

    public void setListSale(List<SaleDTO> listSale) {
        this.listSale = listSale;
    }
}
