import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by tully.
 */
@SuppressWarnings("SameParameterValue")
public class ReflectionHelper {
    private ReflectionHelper() {
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
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
