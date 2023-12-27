import java.io.*;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

import compiler.sintactic.ParserSym;

%%
%standalone
%cup

%public
%class Scanner

%char
%line
%column

%eofval{
  return symbol(ParserSym.EOF);
%eofval}

// Declaraciones

LETRA = [a-zA-Z]
DIGITO = [1-9][0-9]*
ZERO = 0
ENTERO = \-?{DIGITO}
ID = {LETRA}+({LETRA}|{DIGITO}|'_')*

// Palabras reservadas
SI = si
SINO = sino
SINOSI = sinosi
PARA = para
MIENTRAS = mientras
HACER = hacer
ROMPER = romper
ENTERO = ent
BOOLEANO = bool
CARACTER = car
CONST = const
FUNCION = funcion
DEVUELVE = devuelve
CONTINUAR = continuar
IMPRIMIR = imprimir
LEERENT = leerEnt{PAR_A}{PAR_C}
LEERCAR = leerCar{PAR_A}{PAR_C}
LEERBOOL = leerBool{PAR_A}{PAR_C}
PRINCIPAL = principal
VERDADERO = verdadero
FALSO = falso
VACIO = vacio

// Simbolos
SUMA = \+
RESTA = \-
MULT = \*
DIV = \\
MOD = \%
Y = [yY]
O = [oO]
IGUAL = \=
MAQ = \>
MEQ = \<
MAI = {MAQ}{IGUAL}
MEI = {MEQ}{IGUAL}
D_PUNTOS = :
ASIG = {D_PUNTOS}{IGUAL}
PAR_A = \(
PAR_C = \)
LLAVE_A = \{
LLAVE_C = \}
CORCHETE_A = \[
CORCHETE_C = \]
COMILLAS = \"
P_COMA = ;
COMA = ,
CADENA = {COMILLAS}.*{COMILLAS}
INI_COMT = ##
A_COMT_M = \/\*
C_COMT_M = \*\/
ESPACIO = (' '|'\r'|{SALTO_LINEA}|'\f')+
SALTO_LINEA = \n

%{
    /***
       Mecanismo de gestión de símbolos basado en ComplexSymbol
     ***/

    /**
       Construcción de un símbolo sin valor asociado.
    **/

    private ComplexSymbol symbol(int type) {
        Location l = new Location(yyline+1, yycolumn+1); // primera posición del token
        Location r = new Location(yyline+1, yycolumn+1+yylength()); // ultima posición del token
        ComplexSymbol c = new ComplexSymbol(ParserSym.terminalNames[type], type, l, r);
        return  c;
    }

    /**
       Construcción de un simbolo con atributo asociado.
    **/
    private ComplexSymbol symbol(int type, Object value){
        Location l = new Location(yyline+1, yycolumn+1); // primera posición del token
        Location r = new Location(yyline+1, yycolumn+1+yylength()); // ultima posición del token
        ComplexSymbol c = new ComplexSymbol(ParserSym.terminalNames[type], type, l, r, value);
        return  c;
    }

    public static void main(String []args) {
        if (args.length < 1) {
            System.err.println("Indica un fitxer amb les dades d'entrada");

            System.exit(0);
        }
        try {
            FileReader in = new FileReader(args[0]);
            Scanner parser = new Scanner(in);
            parser.yylex();
        } catch (FileNotFoundException e) {
                System.err.println("El fitxer d'entrada '"+args[0]+"' no existeix");
        } catch (IOException e) {
                System.err.println("Error processant el fitxer d'entrada");
        }
    }
%}

%%

{PRINCIPAL}     { return symbol(Parsersym.PRINCIPAL); }
{VERDADERO}     { return symbol(Parsersym.VERDADERO); }
{FALSO}         { return symbol(Parsersym.FALSO); }
{VACIO}         { return symbol(Parsersym.VACIO); }
{DIGITO}        { return symbol(ParserSym.valor, Integer.parseInt(this.yytext())); }
{ZERO}          { return symbol(ParserSym.valor, 0.0); }

// TIPOS
{ENTERO}        { return symbol(Parsersym.ENTERO); }
{BOOLEANO}      { return symbol(Parsersym.BOOLEANO); }
{CARACTER}      { return symbol(Parsersym.CARACTER); }
{CONST}         { return symbol(Parsersym.CONST); }

// Condicional
{SI}            { return symbol(Parsersym.SI); }
{SINO}          { return symbol(Parsersym.SINO); }
{SINOSI}        { return symbol(Parsersym.SINOSI); }

// Bucles
{PARA}          { return symbol(Parsersym.PARA); }
{MIENTRAS}      { return symbol(Parsersym.MIENTRAS); }
{HACER}         { return symbol(Parsersym.HACER); }
{ROMPER}        { return symbol(Parsersym.ROMPER); }

// Funciones
{FUNCION}       { return symbol(Parsersym.FUNCION); }
{DEVUELVE}      { return symbol(Parsersym.DEVUELVE); }
{CONTINUAR}     { return symbol(Parsersym.CONTINUAR); }

// Entrada/Salida
{IMPRIMIR}      { return symbol(Parsersym.IMPRIMIR); }
{LEERENT}       { return symbol(Parsersym.LEERENT); }
{LEERCAR}       { return symbol(Parsersym.LEERCAR); }
{LEERBOOL}      { return symbol(Parsersym.LEERBOOL); }

// Simbolos

{SUMA}          { return symbol(Parsersym.SUMA); }
{RESTA}         { return symbol(Parsersym.RESTA); }
{MULT}          { return symbol(Parsersym.MULT); }
{DIV}           { return symbol(Parsersym.DIV); }
{MOD}           { return symbol(Parsersym.MOD); }
{Y}             { return symbol(Parsersym.Y); }
{O}             { return symbol(Parsersym.O); }
{IGUAL}         { return symbol(Parsersym.IGUAL); }
{MAQ}           { return symbol(Parsersym.MAQ); }
{MEQ}           { return symbol(Parsersym.MEQ); }
{MAI}           { return symbol(Parsersym.MAI); }
{MEI}           { return symbol(Parsersym.MEI); }
{D_PUNTOS}      { return symbol(Parsersym.D_PUNTOS); }
{ASIG}          { return symbol(Parsersym.ASIG); }


{PAR_A}         { return symbol(Parsersym.PAR_A); }
{PAR_C}         { return symbol(Parsersym.PAR_C); }
{LLAVE_A}       { return symbol(Parsersym.LLAVE_A); }
{LLAVE_C}       { return symbol(Parsersym.LLAVE_C); }
{CORCHETE_A}    { return symbol(Parsersym.CORCHETE_A); }
{CORCHETE_C}    { return symbol(Parsersym.CORCHETE_C); }
{COMILLAS}      { return symbol(Parsersym.COMILLAS); }
{P_COMA}        { return symbol(Parsersym.P_COMA); }
{COMA}          { return symbol(Parsersym.COMA); }
{CADENA}        { return symbol(Parsersym.CADENA, yytext()); }
{INI_COMT}      { return symbol(Parsersym.INI_COMT); }
{A_COMT_M}      { return symbol(Parsersym.A_COMT_M); }
{C_COMT_M}      { return symbol(Parsersym.C_COMT_M); }
{SALTO_LINEA}   { return symbol(Parsersym.SALTO_LINEA); }
{ESPACIO}       { /* Ignorar espacios */ }

{ID}            { return symbol(sym.ID, this.yytext()); }


[@]               { return symbol(ParserSym.error); }

