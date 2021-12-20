package dependency_injection;

import testclass.EImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.Properties;

/**
 * TODO you should complete the class
 */
public class BeanFactoryImpl implements BeanFactory {
    Properties injectProp;
    Properties valueProp;
    int cnt;

    private Properties loadProperties(File file) {
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
        injectProp = loadProperties(file);
    }

    @Override
    public void loadValueProperties(File file) {
        valueProp = loadProperties(file);
    }

    private  <T> Constructor getConstructor(Class<T> clazz) {//获取构造器
        Constructor[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 1) {
            return constructors[0];
        } else {
            for (Constructor c : constructors) {//遍历构造器找到@inject的构造函数
                if (c.getAnnotation(Inject.class) != null) return c;
            }
        }
        return null;
    }

    private Object getValue(String string, Class clazz, Value val) {//通过value类获取参数取值
        if (clazz == String.class) {
            if (string.length() >= val.min() && string.length() <= val.max()) return string;
        } else if (clazz == int.class) {
            int temp = Integer.parseInt(string);
            if (temp >= val.min() && temp <= val.max()) return temp;
        } else if (clazz == byte.class) {
            byte temp = Byte.parseByte(string);
            if (temp >= val.min() && temp <= val.max()) return temp;
        } else if (clazz == short.class) {
            short temp = Short.parseShort(string);
            if (temp >= val.min() && temp <= val.max()) return temp;
        } else if (clazz == long.class) {
            long temp = Long.parseLong(string);
            if (temp >= val.min() && temp <= val.max()) return temp;
        } else if (clazz == float.class) {
            return Float.parseFloat(string);
        } else if (clazz == double.class) {
            return Double.parseDouble(string);
        } else if (clazz == char.class) {
            return string.charAt(0);
        } else if (clazz == boolean.class) {
            return Boolean.parseBoolean(string);
        }
        return null;
    }

    private Object defaultValue(Class clazz){
        if (clazz == String.class) return "default value";
        else if (clazz == int.class) return 0;
        else if (clazz == byte.class) return (byte) 0;
        else if (clazz == short.class) return (short) 0;
        else if (clazz == long.class) return (long) 0;
        else if (clazz == float.class) return (float) 0;
        else if (clazz == double.class) return (double) 0;
        else if (clazz == char.class) return (char) 0;
        else if (clazz == boolean.class) return false;
        else return createInstance(clazz);
    }

    private Object[] getFunctionParameters(Parameter[] parameters){
        Object[] objects = new Object[parameters.length];//4.创建存放参数的Object数组
        for (int i = 0; i < parameters.length; i++) {//5.根据参数类型和注解创建object
            Value val = parameters[i].getAnnotation(Value.class);
            if (val != null) {
                String[] data = valueProp.getProperty(val.value()).split(val.delimiter());
                for (String s : data) {
                    Object tmp = getValue(s, parameters[i].getType(), val);
                    if (tmp != null) {
                        objects[i] = tmp;
                        break;
                    }
                }
            }
            if (objects[i] == null){
                objects[i] = defaultValue(parameters[i].getType());
            }
        }
        return objects;
    }

    //  Value includes: byte, short, int, long, float, double, boolean, char, String
    //  May have multiple value: byte, short, int, long, String 需要min,max取值
    //  Default value: 0 or "default value"
    @Override
    public <T> T createInstance(Class<T> clazz) {
        //1.若为抽象类or接口，找到实现类
        String className = injectProp.getProperty(clazz.getName());
        Class clz = null;
        if (className != null) {
            try {
                clz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else clz = clazz;

        Constructor constructor = getConstructor(clz);//2.找到构造器: 带有inject注解或者为默认构造器
        System.out.println("constructor of "+clz+": "+constructor);
        if (constructor == null) System.out.println("null class:"+clz);

        //3.找到构造函数要求的参数; 4.创建存放参数的Object数组
        assert constructor != null;
        Object[] objects = getFunctionParameters(constructor.getParameters());

        T instance = null;
        try {
            instance = (T) constructor.newInstance(objects);//6.根据构造函数和object创建了实例
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            System.out.println(constructor);
            for (Object o: objects) System.out.println(o);
            e.printStackTrace();
        }
        //7.找到实例有的参数类型，并检查注解
        Field[] clazzFields = clz.getDeclaredFields();//得到所有的成员变量
        cnt = 0;
        for (Field f : clazzFields) {
            boolean isPrivate = Modifier.isPrivate(f.getModifiers());
            if (isPrivate) f.setAccessible(true);
            if (f.getAnnotation(Inject.class) != null) {
                System.out.println("field "+(cnt++)+" of "+clz+": "+f);
                Object temp = createInstance(f.getType());
                try {
                    f.set(instance, temp);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (f.getAnnotation(Value.class) != null) {
                System.out.println("field "+(cnt++)+" of "+clz+": "+f);
                Value val = f.getAnnotation(Value.class);
                String[] data = valueProp.getProperty(val.value()).split(val.delimiter());
                Object tmp = null;
                for (String s : data) {
                    tmp = getValue(s, f.getType(), val);
                    if (tmp != null) {
                        try {
                            f.set(instance, tmp);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                if (tmp == null) {
                    try {
                        f.set(instance, defaultValue(f.getType()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
            else System.out.println("null field "+(cnt++)+" of "+clz+": "+f);
            if (isPrivate) f.setAccessible(false);
        }
        //8.通过inject的方法set值
        Method[] methods = clz.getDeclaredMethods();//找到所有method
        cnt = 0;
        for (Method m : methods) {
            if (m.getAnnotation(Inject.class) != null) {//通过Inject注入参数
                System.out.println("method "+(cnt++)+" of "+clz+": "+m);
                objects = getFunctionParameters(m.getParameters());
                try {
                    m.invoke(instance, objects);
                } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                    System.out.println(m);
                    for (Object o: objects) System.out.println(o);
                    e.printStackTrace();
                }
            }
        }

        return instance;
    }
}
