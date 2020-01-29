package br.com.wises.services;

import br.com.wises.database.EManager;
import br.com.wises.database.pojo.Organizacao;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("organizacao")
public class OrganizacaoService {
    
    @GET
    @Path("organizacoes")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Organizacao> getOrganizacoes() {
        List<Organizacao> lista = EManager.getInstance().getDbAccessor().getAllOrganizacoes();
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setUsuarioCollection(null);
        }
        return lista;
    }
    
}
