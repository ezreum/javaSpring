package org.pap.repositories;


import org.pap.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryHobby extends JpaRepository<Hobby, Long>{

}
