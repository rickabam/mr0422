package models;

import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RentalAgreement {

    private final String toolCode;
    private final String toolType;
    private final String toolBrand;
    private final int rentalDays;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private final double dailyRentalCharge;
    private final int totalBillableDays; // Charge Days
    private final double grossCharge; // Pre-discount Charge
    private final int discountPercent;
    private final double discountAmount;
    private final double finalCharge;

    public RentalAgreement(String tc, String tt, String tb, int rd, LocalDate cd, LocalDate dd, double drc, int tbd, double gc, int dp, double da, double fc){
        this.toolCode = tc;
        this.toolType = tt;
        this.toolBrand = tb;
        this.rentalDays = rd;
        this.checkoutDate = cd;
        this.dueDate = dd;
        this.dailyRentalCharge = drc;
        this.totalBillableDays = tbd;
        this.grossCharge = gc;
        this.discountPercent = dp;
        this.discountAmount = da;
        this.finalCharge = fc;
    }

    public void printToolCode(){
        System.out.println("Tool Code: " + this.toolCode);
    }

    public void printToolType(){
        System.out.println("Tool Type: " + this.toolType);
    }

    public void printToolBrand(){
        System.out.println("Tool Brand: " + this.toolBrand);
    }

    public void printRentalDays(){
        System.out.println("Rental Days: " + this.rentalDays);
    }

    public void printCheckoutDate(){
        System.out.println("Checkout Date: " + this.checkoutDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    }

    public void printDueDate(){
        System.out.println("Due Date: " + this.dueDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    }

    public void printDailyRentalCharge(){
        System.out.println("Daily Rental Charge: " + NumberFormat.getCurrencyInstance(Locale.US).format(this.dailyRentalCharge));
    }

    public void printTotalBillableDays(){
        System.out.println("Charge Days: " + this.totalBillableDays);
    }

    public void printGrossCharge(){
        System.out.println("Pre-discount Charge: " + NumberFormat.getCurrencyInstance(Locale.US).format(this.grossCharge));
    }

    public void printDiscountPercent(){
        System.out.println("Discount Percent: " + this.discountPercent + "%");
    }

    public void printDiscountAmount(){
        System.out.println("Discount Amount: " + NumberFormat.getCurrencyInstance(Locale.US).format(this.discountAmount));
    }

    public void printFinalCharge(){
        System.out.println("Final Charge: " + NumberFormat.getCurrencyInstance(Locale.US).format(this.finalCharge));
    }

    public void printAll(){
        printToolCode();
        printToolType();
        printToolBrand();
        printRentalDays();
        printCheckoutDate();
        printDueDate();
        printDailyRentalCharge();
        printTotalBillableDays();
        printGrossCharge();
        printDiscountPercent();
        printDiscountAmount();
        printFinalCharge();
    }

}
