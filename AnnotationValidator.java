package util;

import model.annotation.MaxLength;
import model.annotation.Positive;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * AnnotationValidator validates objects at runtime using Java Reflection.
 * Semester 3 Object-Oriented Programming (CEUE203) - Practical 07
 */
public class AnnotationValidator {

    /**
     * Inspects every field of an object across its class hierarchy using reflection,
     * evaluates custom annotations (@Positive, @MaxLength), and returns an array of error messages.
     *
     * @param obj the object instance to validate
     * @return String[] containing error messages, or an empty array if all validations pass
     */
    public static String[] validate(Object obj) {
        if (obj == null) {
            return new String[0];
        }

        List<String> errors = new ArrayList<>();
        Class<?> currentClass = obj.getClass();

        // Traverse class hierarchy to inspect fields in subclasses and superclasses
        while (currentClass != null && currentClass != Object.class) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);

                    // Check @Positive annotation
                    if (field.isAnnotationPresent(Positive.class)) {
                        Positive positive = field.getAnnotation(Positive.class);
                        if (value instanceof Number num) {
                            if (num.doubleValue() <= 0) {
                                errors.add(positive.message());
                            }
                        }
                    }

                    // Check @MaxLength annotation
                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength maxLength = field.getAnnotation(MaxLength.class);
                        if (value instanceof String str) {
                            if (str != null && str.length() > maxLength.value()) {
                                errors.add(field.getName() + ": " + maxLength.message());
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    // Suppress or log reflection access exceptions
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}
