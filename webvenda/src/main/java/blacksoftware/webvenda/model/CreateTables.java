package blacksoftware.webvenda.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateTables {
	public static void main(String[] args) {
		EntityManagerFactory factory = null;
		try {
			factory = Persistence.createEntityManagerFactory("development");
			System.out.println("CREATED TABLES");
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		} finally {
			if (factory != null) factory.close();
		}
	}
}
