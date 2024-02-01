package compiler.grammar;
import java.io.*;
import java.util.ArrayList;

import compiler.ErrorC;
import compiler.sintactic.Fase;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

import compiler.grammar.ParserSym;

%%
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
DIGITO = 0 | [\+\-]?[1-9][0-9]*
ID = {LETRA}+({LETRA}|[_0-9])*

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
NO = no

// Simbolos
SUMA = \+
RESTA = \-
MULT = \*
DIV = \/
MOD = \%
Y = [yY]
O = [oO]
IGUAL = \=
IGUALNT = \!\=
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
COMENTARIO = ## .* [\r|\n|\r\n]?
MULT_COMT = \/\*[\s\S]*\*\/

ESPACIO = [' '| \t|\r|\f]+
SALTO_LINEA = \n
VAL_LETRA = {COMILLAS}{LETRA}{COMILLAS}

ERROR = [^]

%{
    /***
       Mecanismo de gestión de símbolos basado en ComplexSymbol
     ***/

    public ArrayList<ComplexSymbol> tokens = new ArrayList<>();

    /**
       Construcción de un símbolo sin valor asociado.
    **/

    private ComplexSymbol symbol(int type) {
        Location l = new Location(yyline+1, yycolumn+1); // primera posición del token
        Location r = new Location(yyline+1, yycolumn+1+yylength()); // ultima posición del token
        ComplexSymbol c = new ComplexSymbol(ParserSym.terminalNames[type], type, l, r);
        tokens.add(c);
        return c;
    }

    /**
       Construcción de un simbolo con atributo asociado.
    **/
    private ComplexSymbol symbol(int type, Object value){
        Location l = new Location(yyline+1, yycolumn+1); // primera posición del token
        Location r = new Location(yyline+1, yycolumn+1+yylength()); // ultima posición del token
        ComplexSymbol c = new ComplexSymbol(ParserSym.terminalNames[type], type, l, r, value);
        tokens.add(c);
        return c;
    }
%}

%%

{PRINCIPAL}     { return symbol(ParserSym.PRINCIPAL);               }
{VERDADERO}     { return symbol(ParserSym.VAL_BOL, "verdadero");    }
{FALSO}         { return symbol(ParserSym.VAL_BOL, "falso");        }
{VACIO}         { return symbol(ParserSym.VACIO);                   }
{DIGITO}        { return symbol(ParserSym.DIGITO, this.yytext());   }
{VAL_LETRA}     { return symbol(ParserSym.VAL_LETRA, this.yytext());}

// TIPOS
{ENTERO}        { return symbol(ParserSym.ENTERO); }
{BOOLEANO}      { return symbol(ParserSym.BOOLEANO); }
{CARACTER}      { return symbol(ParserSym.CARACTER); }
{CONST}         { return symbol(ParserSym.CONST); }

// Condicional
{SI}            { return symbol(ParserSym.SI); }
{SINO}          { return symbol(ParserSym.SINO); }
{SINOSI}        { return symbol(ParserSym.SINOSI); }

// Bucles
{PARA}          { return symbol(ParserSym.PARA); }
{MIENTRAS}      { return symbol(ParserSym.MIENTRAS); }
{HACER}         { return symbol(ParserSym.HACER); }
{ROMPER}        { return symbol(ParserSym.ROMPER); }

// Funciones
{FUNCION}       { return symbol(ParserSym.FUNCION); }
{DEVUELVE}      { return symbol(ParserSym.DEVUELVE); }
{CONTINUAR}     { return symbol(ParserSym.CONTINUAR); }

// Entrada/Salida
{IMPRIMIR}      { return symbol(ParserSym.IMPRIMIR); }
{LEERENT}       { return symbol(ParserSym.LEERENT); }
{LEERCAR}       { return symbol(ParserSym.LEERCAR); }
{LEERBOOL}      { return symbol(ParserSym.LEERBOOL); }

// Simbolos

{SUMA}          { return symbol(ParserSym.SUMA); }
{RESTA}         { return symbol(ParserSym.RESTA); }
{MULT}          { return symbol(ParserSym.MULT); }
{DIV}           { return symbol(ParserSym.DIV); }
{MOD}           { return symbol(ParserSym.MOD); }
{Y}             { return symbol(ParserSym.Y); }
{O}             { return symbol(ParserSym.O); }
{NO}            { return symbol(ParserSym.NO); }

{IGUAL}         { return symbol(ParserSym.IGUAL); }
{IGUALNT}       { return symbol(ParserSym.IGUALNT); }
{MAQ}           { return symbol(ParserSym.MAQ); }
{MEQ}           { return symbol(ParserSym.MEQ); }
{MAI}           { return symbol(ParserSym.MAI); }
{MEI}           { return symbol(ParserSym.MEI); }
{ASIG}          { return symbol(ParserSym.ASIG); }


{PAR_A}         { return symbol(ParserSym.PAR_A); }
{PAR_C}         { return symbol(ParserSym.PAR_C); }
{LLAVE_A}       { return symbol(ParserSym.LLAVE_A); }
{LLAVE_C}       { return symbol(ParserSym.LLAVE_C); }
{CORCHETE_A}    { return symbol(ParserSym.CORCHETE_A); }
{CORCHETE_C}    { return symbol(ParserSym.CORCHETE_C); }
{P_COMA}        { return symbol(ParserSym.P_COMA); }
{COMA}          { return symbol(ParserSym.COMA); }
{COMENTARIO}    { /* Ignorar comentarios */ }
{MULT_COMT}     { /* Ignorar comentarios */ }
{SALTO_LINEA}   { }
{ESPACIO}       { /* Ignorar espacios */ }

{ID}            { return symbol(ParserSym.ID, this.yytext()); }


{ERROR}         { ErrorC.añadirError(new ErrorC("Token invalido", yyline + 1, Fase.LEXICO)); }

