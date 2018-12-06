
import annotations.After;
import annotations.Before;
import annotations.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * @author Igor on 03.12.18.
 */
public class TestLauncher {

    public static void launch(String nameClass) {

        Class testClazz = ReflectionHelper.getClass(nameClass);

        Method[] methodsPublic = testClazz.getMethods();

        Method[] beforeMethods = getListMethods(Before.class, methodsPublic);
        Method[] testMethods = getListMethods(Test.class, methodsPublic);
        Method[] afterMethods = getListMethods(After.class, methodsPublic);

        Arrays.stream(testMethods).forEach(testMethod -> {
            Object testObject = ReflectionHelper.instantiate(testClazz);
            if (invokeMethods(testObject, beforeMethods)) {
                ReflectionHelper.callMethod(testObject, testMethod);
                invokeMethods(testObject, afterMethods);
            }
        });
    }

    private static boolean invokeMethods(Object testObject, Method[] methods) {
        boolean isSuccess = true;
        for (Method method : methods) {
            if (!ReflectionHelper.callMethod(testObject, method)) {
                isSuccess = false;
                System.out.println("method @Before " + method.getName() + " threw an exception, the test is not done.");
            }
        }
        return isSuccess;
    }

    private static Method[] getListMethods(Class<? extends Annotation> clazz, Method[] methodsPublic) {
        return Arrays.stream(methodsPublic).filter(method -> method.isAnnotationPresent(clazz)).toArray(Method[]::new);
    }

    public static void main(String[] args) {
        Reflections reflections = new Reflections(ClasspathHelper.forPackage("tests"), new SubTypesScanner(false));
        Set<String> classes = reflections.getAllTypes();
        classes.forEach(TestLauncher::launch);
    }


}
