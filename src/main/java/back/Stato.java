package back;

public enum Stato {
    DA_LEGGERE,
    IN_LETTURA,
    LETTO;

    @Override
    public String toString() {
        switch (this) {
            case DA_LEGGERE:
                return "Da Leggere";
            case IN_LETTURA:
                return "In Lettura";
            case LETTO:
                return "Letto";
        }
        return null;
    }
}
