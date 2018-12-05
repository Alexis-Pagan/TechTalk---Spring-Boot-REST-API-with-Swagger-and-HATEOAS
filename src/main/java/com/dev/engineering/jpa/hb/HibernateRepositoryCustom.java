package com.dev.engineering.jpa.hb;

import com.dev.engineering.entities.DaoUser;

public interface HibernateRepositoryCustom {
	
	public void createUser(DaoUser user);
	
	public boolean existByEmployeeNumber(int number);
	
	public void updateExistingEmployee(DaoUser user, String employeeNumber);

	public void deleteExistingEmployee(int number);
}
