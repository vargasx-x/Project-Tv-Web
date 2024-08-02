package co.edu.uptc.management.library.dto;

import java.util.Date;

public class BorrowDTO {

	private String nameUser;
	private String codeBook;
	private Integer quantityDays;
	private Date borrowDate;
	
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String getCodeBook() {
		return codeBook;
	}
	public void setCodeBook(String codeBook) {
		this.codeBook = codeBook;
	}
	public Integer getQuantityDays() {
		return quantityDays;
	}
	public void setQuantityDays(Integer quantityDays) {
		this.quantityDays = quantityDays;
	}
	public Date getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	
}
