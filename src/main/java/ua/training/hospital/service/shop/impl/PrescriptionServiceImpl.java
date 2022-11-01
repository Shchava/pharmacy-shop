package ua.training.hospital.service.shop.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.training.hospital.entity.User;
import ua.training.hospital.entity.enums.Status;
import ua.training.hospital.entity.shop.BuyOrder;
import ua.training.hospital.entity.shop.Prescription;
import ua.training.hospital.entity.shop.ProductOrder;
import ua.training.hospital.repository.UserRepository;
import ua.training.hospital.repository.shop.BuyOrderRepository;
import ua.training.hospital.repository.shop.PrescriptionRepository;
import ua.training.hospital.service.shop.BuyOrderService;
import ua.training.hospital.service.shop.PrescriptionService;
import ua.training.hospital.service.shop.ProductsOrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {
    private static final Logger logger = LogManager.getLogger(PrescriptionServiceImpl.class);
    private final PrescriptionRepository prescriptionRepository;


    @Override
    public Page<Prescription> getPrescriptionsByUserId(int pageNumber, int requestsPerPage, long userId) {
        return prescriptionRepository.getPrescriptionsByIdUser(userId, PageRequest.of(pageNumber, requestsPerPage));
    }

    @Override
    public Optional<Prescription> createPrescription(Prescription prescription) {
        return Optional.of(prescriptionRepository.save(prescription));
    }

    @Override
    public Optional<Prescription> getPrescription(long prescriptionId) {
        return prescriptionRepository.getPrescriptionsByPrescriptionId(prescriptionId);
    }

    @Override
    public void deletePrescription(long id) {
        prescriptionRepository.deleteById(id);
    }
}
