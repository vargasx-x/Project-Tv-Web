package co.edu.uptc.management.tv.rest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public static ManagementPersistenceTv managementPersistenceTv = new ManagementPersistenceTv();

    static {
        managementPersistenceTv.loadFilePlain("/data/Tv.txt");
    }

    @GET
    @Path("/getTvs")
    @Produces({ MediaType.APPLICATION_JSON })
    public List<TvDTO> getTvs() {
        return managementPersistenceTv.getListTv().stream()
                .map(tv -> new TvDTO(tv.getSerialNumber(), tv.getResolution(), tv.getSizeDisplay(),
                        tv.getTechnologyDisplay(), tv.getSystemOperational()))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/getTvBySerialNumber")
    @Produces({ MediaType.APPLICATION_JSON })
    public TvDTO getTvBySerialNumber(@QueryParam("serialNumber") String serialNumber) {
        for (TvDTO tv : managementPersistenceTv.getListTv()) {
            if (tv.getSerialNumber().equals(serialNumber)) {
                return tv;
            }
        }
        return null;
    }

    @POST
    @Path("/createTv")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public TvDTO createTv(TvDTO tvDTO) {
        if (managementPersistenceTv.getListTv().add(tvDTO)) {
            managementPersistenceTv.dumpFilePlain("Tv.txt");
            return tvDTO;
        }
        return null;
    }

    @PUT
    @Path("/updateTv")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public TvDTO updateTv(TvDTO tvDTO) {
        for (TvDTO tv : managementPersistenceTv.getListTv()) {
            if (tv.getSerialNumber().equals(tvDTO.getSerialNumber())) {
                tv.setResolution(tvDTO.getResolution());
                tv.setSizeDisplay(tvDTO.getSizeDisplay());
                tv.setTechnologyDisplay(tvDTO.getTechnologyDisplay());
                tv.setSystemOperational(tvDTO.getSystemOperational());
                managementPersistenceTv.dumpFilePlain("Tv.txt");
                return tvDTO;
            }
        }
        return null;
    }

    @PUT
    @Path("/updateTvAttribute")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public TvDTO updateTvAttribute(TvDTO tvDTO) {
        for (TvDTO tv : managementPersistenceTv.getListTv()) {
            if (tv.getSerialNumber().equals(tvDTO.getSerialNumber())) {
                if (!Objects.isNull(tvDTO.getResolution())) {
                    tv.setResolution(tvDTO.getResolution());
                }
                if (!Objects.isNull(tvDTO.getSizeDisplay())) {
                    tv.setSizeDisplay(tvDTO.getSizeDisplay());
                }
                if (!Objects.isNull(tvDTO.getTechnologyDisplay())) {
                    tv.setTechnologyDisplay(tvDTO.getTechnologyDisplay());
                }
                if (!Objects.isNull(tvDTO.getSystemOperational())) {
                    tv.setSystemOperational(tvDTO.getSystemOperational());
                }
                managementPersistenceTv.dumpFilePlain("Tv.txt");
                return tvDTO;
            }
        }
        return null;
    }

    @DELETE
    @Path("/deleteTv")
    @Produces({ MediaType.APPLICATION_JSON })
    public TvDTO deleteTv(@QueryParam("serialNumber") String serialNumber) {
        TvDTO tvDTO = this.getTvBySerialNumber(serialNumber);
        if (tvDTO != null) {
            managementPersistenceTv.getListTv().remove(tvDTO);
            managementPersistenceTv.dumpFilePlain("Tv.txt");
        }
        return tvDTO;
    }
}
