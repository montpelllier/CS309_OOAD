import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dependency_injection.BeanFactoryImpl;
import testclass.*;

public class Test {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        B Object = null;
//        int a = int.class.newInstance();
//        System.out.println(a);
        BeanFactoryImpl beanFactory = new BeanFactoryImpl();
        Object = beanFactory.createInstance(B.class);
//        Constructor constructor = beanFactory.getConstructor(B.class);
//        Object = (B) constructor.newInstance(C.class, D.class);
        if (Object != null){
            System.out.println("success!");
        }
    }
}
