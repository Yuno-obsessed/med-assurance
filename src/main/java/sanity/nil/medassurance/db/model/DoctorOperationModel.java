package sanity.nil.medassurance.db.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctor_operation")
@Entity
public class DoctorOperationModel {

    @EmbeddedId
    private DoctorOperationKey id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("doctorID")
    @JoinColumn(name = "doctor_id")
    private DoctorModel doctor;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("operationID")
    @JoinColumn(name = "operation_id")
    private OperationModel operation;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public DoctorOperationModel(DoctorModel doctor, OperationModel operation, BigDecimal price) {
        this.id = new DoctorOperationKey(doctor.getId(), operation.getId());
        this.doctor = doctor;
        this.operation = operation;
        this.price = price;
    }
}
