package com.cool.demo.common;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 这个类是真的累
 */
public class MapConvert {

    public static <T> T mapToObj(Class<T> cls, Map<? extends String, ?> map){
        T instance = null;
        try {
            instance = cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        Class<?> prototype = cls;
        do {
            for (Field field : prototype.getDeclaredFields()){
                if (Modifier.isFinal(field.getModifiers())
                        || Modifier.isStatic(field.getModifiers())
                        || Modifier.isTransient(field.getModifiers())){
                    continue;
                }
                String fieldName = field.getName();
                Object val = null;
                if (map.containsKey(fieldName)){
                    val = map.get(fieldName);
                }
                if (val != null){
                    boolean fieldAccessible = field.isAccessible();
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    try {
                        Constructor<?> constructor = type.getDeclaredConstructor(String.class);
                        boolean constructorAccessible = constructor.isAccessible();
                        constructor.setAccessible(true);
                        field.set(instance, constructor.newInstance(String.valueOf(val)));
                        constructor.setAccessible(constructorAccessible);
                    } catch (IllegalAccessException
                            | InstantiationException
                            | InvocationTargetException
                            | NoSuchMethodException e) {
                        System.err.println("convert error ===> Class["+prototype+"],Field:["+fieldName+"],Type:["+type+"],Value:["+val+"]");
                    }
                    field.setAccessible(fieldAccessible);
                }
            }
            prototype = prototype.getSuperclass();
        } while (!Object.class.equals(prototype));
        return instance;
    }

    public static Map<String, Object> objToMap(Object obj){
        Class<?> cls = obj.getClass();
        Field[] fields = getAllFields(cls);
        Map<String, Object> map = new LinkedHashMap<>();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())){
                continue;
            }
            String key = field.getName();
            boolean flag = field.isAccessible();
            field.setAccessible(true);
            Object val = null;
            try {
                val = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(flag);
            if (val != null){
                map.put(key, val);
            }
        }
        return map;
    }

    /**
     * 获取指定Class（及其SuperClass）的成员变量
     */
    public static Field[] getAllFields(Class<?> cls){
        return getAllFields(cls, null);
    }

    /**
     * 递归合并基类Field
     */
    private static Field[] getAllFields(Class<?> cls, Field[] params){
        Field[] fields = (params == null) ? cls.getDeclaredFields() : params;
        Class<?> superCls = cls.getSuperclass();
        if (superCls == null || superCls == Object.class){
            return fields;
        }
        Field[] superClsFields = superCls.getDeclaredFields();
        fields = addAll(fields, superClsFields);
        return getAllFields(superCls, fields);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] addAll(T[] array1, T... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            Class<?> cls = array1.getClass().getComponentType();
            T[] joinedArray = (T[]) Array.newInstance(cls, array1.length + array2.length);
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);

            try {
                System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
                return joinedArray;
            } catch (ArrayStoreException e) {
                Class<?> type2 = array2.getClass().getComponentType();
                if (!cls.isAssignableFrom(type2)) {
                    throw new RuntimeException("Cannot store " + type2.getName() + " in an array of " + cls.getName(), e);
                } else {
                    throw e;
                }
            }
        }
    }

    private static <T> T[] clone(T[] array) {
        return array == null ? null : array.clone();
    }


    /*****************************************************************************************************/
    /**************************************** data processing ********************************************/
    /*****************************************************************************************************/

}
