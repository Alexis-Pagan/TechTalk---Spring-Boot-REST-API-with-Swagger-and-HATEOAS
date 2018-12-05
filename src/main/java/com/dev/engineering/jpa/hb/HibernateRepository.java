package com.dev.engineering.jpa.hb;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dev.engineering.entities.DaoUser;

@Repository
public interface HibernateRepository extends CrudRepository<DaoUser, Serializable>, HibernateRepositoryCustom {

}
