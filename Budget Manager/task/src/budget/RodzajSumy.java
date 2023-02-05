package budget;

class RodzajSumy {
    private final String rodzaj;
    private final double calkowitaSuma;

    public RodzajSumy(String rodzaj, double calkowitaSuma) {
        this.rodzaj = rodzaj;
        this.calkowitaSuma = calkowitaSuma;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public double suma() {
        return calkowitaSuma;
    }
}




