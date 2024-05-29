package sanity.nil.medassurance.controller;

import jakarta.persistence.Access;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sanity.nil.medassurance.db.model.RefundModel;
import sanity.nil.medassurance.dto.*;
import sanity.nil.medassurance.dto.request.*;
import sanity.nil.medassurance.service.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/med-ass")
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;
    private final AssuranceService assuranceService;
    private final RefundService refundService;
    private final AuthService authService;
    private final OperationService operationService;
    private final DoctorService doctorService;
    private final StructureService structureService;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable(value = "id") UUID id) {
        return ResponseEntity
                .status(200)
                .body(userService.getByID(id));
    }

    @GetMapping("/assurance/{id}")
    public ResponseEntity<AssuranceDTO> getAssuranceByID(@PathVariable(value = "id") UUID id) {
        return ResponseEntity
                .status(200)
                .body(assuranceService.getByID(id));
    }

    @GetMapping("/assurance/search")
    public ResponseEntity<List<AssuranceCardDTO>> searchAssurances() {
        return ResponseEntity
                .status(200)
                .body(assuranceService.getAll());
    }

    @GetMapping("/assurance")
    public ResponseEntity<AssuranceEstimatedDTO> getAssuranceEstimation(
            @RequestParam(value = "assuranceType") String assuranceType,
            @RequestParam(value = "durationType") String durationType
    ) {
        return ResponseEntity
                .status(200)
                .body(assuranceService.getEstimatedDetails(
                        new AssuranceEstimateDTO(assuranceType, durationType))
                );
    }

    @PostMapping("/assurance")
    public ResponseEntity<AssuranceCardDTO> createAssurance(@RequestBody CreateAssuranceDTO createAssuranceDTO) {
        return ResponseEntity
                .status(201)
                .body(assuranceService.save(createAssuranceDTO));
    }

    @GetMapping("/refund/{id}")
    public ResponseEntity<RefundDTO> getRefundByID(@PathVariable(value = "id") UUID id) {
        return ResponseEntity
                .status(200)
                .body(refundService.getByID(id));
    }

    @PostMapping("/refund")
    public ResponseEntity<UUID> createRefund(@RequestBody CreateRefundDTO createRefundDTO) {
        return ResponseEntity
                .status(201)
                .body(refundService.save(createRefundDTO));
    }

    @GetMapping("/refund/search")
    public ResponseEntity<List<RefundCardDTO>> searchRefunds() {
        return ResponseEntity
                .status(200)
                .body(refundService.getAllRefundsByUser());
    }

    @PostMapping ("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity
                .status(200)
                .body(authService.login(loginDTO));
    }

    @GetMapping ("/access")
    public ResponseEntity<UserDTO> access() {
        return ResponseEntity
                .status(200)
                .body(authService.access());
    }

    @PostMapping("/register")
    public ResponseEntity<UUID> register(@RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity
                .status(201)
                .body(userService.save(createUserDTO));
    }

    @GetMapping("/operation/search")
    public ResponseEntity<List<OperationCardDTO>> searchOperations(
            @RequestParam(value = "doctorName", required = false) String doctorName,
            @RequestParam(value = "structureName", required = false) String structureName,
            @RequestParam(value = "operationName", required = false) String operationName,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit
    ) {
        return ResponseEntity
                .status(200)
                .body(operationService.getAllOperationsByFilters(
                        new OperationFilterDTO(doctorName, structureName, operationName, offset == null ? 0 : offset, limit == null ? 5 : limit))
                );
    }

    @GetMapping ("/operation/{id}")
    public ResponseEntity<OperationDTO> getOperationByID(@PathVariable(value = "id") Integer id) {
        return ResponseEntity
                .status(200)
                .body(operationService.getByID(id));
    }

    @GetMapping ("/doctor/{id}")
    public ResponseEntity<DoctorDTO> getDoctorByID(@PathVariable(value = "id") Integer id) {
        return ResponseEntity
                .status(200)
                .body(doctorService.getByID(id));
    }

    @GetMapping ("/structure/{id}")
    public ResponseEntity<StructureDTO> getStructureByID(@PathVariable(value = "id") Integer id) {
        return ResponseEntity
                .status(200)
                .body(structureService.getByID(id));
    }
}
