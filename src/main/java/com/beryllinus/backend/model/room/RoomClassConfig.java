package com.beryllinus.backend.model.room;

import com.beryllinus.backend.enumuration.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room_class_config")
public class RoomClassConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_class_id")
    private RoomClass roomClass;

    private boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal priceLocal;
    @Enumerated(EnumType.STRING)
    private Currency priceLocalCurrency;
    @Column(precision = 10, scale = 2)
    private boolean isLocalBookingActive;

    private BigDecimal priceInternational;
    @Enumerated(EnumType.STRING)
    private Currency priceInternationalCurrency;
    private boolean isInternationalBookingActive;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
