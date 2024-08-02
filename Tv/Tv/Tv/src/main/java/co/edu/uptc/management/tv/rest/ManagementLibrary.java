package co.edu.uptc.management.library.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.edu.uptc.management.tv.dto.SaleDTO;

@Path("/ManagementLibrary")
public class ManagementLibrary {
	
	public static ManagementPersistenceBooks managementPersistenceBooks = new ManagementPersistenceBooks();;
	
	static {
		managementPersistenceBooks.loadFilePlain("/data/books.txt");
	}
	
	@GET
	@Path("/getBooks")
	@Produces( { MediaType.APPLICATION_JSON } )
	public List<SaleDTO> getBooks(){
		return managementPersistenceBooks.getListBooksDTO();
	}
	
	@GET
	@Path("/getBooksByCode")
	@Produces( { MediaType.APPLICATION_JSON } )
	public SaleDTO getBooksByCode(@QueryParam("codeBook") String codeBook){
		for(SaleDTO bookDTO: managementPersistenceBooks.getListBooksDTO()) {
			if(bookDTO.getCode().equals(codeBook)) {
				return bookDTO;
			}
		}
		return null;
	}
	
	
	@POST
	@Path("/createBook")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public SaleDTO createBook(SaleDTO bookDTO) {
		if(managementPersistenceBooks.getListBooksDTO().add(bookDTO)) {
			managementPersistenceBooks.dumpFilePlain("books.txt");
			return bookDTO;
		}
		return null;
	}
	
	@PUT
	@Path("/updateBook")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public SaleDTO updateBook(SaleDTO bookDTO) {
		for(SaleDTO book: managementPersistenceBooks.getListBooksDTO()) {
			if(book.getCode().equals(bookDTO.getCode())) {
				book.setCode(bookDTO.getName());
				book.setName(bookDTO.getName());
				book.setAuthor(bookDTO.getAuthor());
				book.setGenre(bookDTO.getGenre());
				book.setYearPublish(bookDTO.getYearPublish());
				book.setPageQuantity(bookDTO.getPageQuantity());
				book.setPublisher(bookDTO.getPublisher());
				managementPersistenceBooks.dumpFilePlain("books.txt");
				return bookDTO;
			}
		}
		return null;
	}
	
	@PUT
	@Path("/updateBookAttribute")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public SaleDTO updateBookAttribute(SaleDTO bookDTO) {
		for(SaleDTO book: managementPersistenceBooks.getListBooksDTO()) {
			if(book.getCode().equals(bookDTO.getCode())) {
				if(!Objects.isNull(bookDTO.getName())) {
					book.setName(bookDTO.getName());
				}
				
				if(!Objects.isNull(bookDTO.getAuthor())) {
					book.setAuthor(bookDTO.getAuthor());
				}
				
				if(!Objects.isNull(bookDTO.getGenre())) {
					book.setGenre(bookDTO.getGenre());
				}
				
				if(!Objects.isNull(bookDTO.getYearPublish())) {
					book.setYearPublish(bookDTO.getYearPublish());
				}
				
				if(!Objects.isNull(bookDTO.getPageQuantity())) {
					book.setPageQuantity(bookDTO.getPageQuantity());
				}
				
				if(!Objects.isNull(bookDTO.getPublisher())) {
					book.setPublisher(bookDTO.getPublisher());
				}
				managementPersistenceBooks.dumpFilePlain("books.txt");
				return bookDTO;
			}
		}
		return null;
	}
	
	@DELETE
	@Path("/deleteBook")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public SaleDTO deleteBook(@QueryParam("codeBook") String codeBook) {
		SaleDTO bookDTO = this.getBooksByCode(codeBook);
		if(bookDTO != null) {
			managementPersistenceBooks.getListBooksDTO().remove(bookDTO);
			managementPersistenceBooks.dumpFilePlain("books.txt");
		}
		return bookDTO;
	}
	
	
}
