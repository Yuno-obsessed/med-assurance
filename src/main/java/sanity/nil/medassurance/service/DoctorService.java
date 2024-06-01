package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.db.model.DoctorModel;
import sanity.nil.medassurance.db.model.OperationModel;
import sanity.nil.medassurance.db.repos.DoctorRepo;
import sanity.nil.medassurance.dto.DoctorCardDTO;
import sanity.nil.medassurance.dto.DoctorDTO;
import sanity.nil.medassurance.dto.DoctorOperationDTO;
import sanity.nil.medassurance.interfaces.DoctorRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorDTO getByID(Integer id) {
        DoctorModel doctor = doctorRepository.getByID(id);
        return new DoctorDTO(doctor.getName(), doctor.getSpecialty(),
                doctor.getOperations().stream().map((e) ->
                        new DoctorOperationDTO(e.getName(), e.getDescription())).toList());
    }

    public List<DoctorCardDTO> getByOperation(Integer id) {
        return doctorRepository.getByOperation(id).stream()
                .map(e -> new DoctorCardDTO(e.getId(), e.getName())).toList();
    }
}
