package co.edu.uptc.management.library.dto;

public class BookDTO {
	private String code;
	private String name;
	private String author;
	private String genre;
	private String yearPublish;
	private Integer pageQuantity;
	private String publisher;
	
	public BookDTO() {
	}
	
	public BookDTO(String code, String name, String author, String genre, String yearPublish, Integer pageQuantity,
			String publisher) {
		this.code = code;
		this.name = name;
		this.author = author;
		this.genre = genre;
		this.yearPublish = yearPublish;
		this.pageQuantity = pageQuantity;
		this.publisher = publisher;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getYearPublish() {
		return yearPublish;
	}
	public void setYearPublish(String yearPublish) {
		this.yearPublish = yearPublish;
	}
	public Integer getPageQuantity() {
		return pageQuantity;
	}
	public void setPageQuantity(Integer pageQuantity) {
		this.pageQuantity = pageQuantity;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Override
	public String toString() {
		return "BookDTO [code=" + code + ", name=" + name + ", author=" + author + ", genre=" + genre + ", yearPublish="
				+ yearPublish + ", pageQuantity=" + pageQuantity + ", publisher=" + publisher + "]";
	}
	
}
