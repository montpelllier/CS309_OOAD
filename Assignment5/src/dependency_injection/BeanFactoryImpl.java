package dependency_injection;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Properties;

/**
 * TODO you should complete the class
 */
public class BeanFactoryImpl implements BeanFactory {
    Properties injectProp;
    Properties valueProp;

    private Properties loadPropties(File file){
        Properties prop = new Properties();
        try {
            prop.load(new BufferedReader(new FileReader(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    @Override
    public void loadInjectProperties(File file) {
        injectProp = loadPropties(file);
    }

    @Override
    public void loadValueProperties(File file) {
        valueProp = loadPropties(file);
    }

    public <T> Constructor getConstructor(Class<T> clazz){
        Constructor[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 1) {
            return constructors[0];
        }else {
            for (Constructor c : constructors) {//遍历构造器找到@inject的构造函数
                if (c.getAnnotation(Inject.class) != null) {
                    //System.out.println("inject");
                    return c;
                }
            }
        }
        return null;
    }

    private boolean isBaseType(Class clazz){
        return clazz == byte.class || clazz == short.class || clazz == int.class || clazz == long.class || clazz == float.class || clazz == double.class || clazz == boolean.class || clazz == char.class || clazz == String.class;
    }
    @Override
    public <T> T createInstance(Class<T> clazz) {
        //1.若为抽象类or接口，找到实现类
        String className = injectProp.getProperty(clazz.getName());
        Class clz = null;
        if (className != null){
            try {
                clz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else clz = clazz;

        Constructor constructor = getConstructor(clz);//2.找到构造器: 带有inject注解或者为默认构造器
//        System.out.println(constructor);
        Class[] fieldTypes = constructor.getParameterTypes();//3.找到构造函数要求的参数类型
        Object[] objects = new Object[fieldTypes.length];//4.创建存放参数的Object数组
        for (int i=0;i<fieldTypes.length;i++){//5.根据参数类型和注解创建object
            if (isBaseType(fieldTypes[i])){
                //objects[i] = fieldTypes
            }else {
                objects[i] = createInstance(fieldTypes[i]);
            }
        }

        T instance = null;
        try {
            instance =  (T) constructor.newInstance(objects);//6.根据构造函数和object创建了实例
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //7.找到实例有的参数类型，并检查注解
        Field[] clazzFields = clz.getDeclaredFields();//得到所有的成员变量
        for (Field f: clazzFields) {
            if (f.getAnnotation(Inject.class) != null){
                boolean isPrivate = Modifier.isPrivate(f.getModifiers());
                if (isPrivate) f.setAccessible(true);
                Object temp = createInstance(f.getType());
                try {
                    f.set(instance, temp);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (isPrivate) f.setAccessible(false);
            }
        }
        //8.通过inject的方法set值
        Method[] methods = clz.getDeclaredMethods();//找到所有method
        for (Method m: methods) {
            if (m.getAnnotation(Inject.class) != null){
                System.out.println("inject");

                fieldTypes = m.getParameterTypes();//3.找到构造函数要求的参数类型
                objects = new Object[fieldTypes.length];//4.创建存放参数的Object数组
                for (int i=0;i<fieldTypes.length;i++) {//5.根据参数类型和注解创建object
                    objects[i] = createInstance(fieldTypes[i]);
                }

                try {
                    m.invoke(instance, objects);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            //System.out.println(m);
        }

        return instance;
    }
}
