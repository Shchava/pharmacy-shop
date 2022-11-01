package ua.training.hospital.repository.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.training.hospital.entity.shop.BuyOrder;
import ua.training.hospital.entity.shop.Prescription;

import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Page<Prescription> getPrescriptionsByIdUser (long idUser, Pageable pageable);
    Optional<Prescription> getPrescriptionsByPrescriptionId (long prescriptionId);
}
