package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Department> findAll() {  // criado esse metodo para popular a tabela do programa grafico para testes
	return dao.findAll();
	}

	public void saveOrUpdate(Department obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
			
		}
	      dao.update(obj);
	}
	
	public void remove(Department obj) {  // remover um departamento do banco de dados 
		dao.deleteById(obj.getId());
	}
}
