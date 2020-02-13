package br.com.wises.database;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EManager implements java.io.Serializable {

    // Para inicializar corertamente o Emanager, na hora de configurar a PU
    // colocar ?useTimezone=true&serverTimezone=UTC na conexão
    private static EManager instance = null;
    private static EntityManager em = null;

    public EManager() {
    }

    public static EntityManager getInstance() {
        if (em == null) {
            em = Persistence.createEntityManagerFactory("ReservaDeSalasPU").createEntityManager();
        }

        return em;
    }

}
