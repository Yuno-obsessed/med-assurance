package sanity.nil.medassurance.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sanity.nil.medassurance.db.model.UserModel;

import java.util.UUID;

public interface UserRepo extends JpaRepository<UserModel, UUID> {
    UserModel findByEmail(String email);
}
