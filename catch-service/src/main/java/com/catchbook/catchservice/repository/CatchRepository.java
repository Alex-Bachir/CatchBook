package com.catchbook.catchservice.repository;

import com.catchbook.catchservice.model.Catch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatchRepository extends JpaRepository<Catch, Long> {

}
