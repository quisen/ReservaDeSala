package br.com.wises.database;

import br.com.wises.database.pojo.Reserva;
import br.com.wises.database.pojo.Organizacao;
import br.com.wises.database.pojo.Sala;
import br.com.wises.database.pojo.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class DbAccessor {

    private final EntityManager manager;
    private final Object operationLock;

    public DbAccessor(EntityManager manager, Object operationLock) {
        this.manager = manager;
        this.operationLock = operationLock;
    }

    public Usuario getUserByEmail(String email) {
        try {
            return (Usuario) this.manager.createNamedQuery("Usuario.findByEmail").setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Usuario getCredencials(String email, String senha) {
        try {
            return (Usuario) this.manager.createNamedQuery("Usuario.findByEmailAndPassword").setParameter("email", email).setParameter("senha", senha).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Organizacao getOrganizacaoById(int id) {
        try {
            return (Organizacao) this.manager.createNamedQuery("Organizacao.findById").setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Organizacao getOrganizacaoByDominio(String dominio) {
        try {
            return (Organizacao) this.manager.createNamedQuery("Organizacao.findDominioLike").setParameter("dominio", dominio).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Organizacao> getOrganizacoesByDominio(String dominio) {
        try {
            return this.manager.createNamedQuery("Organizacao.findDominioLike").setParameter("dominio", dominio).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Sala> getSalasByOrganizacaoId(int id) {
        try {
            return this.manager.createNamedQuery("Sala.findByOrganizacaoId").setParameter("idOrganizacao", id).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Reserva> getReservasByIdSala(int idSala) {
        try {
            return this.manager.createNamedQuery("Reserva.findByIdSala").setParameter("idSala", idSala).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Reserva> getReservasByIdUsuario(int idUsuario) {
        try {
            return this.manager.createNamedQuery("Reserva.findByIdUsuario").setParameter("idUsuario", idUsuario).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void novaReserva(Reserva reserva) {
        synchronized (this.operationLock) {
            this.manager.getTransaction().begin();
            this.manager.persist(reserva);
            this.manager.getTransaction().commit();
        }
    }

    public void novoUsuario(Usuario usuario) {
        synchronized (this.operationLock) {
            this.manager.getTransaction().begin();
            this.manager.persist(usuario);
            this.manager.getTransaction().commit();
        }
    }
//
//    public void modificaUsuario(Usuario usuario) {
//        synchronized (this.operationLock) {
//            this.manager.getTransaction().begin();
//            this.manager.merge(usuario);
//            this.manager.getTransaction().commit();
//        }
//    }
//
//    public void excluirUsuario(Usuario usuario) {
//        synchronized (this.operationLock) {
//            this.manager.getTransaction().begin();
//            this.manager.remove(usuario);
//            this.manager.getTransaction().commit();
//        }
//    }
}
