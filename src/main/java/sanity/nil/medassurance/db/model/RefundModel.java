package sanity.nil.medassurance.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sanity.nil.medassurance.consts.RefundStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refunds")
public class RefundModel {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "refund_status")
    private RefundStatus refundStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assurance_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AssuranceModel assurance;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "diagnosis_id", referencedColumnName = "id")
    private DiagnosisModel diagnosis;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "doctorId", column = @Column(name = "doctor_id")),
            @AttributeOverride(name = "operationId", column = @Column(name = "operation_id"))
    })
    private DoctorOperationKey doctorOperationKey;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("doctorOperationKey")
    @JoinColumns({
            @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id", insertable = false, updatable = false),
            @JoinColumn(name = "operation_id", referencedColumnName = "operation_id", insertable = false, updatable = false)
    })
    private DoctorOperationModel doctorOperation;

    @Column(name = "updated_at", columnDefinition = "timestamptz")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onInsert() {
        this.id = UUID.randomUUID();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
