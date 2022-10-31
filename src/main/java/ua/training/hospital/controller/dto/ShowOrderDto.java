package ua.training.hospital.controller.dto;

import lombok.*;
import ua.training.hospital.entity.enums.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowOrderDto {
    private long orderId;
    private String name;
    private String address;
    private String currentStatus;
    private String shortListOfProducts;
    long totalPrice;

}
