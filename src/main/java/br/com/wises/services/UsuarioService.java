package br.com.wises.services;

import br.com.wises.database.EManager;
import br.com.wises.database.pojo.Usuario;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("usuario")
public class UsuarioService {

    @GET
    @Path("usuarios")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Usuario> getUsuarios() {
        List<Usuario> lista = EManager.getInstance().getUsuarioAccessor().getAllUsuarios();
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).getIdOrganizacao().setUsuarioCollection(null);
        }
        return lista;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Usuario metodoGetJson(@PathParam("id") int id, @HeaderParam("authorization") String authorization) {
        if (authorization != null && authorization.equals("secret")) {
            Usuario user = EManager.getInstance().getUsuarioAccessor().getUserById(id);
            if (user != null) {
                user.getIdOrganizacao().setUsuarioCollection(null);
                return user;
            }
        } else {
            return null;
        }
        return null;
    }
}
