package co.edu.uptc.management.tv.rest;

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

import co.edu.uptc.management.persistence.ManagementPersistenceSale;
import co.edu.uptc.management.tv.dto.SaleDTO;
import co.edu.uptc.management.tv.dto.TvDTO;

@Path("/ManagementSale")
public class ManagementSale {

    public static ManagementPersistenceSale managementPersistenceSale = new ManagementPersistenceSale();

    static {
        managementPersistenceSale.loadFilePlain("/data/Sale.txt");
    }

    @GET
    @Path("/getSales")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<SaleDTO> getSales() {
        return managementPersistenceSale.getListSale();
    }

    @GET
    @Path("/getSaleById")
    @Produces({ MediaType.APPLICATION_JSON })
    public SaleDTO getSaleById(@QueryParam("idSale") String idSale) {
        for (SaleDTO sale : managementPersistenceSale.getListSale()) {
            if (sale.getIdSale().equals(idSale)) {
                return sale;
            }
        }
        return null; // or throw a custom exception if preferred
    }

    @POST
    @Path("/createSale")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public SaleDTO createSale(SaleDTO saleDTO) {
        TvDTO televisor = saleDTO.getTelevisor();
        // Ensure the TV exists before creating the sale
        if (televisor != null && televisor.getSerialNumber() != null) {
            SaleDTO newSale = new SaleDTO(saleDTO.getIdSale(), televisor, saleDTO.getSaleDate(), saleDTO.getSalePrice(),
                    saleDTO.getPaymentMethod());
            if (managementPersistenceSale.getListSale().add(newSale)) {
                managementPersistenceSale.dumpFilePlain("/data/Sale.txt");
                return newSale;
            }
        }
        return null;
    }

    @PUT
    @Path("/updateSale")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public SaleDTO updateSale(SaleDTO saleDTO) {
        for (SaleDTO existingSale : managementPersistenceSale.getListSale()) {
            if (existingSale.getIdSale().equals(saleDTO.getIdSale())) {
                TvDTO televisor = saleDTO.getTelevisor();
                if (televisor != null) {
                    existingSale.setTelevisor(televisor);
                }
                existingSale.setSaleDate(saleDTO.getSaleDate());
                existingSale.setSalePrice(saleDTO.getSalePrice());
                existingSale.setPaymentMethod(saleDTO.getPaymentMethod());
                managementPersistenceSale.dumpFilePlain("/data/Sale.txt");
                return existingSale;
            }
        }
        return null; // or throw a custom exception if preferred
    }

    @PUT
    @Path("/updateSaleAttribute")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public SaleDTO updateSaleAttribute(SaleDTO saleDTO) {
        for (SaleDTO existingSale : managementPersistenceSale.getListSale()) {
            if (existingSale.getIdSale().equals(saleDTO.getIdSale())) {
                if (!Objects.isNull(saleDTO.getTelevisor())) {
                    existingSale.setTelevisor(saleDTO.getTelevisor());
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
                managementPersistenceSale.dumpFilePlain("/data/Sale.txt");
                return existingSale;
            }
        }
        return null; // or throw a custom exception if preferred
    }

    @DELETE
    @Path("/deleteSale")
    @Produces({ MediaType.APPLICATION_JSON })
    public SaleDTO deleteSale(@QueryParam("idSale") String idSale) {
        SaleDTO saleToDelete = getSaleById(idSale);
        if (saleToDelete != null) {
            if (managementPersistenceSale.getListSale().remove(saleToDelete)) {
                managementPersistenceSale.dumpFilePlain("/data/Sale.txt");
                return saleToDelete;
            }
        }
        return null; // or throw a custom exception if preferred
    }
}
