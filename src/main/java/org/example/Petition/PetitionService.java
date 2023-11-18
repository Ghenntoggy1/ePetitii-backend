package org.example.Petition;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.example.CustomException.DuplicateSignException;
import org.example.CustomException.NotFoundException;
import org.example.Location.LocationRepository;
import org.example.category.Categories;
import org.example.category.CategoryRepository;
import org.example.deepL.TranslatorClass;
import org.example.user.User;
import org.example.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.example.Receiver.ReceiverRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.*;

@Service
public class PetitionService {
    private final PetitionRepository petitionRepository;
    private final UserRepository userRepository;
    private final ReceiverRepository receiverRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public PetitionService(PetitionRepository petitionRepository, UserRepository userRepository, ReceiverRepository receiverRepository
    , CategoryRepository categoryRepository, LocationRepository locationRepository) {
        this.petitionRepository = petitionRepository;
        this.userRepository = userRepository;
        this.receiverRepository = receiverRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
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
    public PetitionDTO getPetitionById(Integer petitionId, String locale) throws Exception {
        Petition petition =  petitionRepository.findById(petitionId).get();
        TranslatorClass translatorClass = new TranslatorClass();
        PetitionDTO petitionDTO = PetitionDTO.mapPetitionToDTO(petition);
        petitionDTO.setName(translatorClass.translate(petitionDTO.getName(), locale).getText());
        petitionDTO.setDescription(translatorClass.translate(petitionDTO.getDescription(), locale).getText());
        return petitionDTO;
    }
    public boolean signPetition(Integer petitionId, String userIdnp) throws DuplicateSignException, NotFoundException {
        Optional<Petition> optionalPetition = petitionRepository.findById(petitionId);
        Optional<User> optionalUser = userRepository.findByIdnp(userIdnp);

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

    public Integer createPetition(PetitionCreateRequest petitionCreateRequest){
        Petition petition = new Petition();
        petition.setInitiator(userRepository.findByIdnp(petitionCreateRequest.getInitiator_idnp()).get());
        petition.setName(petitionCreateRequest.getName());
        petition.setDescription(petitionCreateRequest.getDescription());
        petition.setReceiver(receiverRepository.findById(petitionCreateRequest.getReceiver()).get());
        Set<Categories> categories = new HashSet<>();
        for(Integer id : petitionCreateRequest.getCategories()){
            categories.add(categoryRepository.findById(id).get());
        }
        petition.setCategories(categories);
        petition.setCurrSigns(1);
        petition.setNeededSigns(100);
        petition.setStatus("pending_review");
        petition.setLocation(locationRepository.findById(petitionCreateRequest.getRegion()).get());
        LocalDate currentDate = LocalDate.now();
        petition.setDate(java.sql.Date.valueOf(currentDate));
        petition.setDeadLine(java.sql.Date.valueOf(currentDate.plusDays(30)));
        petition.setSigners(new HashSet<>(){
            {
                add(userRepository.findByIdnp(petitionCreateRequest.getInitiator_idnp()).get());
            }
        });
        petitionRepository.save(petition);
        return petition.getPetition_id();
    }

    public List<Petition> getTop3PetitionsByCategoryIds(List<Integer> categoryIds) {
        return petitionRepository.findTop3PetitionsByCategoryIds(categoryIds);
    }
}
