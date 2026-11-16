package com.thakur.employee.model;
import jakarta.persistence.*;import jakarta.validation.constraints.*;import lombok.*;import java.time.*;
@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LeaveRequest { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(optional=false) private AppUser employee; @NotNull private LocalDate startDate; @NotNull private LocalDate endDate; @NotBlank @Column(length=500) private String reason; @Enumerated(EnumType.STRING) private RequestStatus status; private String managerComment; private LocalDateTime createdAt; @PrePersist void pre(){if(status==null)status=RequestStatus.PENDING;if(createdAt==null)createdAt=LocalDateTime.now();} }
