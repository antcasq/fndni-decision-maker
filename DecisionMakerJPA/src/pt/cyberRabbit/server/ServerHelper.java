package pt.cyberRabbit.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ServerHelper {

	private static final ThreadLocal<ServerHelper> instance = new ThreadLocal<ServerHelper>();

	private EntityManagerFactory emf = null;
	private EntityManager entityManager = null;
	private boolean automaticTransactionManagement;

	private ServerHelper(boolean automaticTransactionManagement) {
		super();
		this.emf = Persistence.createEntityManagerFactory("DecisionMakerJPA");
		this.entityManager = emf.createEntityManager();
		this.automaticTransactionManagement = automaticTransactionManagement;
		if (automaticTransactionManagement) {
			// Start the transaction
			entityManager.getTransaction().begin();
		}
	}

	public static void init(boolean automaticTransactionManagement) {
		if (instance.get() != null) {
			throw new IllegalStateException("ServiceHelper already initialized");
		}
		instance.set(new ServerHelper(automaticTransactionManagement));
	}

	public static ServerHelper getInstance() {
		if (instance.get() == null) {
			throw new IllegalStateException("ServiceHelper not initialized");
		}

		return instance.get();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	private void closeEntityManager() {
		if (entityManager != null && entityManager.isOpen()) {
			if (automaticTransactionManagement) {
				// Manage transaction
				if (entityManager.getTransaction().getRollbackOnly()) {
					entityManager.getTransaction().rollback();
				} else {
					entityManager.getTransaction().commit();
				}
			}
			entityManager.close();
		}
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}

	public void releaseResources() {
		closeEntityManager();
		if (instance.get() != null) {
			instance.set(null);
		}
	}

}
