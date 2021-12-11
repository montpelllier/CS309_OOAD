package singleton;

import abstractFactory.DaoFactory;
import dao.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class DaoFactoryImpl implements DaoFactory{
    private static DaoFactoryImpl instance;

    private DaoFactoryImpl(){}

    public static DaoFactory getInstance(){
        if (instance == null){
            instance = new DaoFactoryImpl();
        }
        return instance;
    }
    //private final String configFile = "src/singleton/resources.properties";
    String configFile = "E:\\Programme\\JAVA\\OOAD\\LAB12\\src\\singleton\\resources.properties";
    Properties properties = PropertiesReader.readProperties(configFile);
    @Override
    public StaffDao createStaffDao() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String className = "dao." + properties.getProperty("StaffDao");
        Class clz = Class.forName(className);
        Object instance = clz.getDeclaredConstructor().newInstance();
        return (StaffDao) instance;
    }

    @Override
    public ComputerDao createComputerDao() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String className = "dao." + properties.getProperty("ComputerDao");
        Class clz = Class.forName(className);
        Object instance = clz.getDeclaredConstructor().newInstance();
        return (ComputerDao) instance;
    }
}