package br.com.wises.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EManager implements java.io.Serializable {

    // Para inicializar corertamente o Emanager, na hora de configurar a PU
    // colocar ?useTimezone=true&serverTimezone=UTC na conexão
    private static final Object emLock = new Object();
    private static EManager instance = null;

    private static EntityManager em = null;

    private static final Object operationLock = new Object();

    private final DbAccessor dbAccessor;

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReservaDeSalasPU");

    public EManager() {
        this.em = Persistence.createEntityManagerFactory("ReservaDeSalasPU").createEntityManager();
        this.dbAccessor = new DbAccessor(this.em, this.operationLock);
    }

    public static EManager getInstance() {
        if (instance == null) {
            synchronized (emLock) {
                if (instance == null) {
                    instance = new EManager();
                }
            }
        }
        return instance;
    }

    public DbAccessor getDbAccessor() {
        return dbAccessor;
    }

}
