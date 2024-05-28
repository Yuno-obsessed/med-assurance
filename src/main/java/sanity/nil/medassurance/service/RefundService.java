package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.db.mappers.RefundMapper;
import sanity.nil.medassurance.db.model.RefundModel;
import sanity.nil.medassurance.db.model.UserModel;
import sanity.nil.medassurance.dto.RefundCardDTO;
import sanity.nil.medassurance.dto.request.CreateRefundDTO;
import sanity.nil.medassurance.dto.RefundDTO;
import sanity.nil.medassurance.interfaces.RefundRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundRepository refundRepository;
    private final RefundMapper refundMapper;


    public UUID save(CreateRefundDTO createRefundDTO) {
        return refundRepository.save(refundMapper.createDTOToModel(createRefundDTO));
    }

    public RefundDTO getByID(UUID id) {
        return refundMapper.modelToDTO(refundRepository.getByID(id));
    }

    public List<RefundCardDTO> getAllRefundsByUser() {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RefundModel> refunds = refundRepository.getAllByUserID(userModel.getId());
        return refunds.stream().map(e -> new RefundCardDTO(e.getAmount(),
                e.getRefundStatus().name(), e.getAssurance().getType().name(),
                e.getDiagnosis().getName(), e.getUpdatedAt())
        ).toList();
    }

}
