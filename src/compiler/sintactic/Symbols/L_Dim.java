package compiler.sintactic.Symbols;

public class L_Dim extends SimboloBase{

    private final int num;

    private final L_Dim l_dim;

    public L_Dim(String num, L_Dim l_dim, int linea, int columna) {
        super(linea, columna);
        this.num = Integer.parseInt(num);
        this.l_dim = l_dim;
    }

    /**
     * Utilizado para obtener el número total de elementos en un array
     * @return número total de elementos en un array
     */
    public int getTamañoArray() {
        if (l_dim == null) {
            return num;
        } else {
            return num * l_dim.getTamañoArray();
        }
    }

    public int getNum() {
        return num;
    }

    public L_Dim getL_dim() {
        return l_dim;
    }

}
