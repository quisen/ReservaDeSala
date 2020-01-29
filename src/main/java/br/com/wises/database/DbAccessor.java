package br.com.wises.database;

import br.com.wises.database.pojo.AlocacaoSala;
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

    public List<Usuario> getAllUsuarios() {
        return this.manager.createNamedQuery("Usuario.findAll").getResultList();
    }

    public Usuario getUserById(int id) {
        try {
            return (Usuario) this.manager.createNamedQuery("Usuario.findById").setParameter("id", id).getSingleResult();
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

    public List<Organizacao> getAllOrganizacoes() {
        return this.manager.createNamedQuery("Organizacao.findAll").getResultList();
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

    public List<Sala> getAllSalas() {
        return this.manager.createNamedQuery("Sala.findAll").getResultList();
    }

    public List<Sala> getSalasByOrganizacaoId(int id) {
        try {
            return this.manager.createNamedQuery("Sala.findByOrganizacaoId").setParameter("id_organicacao", id).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<AlocacaoSala> getAllAlocacaoSalas() {
        return this.manager.createNamedQuery("AlocacaoSala.findAll").getResultList();
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
