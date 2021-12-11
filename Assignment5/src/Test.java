import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File("./Assignment5/local-inject.properties");
        System.out.println(file.canRead());
        System.out.println(file.getAbsoluteFile());
    }
}
