package co.edu.uptc.management.tv.dto;

import java.io.Serializable;

public class SaleDTO implements Serializable  {
	private String idSale;
	private TvDTO televisor;
	private String saleDate;
	private String salePrice;

	private String paymentMethod;

	public SaleDTO() {
    }

	public SaleDTO(String idSale, TvDTO televisor, String saleDate, String salePrice, String paymentMethod) {
        this.idSale = idSale;
        this.televisor = televisor;
        this.saleDate = saleDate;
        this.salePrice = salePrice;
        this.paymentMethod = paymentMethod;
    }

	public String getIdSale() {
		return idSale;
	}

	public void setIdSale(String idSale) {
		this.idSale = idSale;
	}

	public TvDTO getTelevisor() {
		return televisor;
	}

	public void setTelevisor(TvDTO televisor) {
		this.televisor = televisor;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "Sale [idSale=" + idSale + ", televisor=" + televisor + ", saleDate=" + saleDate + ", salePrice="
				+ salePrice + ", paymentMethod=" + paymentMethod + "]";
	}

}
