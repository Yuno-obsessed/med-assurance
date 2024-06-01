package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Filters;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.consts.AssuranceType;
import sanity.nil.medassurance.consts.DurationType;
import sanity.nil.medassurance.db.mappers.AssuranceMapper;
import sanity.nil.medassurance.db.model.AssuranceModel;
import sanity.nil.medassurance.db.model.UserModel;
import sanity.nil.medassurance.dto.AssuranceCardDTO;
import sanity.nil.medassurance.dto.AssuranceDTO;
import sanity.nil.medassurance.dto.AssuranceEstimatedDTO;
import sanity.nil.medassurance.dto.request.AssuranceEstimateDTO;
import sanity.nil.medassurance.dto.request.CreateAssuranceDTO;
import sanity.nil.medassurance.interfaces.AssuranceRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AssuranceService {

    private final AssuranceRepository assuranceRepository;
    private final AssuranceMapper assuranceMapper;

    public AssuranceCardDTO save(CreateAssuranceDTO createAssuranceDTO) {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AssuranceModel assuranceToSave = assuranceMapper.createDTOToModel(createAssuranceDTO);
        assuranceToSave.setUser(userModel);
        assuranceToSave.setId(UUID.randomUUID());
        assuranceToSave.setType(AssuranceType.valueOf(createAssuranceDTO.assuranceType()));
        assuranceToSave.setDurationType(DurationType.valueOf(createAssuranceDTO.durationType()));
        assuranceToSave.setActiveUntil(createAssuranceDTO.activeUntil());
        BigDecimal percent = BigDecimal.ONE;
        switch (AssuranceType.valueOf(createAssuranceDTO.assuranceType())) {
            case MANDATORY -> percent = BigDecimal.valueOf(40);
            case VOLUNTARY -> percent = BigDecimal.valueOf(60);
            case CRITICAL -> percent = BigDecimal.valueOf(90);
        }
        assuranceToSave.setPercent(percent);

        AssuranceModel assurance = assuranceRepository.save(assuranceToSave);
        return new AssuranceCardDTO(assurance.getId(), assurance.getType().name(),
                assurance.getDurationType().name(), assurance.getPercent(), assurance.getActiveUntil());
    }

    public AssuranceDTO getByID(UUID id) {
        return assuranceMapper.modelToDTO(assuranceRepository.getByID(id));
    }

    public List<AssuranceCardDTO> getAll(Boolean active) {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AssuranceModel> assurances = assuranceRepository.getAllByUserID(userModel.getId(), active);
        return assurances.stream().map(e -> new AssuranceCardDTO(e.getId(), e.getType().name(),
                e.getDurationType().name(), e.getPercent(), e.getActiveUntil())
        ).toList();
    }

    public AssuranceEstimatedDTO getEstimatedDetails(AssuranceEstimateDTO estimateDTO) {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return calculateEstimatedDetails(userModel,
                AssuranceType.valueOf(estimateDTO.assuranceType()), DurationType.valueOf(estimateDTO.durationType()));
    }

    private AssuranceEstimatedDTO calculateEstimatedDetails(UserModel user, AssuranceType assuranceType, DurationType durationType) {
        BigDecimal price = BigDecimal.ONE;
        if (assuranceType.equals(AssuranceType.MANDATORY)) {
            switch (durationType) {
                case MONTHLY -> price = BigDecimal.valueOf(5);
                case SEMI_ANNUAL -> price = BigDecimal.valueOf(30);
                case ANNUAL -> price = BigDecimal.valueOf(60);
            }
        } else if (assuranceType.equals(AssuranceType.VOLUNTARY)) {
            switch (durationType) {
                case MONTHLY -> price = BigDecimal.valueOf(30);
                case SEMI_ANNUAL -> price = BigDecimal.valueOf(180);
                case ANNUAL -> price = BigDecimal.valueOf(360);
            }
        } else if (assuranceType.equals(AssuranceType.CRITICAL)) {
            switch (durationType) {
                case MONTHLY -> price = BigDecimal.valueOf(100);
                case SEMI_ANNUAL -> price = BigDecimal.valueOf(600);
                case ANNUAL -> price = BigDecimal.valueOf(1200);
            }
        }
        LocalDateTime activeUntil = LocalDateTime.now();
        switch (durationType) {
            case ANNUAL -> activeUntil = activeUntil.plusYears(1L);
            case MONTHLY -> activeUntil = activeUntil.minusMonths(1L);
            case SEMI_ANNUAL -> activeUntil = activeUntil.plusMonths(6L);
        }

        return new AssuranceEstimatedDTO(price,activeUntil);
    }
}
