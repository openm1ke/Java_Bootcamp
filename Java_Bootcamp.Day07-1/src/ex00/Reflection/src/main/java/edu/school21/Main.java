package edu.school21;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {

    static final private String BUILDER_CLASS = "classes";
    static final private String SEPARATOR = "---------------------";
    static final private Scanner scanner = new Scanner(System.in);

    static private Map<String, Class<?>> classes;

    public static void main(String[] args) {
        classes = loadClassesFromPackage();
        if(classes != null) {
            classes.keySet().forEach(key -> System.out.println("  - " + key));
        } else {
            System.out.println("No classes found in the package: " + BUILDER_CLASS);
            System.exit(1);
        }

        String className = null;
        while (true) {
            System.out.print("Enter class name: ");
            className = scanner.nextLine();
            if (!classes.containsKey(className)) {
                System.out.println("No such class: " + className);
            } else {
                viewClassFieldsAndMethods(className);
                break;
            }
        }
        System.out.println(SEPARATOR);
        System.out.println("Let’s create an object.");

        Object object = createObjectByClassName(className, scanner);
        viewCreatedObject(object);
        System.out.println(SEPARATOR);

        changeObjectFieldValue(object, className, scanner);
        viewCreatedObject(object);
        
        changeObjectMethodValue(object, className, scanner);
        viewCreatedObject(object);
    }

    private static void changeObjectMethodValue(Object object, String className, Scanner scanner) {
        Class<?> clazz = classes.get(className);
        Method method = null;
        System.out.println("Enter name of the method for changing:");
        String methodName = scanner.nextLine();
        try {
            // Получаем все методы класса
            Method[] methods = clazz.getDeclaredMethods();
            for (Method m : methods) {
                // Проверяем, совпадает ли имя метода с введенным пользователем
                if (m.getName().equals(methodName)) {
                    method = m;
                    break;
                }
            }

            if (method == null) {
                throw new NoSuchMethodException("Method " + methodName + " not found");
            }

            method.setAccessible(true);
            Object[] args = new Object[method.getParameterCount()];

            for (int i = 0; i < args.length; i++) {
                System.out.print("Enter " + method.getParameters()[i].getType() + " value: ");
                args[i] = convertValue(scanner.nextLine(), method.getParameters()[i].getType());
            }
            Object res = method.invoke(object, args);
            method.setAccessible(false);

            if (res != null) {
                System.out.println("Method returned: " + res.toString());
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static void changeObjectFieldValue(Object object, String className, Scanner scanner) {
        Class<?> clazz = classes.get(className);
        Field field = null;
        System.out.println("Enter name of the field for changing:");
        String fieldName = scanner.nextLine();
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            System.out.print("Enter " + field.getType() + " value: ");
            Object objectValue = convertValue(scanner.nextLine(), field.getType());
            field.set(object, objectValue);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void viewCreatedObject(Object object) {
        System.out.println(object.toString());
    }


    private static Object createObjectByClassName(String className, Scanner scanner) {
        Class<?> clazz = classes.get(className);
        Object object = null;
        try {
            Constructor<?> constructor = clazz.getConstructor();
            object = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            System.out.print("Enter " + field.getName() + ": ");
            String value = scanner.nextLine();
            Object objectValue = convertValue(value, field.getType());
            try {
                field.set(object, objectValue);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    private static Object convertValue(String value, Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == long.class || type == Long.class) {
            return Long.parseLong(value);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == float.class || type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (type == char.class || type == Character.class) {
            return value.charAt(0);
        } else if (type == String.class) {
            return value;
        }
        throw new IllegalArgumentException("Unsupported field type: " + type.getName());
    }

    private static void viewClassFieldsAndMethods(String className) {
        try {
            Class<?> clazz = classes.get(className);
            System.out.println("fields:");
            for (Field field : clazz.getDeclaredFields()) {
                System.out.print("\t");
                System.out.print(field.getType().getSimpleName());
                System.out.print(" ");
                System.out.println(field.getName());
            }
            System.out.println("methods:");
            for (Method method : clazz.getDeclaredMethods()) {
                System.out.print("\t");
                System.out.print(method.getReturnType().getSimpleName());
                System.out.print(" ");
                System.out.print(method.getName());
                System.out.print("(");
                int paramsCount = method.getParameterCount();
                for (int i = 0; i < paramsCount; i++){
                    System.out.print(method.getParameterTypes()[i].getSimpleName());
                    if (i + 1 != paramsCount) System.out.print(", ");
                }
                System.out.print(")\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Map<String, Class<?>> loadClassesFromPackage() {
        Map<String, Class<?>> classes = new HashMap<>();
        try {
            URL resourceURL = ClassLoader.getSystemClassLoader().getResource(BUILDER_CLASS);
            System.out.println("Resource URL: " + resourceURL);
            if (resourceURL == null) {
                System.out.println("Package not found: " + BUILDER_CLASS);
                return null;
            }

            if (resourceURL.getProtocol().equals("jar")) {
                String jarPath = resourceURL.getPath().substring(5, resourceURL.getPath().indexOf("!"));
                System.out.println("JAR Path: " + jarPath);
                try (JarFile jarFile = new JarFile(jarPath)) {
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String entryName = entry.getName();
                        if (entryName.startsWith(BUILDER_CLASS + "/") && entryName.endsWith(".class")) {
                            String className = entryName.replace("/", ".").replace(".class", "");
                            String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
                            // System.out.println("Found class: " + simpleClassName);
                            classes.put(simpleClassName, Class.forName(className));
                        }
                    }
                }
            } else {
                File directory = new File(resourceURL.toURI());
                File[] files = directory.listFiles((dir, name) -> name.endsWith(".class"));
                if (files != null) {
                    for (File file : files) {
                        String className = BUILDER_CLASS + '.' + file.getName().replace(".class", "");
                        String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
                        // System.out.println("Found class: " + simpleClassName);
                        classes.put(simpleClassName, Class.forName(className));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return classes;
    }


}
