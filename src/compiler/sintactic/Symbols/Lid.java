package compiler.sintactic.Symbols;

public class Lid extends SimboloBase {

    private String id;
    private Lid lid;

    public Lid(String id, Lid lid, int linea, int columna) {
        super(linea,columna);
        this.id = id;
        this.lid = lid;
    }

    public Lid(String id, int linea, int columna) {
        super(linea,columna);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Lid getLid() {
        return lid;
    }

    public void setLid(Lid lid) {
        this.lid = lid;
    }
}
