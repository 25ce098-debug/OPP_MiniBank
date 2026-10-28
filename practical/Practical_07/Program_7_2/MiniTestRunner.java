import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

class CalculatorTests {
    @Run
    public void testAddition() {
        int res = 10 + 20;
        System.out.println("  [PASS] testAddition: 10 + 20 = " + res);
    }

    @Run
    public void testMultiplication() {
        int res = 6 * 7;
        System.out.println("  [PASS] testMultiplication: 6 * 7 = " + res);
    }

    public void helperMethod() {
        System.out.println("  [SKIP] helperMethod is not annotated with @Run");
    }

    @Run
    public void testDivision() {
        int res = 50 / 5;
        System.out.println("  [PASS] testDivision: 50 / 5 = " + res);
    }
}

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {
        CalculatorTests testInstance = new CalculatorTests();
        Method[] methods = CalculatorTests.class.getDeclaredMethods();

        int runCount = 0;
        System.out.println("Scanning methods in " + CalculatorTests.class.getSimpleName() + "...");

        for (Method method : methods) {
            if (method.isAnnotationPresent(Run.class) && method.getParameterCount() == 0) {
                System.out.println("Invoking @Run method: " + method.getName() + "()");
                method.invoke(testInstance);
                runCount++;
            }
        }

        System.out.println("Summary: " + runCount + " @Run method(s) executed out of " + methods.length + " total methods.");
    }
}
