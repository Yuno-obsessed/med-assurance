package sanity.nil.medassurance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sanity.nil.medassurance.dto.DiagnosisDTO;
import sanity.nil.medassurance.interfaces.DiagnosisRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    public List<DiagnosisDTO> getAll() {
        return diagnosisRepository.getAll().stream()
                .map(e -> new DiagnosisDTO(e.getId(), e.getName())).toList();
    }
}
