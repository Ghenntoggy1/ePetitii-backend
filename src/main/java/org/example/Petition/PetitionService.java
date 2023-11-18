package org.example.Petition;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.example.CustomException.DuplicateSignException;
import org.example.CustomException.NotFoundException;
import org.example.category.Categories;
import org.example.user.User;
import org.example.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class PetitionService {
    private final PetitionRepository petitionRepository;
    private final UserRepository userRepository;

    @Autowired
    public PetitionService(PetitionRepository petitionRepository, UserRepository userRepository) {
        this.petitionRepository = petitionRepository;
        this.userRepository = userRepository;
    }
    public static Specification<Petition> hasCategoryIds(List<Integer> categoryIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Petition, Categories> categories = root.join(Petition_.categories, JoinType.INNER);

            return categories.get("category_id").in(categoryIds);
        };
    }

    public static Specification<Petition> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Petition_.status), status);
    }

    public static Specification<Petition> hasRegionId(Integer regionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Petition_.location).get("region").get("location_id"), regionId);
    }

    public static Specification<Petition> hasSearchTerm(String searchTerm) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get(Petition_.name), "%" + searchTerm + "%"),
                        criteriaBuilder.like(root.get(Petition_.description), "%" + searchTerm + "%")
                );
    }

    public static Specification<Petition> buildSpecification(List<Integer> categoryIds, String status, Integer regionId, String searchTerm) {
        Specification<Petition> spec = Specification.where(null);

        if (categoryIds != null && !categoryIds.isEmpty()) {
            spec = spec.and(hasCategoryIds(categoryIds));
        }

        if (status != null) {
            spec = spec.and(hasStatus(status));
        }

        if (regionId != null) {
            spec = spec.and(hasRegionId(regionId));
        }

        if (searchTerm != null) {
            spec = spec.and(hasSearchTerm(searchTerm));
        }

        return spec;
    }
    public List<Petition> getPetitionsWithFilter(List<Integer> categoryIds, String status, Integer regionId, String searchTerm) {
        Specification<Petition> spec = buildSpecification(categoryIds, status, regionId, searchTerm);
        return petitionRepository.findAll(spec);
    }
    public List<Petition> getAllPetitions() {
        return petitionRepository.findAll();
    }
    public Optional<Petition> getPetitionById(Integer petitionId) {
        return petitionRepository.findById(petitionId);
    }
    public boolean signPetition(Integer petitionId, String userIcnp) throws DuplicateSignException, NotFoundException {
        Optional<Petition> optionalPetition = petitionRepository.findById(petitionId);
        Optional<User> optionalUser = userRepository.findByIdnp(userIcnp);

        if (optionalPetition.isPresent() && optionalUser.isPresent()) {
            Petition petition = optionalPetition.get();
            User user = optionalUser.get();

            // Check if the user hasn't already signed the petition
            if (!petition.getSigners().contains(user)) {
                petition.getSigners().add(user);
                petition.setCurrSigns(petition.getCurrSigns() + 1);
                petitionRepository.save(petition);
                return true;
            } else {
                throw new DuplicateSignException("User has already signed this petition");
            }
        } else {
            throw new NotFoundException("Petition or User not found");
        }
    }
}
