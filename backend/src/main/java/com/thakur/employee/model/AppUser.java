package com.thakur.employee.model;
import com.fasterxml.jackson.annotation.JsonIgnore;import jakarta.persistence.*;import jakarta.validation.constraints.*;import lombok.*;
@Entity @Table(name="users") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String name; @Email @Column(unique=true,nullable=false) private String email;
 @JsonIgnore @Column(nullable=false) private String password; private String department;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
}
