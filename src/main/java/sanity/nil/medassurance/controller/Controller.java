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
import sanity.nil.medassurance.service.AssuranceService;
import sanity.nil.medassurance.service.AuthService;
import sanity.nil.medassurance.service.RefundService;
import sanity.nil.medassurance.service.UserService;

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
}