package co.edu.uptc.management.library.dto;

public class SaleDTO implements Serializable {
	private String idSale;
	private Tv televisor;
	private String saleDate;
	private String salePrice;

	private String paymentMethod;

	public Sale() {
    }

	public Sale(String idSale, Tv televisor, String saleDate, String salePrice, String paymentMethod) {
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

	public Tv getTelevisor() {
		return televisor;
	}

	public void setTelevisor(Tv televisor) {
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
