import ITStaff.ITStaff;
import factoryMethod.*;

import java.util.*;

public class client {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int op = -1;

        List<ITStaff> staffList = new ArrayList<>();

        System.out.println("""
                Please input an operation number:
                1: add an IT manager
                2: add an Developer
                3: add an Tester
                4: add an ArtDesigner
                5: print all staff by salary order
                6: print all staff by working order
                0: Stop
                """);
        do {
            try {
                ITStaffFactoryInterface ITManagerFactory = new ITManagerFactory();
                ITStaffFactoryInterface DeveloperFactory = new DeveloperFactory();
                ITStaffFactoryInterface TesterFactory = new TesterFactory();
                ITStaffFactoryInterface ArtDesignerFactory = new ArtDesignerFactory();

                op = input.nextInt();

                switch (op) {
                    case 1 -> {
                        staffList.add(ITManagerFactory.createITStaff());
                    }
                    case 2 -> {
                        staffList.add(DeveloperFactory.createITStaff());
                    }
                    case 3 -> {
                        staffList.add(TesterFactory.createITStaff());
                    }
                    case 4 -> {
                        staffList.add(ArtDesignerFactory.createITStaff());
                    }
                    case 5 -> {
                        System.out.println("All information:");
                        staffList.stream().sorted(Comparator.comparing(ITStaff::getSalary)).forEach(System.out::println);
                    }
                    case 6 -> {
                        System.out.println("All name:");
                        staffList.stream().sorted(Comparator.comparing(ITStaff::working)).forEach(System.out::println);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println(e);
                input.nextLine();
            }

        } while (op != 0);
        input.close();
    }

}
