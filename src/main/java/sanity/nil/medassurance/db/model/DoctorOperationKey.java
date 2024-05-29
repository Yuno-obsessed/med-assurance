package sanity.nil.medassurance.db.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class DoctorOperationKey implements Serializable {

    @Column(name = "doctor_id")
    private Integer doctorID;

    @Column(name = "operation_id")
    private Integer operationID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorOperationKey that = (DoctorOperationKey) o;
        return Objects.equals(doctorID, that.doctorID) && Objects.equals(operationID, that.operationID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctorID, operationID);
    }
}
