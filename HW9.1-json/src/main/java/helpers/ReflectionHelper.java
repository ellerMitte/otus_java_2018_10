package helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tully.
 */
@SuppressWarnings("SameParameterValue")
public class ReflectionHelper {
    private ReflectionHelper() {
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        Constructor<T> constructor = null;
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                constructor = type.getDeclaredConstructor(classes);
                constructor.setAccessible(true);
                return constructor.newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (constructor != null && !constructor.isAccessible()) {
                constructor.setAccessible(false);
            }
        }

        return null;
    }

    public static Object getFieldValue(Object object, String name) {
        Field field = null;

        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields

            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !field.isAccessible()) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public static Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !field.isAccessible()) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public static List<Object> getListFromArray(Object array) {
        if (array.getClass().isArray()) {
            List<Object> objectList = new ArrayList<>();
            for (int i = 0; i < Array.getLength(array); i++) {
                objectList.add(Array.get(array, i));
            }
            return objectList;
        }
        return null;
    }

    public static Field[] getFields(Object object) {
        return object.getClass().getDeclaredFields();
    }

    public static String[] getFieldsName(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .map(field -> field.getName())
                .toArray(String[]::new);
    }

    public static Field getFieldWithAnnotation(Class clazz, Class annotationClazz) {

        Field[] clazzDeclaredFields = clazz.getDeclaredFields();
        for (Field field : clazzDeclaredFields) {
            Annotation annotation = field.getAnnotation(annotationClazz);
            if (annotation != null) {
                return field;
            }
        }
        return null;
    }

    public static void setFieldValue(Object object, String name, Object value) {
        Field field = null;

        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields

            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !field.isAccessible()) {
                field.setAccessible(false);
            }
        }
    }

    public static Object callMethodByName(Object object, String name, Object... args) {
        Method method = null;

        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));

            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (method != null && !method.isAccessible()) {
                method.setAccessible(false);
            }
        }
        return null;
    }

    public static boolean callMethod(Object object, Method method) {
        try {
            method.invoke(object);
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }

    public static Class getClass(String nameClass) {
        try {
            return Class.forName(nameClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
