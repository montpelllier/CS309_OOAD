import singleton.PropertiesReader;

import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        String configFile = "E:\\Programme\\JAVA\\OOAD\\LAB12\\src\\singleton\\resources.properties";
        Properties properties = PropertiesReader.readProperties(configFile);
        System.out.println(properties.getProperty("StaffDao"));
        System.out.println(properties);
    }
}
