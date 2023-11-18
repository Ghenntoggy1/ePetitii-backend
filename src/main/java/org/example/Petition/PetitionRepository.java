package org.example.Petition;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Integer> {
    List<Petition> findAll(Specification<Petition> specification);

    @Query("")
    List<Petition> findTopByPopularity();
}
