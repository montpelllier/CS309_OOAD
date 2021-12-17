import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import dependency_injection.BeanFactoryImpl;
import testclass.*;

public class Test {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {

//        BeanFactoryImpl beanFactory = new BeanFactoryImpl();
//        beanFactory.loadInjectProperties(new File("./Assignment5/local-inject.properties"));
//        beanFactory.loadValueProperties(new File("./Assignment5/local-value.properties"));
//        PrivateTest object = beanFactory.createInstance(PrivateTest.class);
//        //Object = beanFactory.createInstance(J.class);
//
//        if (object != null){
//            System.out.println("\ncreate object successfully!");
//        }
//        //System.out.println(Object.isBool());
//        object.Display();

        PrivateTest a = new PrivateTest(null, "1", null, 3.2, null);
        a.Display();



    }
}
