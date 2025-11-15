package com.cub.coindesk.currency;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "currency")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Currency {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique = true, length = 8)
    @NotBlank(message = "{currency.code.notblank}")
    private String code;

    @Column(name="name_zh", nullable=false, length = 50)
    @NotBlank(message = "{currency.name.notblank}")
    private String nameZh;
}
