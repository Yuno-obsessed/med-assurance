package sanity.nil.medassurance.db.model;


import jakarta.persistence.*;
import lombok.*;
import sanity.nil.medassurance.dto.OperationCardDTO;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "operations")
public class OperationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
