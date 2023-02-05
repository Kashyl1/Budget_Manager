package budget;

public record Zakupy(String nazwa, double cena) {

    @Override
    public String toString() {
        return String.format("%s $%.2f", nazwa, cena);
    }
}