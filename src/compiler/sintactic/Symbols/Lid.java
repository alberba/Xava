package compiler.sintactic.Symbols;

public class Lid {

    private String id;
    private Lid lid;

    public Lid(String id, Lid lid) {
        this.id = id;
        this.lid = lid;
    }

    public Lid(String id) {
        this.id = id;
    }
}
