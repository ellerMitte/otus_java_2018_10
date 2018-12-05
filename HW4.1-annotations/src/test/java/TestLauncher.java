
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

        Object testObject = ReflectionHelper.instantiate(testClazz);

        Arrays.stream(testMethods).forEach(testMethod -> {
            invokeMethods(testObject, beforeMethods);
            ReflectionHelper.callMethod(testObject, testMethod.getName());
            invokeMethods(testObject, afterMethods);
        });
    }

    private static void invokeMethods(Object testObject, Method[] methods) {
        Arrays.stream(methods).forEach(method -> ReflectionHelper.callMethod(testObject, method.getName()));
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
