package org.pap.repositories;

import java.util.List;

import org.pap.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPerson extends JpaRepository<Person, Long>{
	//find person by nick
	public Person findByNick(String nick);
	
	//public List<Person> findByNameOrderByNameDesc(String f);
	
}
