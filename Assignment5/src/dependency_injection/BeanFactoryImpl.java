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
                if (c.getAnnotation(Inject.class) != null) return c;
            }
        }
        return null;
    }

    private Object getValue(String string, Class clazz, Value val){
        if (clazz == String.class){
            if (string.length() >= val.min() && string.length() <= val.max()) return string;
        }else if (clazz == int.class){
            int temp = Integer.parseInt(string);
            if (temp >= val.min() && temp <= val.max()) return temp;
        }else if (clazz == byte.class){
            byte temp = Byte.parseByte(string);
            if (temp>= val.min() && temp<= val.max()) return temp;
        }else if (clazz == short.class){
            short temp = Short.parseShort(string);
            if (temp>= val.min() && temp<= val.max()) return temp;
        }else if (clazz == long.class){
            long temp = Long.parseLong(string);
            if (temp>= val.min() && temp<= val.max()) return temp;
        }else if (clazz == char.class){
            return string.charAt(0);
        }else if (clazz == boolean.class){
            return Boolean.parseBoolean(string);
        }
        return null;
    }
//  Value includes: byte, short, int, long, float, double, boolean, char, String
//  May have multiple value: byte, short, int, long, String 需要min,max取值
//  Default value: 0 or "default value"
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
        Parameter[] parameters = constructor.getParameters();//3.找到构造函数要求的参数类型
        Object[] objects = new Object[parameters.length];//4.创建存放参数的Object数组
        for (int i=0;i<parameters.length;i++){//5.根据参数类型和注解创建object
            Value val = parameters[i].getAnnotation(Value.class);
            if (val != null){
//                System.out.println("5."+parameters[i].getAnnotation(Value.class));
                String[] data = valueProp.getProperty(val.value()).split(val.delimiter());
                for (String s: data) {
                    Object tmp = getValue(s,parameters[i].getType(),val);
                    if (tmp != null) {
                        objects[i] = tmp;
                        break;
                    }
                }
            }else objects[i] = createInstance(parameters[i].getType());
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
            boolean isPrivate = Modifier.isPrivate(f.getModifiers());
            if (isPrivate) f.setAccessible(true);
            if (f.getAnnotation(Inject.class) != null){
                Object temp = createInstance(f.getType());
                try {
                    f.set(instance, temp);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else if (f.getAnnotation(Value.class) != null){
                Value val = f.getAnnotation(Value.class);
//                System.out.println("7."+val);
                String[] data = valueProp.getProperty(val.value()).split(val.delimiter());
                for (String s: data) {
                    Object tmp = getValue(s,f.getType(),val);
                    if (tmp != null) {
                        try {
                            f.set(instance, tmp);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                if (isPrivate) f.setAccessible(false);
            }
        }
        //8.通过inject的方法set值
        Method[] methods = clz.getDeclaredMethods();//找到所有method
        for (Method m: methods) {
            if (m.getAnnotation(Inject.class) != null){
                Class[] fieldTypes = m.getParameterTypes();//3.找到构造函数要求的参数类型
                objects = new Object[fieldTypes.length];//4.创建存放参数的Object数组
                for (int i=0;i<fieldTypes.length;i++) objects[i] = createInstance(fieldTypes[i]);

                try {
                    m.invoke(instance, objects);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return instance;
    }
}
