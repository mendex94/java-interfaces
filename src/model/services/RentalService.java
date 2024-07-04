package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

import java.time.Duration;

public class RentalService {
    private Double pricePerDay;
    private Double pricePerHour;

    private BrazilTaxService taxService;

    public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.taxService = taxService;
    }

    public void proccessInvoice(CarRental carRental) {
        Double minutes = (double) Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        Double hours = minutes / 60;

        Double basicPayment;

        if (hours <= 12) {
            basicPayment = pricePerHour * Math.ceil(hours);
        } else {
            basicPayment = pricePerDay * Math.ceil(hours / 24);
        }

        Double tax = taxService.tax(basicPayment);

        carRental.setInvoice(new Invoice(tax, basicPayment));
    }
}
