import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What location?");
        String location = scanner.nextLine();
        ApiCall caller = new ApiCall(location);

    }
}