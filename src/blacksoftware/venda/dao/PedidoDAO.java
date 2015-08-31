package blacksoftware.venda.dao;

import blacksoftware.venda.config.DatabaseOrm;
import blacksoftware.venda.models.Pedido;

public class PedidoDAO extends GenericDAO<Pedido> {

	private static final long serialVersionUID = 2767186792200120954L;

	public PedidoDAO(DatabaseOrm db) {
		super(db, Pedido.class);
	}
	
	public PedidoDAO(DatabaseOrm db, Class<Pedido> clazz) {
		super(db, clazz);
	}

	
}
