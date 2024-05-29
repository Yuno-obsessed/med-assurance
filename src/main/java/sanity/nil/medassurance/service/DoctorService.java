package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.db.model.DoctorModel;
import sanity.nil.medassurance.db.model.OperationModel;
import sanity.nil.medassurance.db.repos.DoctorRepo;
import sanity.nil.medassurance.dto.DoctorDTO;
import sanity.nil.medassurance.interfaces.DoctorRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorDTO getByID(Integer id) {
        DoctorModel doctor = doctorRepository.getByID(id);
        return new DoctorDTO(doctor.getName(), doctor.getSpecialty(),
                doctor.getOperations().stream().map(OperationModel::getName).toList());
    }
}
