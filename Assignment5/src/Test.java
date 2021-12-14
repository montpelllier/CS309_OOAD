import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dependency_injection.BeanFactoryImpl;
import testclass.*;

public class Test {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        L Object = null;
//        int a = int.class.newInstance();
//        System.out.println(a);
        BeanFactoryImpl beanFactory = new BeanFactoryImpl();
        beanFactory.loadInjectProperties(new File("./Assignment5/local-inject.properties"));
        beanFactory.loadValueProperties(new File("./Assignment5/local-value.properties"));

        Object = beanFactory.createInstance(L.class);

        if (Object != null){
            System.out.println("create object successfully!");
        }
        System.out.println(Object.isBool());
//        if (Object.getCDep() != null){
//            System.out.println("create private class successfully!");
//        }
    }
}
