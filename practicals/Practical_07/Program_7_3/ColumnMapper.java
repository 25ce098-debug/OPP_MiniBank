import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

class StudentRecord {
    @Column(name = "student_id")
    private String id = "N/A";

    @Column(name = "full_name")
    private String name = "N/A";

    @Column(name = "semester")
    private int semester = 0;

    @Column(name = "department")
    private String department = "Not Provided";

    @Override
    public String toString() {
        return "StudentRecord{id='" + id + "', name='" + name + "', semester=" + semester + ", department='" + department + "'}";
    }
}

public class ColumnMapper {
    public static <T> T populate(Class<T> clazz, String[] headers, String[] row) throws Exception {
        T instance = clazz.getDeclaredConstructor().newInstance();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                String colName = field.getAnnotation(Column.class).name();
                int colIndex = -1;
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].trim().equalsIgnoreCase(colName)) {
                        colIndex = i;
                        break;
                    }
                }

                if (colIndex != -1 && colIndex < row.length) {
                    field.setAccessible(true);
                    String rawValue = row[colIndex].trim();
                    if (field.getType() == int.class) {
                        field.setInt(instance, Integer.parseInt(rawValue));
                    } else {
                        field.set(instance, rawValue);
                    }
                } else {
                    System.out.println("Notice: Column '" + colName + "' missing in input; keeping default value for field '" + field.getName() + "'.");
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws Exception {
        String[] headers1 = {"student_id", "full_name", "semester", "department"};
        String[] dataRow1 = {"25CE098", "Bhavy Pan", "3", "Computer Engineering"};

        System.out.println("--- Case 1: All columns present ---");
        StudentRecord s1 = populate(StudentRecord.class, headers1, dataRow1);
        System.out.println("Populated Object: " + s1);

        System.out.println("\n--- Case 2: Missing 'department' column ---");
        String[] headers2 = {"student_id", "full_name", "semester"};
        String[] dataRow2 = {"25CE098", "Bhavy Pan", "3"};
        StudentRecord s2 = populate(StudentRecord.class, headers2, dataRow2);
        System.out.println("Populated Object: " + s2);
    }
}
