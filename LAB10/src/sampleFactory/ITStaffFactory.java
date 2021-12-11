package sampleFactory;

import ITStaff.*;

public class ITStaffFactory {

    public static ITStaff createITStaff(int StaffType){
        switch (StaffType) {
            case 1 -> {return new ITManager();}
            case 2 -> {return new Developer();}
            case 3 -> {return new Tester();}
            default -> {return null;}
        }
        //return null;
    }
}
