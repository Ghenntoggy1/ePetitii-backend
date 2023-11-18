package org.example.Petition;

import org.example.CustomException.DuplicateSignException;
import org.example.CustomException.NotFoundException;
import org.example.user.User;
import org.example.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetitionService {
    private final PetitionRepository petitionRepository;
    private final UserRepository userRepository;

    @Autowired
    public PetitionService(PetitionRepository petitionRepository, UserRepository userRepository) {
        this.petitionRepository = petitionRepository;
        this.userRepository = userRepository;
    }
    public List<Petition> getAllPetitions() {
        return petitionRepository.findAll();
    }
    public Optional<Petition> getPetitionById(Integer petitionId) {
        return petitionRepository.findById(petitionId);
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
}
