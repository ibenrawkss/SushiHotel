package com.sushihotel.main;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.sushihotel.hotel.HotelMgr;

public class hotelApp    {
    private static final Logger logger = Logger.getLogger(hotelApp.class.getName());
    private static Scanner sc = new Scanner(System.in);
    private static final HotelMgr hotelMgr = new HotelMgr();

    public static void main(String[] args)  {
        int choice = 0;
        String staffName;
        
        try {
            /**
             * LOGGING HANDLER
             */
            Handler files = new FileHandler("log/system.log", true);
            Handler[] handlers = Logger.getLogger("").getHandlers();

            files.setFormatter(new SimpleFormatter());
            for(Handler handler : handlers)
                Logger.getLogger("").removeHandler(handler);
            Logger.getLogger("").addHandler(files);
            /**
             * LOGGING HANDLER END
             */

            System.out.println(
                "========= Welcome to Sushi Hotel =========\n" +
                "Please enter your name: "
                );
            staffName = sc.nextLine();
            System.out.println("Hello, " + staffName + ".\n");
            
            hotelMgr.updateRoomSvcDelivered(); // if theres no room service database will show WARNING: RoomService DB not found
            hotelMgr.updateExpiredStatus();
            hotelMgr.updateRoomToReserved();
            
        	}catch(IOException ioe)    {
        		logger.severe(ioe.getMessage());
        		System.out.println("System failure. Please contact the system developer.");
        	}
		do {
				System.out.println(
						"Please select an option(1-20):\n" +
						"=============== GUEST MATTERS ===============\n" +
						"1) Register Guest\n" +
						"2) Update Guest Details\n"+
						"3) Search Guest\n"+
						"============ RESERVATION MATTERS ============\n" +
						"4) New Reservation\n"+
						"5) Edit Reservation\n"+
						"6) Print Reservation\n"+
						"7) Remove Reservation\n" +
						"================ ROOM MATTERS ===============\n" +
						"8) Create Room\n" +
						"9) View/Edit Room Details\n" +
						"================ MENU MATTERS ===============\n" +
						"10) Add New Menu Item\n" +
						"11) Edit Menu Item\n" +
						"12) Remove Menu Item\n" +
						"13) Print Menu\n" +
						"============ OPERATIONAL MATTERS ============\n" +
						"14) Order Room Service\n"+
						"15) Check Room Availability\n" +
						"16) Check In\n" +
						"17) Check Out\n" +
						"18) Print Room Occupancy Rate (One Day)\n" +
						"19) Print Reservation List\n" +
						"20) Check Room Service Status\n" +
						"21) Print List of Occupied Rooms\n" +
						"22) Exit\n" +
						"Choice (1-22): "
					);

				try{
					choice = sc.nextInt();

					switch(choice)  {
						case 1:
							hotelMgr.guestRegistration();
							break;
						case 2:
							hotelMgr.updateGuestInformation();
							break;
						case 3:
							hotelMgr.searchGuests();
							break;
						case 4:
							hotelMgr.newReservation();
							break;
						case 5:
							hotelMgr.editReservation();
							break;
						case 6:
							int reservationID;
							System.out.println("Please input Reservation ID to print: ");
							reservationID = sc.nextInt();
							sc.nextLine();
							hotelMgr.printReservation(reservationID);
							break;
						case 7:
							hotelMgr.removeReservation();
							break;
						case 8:
							hotelMgr.createRoom();
							break;
						case 9:
							hotelMgr.updateRoomDetails();
							break;
						case 10:
							hotelMgr.addMenuItem();
							break;
						case 11:
							hotelMgr.editMenuItem();
							break;
						case 12:
							hotelMgr.removeMenuItem();
							break;
						case 13:
							hotelMgr.printMealList();
							break;
						case 14:
							hotelMgr.callRoomService();
							break;
						case 15:
							hotelMgr.checkRoomAvailability();
							break;
						case 16:
							hotelMgr.checkIn();
							break;
						case 17:
							hotelMgr.checkOut();
							break;
						case 18:
							hotelMgr.printRoomStatusStatisticReport();
							break;
						case 19:
							hotelMgr.printReservationList();     
							break;
						case 20:
							hotelMgr.checkRoomServiceStatus();
							break;
						case 21:
							hotelMgr.printOccupiedRooms();
							break;
						default:
							break;
					}
				} catch (InputMismatchException ime)	{
					logger.severe(ime.getMessage());
					System.out.println("Invalid input, please enter an option from (1-22)");
					sc.nextLine();
				}
		} while (choice != 22);
        System.exit(0);
    }
}