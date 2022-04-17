package models;

public class ToolPolicy {

    private double dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public ToolPolicy(double newDailyCharge, boolean newWeekdayChargePolicy, boolean newWeekendChargePolicy, boolean newHolidayChargePolicy) {

        //this.toolTypeForPolicy = newToolTypeForPolicy;
        this.dailyCharge = newDailyCharge;
        this.weekdayCharge = newWeekdayChargePolicy;
        this.weekendCharge = newWeekendChargePolicy;
        this.holidayCharge = newHolidayChargePolicy;

    }

    public double getDailyCharge(){
        return this.dailyCharge;
    }

    public boolean isWeekdayCharge(){
        return this.weekdayCharge;
    }

    public boolean isWeekendCharge(){
        return this.weekendCharge;
    }

    public boolean isHolidayCharge(){
        return this.holidayCharge;
    }

}
