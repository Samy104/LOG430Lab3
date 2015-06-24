package ca.etsmtl.log430.lab3;

import java.util.Observable;

public class DeliveriesAlreadyAssignedToDriver extends Communication{

	public DeliveriesAlreadyAssignedToDriver(Integer registrationNumber,
			String componentName) {
		super(registrationNumber, componentName);
		// TODO Auto-generated constructor stub
	}

	public void update(Observable thing, Object notificationNumber) {
		Menus menu = new Menus();
		Displays display = new Displays();
		Driver theDriver = new Driver();
		
		if (registrationNumber.compareTo((Integer) notificationNumber) == 0) {
			addToReceiverList("ListDriversComponent");
			signalReceivers("ListDriversComponent");

			theDriver = menu.pickDriver(CommonData.theListOfDrivers.getListOfDrivers());

			if (theDriver != null) {
				/*
				 * If the driver is valid (exists in the list), then we display
				 * the deliveries that are assigned to him.
				 */
				display.displayDeliveriesAlreadyMadeByDriver(theDriver);
				//display.displayDriversAssignedToDelivery(myDelivery);
			} else {
				System.out.println("\n\n *** Driver not found ***");
			}
		}
		removeFromReceiverList("ListDriversComponent");
	}

}
