package abstractFactory;

import dao.ComputerDao;
import dao.StaffDao;

import java.lang.reflect.InvocationTargetException;

public interface DaoFactory {

    StaffDao createStaffDao() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    ComputerDao createComputerDao() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
