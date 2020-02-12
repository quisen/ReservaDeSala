package br.com.wises.services;

import br.com.wises.database.EManager;
import br.com.wises.database.pojo.Organizacao;
import br.com.wises.database.pojo.Status;
import br.com.wises.database.pojo.Usuario;
import java.nio.charset.Charset;
import java.util.Base64;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("usuario")
public class UsuarioService {

    @GET
    @Path("getByEmail")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Usuario getUserByEmailJson(
            @HeaderParam("email") String email,
            @HeaderParam("authorization") String authorization) {
        if (authorization != null && authorization.equals("secret")) {
            Usuario user = EManager.getInstance().getDbAccessor().getUserByEmail(email);
            if (user != null) {
                user.getIdOrganizacao().setUsuarioCollection(null);
                user.getIdOrganizacao().setSalaCollection(null);
                user.setSenha(null);
                return user;
            }
        } else {
            return null;
        }
        return null;
    }

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response authentication(
            @HeaderParam("email") String email,
            @HeaderParam("password") String password,
            @HeaderParam("authorization") String authorization) {
        if (authorization != null && authorization.equals("secret")) {
            Usuario user = EManager.getInstance().getDbAccessor().getCredencials(email, password);
            if (user != null) {
                user.getIdOrganizacao().setUsuarioCollection(null);
                user.getIdOrganizacao().setSalaCollection(null);
//                user.getIdOrganizacao().setDataAlteracao(null);
//                user.getIdOrganizacao().setDataCriacao(null);
                user.getIdOrganizacao().setAtivo(null);
                user.setSenha(null);
                return Response.ok(user).build();
            } else {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new Status("Usuário não encontrado"))
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } else {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(new Status("Request inválido"))
                    .build();
        }
    }
    
    @GET
    @Path("loginV2")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Usuario authenticationV2(
            @HeaderParam("email") String email,
            @HeaderParam("password") String password,
            @HeaderParam("authorization") String authorization) {
        if (authorization != null && authorization.equals("secret")) {
            Usuario user = EManager.getInstance().getDbAccessor().getCredencials(email, password);
            if (user != null) {
                user.getIdOrganizacao().setUsuarioCollection(null);
                user.getIdOrganizacao().setSalaCollection(null);
                user.setSenha(null);
                return user;
            }
        } else {
            return null;
        }
        return null;
    }

    @POST
    @Path("cadastro")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String cadastrarUsuario(@HeaderParam("authorization") String authorization,
            @HeaderParam("novoUsuario") String novoUsuarioEncoded) {
        if (authorization != null && authorization.equals("secret")) {
            try {
                //String userEncodedOk = "ewogICAgImVtYWlsIjogInJvZHJpZ28ucXVpc2VuQHdpc2VzLmNvbS5iciIsCiAgICAibm9tZSI6ICJSb2RyaWdvIFF1aXNlbiAzIiwKICAgICJzZW5oYSI6ICIxMjMiCn0=";
                //String userEncodedNotOk = "ewogICAgImVtYWlsIjogInJvZHJpZ28ucXVpc2VuQHdpc2UuY29tLmJyIiwKICAgICJub21lIjogIlJvZHJpZ28gUXVpc2VuIDUiLAogICAgInNlbmhhIjogIjEyMyIKfQ==";
                String userDecoded = new String(Base64.getDecoder().decode(novoUsuarioEncoded.getBytes()), Charset.forName("UTF-8"));

                JSONObject userObj = new JSONObject(userDecoded);
                Usuario novoUsuario = new Usuario();
                String email, nome, senha;
                String dominio = null;
                int idOrganizacao = 0;

                if (userObj.has("email") && userObj.has("nome") && userObj.has("senha") && userObj.has("idOrganizacao")) {
                    email = userObj.getString("email");
                    nome = userObj.getString("nome");
                    senha = userObj.getString("senha");
                    idOrganizacao = userObj.getInt("idOrganizacao");

                    if (email.isEmpty() || nome.isEmpty() || senha.isEmpty() || idOrganizacao == 0) {
                        return "Erro ao criar conta, os dados enviados estão incompletos";
                    } else if (email.contains("@")) {
                        dominio = email.split("@")[1];
                    }
                } else {
                    return "Erro ao criar conta, os dados enviados estão incompletos";
                }

                if (EManager.getInstance().getDbAccessor().getUserByEmail(email) != null) {
                    return "O email informado já está cadastrado";
                }

                Organizacao organizacao = new Organizacao();
                try {
                    organizacao = EManager.getInstance().getDbAccessor().getOrganizacaoById(idOrganizacao);
                    if (organizacao == null) {
                        return "Erro ao cadastrar usuário, a organização informada não existe";
                    }
                } catch (Exception e) {
                    return "Erro ao criar conta, os dados enviados estão incompletos";
                }

                novoUsuario.setEmail(email);
                novoUsuario.setNome(nome);
                novoUsuario.setSenha(senha);
                novoUsuario.setIdOrganizacao(organizacao);

                EManager.getInstance().getDbAccessor().novoUsuario(novoUsuario);

                return "Usuário criado com sucesso";
            } catch (Exception e) {
                e.printStackTrace();
                return "Erro ao criar usuário";
            }

        } else {
            return "Token inválido";
        }
    }
}

// Esse aqui fica só de exemplo pra mostrar como faz pra pegar o parâmetro pelo path do request
//    @GET
//    @Path("/{email}")
//    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    public Usuario getUserJson(
//            @PathParam("email") String email,
//            @HeaderParam("authorization") String authorization) {
//        if (authorization != null && authorization.equals("secret")) {
//            Usuario user = EManager.getInstance().getDbAccessor().getUserByEmail(email);
//            if (user != null) {
//                user.getIdOrganizacao().setUsuarioCollection(null);
//                user.getIdOrganizacao().setSalaCollection(null);
//                user.setSenha(null);
//                return user;
//            }
//        } else {
//            return null;
//        }
//        return null;
//    }
