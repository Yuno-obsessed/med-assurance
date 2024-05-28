package sanity.nil.medassurance.interfaces;

import sanity.nil.medassurance.db.model.UserModel;

import java.util.UUID;

public interface UserRepository {
    UUID save(UserModel user);
    UserModel getByID(UUID id);

    UserModel getByEmail(String email);
}
