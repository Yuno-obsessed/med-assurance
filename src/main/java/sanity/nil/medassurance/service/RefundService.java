package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.consts.RefundStatus;
import sanity.nil.medassurance.db.mappers.RefundMapper;
import sanity.nil.medassurance.db.model.*;
import sanity.nil.medassurance.db.repos.DoctorOperationRepo;
import sanity.nil.medassurance.dto.FilteredRefundsDTO;
import sanity.nil.medassurance.dto.RefundCardDTO;
import sanity.nil.medassurance.dto.RefundFilterDTO;
import sanity.nil.medassurance.dto.request.CreateRefundDTO;
import sanity.nil.medassurance.dto.RefundDTO;
import sanity.nil.medassurance.interfaces.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundRepository refundRepository;
    private final RefundMapper refundMapper;
    private final AssuranceRepository assuranceRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final DoctorRepository doctorRepository;
    private final OperationRepository operationRepository;
    private final DoctorOperationRepo doctorOperationRepo;

    public RefundCardDTO save(CreateRefundDTO createRefundDTO) {
        RefundModel refund = refundMapper.createDTOToModel(createRefundDTO);
        refund.setAmount(createRefundDTO.amount());
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        refund.setUser(userModel);
        refund.setRefundStatus(RefundStatus.AWAITING);
        refund.setDiagnosis(diagnosisRepository.getByID(createRefundDTO.diagnosisID()));
        refund.setAssurance(assuranceRepository.getByID(createRefundDTO.assuranceID()));
        DoctorModel doctor = doctorRepository.getByID(createRefundDTO.doctorID());
        OperationModel operation = operationRepository.getByID(createRefundDTO.operationID());
        DoctorOperationKey key = new DoctorOperationKey(doctor.getId(), operation.getId());
        DoctorOperationModel doctorOperation = doctorOperationRepo.getByIds(doctor.getId(), operation.getId()).orElseThrow(NoSuchElementException::new);
        refund.setDoctorOperation(doctorOperation);
        refund.setDoctorOperationKey(key);
        RefundModel newModel = refundRepository.save(refund);

        return new RefundCardDTO(newModel.getAmount(), newModel.getRefundStatus().name(),
                newModel.getAssurance().getType().name(), newModel.getDiagnosis().getName(),
                newModel.getDoctorOperation().getOperation().getName(), newModel.getDoctorOperation().getDoctor().getName(),
                newModel.getUpdatedAt()
        );
    }

    public RefundDTO getByID(UUID id) {
        return refundMapper.modelToDTO(refundRepository.getByID(id));
    }

    public FilteredRefundsDTO getAllRefundsByUser(Integer offset, Integer limit) {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RefundModel> refunds = refundRepository.getAllByUserID(new RefundFilterDTO(userModel.getId(), offset, limit));
        int totalPages = refundRepository.countByUserID(userModel.getId());
        if (totalPages % limit == 0) {
            totalPages = totalPages / limit;
        } else {
            totalPages = totalPages / limit + 1;
        }
        List<RefundCardDTO> refundCards = refunds.stream().map(e -> new RefundCardDTO(e.getAmount(),
                e.getRefundStatus().name(), e.getAssurance().getType().name(),
                e.getDiagnosis().getName(), e.getDoctorOperation().getDoctor().getName(),
                e.getDoctorOperation().getOperation().getName(), e.getUpdatedAt())
        ).toList();
        return new FilteredRefundsDTO(refundCards, totalPages, offset+1);
    }

}
