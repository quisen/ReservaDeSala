package br.com.wises.database;

import br.com.wises.database.pojo.Usuario;
import java.util.List;
import javax.persistence.EntityManager;

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
        return (Usuario) this.manager.createNamedQuery("Usuario.findById").setParameter("id", id).getSingleResult();
    }

    public List<Usuario> getAllOrganizacoes() {
        return this.manager.createNamedQuery("Organizacao.findAll").getResultList();
    }

    public List<Usuario> getAllSalas() {
        return this.manager.createNamedQuery("Sala.findAll").getResultList();
    }

    public List<Usuario> getAllAlocacaoSalas() {
        return this.manager.createNamedQuery("AlocacaoSala.findAll").getResultList();
    }

//    public void novoUsuario(Usuario usuario) {
//        synchronized (this.operationLock) {
//            this.manager.getTransaction().begin();
//            this.manager.persist(usuario);
//            this.manager.getTransaction().commit();
//        }
//    }
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
