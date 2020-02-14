package br.com.wises.database;

import br.com.wises.database.pojo.Reserva;
import br.com.wises.database.pojo.Organizacao;
import br.com.wises.database.pojo.Sala;
import br.com.wises.database.pojo.Usuario;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

public class DbAccessor {

    public DbAccessor() {

    }

    //--------------- USUÁRIO ---------------//
    public static Usuario getUserByEmail(String email) {
        try {
            Usuario u = (Usuario) EManager.getInstance().createNamedQuery("Usuario.findByEmail").setParameter("email", email).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
            clear();
            return u;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    public static Usuario getUserById(int id) {
        try {
            Usuario u = (Usuario) EManager.getInstance().createNamedQuery("Usuario.findById").setParameter("id", id).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
            clear();
            return u;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    public static Usuario getCredencials(String email, String senha) {
        try {
            Usuario u = (Usuario) EManager.getInstance().createNamedQuery("Usuario.findByEmailAndPassword").setParameter("email", email).setParameter("senha", senha).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
            clear();
            return u;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    public static synchronized void novoUsuario(Usuario usuario) {
        try {
            EManager.getInstance().getTransaction().begin();
            EManager.getInstance().persist(usuario);
            EManager.getInstance().getTransaction().commit();
            clear();
        } catch (Exception e) {
            if (EManager.getInstance().getTransaction().isActive()) {
                EManager.getInstance().getTransaction().rollback();
                clear();
            }
        }
    }

    //--------------- ORGANIZAÇÃO ---------------//
    public static Organizacao getOrganizacaoById(int id) {
        try {
            Organizacao o = (Organizacao) EManager.getInstance().createNamedQuery("Organizacao.findById").setParameter("id", id).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
            clear();
            return o;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    public static Organizacao getOrganizacaoByDominio(String dominio) {
        try {
            Organizacao o = (Organizacao) EManager.getInstance().createNamedQuery("Organizacao.findDominioLike").setParameter("dominio", dominio).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
            clear();
            return o;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    public static List<Organizacao> getOrganizacoesByDominio(String dominio) {
        try {
            List<Organizacao> l = EManager.getInstance().createNamedQuery("Organizacao.findDominioLike").setParameter("dominio", dominio).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
            clear();
            return l;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    public static List<Sala> getSalasByOrganizacaoId(int id) {
        try {
            List<Sala> l = EManager.getInstance().createNamedQuery("Sala.findByOrganizacaoId").setParameter("idOrganizacao", id).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
            clear();
            return l;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    //--------------- RESERVA ---------------//
    public static Reserva getReservaById(int id) {
        try {
            Reserva r = (Reserva) EManager.getInstance().createNamedQuery("Reserva.findById").setParameter("id", id).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
            clear();
            return r;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    public static List<Reserva> getReservasByIdSala(int idSala) {
        try {
            List<Reserva> l = EManager.getInstance().createNamedQuery("Reserva.findByIdSala").setParameter("idSala", idSala).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
            clear();
            return l;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    public static boolean isReservaDisponivel(int idSala, Date dataHoraInicio, Date dataHoraFim) {
        try {
            List<Reserva> l = EManager.getInstance().createNamedQuery("Reserva.findDisponibilidade").setParameter("idSala", idSala).setParameter("dataHoraInicio", dataHoraInicio).setParameter("dataHoraFim", dataHoraFim).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
            System.out.println("Tamanho da lista retornada: " + l.size());
            clear();
            return !(l.size() > 0);
        } catch (Exception e) {
            clear();
            e.printStackTrace();
            return false;
        }
    }

    public static List<Reserva> getReservasByIdUsuario(int idUsuario) {
        try {
            List<Reserva> l = EManager.getInstance().createNamedQuery("Reserva.findByIdUsuario").setParameter("idUsuario", idUsuario).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
            clear();
            return l;
        } catch (NoResultException e) {
            clear();
            return null;
        }
    }

    public static synchronized void novaReserva(Reserva reserva) {
        try {
            EManager.getInstance().getTransaction().begin();
            EManager.getInstance().persist(reserva);
            EManager.getInstance().getTransaction().commit();
            clear();
        } catch (Exception e) {
            if (EManager.getInstance().getTransaction().isActive()) {
                EManager.getInstance().getTransaction().rollback();
                clear();
            }
        }
    }

    public static synchronized void modificaReserva(Reserva reserva) {
        try {
            EManager.getInstance().getTransaction().begin();
            EManager.getInstance().merge(reserva);
            EManager.getInstance().getTransaction().commit();
            clear();
        } catch (Exception e) {
            if (EManager.getInstance().getTransaction().isActive()) {
                EManager.getInstance().getTransaction().rollback();
                clear();
            }
        }
    }

    public static void clear() {
        EManager.getInstance().clear();
    }
}
