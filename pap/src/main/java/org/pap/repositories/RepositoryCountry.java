package org.pap.repositories;

import org.pap.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RepositoryCountry extends JpaRepository<Country, Long>{
	
	
	
}
