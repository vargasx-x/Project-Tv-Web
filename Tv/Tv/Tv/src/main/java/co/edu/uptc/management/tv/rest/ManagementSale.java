package co.edu.uptc.management.library.rest;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.edu.uptc.management.enums.EtypeFile;
import co.edu.uptc.management.persistence.ManagementPersistenceSale;
import co.edu.uptc.management.tv.dto.SaleDTO;

@Path("/ManagementSale")
public class ManagementSale {

	public static ManagementPersistenceSale managementPersistenceSale = new ManagementPersistenceSale(
			new ManagementTv());

	static {
		managementPersistenceSale.loadFile(EtypeFile.SALE_TXT);
		managementPersistenceSale.loadFile(EtypeFile.SALE_XML);
		managementPersistenceSale.loadFile(EtypeFile.SALE_JSON);
		managementPersistenceSale.loadFile(EtypeFile.SALE_CSV);
		managementPersistenceSale.loadFile(EtypeFile.SALE_SERIALIZATE);
	}

	@GET
	@Path("/getSales")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SaleDTO> getSales() {
		return managementPersistenceSale.listAllSales().stream()
				.map(sale -> new SaleDTO(sale.getIdSale(), sale.getTelevisor().getSerialNumber(), sale.getSaleDate(),
						sale.getSalePrice(), sale.getPaymentMethod()))
				.toList();
	}

	@GET
	@Path("/getSaleById")
	@Produces({ MediaType.APPLICATION_JSON })
	public SaleDTO getSaleById(@QueryParam("idSale") String idSale) {
		Sale sale = managementPersistenceSale.getSale(idSale);
		if (sale != null) {
			return new SaleDTO(sale.getIdSale(), sale.getTelevisor().getSerialNumber(), sale.getSaleDate(),
					sale.getSalePrice(), sale.getPaymentMethod());
		}
		return null;
	}

	@POST
	@Path("/createSale")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public SaleDTO createSale(SaleDTO saleDTO) {
		Tv televisor = managementPersistenceSale.findTvBySerialNumber(saleDTO.getSerialNumber());
		if (televisor != null) {
			Sale sale = new Sale(saleDTO.getIdSale(), televisor, saleDTO.getSaleDate(), saleDTO.getSalePrice(),
					saleDTO.getPaymentMethod());
			if (managementPersistenceSale.insertSale(sale)) {
				managementPersistenceSale.synchronizeAllFiles(); // Sincroniza todos los formatos
				return saleDTO;
			}
		}
		return null;
	}

	@PUT
	@Path("/updateSale")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public SaleDTO updateSale(SaleDTO saleDTO) {
		Sale existingSale = managementPersistenceSale.getSale(saleDTO.getIdSale());
		if (existingSale != null) {
			Tv televisor = managementPersistenceSale.findTvBySerialNumber(saleDTO.getSerialNumber());
			if (televisor != null) {
				existingSale.setTelevisor(televisor);
				existingSale.setSaleDate(saleDTO.getSaleDate());
				existingSale.setSalePrice(saleDTO.getSalePrice());
				existingSale.setPaymentMethod(saleDTO.getPaymentMethod());
				managementPersistenceSale.synchronizeAllFiles(); // Sincroniza todos los formatos
				return saleDTO;
			}
		}
		return null;
	}

	@PUT
	@Path("/updateSaleAttribute")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public SaleDTO updateSaleAttribute(SaleDTO saleDTO) {
		Sale existingSale = managementPersistenceSale.getSale(saleDTO.getIdSale());
		if (existingSale != null) {
			if (!Objects.isNull(saleDTO.getSerialNumber())) {
				Tv televisor = managementPersistenceSale.findTvBySerialNumber(saleDTO.getSerialNumber());
				if (televisor != null) {
					existingSale.setTelevisor(televisor);
				}
			}
			if (!Objects.isNull(saleDTO.getSaleDate())) {
				existingSale.setSaleDate(saleDTO.getSaleDate());
			}
			if (!Objects.isNull(saleDTO.getSalePrice())) {
				existingSale.setSalePrice(saleDTO.getSalePrice());
			}
			if (!Objects.isNull(saleDTO.getPaymentMethod())) {
				existingSale.setPaymentMethod(saleDTO.getPaymentMethod());
			}
			managementPersistenceSale.synchronizeAllFiles(); // Sincroniza todos los formatos
			return saleDTO;
		}
		return null;
	}

	@DELETE
	@Path("/deleteSale")
	@Produces({ MediaType.APPLICATION_JSON })
	public SaleDTO deleteSale(@QueryParam("idSale") String idSale) {
		Sale sale = managementPersistenceSale.getSale(idSale);
		if (sale != null) {
			if (managementPersistenceSale.deleteSale(idSale)) {
				managementPersistenceSale.synchronizeAllFiles(); // Sincroniza todos los formatos
				return new SaleDTO(sale.getIdSale(), sale.getTelevisor().getSerialNumber(), sale.getSaleDate(),
						sale.getSalePrice(), sale.getPaymentMethod());
			}
		}
		return null;
	}
}
