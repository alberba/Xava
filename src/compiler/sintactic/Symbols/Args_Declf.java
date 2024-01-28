package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Args_Declf extends SimboloBase {
    L_args_Declf args;

    public Args_Declf(L_args_Declf args, int linea, int columna){
        super(linea,columna);
        this.args = args;
    }

    public void generarIntermedio(Intermedio intermedio) {
        if(args != null) {
            args.generarIntermedio(intermedio);
        }
    }
}
