package budget;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menadzer app = new Menadzer(scanner);
        app.menu();
    }
}