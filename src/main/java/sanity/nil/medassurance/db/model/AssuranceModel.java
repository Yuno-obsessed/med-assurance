package sanity.nil.medassurance.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sanity.nil.medassurance.consts.AssuranceType;
import sanity.nil.medassurance.consts.DurationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assurances")
public class AssuranceModel {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AssuranceType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "duration_type")
    private DurationType durationType;

    @Column(name = "percent")
    private BigDecimal percent;

    @Column(name = "active_until", columnDefinition = "timestamptz")
    private LocalDateTime activeUntil;

}
