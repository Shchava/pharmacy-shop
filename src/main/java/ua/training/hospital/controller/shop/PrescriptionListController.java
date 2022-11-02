package ua.training.hospital.controller.shop;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.training.hospital.entity.shop.Prescription;
import ua.training.hospital.service.shop.PrescriptionService;
import ua.training.hospital.service.user.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class PrescriptionListController {

    PrescriptionService prescriptionService;
    UserService userService;

    @RequestMapping(value = "/createPrescription", method = RequestMethod.POST)
    public ResponseEntity addPrescription(@RequestBody  Prescription prescription){
        prescriptionService.createPrescription(prescription);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @RequestMapping(value = "/prescriptions/{pageNumber}", method = RequestMethod.GET)
    public String getOrders(@PathVariable(required = false) int pageNumber,
                            @RequestParam(defaultValue = "10", required = false) int recordsPerPage,
                            Principal principal,
                            Model model) {
        long userID = userService.getUser(principal.getName()).get().getIdUser();
        Page<Prescription> prescriptions = prescriptionService.getPrescriptionsByUserId(pageNumber, recordsPerPage, userID);
        model.addAttribute("page", prescriptions);
        model.addAttribute("prescriptionId", 0L);

        return "shop/showPrescriptions";
    }

    @RequestMapping(value = "/prescriptions/{pageNumber}", method = RequestMethod.POST)
    public String deletePrescription(@PathVariable(required = false) int pageNumber,
                            @RequestParam(defaultValue = "10", required = false) int recordsPerPage,
                            @RequestParam(required = false) long prescriptionId,
                            Principal principal,
                            Model model) {
        long userID = userService.getUser(principal.getName()).get().getIdUser();
        Optional<Prescription> prescriptionToDelete = prescriptionService.getPrescription(prescriptionId);
        if(prescriptionToDelete.isPresent() && prescriptionToDelete.get().getIdUser() == userID) {
            prescriptionService.deletePrescription(prescriptionId);
        }
        Page<Prescription> prescriptions = prescriptionService.getPrescriptionsByUserId(pageNumber, recordsPerPage, userID);
        model.addAttribute("page", prescriptions);

        return "shop/showPrescriptions";
    }
}
