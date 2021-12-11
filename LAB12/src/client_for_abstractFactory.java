import abstractFactory.DaoFactory;
import abstractFactory.MysqlDaoFactory;
import bean.Computer;
import bean.Staff;
import dao.ComputerDao;
import dao.StaffDao;

import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class client_for_abstractFactory {
    public static void main(String [] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        DaoFactory mysqlDaoFactory = new MysqlDaoFactory();

        StaffDao staffDao = mysqlDaoFactory.createStaffDao();
        ComputerDao computerDao = mysqlDaoFactory.createComputerDao();

        test(staffDao,computerDao);
    }

    public static void test(StaffDao staffDao, ComputerDao computerDao){
        Scanner input = new Scanner(System.in);
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
