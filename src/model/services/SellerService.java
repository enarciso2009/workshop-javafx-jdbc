package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	private SellerDao dao = DaoFactory.createSellerDao();
	
	public List<Seller> findAll() {  // criado esse metodo para popular a tabela do programa grafico para testes
	return dao.findAll();
	}

	public void saveOrUpdate(Seller obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
			
		}
	      dao.update(obj);
	}
	
	public void remove(Seller obj) {  // remover um Vendedor do banco de dados 
		dao.deleteById(obj.getId());
	}
}
