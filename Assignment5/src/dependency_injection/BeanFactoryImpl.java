package dependency_injection;

import java.io.*;
import java.lang.reflect.Constructor;
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

    private Object[] getParameters(Constructor constructor) {
        Parameter[] parameters = constructor.getParameters();
        Object[] ParameterObjects = new Object[parameters.length];

        //Object parameterObject = null;
        //Class clz;
        int i = 0;
        for (Parameter p : parameters) {
            System.out.println(p.getType());
            ParameterObjects[i++] = p.getType();


            //ParameterObjects
//            if (p.getAnnotation(Value.class) != null) {
//                System.out.println("The name of parameter:" + p.getName());
//                System.out.println("The type of parameter:" + p.getType().getName());
//                Value valueAnnotation = p.getAnnotation(Value.class);
//                System.out.println("value = " + valueAnnotation.value());
//                System.out.println("delimiter = " + valueAnnotation.delimiter());
//
//            }
        }
        return ParameterObjects;
    }

    private boolean isBaseType(Class clazz){
        return clazz == byte.class || clazz == short.class || clazz == int.class || clazz == long.class || clazz == float.class || clazz == double.class || clazz == boolean.class || clazz == char.class || clazz == String.class;
    }
    @Override
    public <T> T createInstance(Class<T> clazz) {
        Constructor constructor = getConstructor(clazz);

        //System.out.println(constructor);
        Class[] fieldTypes = constructor.getParameterTypes();
        Object[] objects = new Object[fieldTypes.length];
        for (int i=0;i<fieldTypes.length;i++){
            if (isBaseType(fieldTypes[i])){
                //objects[i] = fieldTypes
            }else {
                objects[i] = createInstance(fieldTypes[i]);
            }
            System.out.println(fieldTypes[i]);
        }

        //Object[] objects = getParameters(constructor);
        T object = null;
        try {
            object =  (T) constructor.newInstance(objects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
//        Parameter[] parameters = constructor.getParameters();
//        Object parameterObject = null;
//        for (Parameter p : parameters) {
//            if (p.getAnnotation(Value.class) != null) {
//                System.out.println("The name of parameter:" + p.getName());
//                System.out.println("The type of parameter:" + p.getType().getName());
//                Value valueAnnotation = p.getAnnotation(Value.class);
//                System.out.println("value = " + valueAnnotation.name());
//                System.out.println("delimiter = " + valueAnnotation.delimiter());
//
//                if (p.getType() == boolean[].class) {
//                    String[] strings = valueAnnotation.name().split(valueAnnotation.delimiter());
//                    boolean[] booleans = new boolean[strings.length];
//                    for (int i = 0; i < strings.length; i++) {
//                        booleans[i] = Boolean.parseBoolean(strings[i]);
//                    }
//                    parameterObject = booleans;
//                }
//            }
//        }



        return object;
    }
}
