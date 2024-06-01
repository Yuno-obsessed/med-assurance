package sanity.nil.medassurance.interfaces;

import sanity.nil.medassurance.db.model.DoctorModel;

import java.util.List;

public interface DoctorRepository {
     DoctorModel getByID(Integer id);
     List<DoctorModel> getByOperation(Integer id);
}
