package com.dev.engineering.jpa.hb;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.dev.engineering.entities.DaoUser;

@Transactional
@Repository
public class HibernateRepositoryImpl implements HibernateRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void createUser(DaoUser user) {
		try {

			//			EntityManager entityManager = em.createEntityManager();

			//			entityManager.getTransaction().begin();

			em.persist(user);

			//			entityManager.getTransaction().commit();

			//			entityManager.close();
			em.close();

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public boolean existByEmployeeNumber(int number) {

		List<DaoUser> profile = new ArrayList<DaoUser>();

		CriteriaQuery<DaoUser> queryProfile;

		try {

			CriteriaBuilder builder = em.getCriteriaBuilder();	

			queryProfile = builder.createQuery(DaoUser.class);

			Root<DaoUser> profiles = queryProfile.from(DaoUser.class);

			queryProfile.where(builder.equal(profiles.get("employeeNumber"), number));

			profile = em.createQuery(queryProfile).getResultList();

			em.close();

			if(!profile.isEmpty()) {
				return true;
			}
		} catch(Exception ex) {
			em.close();
			ex.printStackTrace();
		}

		return false;
	}

	@Override
	public void updateExistingEmployee(DaoUser user, String employeeNumber) {

		CriteriaUpdate<DaoUser> queryProfile;

		try {

			CriteriaBuilder builder = em.getCriteriaBuilder();	

			queryProfile = builder.createCriteriaUpdate(DaoUser.class); // lo que quiero que devuelva

			Root<DaoUser> profiles = queryProfile.from(DaoUser.class);

			queryProfile.set("firstName", user.getFirstName());

			queryProfile.set("lastName", user.getLastName());

			queryProfile.set("middleName", user.getMiddleName());

			queryProfile.set("employeeNumber", user.getEmployeeNumber());

			queryProfile.where(builder.equal(profiles.get("employeeNumber"), employeeNumber));

			em.createQuery(queryProfile).executeUpdate();

			em.close();

		} catch(Exception exception) {

			em.close();
			exception.printStackTrace();

		} finally {
			System.out.println("database persisted.");
		}
	}

	@Override
	public void deleteExistingEmployee(int number) {

		try {

			CriteriaBuilder builder = em.getCriteriaBuilder();	

			CriteriaDelete<DaoUser> delete = builder.createCriteriaDelete(DaoUser.class);

			Root<DaoUser> entity = delete.from(DaoUser.class);

			delete.where(builder.equal(entity.get("employeeNumber"), number));

			em.createQuery(delete).executeUpdate();

			em.close();

		} catch(Exception exception) {

			em.close();

			exception.printStackTrace();

		} finally {

			System.out.println("hibernate done.");
		}

	}
}
