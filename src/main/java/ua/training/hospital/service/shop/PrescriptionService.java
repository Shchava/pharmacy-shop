package ua.training.hospital.service.shop;

import org.springframework.data.domain.Page;
import ua.training.hospital.entity.shop.Prescription;

import java.util.Optional;

public interface PrescriptionService {
    Page<Prescription> getPrescriptionsByUserId (int pageNumber, int requestsPerPage, long userId);
    Optional<Prescription> createPrescription (Prescription prescription);
    Optional<Prescription> getPrescription (long prescriptionId);
    void deletePrescription (long id);
}
