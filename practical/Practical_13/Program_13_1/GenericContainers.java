class Box<T> {
    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}

class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() { return first; }
    public B getSecond() { return second; }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

public class GenericContainers {
    public static <E> E firstElement(E[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        Box<String> idBox = new Box<>("25CE098");
        Box<Integer> semBox = new Box<>(3);
        String studentId = idBox.get();
        int semester = semBox.get();
        System.out.println("Box<String> value  : " + studentId);
        System.out.println("Box<Integer> value : " + semester);

        Pair<String, Double> studentCgpa = new Pair<>("Bhavy Pan", 9.15);
        Pair<Integer, String> courseInfo = new Pair<>(203, "Object-Oriented Programming");
        System.out.println("Pair<String,Double>: " + studentCgpa);
        System.out.println("Pair<Integer,String>: " + courseInfo);

        String[] langs = {"Java", "Python", "C++"};
        Integer[] primes = {2, 3, 5, 7, 11};
        System.out.println("First of String[]  : " + firstElement(langs));
        System.out.println("First of Integer[] : " + firstElement(primes));
    }
}
