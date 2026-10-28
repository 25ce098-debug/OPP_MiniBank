import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
    String message() default "Field must not be blank";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    @MaxLength(10)
    private String username;

    @NotBlank
    @MaxLength(20)
    private String email;

    @NotBlank
    @MaxLength(12)
    private String password;

    public SignupForm(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupForm[username='" + username + "', email='" + email + "', password='" + password + "']";
    }
}

public class FormValidator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object val = field.get(obj);
                String strVal = (val == null) ? "" : val.toString();

                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (strVal.trim().isEmpty()) {
                        errors.add(field.getName() + ": must not be blank");
                    }
                }

                if (field.isAnnotationPresent(MaxLength.class)) {
                    int max = field.getAnnotation(MaxLength.class).value();
                    if (strVal.length() > max) {
                        errors.add(field.getName() + ": length (" + strVal.length() + ") exceeds maximum (" + max + ")");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Cannot access field: " + field.getName());
            }
        }
        return errors;
    }

    public static void main(String[] args) {
        SignupForm validForm = new SignupForm("bhavy_98", "bhavy@charusat.in", "Java@123");
        System.out.println("Validating Form 1: " + validForm);
        List<String> errors1 = validate(validForm);
        System.out.println("Validation Errors: " + (errors1.isEmpty() ? "None (Valid)" : errors1));

        System.out.println();

        SignupForm invalidForm = new SignupForm("bhavy_pan_25CE098", "   ", "SuperLongPassword12345");
        System.out.println("Validating Form 2: " + invalidForm);
        List<String> errors2 = validate(invalidForm);
        System.out.println("Validation Errors (" + errors2.size() + "):");
        for (String err : errors2) {
            System.out.println(" - " + err);
        }
    }
}
