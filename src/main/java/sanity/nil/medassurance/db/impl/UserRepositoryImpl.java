package sanity.nil.medassurance.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sanity.nil.medassurance.db.model.UserModel;
import sanity.nil.medassurance.db.repos.UserRepo;
import sanity.nil.medassurance.interfaces.UserRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserRepo userRepo;

    @Override
    public UUID save(UserModel user) {
        return userRepo.save(user).getId();
    }

    @Override
    public UserModel getByID(UUID id) {
        return userRepo.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public UserModel getByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
