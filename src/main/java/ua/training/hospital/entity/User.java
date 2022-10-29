package ua.training.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ua.training.hospital.entity.enums.UserRole;
import ua.training.hospital.entity.shop.Cart;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idUser;

    @Column(nullable = false)
    @NotNull
    String name;

    @Column(nullable = false)
    @NotNull
    String surname;

    @Column(nullable = false)
    @NotNull
    String patronymic;

    @Column(nullable = false, unique = true)
    @NotNull
    String email;

    @JsonIgnore
    @Column(nullable = false)
    @NotNull
    String passwordHash;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    UserRole role;

    @Column
    String info;

    @JsonIgnore
    @Transient
    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
    Cart cart;


    public User(@NotNull String name) {
        this.name = name;
    }
}
