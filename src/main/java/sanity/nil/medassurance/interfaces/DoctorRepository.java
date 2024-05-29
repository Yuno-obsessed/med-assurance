package sanity.nil.medassurance.interfaces;

import sanity.nil.medassurance.db.model.DoctorModel;

public interface DoctorRepository {
     DoctorModel getByID(Integer id);
}
