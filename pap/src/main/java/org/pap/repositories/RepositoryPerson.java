package org.pap.repositories;

import org.pap.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPerson extends JpaRepository<Person, Long>{
	
}
