package ca.etsmtl.log430.lab3;

import java.util.Observable;

public class DeliveriesUnassigned extends Communication {

	public DeliveriesUnassigned(Integer registrationNumber, String componentName) {
		super(registrationNumber, componentName);
		// TODO Auto-generated constructor stub
	}

	public void update(Observable thing, Object notificationNumber) {
		if (registrationNumber.compareTo((Integer) notificationNumber) == 0) {
			Displays display = new Displays();
			display.DeliveriesUnassigned(CommonData.theListOfDeliveries.getListOfDeliveries());
		}
	}

}
