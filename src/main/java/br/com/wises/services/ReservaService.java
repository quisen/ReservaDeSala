package br.com.wises.services;

import br.com.wises.database.DbAccessor;
import br.com.wises.database.pojo.Reserva;
import br.com.wises.database.pojo.Usuario;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;

@Path("reserva")
public class ReservaService {

    @GET
    @Path("byIdSala")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Reserva> getReservasByIdSala(
            @HeaderParam("id_sala") int idSala,
            @HeaderParam("authorization") String authorization) {
        if (authorization != null && authorization.equals("secret")) {
            List<Reserva> lista = DbAccessor.getReservasByIdSala(idSala);
            return lista;
        } else {
            return null;
        }
    }

    @GET
    @Path("byIdUsuario")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public List<Reserva> getReservasByIdUsuario(
            @HeaderParam("id_usuario") int idUsuario,
            @HeaderParam("authorization") String authorization) {
        if (authorization != null && authorization.equals("secret")) {
            List<Reserva> lista = DbAccessor.getReservasByIdUsuario(idUsuario);
            return lista;
        } else {
            return null;
        }
    }

    @POST
    @Path("cadastrar")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String createReserva(
            @HeaderParam("novaReserva") String novaReservaEncoded,
            @HeaderParam("authorization") String authorization) {
        try {
            if (authorization != null && authorization.equals("secret")) {
                String reservaDecoded = new String(Base64.getDecoder().decode(novaReservaEncoded.getBytes()), Charset.forName("UTF-8"));
                JSONObject userObj = new JSONObject(reservaDecoded);
                Reserva novaReserva = new Reserva();
                System.out.println("Reserva Encoded: " + novaReservaEncoded);
                System.out.println("Reserva Decoded: " + reservaDecoded);
                int idSala = 0, idUsuario = 0;
                String descricao = "";
                Date dataHoraInicio = null, dataHoraFim = null;
                if (userObj.has("id_sala") && userObj.has("id_usuario") && userObj.has("descricao") && userObj.has("data_hora_inicio") && userObj.has("data_hora_fim") && userObj.has("ativo")) {
                    idSala = userObj.getInt("id_sala");
                    idUsuario = userObj.getInt("id_usuario");
                    descricao = userObj.getString("descricao");
                    dataHoraInicio = new Date(userObj.getLong("data_hora_inicio"));
                    dataHoraFim = new Date(userObj.getLong("data_hora_fim"));

                    if (DbAccessor.isReservaDisponivel(idSala, dataHoraInicio, dataHoraFim)) {
                        Usuario user = DbAccessor.getUserById(idUsuario);
                        novaReserva.setIdUsuario(user.getId());
                        novaReserva.setNomeOrganizador(user.getNome());
                        novaReserva.setIdSala(idSala);
                        novaReserva.setDescricao(descricao);
                        novaReserva.setDataHoraInicio(dataHoraInicio);
                        novaReserva.setDataHoraFim(dataHoraFim);
                        novaReserva.setAtivo(true);

                        Date date = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        c.add(c.HOUR, -3);
                        date = c.getTime();

                        novaReserva.setDataCriacao(date);
                        novaReserva.setDataAlteracao(date);

                        DbAccessor.novaReserva(novaReserva);
                        return "Reserva realizada com sucesso";
                    } else {
                        return "A sala já está reservada para o horário selecionado";
                    }
                } else {
                    return "A reserva não foi realizada";
                }
            } else {
                return "A reserva não foi realizada";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "A reserva não foi realizada";
        }
    }
}
