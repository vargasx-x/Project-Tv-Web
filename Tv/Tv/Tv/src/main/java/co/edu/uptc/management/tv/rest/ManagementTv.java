package co.edu.uptc.management.tv.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import co.edu.uptc.management.persistence.ManagementPersistenceTv;
import co.edu.uptc.management.tv.dto.TvDTO;

@Path("/ManagementTv")
public class ManagementTv {

    private static ManagementPersistenceTv managementPersistenceTv = new ManagementPersistenceTv();

    @GET
    @Path("/getTvs")
    @Produces({MediaType.APPLICATION_JSON})
    public List<TvDTO> getTvs() {
        return managementPersistenceTv.getListTv().stream()
                .map(tv -> new TvDTO(tv.getSerialNumber(), tv.getResolution(), tv.getSizeDisplay(), tv.getTechnologyDisplay(), tv.getSystemOperational()))
                .toList();
    }

    @GET
    @Path("/getTvBySerialNumber")
    @Produces({MediaType.APPLICATION_JSON})
    public TvDTO getTvBySerialNumber(@QueryParam("serialNumber") String serialNumber) {
        Tv tv = managementPersistenceTv.getTv(serialNumber);
        if (tv != null) {
            return new TvDTO(tv.getSerialNumber(), tv.getResolution(), tv.getSizeDisplay(), tv.getTechnologyDisplay(), tv.getSystemOperational());
        }
        return null;
    }

    @POST
    @Path("/createTv")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public TvDTO createTv(TvDTO tvDTO) {
        Tv tv = new Tv(tvDTO.getSerialNumber(), tvDTO.getResolution(), tvDTO.getSizeDisplay(), tvDTO.getTechnologyDisplay(), tvDTO.getSystemOperational());
        if (managementPersistenceTv.insertTv(tv)) {
            return tvDTO;
        }
        return null;
    }

    @PUT
    @Path("/updateTv")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public TvDTO updateTv(TvDTO tvDTO) {
        Tv updatedTv = new Tv(tvDTO.getSerialNumber(), tvDTO.getResolution(), tvDTO.getSizeDisplay(), tvDTO.getTechnologyDisplay(), tvDTO.getSystemOperational());
        if (managementPersistenceTv.updateTv(updatedTv)) {
            return tvDTO;
        }
        return null;
    }

    @DELETE
    @Path("/deleteTv")
    @Produces({MediaType.APPLICATION_JSON})
    public TvDTO deleteTv(@QueryParam("serialNumber") String serialNumber) {
        Tv tv = managementPersistenceTv.getTv(serialNumber);
        if (tv != null && managementPersistenceTv.deleteTv(serialNumber)) {
            return new TvDTO(tv.getSerialNumber(), tv.getResolution(), tv.getSizeDisplay(), tv.getTechnologyDisplay(), tv.getSystemOperational());
        }
        return null;
    }
}
