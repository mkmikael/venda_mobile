package blacksoftware.webvenda.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaProduces {

	@Produces @ApplicationScoped
	public EntityManagerFactory development() {
		return Persistence.createEntityManagerFactory("development");
	}
	
	public void closeDevelopment(@Disposes EntityManagerFactory factory) {
		factory.close();
	}
	
	@Produces @RequestScoped
	public EntityManager createEntityManager(EntityManagerFactory factory) {
		System.out.println("CREATE ENTITY MANAGER");
		return factory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager em) {
		System.out.println("CLOSE ENTITY MANAGER");
		em.close();
	}
}
