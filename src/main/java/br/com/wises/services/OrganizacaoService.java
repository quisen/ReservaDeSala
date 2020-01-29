package br.com.wises.services;

import br.com.wises.database.EManager;
import br.com.wises.database.pojo.Organizacao;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("organizacao")
public class OrganizacaoService {

    @GET
    @Path("organizacoesByDominio")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Organizacao> getOrganizacoesByDominio(
            @HeaderParam("authorization") String authorization,
            @HeaderParam("dominio") String dominio) {
        if (authorization != null && authorization.equals("secret")) {
            List<Organizacao> lista = EManager.getInstance().getDbAccessor().getOrganizacoesByDominio(dominio);
            for (int i = 0; i < lista.size(); i++) {
                lista.get(i).setUsuarioCollection(null);
                lista.get(i).setSalaCollection(null);
            }
            return lista;
        } return null;
    }
}
