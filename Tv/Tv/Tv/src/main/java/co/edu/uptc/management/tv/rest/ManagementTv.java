package co.edu.uptc.management.tv.rest;

import java.util.List;
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
import javax.ws.rs.core.Response;

import co.edu.uptc.management.persistence.ManagementPersistenceTv;
import co.edu.uptc.management.tv.dto.TvDTO;

@Path("/ManagementTv")
public class ManagementTv {

    private static ManagementPersistenceTv managementPersistenceTv;

    static {
        try {
            managementPersistenceTv = new ManagementPersistenceTv();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo del error crítico durante la inicialización
            throw new RuntimeException("No se pudo inicializar ManagementPersistenceTv", e);
        }
    }

    @GET
    @Path("/getTvs")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getTvs() {
        try {
            List<TvDTO> tvs = managementPersistenceTv.getListTv().stream()
                .map(tv -> new TvDTO(tv.getSerialNumber(), tv.getResolution(), tv.getSizeDisplay(),
                        tv.getTechnologyDisplay(), tv.getSystemOperational()))
                .collect(Collectors.toList());
            return Response.ok(tvs).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener la lista de televisores").build();
        }
    }

    @GET
    @Path("/getTvBySerialNumber")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getTvBySerialNumber(@QueryParam("serialNumber") String serialNumber) {
        try {
            TvDTO tv = managementPersistenceTv.getTv(serialNumber);
            if (tv != null) {
                return Response.ok(new TvDTO(tv.getSerialNumber(), tv.getResolution(), tv.getSizeDisplay(),
                        tv.getTechnologyDisplay(), tv.getSystemOperational())).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Televisor no encontrado").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener el televisor").build();
        }
    }

    @POST
    @Path("/createTv")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response createTv(TvDTO tvDTO) {
        try {
            TvDTO tv = new TvDTO(tvDTO.getSerialNumber(), tvDTO.getResolution(), tvDTO.getSizeDisplay(),
                    tvDTO.getTechnologyDisplay(), tvDTO.getSystemOperational());
            if (managementPersistenceTv.insertTv(tv)) {
                return Response.status(Response.Status.CREATED).entity(tvDTO).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al agregar el televisor.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno al crear el televisor").build();
        }
    }

    @PUT
    @Path("/updateTv")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response updateTv(TvDTO tvDTO) {
        try {
            TvDTO updatedTv = new TvDTO(tvDTO.getSerialNumber(), tvDTO.getResolution(), tvDTO.getSizeDisplay(),
                    tvDTO.getTechnologyDisplay(), tvDTO.getSystemOperational());
            if (managementPersistenceTv.updateTv(updatedTv)) {
                return Response.ok(tvDTO).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Error al actualizar el televisor.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno al actualizar el televisor").build();
        }
    }

    @DELETE
    @Path("/deleteTv")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteTv(@QueryParam("serialNumber") String serialNumber) {
        try {
            TvDTO tv = managementPersistenceTv.getTv(serialNumber);
            if (tv != null && managementPersistenceTv.deleteTv(serialNumber)) {
                return Response.ok(new TvDTO(tv.getSerialNumber(), tv.getResolution(), tv.getSizeDisplay(),
                        tv.getTechnologyDisplay(), tv.getSystemOperational())).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Error al eliminar el televisor.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno al eliminar el televisor").build();
        }
    }
}
