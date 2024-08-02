package co.edu.uptc.management.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import co.edu.uptc.management.constants.CommonConstants;
import co.edu.uptc.management.library.dto.BookDTO;

public class ManagementPersistenceBooks extends FilePlain {
	private List<SaleDTO> listBooksDTO = new ArrayList<>();

	public void dumpFilePlain(String rutaArchivo) {
		List<String> records = new ArrayList<>();
		for (SaleDTO bookDTO : listBooksDTO) {
			StringBuilder contentContact = new StringBuilder();
			contentContact.append(bookDTO.getCode()).append(CommonConstants.SEMI_COLON);
			contentContact.append(bookDTO.getName()).append(CommonConstants.SEMI_COLON);
			contentContact.append(bookDTO.getAuthor()).append(CommonConstants.SEMI_COLON);
			contentContact.append(bookDTO.getGenre()).append(CommonConstants.SEMI_COLON);
			contentContact.append(bookDTO.getYearPublish()).append(CommonConstants.SEMI_COLON);
			contentContact.append(bookDTO.getPageQuantity()).append(CommonConstants.SEMI_COLON);
			contentContact.append(bookDTO.getPublisher());
			records.add(contentContact.toString());
		}
		this.writer(rutaArchivo, records);
	}

	public void loadFilePlain(String rutaNombreArchivo) {
		List<String> contentInLine = this.reader(rutaNombreArchivo);
		for (String row : contentInLine) {
			StringTokenizer tokens = new StringTokenizer(row, CommonConstants.SEMI_COLON);
			while (tokens.hasMoreElements()) {
				String code = tokens.nextToken();
				String name = tokens.nextToken();
				String author = tokens.nextToken();
				String genre = tokens.nextToken();
				String yearPublish = tokens.nextToken();
				String pageQuantity = tokens.nextToken();
				String publisher = tokens.nextToken();
				listBooksDTO.add(new SaleDTO(code, name, author,
						genre, yearPublish,
						Integer.parseInt(pageQuantity), publisher));
			}
		}
	}

	public List<SaleDTO> getListBooksDTO() {
		return listBooksDTO;
	}

	public void setListBooksDTO(List<SaleDTO> listBooksDTO) {
		this.listBooksDTO = listBooksDTO;
	}
}
