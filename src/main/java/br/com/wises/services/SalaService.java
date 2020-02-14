package br.com.wises.services;

import br.com.wises.database.DbAccessor;
import br.com.wises.database.pojo.Sala;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("sala")
public class SalaService {

    @GET
    @Path("salas")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Sala> getSalas(
            @HeaderParam("id_organizacao") int idOrganizacao,
            @HeaderParam("authorization") String authorization) {
        if (authorization != null && authorization.equals("secret")) {
            List<Sala> lista = DbAccessor.getSalasByOrganizacaoId(idOrganizacao);
            return lista;
        } else {
            return null;
        }
    }

}
