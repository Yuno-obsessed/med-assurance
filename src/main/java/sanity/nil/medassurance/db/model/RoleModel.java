package sanity.nil.medassurance.db.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleModel implements GrantedAuthority {

    @Id
    @Column(name = "type", nullable = false, unique = true, length = 30)
    private String type;

    @Override
    public String getAuthority() {
        return type;
    }

    public RoleModel(String type) {
        this.type = type;
    }
}
