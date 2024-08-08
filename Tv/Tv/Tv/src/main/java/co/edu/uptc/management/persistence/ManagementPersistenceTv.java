package co.edu.uptc.management.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import co.edu.uptc.management.constants.CommonConstants;
import co.edu.uptc.management.tv.dto.TvDTO;

public class ManagementPersistenceTv extends FilePlain {

    private List<TvDTO> listTv = new ArrayList<>();

    public void dumpFilePlain(String rutaArchivo) {
        List<String> records = new ArrayList<>();
        for (TvDTO tvDTO : listTv) {
            StringBuilder contentObject = new StringBuilder();
            contentObject.append(tvDTO.getSerialNumber()).append(CommonConstants.SEMI_COLON);
            contentObject.append(tvDTO.getResolution()).append(CommonConstants.SEMI_COLON);
            contentObject.append(tvDTO.getSizeDisplay()).append(CommonConstants.SEMI_COLON);
            contentObject.append(tvDTO.getTechnologyDisplay()).append(CommonConstants.SEMI_COLON);
            contentObject.append(tvDTO.getSystemOperational());

            records.add(contentObject.toString());
        }
        this.writer(rutaArchivo, records);
    }

    public void loadFilePlain(String rutaNombreArchivo) {
        List<String> contentInLine = this.reader(rutaNombreArchivo);
        for (String row : contentInLine) {
            StringTokenizer tokens = new StringTokenizer(row, CommonConstants.SEMI_COLON);
            while (tokens.hasMoreElements()) {
                String serialNumber = tokens.nextToken();
                String resolution = tokens.nextToken();
                String sizeDisplay = tokens.nextToken();
                String technologyDisplay = tokens.nextToken();
                String systemOperational = tokens.nextToken();

                listTv.add(new TvDTO(serialNumber, resolution, sizeDisplay, technologyDisplay, systemOperational));
            }
        }
    }

    public List<TvDTO> getListTv() {
        return listTv;
    }

    public void setListTv(List<TvDTO> listTv) {
        this.listTv = listTv;
    }
    
}
