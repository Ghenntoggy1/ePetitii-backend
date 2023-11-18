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
            "WITH RankedPetitions AS (" +
                    "    SELECT  " +
                    "        p.*, " +
                    "        ROW_NUMBER() OVER (ORDER BY p.date) AS petition_rank " +
                    "    FROM " +
                    "        Petitions p " +
                    "        INNER JOIN Category_Petition cp ON p.petition_id = cp.petition_id " +
                    "        INNER JOIN Categories c ON cp.category_id = c.category_id " +
                    "    WHERE " +
                    "        c.category_id IN (?1)  " +
                    ")" +
                    "SELECT DISTINCT " +
                    "    * " +
                    "FROM " +
                    "    RankedPetitions " +
                    "WHERE " +
                    "    petition_rank <= 5 " +
                    "ORDER BY " +
                    "    date;", nativeQuery = true)
    List<Petition> findTop3PetitionsByCategoryIds(List<Integer> categoryIds);
}
