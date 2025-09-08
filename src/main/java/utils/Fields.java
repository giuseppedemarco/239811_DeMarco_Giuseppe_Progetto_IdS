package src.main.java.utils;

public enum Fields {
    NOME("nome", true),
    AUTORE("autore", true),
    ISBN("isbn", false),
    GENERE("genere", true),
    VALUTAZIONE("valutazione", false),
    STATO_LETTURA("statoLettura", true);

    private final String column;
    private final boolean ignoreCase;

    Fields(String column, boolean ignoreCase) {
        this.column = column;
        this.ignoreCase = ignoreCase;
    }

    public String getColumn() {
        return column;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }
}
