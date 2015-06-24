package ca.etsmtl.log430.lab3;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DriverSchedule {

	private static DriverSchedule scheduleInstance = null;
	
	//status (JNR) (SNR)??
	//nb de livraisons a complete?
	
	private DriverSchedule(){
	}
	
	
	public static DriverSchedule getDriverScheduleInstance(){
		if(scheduleInstance == null){
			return scheduleInstance = new DriverSchedule();
		}
		else{
			return scheduleInstance;
		}
	}
	public double getMaxDriverSchedule(Driver driver){
		return driver.getMaxShift();
	}
	
	
    /*Modification 4!*/
   

    public boolean validateNotOvertime(Driver driver, Delivery currentDelivery) {
        //Get the driver's delivery list
        DeliveryList dList = driver.getDeliveriesAssigned();
        dList.goToFrontOfList();

        Delivery tempDelivery;
        double otherDeliveriesTime = 0.0;
        double currentDeliveryTime = currentDelivery.getEstimatedDeliveryDurationDouble() * 2;
        boolean done = false;

        //Add together the time of the already scheduled deliveries
        while(!done){
            tempDelivery = dList.getNextDelivery();
            if(tempDelivery != null){
                otherDeliveriesTime += tempDelivery.getEstimatedDeliveryDurationDouble() * 2;
            }
            else{
                done = true;
            }
        }

        double total = currentDeliveryTime + otherDeliveriesTime;
        return total <= driver.getMaxShift();
    }

    public boolean verifyNoConflict(Driver driver, Delivery currentDelivery) {
        DeliveryList dList = driver.getDeliveriesAssigned();
        dList.goToFrontOfList();
        Delivery tempDelivery;

        boolean done = false;
        boolean isOkay = true;

        DateFormat df = new SimpleDateFormat("HHmm");

        if(currentDelivery.getDesiredDeliveryTime() != null){
            long currentDeliveryTime = 0;

            try {
                currentDeliveryTime = df.parse(currentDelivery.getDesiredDeliveryTime()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            int currentDeliveryDurationHour = Integer.parseInt(currentDelivery.getEstimatedDeliveryDuration().substring(0,2));
            int currentDeliveryDurationMinute = Integer.parseInt(currentDelivery.getEstimatedDeliveryDuration().substring(2,4));

            long currentDeliveryBegin = currentDeliveryTime;
            long currentDeliveryEnd = currentDeliveryBegin + (currentDeliveryDurationHour * 2 * 60 * 60 * 1000) +
                    (currentDeliveryDurationMinute * 2 * 60 * 1000);

            while(!done) {
                tempDelivery = dList.getNextDelivery();
                if(tempDelivery != null) {

                    long testedDeliveryTime = 0;

                    try {
                        testedDeliveryTime = df.parse(tempDelivery.getDesiredDeliveryTime()).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                    int testedDeliveryDurationHour = Integer.parseInt(tempDelivery.getEstimatedDeliveryDuration().substring(0, 2));
                    int testedDeliveryDurationMinute = Integer.parseInt(tempDelivery.getEstimatedDeliveryDuration().substring(2, 4));

                    long testedDeliveryBegin = testedDeliveryTime;
                    long testedDeliveryEnd = testedDeliveryBegin + (testedDeliveryDurationHour * 2 * 60 * 60 * 1000) +
                            (testedDeliveryDurationMinute * 2 * 60 * 1000);

                    boolean endCurrentDeliveryCheck = (currentDeliveryEnd > testedDeliveryBegin &&
                            currentDeliveryEnd < testedDeliveryEnd);
                    boolean beginningDeliveryCheck = (currentDeliveryBegin > testedDeliveryBegin &&
                            currentDeliveryBegin < testedDeliveryEnd);
                    boolean coveringCheck = (currentDeliveryBegin < testedDeliveryBegin &&
                            currentDeliveryEnd > testedDeliveryEnd);
                    boolean coveredCheck = (currentDeliveryBegin > testedDeliveryBegin &&
                            currentDeliveryEnd < testedDeliveryEnd);
                    boolean timesAreEqualCheck = (currentDeliveryBegin == testedDeliveryBegin ||
                            // - ca c'est legal currentDeliveryEnd == testedDeliveryBegin ||
                            //- ca c'est legal currentDeliveryBegin == testedDeliveryEnd ||
                            currentDeliveryEnd == testedDeliveryEnd);

                    if(endCurrentDeliveryCheck || beginningDeliveryCheck || timesAreEqualCheck || coveringCheck || coveredCheck) {
                        isOkay = false;
                        done = true;
                    }
                }
                else {
                    done = true;
                }
            }
        }
        return isOkay;
    }

	public double getCurrentDriverSchedule(Driver driver){
		DeliveryList dList = driver.getDeliveriesAssigned();
		dList.goToFrontOfList();
		Delivery tempDelivery = null;
		double currentHours = 0.0;
		boolean done = false;
		
		
		while(!done){
			
			tempDelivery = dList.getNextDelivery();
			if(tempDelivery != null){
				currentHours += (tempDelivery.getEstimatedDeliveryDurationDouble() * 2);
			}
			else{
				done = true;
			}
			
		}
		return (currentHours);
	}
}
