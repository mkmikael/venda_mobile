package blacksoftware.webvenda.dao;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import blacksoftware.webvenda.model.Canal;

public class DaoFactory {
	
	@Produces
	public DAO<Canal> createDAO(InjectionPoint injectionPoint) {
		return new DAO<Canal>();
	}
}
