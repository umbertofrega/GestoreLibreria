package back.stati;

public enum Stato {
    DA_LEGGERE,
    IN_LETTURA,
    LETTO;

    @Override
    public String toString() {
        return switch (this) {
            case DA_LEGGERE -> "Da Leggere";
            case IN_LETTURA -> "In Lettura";
            case LETTO -> "Letto";
        };
    }
}
