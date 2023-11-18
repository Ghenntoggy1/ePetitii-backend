package org.example.Petition;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Integer> {
    List<Petition> findAll(Specification<Petition> specification);

    @Query(value =
            "SELECT DISTINCT TOP 3 p.* " +
                    "FROM Petitions p " +
                    "JOIN Category_Petition cp ON p.petition_id = cp.petition_id " +
                    "JOIN Categories c ON cp.category_id = c.category_id " +
                    "WHERE c.category_id IN :categoryIds " +
                    "ORDER BY p.date DESC", nativeQuery = true)
    List<Petition> findTop3PetitionsByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);
}
