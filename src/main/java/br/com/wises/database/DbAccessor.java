package br.com.wises.database;

import br.com.wises.database.pojo.Reserva;
import br.com.wises.database.pojo.Organizacao;
import br.com.wises.database.pojo.Sala;
import br.com.wises.database.pojo.Usuario;
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
            return (Usuario) EManager.getInstance().createNamedQuery("Usuario.findByEmail").setParameter("email", email).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static Usuario getUserById(int id) {
        try {
            return (Usuario) EManager.getInstance().createNamedQuery("Usuario.findById").setParameter("id", id).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static Usuario getCredencials(String email, String senha) {
        try {
            return (Usuario) EManager.getInstance().createNamedQuery("Usuario.findByEmailAndPassword").setParameter("email", email).setParameter("senha", senha).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static synchronized void novoUsuario(Usuario usuario) {
        try {
            EManager.getInstance().getTransaction().begin();
            EManager.getInstance().persist(usuario);
            EManager.getInstance().getTransaction().commit();
        } catch (Exception e) {
            if (EManager.getInstance().getTransaction().isActive()) {
                EManager.getInstance().getTransaction().rollback();
            }
        }
    }

    //--------------- ORGANIZAÇÃO ---------------//
    public static Organizacao getOrganizacaoById(int id) {
        try {
            return (Organizacao) EManager.getInstance().createNamedQuery("Organizacao.findById").setParameter("id", id).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static Organizacao getOrganizacaoByDominio(String dominio) {
        try {
            return (Organizacao) EManager.getInstance().createNamedQuery("Organizacao.findDominioLike").setParameter("dominio", dominio).setHint(QueryHints.REFRESH, HintValues.TRUE).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static List<Organizacao> getOrganizacoesByDominio(String dominio) {
        try {
            return EManager.getInstance().createNamedQuery("Organizacao.findDominioLike").setParameter("dominio", dominio).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static List<Sala> getSalasByOrganizacaoId(int id) {
        try {
            return EManager.getInstance().createNamedQuery("Sala.findByOrganizacaoId").setParameter("idOrganizacao", id).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    //--------------- RESERVA ---------------//
    public static List<Reserva> getReservasByIdSala(int idSala) {
        try {
            return EManager.getInstance().createNamedQuery("Reserva.findByIdSala").setParameter("idSala", idSala).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static List<Reserva> getReservasByIdUsuario(int idUsuario) {
        try {
            return EManager.getInstance().createNamedQuery("Reserva.findByIdUsuario").setParameter("idUsuario", idUsuario).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static synchronized void novaReserva(Reserva reserva) {
        try {
            EManager.getInstance().getTransaction().begin();
            EManager.getInstance().persist(reserva);
            EManager.getInstance().getTransaction().commit();
        } catch (Exception e) {
            if (EManager.getInstance().getTransaction().isActive()) {
                EManager.getInstance().getTransaction().rollback();
            }
        }
    }

    public static synchronized void modificaReserva(Reserva reserva) {
        try {
            EManager.getInstance().getTransaction().begin();
            EManager.getInstance().merge(reserva);
            EManager.getInstance().getTransaction().commit();
        } catch (Exception e) {
            if (EManager.getInstance().getTransaction().isActive()) {
                EManager.getInstance().getTransaction().rollback();
            }
        }
    }
}
