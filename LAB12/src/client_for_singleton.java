import abstractFactory.DaoFactory;
import bean.Computer;
import bean.Staff;
import dao.ComputerDao;
import dao.StaffDao;
import singleton.DaoFactoryImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class client_for_singleton {
    public static void main(String [] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

//        String className = "MysqlStaffDao";
//        Class clz = Class.forName(className);
//        Object instance = clz.getDeclaredConstructor().newInstance();


        DaoFactory daoFactory = DaoFactoryImpl.getInstance();

        StaffDao staffDao = daoFactory.createStaffDao();
        ComputerDao computerDao = daoFactory.createComputerDao();

        test(staffDao,computerDao);
    }

    public static void test(StaffDao staffDao, ComputerDao computerDao){
        Scanner input=new Scanner(System.in);
        int op=-1;
        do{
            try{
                op=input.nextInt();
                switch (op) {
                    case 1 -> staffDao.insertStaff(new Staff());
                    case 2 -> staffDao.updateStaff(1);
                    case 3 -> staffDao.deleteStaff(1);
                    case 4 -> computerDao.insertComputer(new Computer(1));
                    case 5 -> computerDao.updateComputer(1);
                    case 6 -> computerDao.deleteComputer(1);
                }
            }catch(InputMismatchException e){
                System.out.println("Exception "+e);
            }
        }while(op!=0);
    }
}
