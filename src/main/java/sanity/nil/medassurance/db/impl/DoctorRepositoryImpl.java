package sanity.nil.medassurance.db.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sanity.nil.medassurance.db.model.DoctorModel;
import sanity.nil.medassurance.db.repos.DoctorRepo;
import sanity.nil.medassurance.interfaces.DoctorRepository;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Repository
public class DoctorRepositoryImpl implements DoctorRepository {

    private final DoctorRepo doctorRepo;

    @Override
    public DoctorModel getByID(Integer id) {
        return doctorRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
