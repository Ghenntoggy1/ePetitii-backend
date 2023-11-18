package org.example.Petition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Integer> {
    @Query("SELECT p FROM Petition p JOIN FETCH p.signers JOIN FETCH p.categories")
    List<Petition> getAllPetitions();
}
