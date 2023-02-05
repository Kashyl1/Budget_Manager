package budget;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Menadzer {
    private final Map<String, List<Zakupy>> zakupy;
    private final Scanner scanner;
    private double pieniadze;
    private double calkowitaSuma;

    private static final String MENU = """
            Choose your action:
            1) Add income
            2) Add purchase
            3) Show list of purchases
            4) Balance
            5) Save
            6) Load
            7) Analyze (Sort)
            0) Exit""";
    private static final String RODZAJE = """
            Choose the type of purchases
            1) Food
            2) Clothes
            3) Entertainment
            4) Other
            5) All
            6) Back""";
    private static final String DODAJ_ZAKUP = """
            
            Choose the type of purchase
            1) Food
            2) Clothes
            3) Entertainment
            4) Other
            5) Back""";
    private static final String SORTOWANIE = """
            How do you want to sort?
            1) Sort all purchases
            2) Sort by type
            3) Sort certain type
            4) Back""";
    private static final String SORTUJRODZAJ = """
            Choose the type of purchase
            1) Food
            2) Clothes
            3) Entertainment
            4) Other""";
    public Menadzer(Scanner scanner) {
        this.scanner = scanner;
        zakupy = new HashMap<>();
        pieniadze = 0.0;
    }

    public void menu() {
        System.out.println(MENU);
        String wybor = scanner.nextLine();
        while (!wybor.equals("0")) {
            String path = "C:\\Users\\assai\\IdeaProjects\\Budget Manager\\Budget Manager\\task\\src\\budget\\purchases.txt";
            switch (wybor) {
                case "1" -> dodajPieniadze();
                case "2" -> dodajZakup();
                case "3" -> pokazZakupy();
                case "4" -> System.out.print("\nBalance: $" + pieniadze + "\n\n");
                case "5" -> {
                    zakupy.remove("5");
                    zapisz(path);
                }
                case "6" -> wczytaj(path);
                case "7" -> sortuj();
            }
            System.out.println(MENU);
            wybor = scanner.nextLine();
        }
        System.out.println("\nBye!");
    }

    private void dodajPieniadze() {
        System.out.println("\nEnter income:");
        pieniadze += Double.parseDouble(scanner.nextLine());
        System.out.println("Income was added!\n");
    }

    private void dodajZakup() {
        System.out.println(DODAJ_ZAKUP);
        String rodzaj = scanner.nextLine();
        while (!rodzaj.equals("5")) {
            System.out.println("\nEnter purchase name:");
            String nazwaProduktu = scanner.nextLine();
            System.out.println("Enter its price:");
            double cena = Double.parseDouble(scanner.nextLine());
            pieniadze -= cena;
            if (this.zakupy.isEmpty()) {
                initMap();
            }
            Zakupy zakupy = new Zakupy(nazwaProduktu, cena);
            this.zakupy.get(rodzaj).add(zakupy);
            this.zakupy.get("5").add(zakupy);
            System.out.println("Purchase was added!");

            System.out.println(DODAJ_ZAKUP);
            rodzaj = scanner.nextLine();
        }
        System.out.println();
    }

    private void pokazZakupy() {
        if (zakupy.isEmpty()) {
            System.out.println("\nThe purchase list is empty\n");
        } else {
            System.out.println("\n" + RODZAJE);
            String rodzaj = scanner.nextLine();

            while (!rodzaj.equals("6")) {
                List<Zakupy> zakup = zakupy.get(rodzaj);

                if (zakup.isEmpty()) {
                    System.out.println(rodzajeZakupow(rodzaj) + "\nThe purchase list is empty!");
                } else {
                    System.out.println(rodzajeZakupow(rodzaj));
                    zakup.forEach(System.out::println);
                    calkowitaSuma = zakup.stream().mapToDouble(Zakupy::cena).sum();
                    calkowitaSuma = Math.round(calkowitaSuma * 100.0) / 100.0;
                    System.out.print("Total sum: $" + calkowitaSuma + "\n\n");
                }

                System.out.println("\n" + RODZAJE);
                rodzaj = scanner.nextLine();
            }
            System.out.println();
        }
    }

    private String rodzajeZakupow(String rodzaj) {
        System.out.println();
        return switch (rodzaj) {
            case "1" -> "Food:";
            case "2" -> "Clothes:";
            case "3" -> "Entertainment:";
            case "4" -> "Other:";
            case "5" -> "All:";
            default -> "Unknown category!";
        };
    }

    private void initMap() {
        zakupy.put("1",new ArrayList<>());
        zakupy.put("2",new ArrayList<>());
        zakupy.put("3",new ArrayList<>());
        zakupy.put("4",new ArrayList<>());
        zakupy.put("5",new ArrayList<>());
    }
    private void sortuj() {
            System.out.println("\n" + SORTOWANIE);
            String opcje = scanner.nextLine();
            while (!opcje.equals("4")) {
                switch (opcje) {
                    case "1":
                        if (zakupy.isEmpty()) {
                            System.out.println("\nThe purchase list is empty!");
                        } else {
                            List<Zakupy> zakup = zakupy.get("5");
                            System.out.println("\nAll:");
                            zakup.sort(Comparator.comparingDouble(Zakupy::cena).reversed());
                            zakup.forEach(System.out::println);
                            calkowitaSuma = zakup.stream().mapToDouble(Zakupy::cena).sum();
                            calkowitaSuma = Math.round(calkowitaSuma * 100.0) / 100.0;
                            System.out.print("Total: $" + calkowitaSuma + "\n");
                        }
                        break;
                    case "2":
                        sortujRodzaje();
                        break;
                    case "3":
                        sortujRodzaj();
                        break;
                }
                System.out.println("\n" + SORTOWANIE);
                opcje = scanner.nextLine();
            }
        System.out.println();
    }
    private void sortujRodzaj() {
        if (zakupy.isEmpty()) {
            initMap();
        }
            System.out.println("\n" + SORTUJRODZAJ);
            String rodzaj = scanner.nextLine();
            List<Zakupy> zakup = zakupy.get(rodzaj);
            if (zakup.isEmpty()) {
                System.out.println("\nThe purchase list is empty!");
            } else {
                System.out.println(rodzajeZakupow(rodzaj));
                zakup.sort(Comparator.comparingDouble(Zakupy::cena).reversed());
                zakup.forEach(System.out::println);
                calkowitaSuma = zakup.stream().mapToDouble(Zakupy::cena).sum();
                calkowitaSuma = Math.round(calkowitaSuma * 100.0) / 100.0;
                System.out.print("Total sum: " + calkowitaSuma + "\n");
            }
    }
    private void sortujRodzaje() {
        if (zakupy.isEmpty()) {
            System.out.println("""
                    
                    Types:
                    Food - $0
                    Entertainment - $0
                    Clothes - $0
                    Other - $0
                    Total sum: $0""");
        } else {
            List<RodzajSumy> rodzaje = new ArrayList<>();

            for (int i = 1; i <= 4; i++) {
                List<Zakupy> zakup = zakupy.get(Integer.toString(i));
                double suma = zakup.stream().mapToDouble(Zakupy::cena).sum();
                suma = Math.round(suma * 100.0) / 100.0;
                rodzaje.add(new RodzajSumy(rodzajeZakupow(Integer.toString(i)), suma));
            }
            rodzaje.sort(Comparator.comparingDouble(RodzajSumy::suma).reversed());
            for (RodzajSumy rodzaj : rodzaje) {
                String rodzajString = rodzaj.getRodzaj();
                int index = rodzajString.indexOf(":");
                if (index != -1) {
                    rodzajString = rodzajString.substring(0, index);
                }
                System.out.println(rodzajString + " - $" + rodzaj.suma());
            }
        }
    }
    private void zapisz(String path) {
        try (PrintWriter writer = new PrintWriter(path)) {
            writer.write(Double.toString(pieniadze));
            writer.write("\n");
            for (Map.Entry<String, List<Zakupy>> entry : zakupy.entrySet()) {
                String rodzaj = entry.getKey();
                List<Zakupy> zakupyList = entry.getValue();
                for (Zakupy zakup : zakupyList) {
                    String line = rodzaj + "," + zakup.nazwa() + "," + zakup.cena();
                    writer.println(line);
                }
            }
            System.out.println("""

                    Purchases saved to file purchases.txt
                    """);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the purchases");
        }
    }
    private void wczytaj(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            String pieniadzeString = scanner.nextLine();
            pieniadze = Double.parseDouble(pieniadzeString);
            while(scanner.hasNextLine()) {

                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String rodzaj = parts[0];
                String nazwaProduktu = parts[1];
                double cena = Double.parseDouble(parts[2]);
                Zakupy zakup = new Zakupy(nazwaProduktu, cena);
                if (this.zakupy.isEmpty()) {
                    initMap();
                }
                this.zakupy.get(rodzaj).add(zakup);
                if (!this.zakupy.containsKey("5")) {
                    this.zakupy.put("5", new ArrayList<>());
                }
                this.zakupy.get("5").add(zakup);
            }
            System.out.println("""

                    Purchases loaded to file purchases.txt
                    """);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
