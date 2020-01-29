package br.com.wises.services;

import br.com.wises.database.EManager;
import br.com.wises.database.pojo.Organizacao;
import br.com.wises.database.pojo.Usuario;
import java.util.Base64;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

@Path("usuario")
public class UsuarioService {

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Usuario getUserJson(
            @PathParam("email") String email,
            @HeaderParam("authorization") String authorization) {
        if (authorization != null && authorization.equals("secret")) {
            Usuario user = EManager.getInstance().getDbAccessor().getUserByEmail(email);
            if (user != null) {
                user.getIdOrganizacao().setUsuarioCollection(null);
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
    public String authentication(
            @HeaderParam("email") String email,
            @HeaderParam("password") String password,
            @HeaderParam("authorization") String authorization) {
        if (authorization != null && authorization.equals("secret")) {
            Usuario user = EManager.getInstance().getDbAccessor().getCredencials(email, password);
            if (user != null) {
                return "Login efetuado com sucesso!";
            } else {
                return "Credenciais Inválidas!";
            }
        } else {
            return "Token Inválido";
        }
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
                String userDecoded = new String(Base64.getDecoder().decode(novoUsuarioEncoded.getBytes()));

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
                
                if(EManager.getInstance().getDbAccessor().getUserByEmail(email) != null){
                return "O email informado já está cadastrado";
                }
                
                Organizacao organizacao = new Organizacao();
                try {
                    organizacao = EManager.getInstance().getDbAccessor().getOrganizacaoById(idOrganizacao);
                    if(organizacao == null){
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
