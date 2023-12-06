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
LEERENT = leerEnt
LEERCAR = leerCar
LEERBOOL = leerBool
PRINCIPAL = principal
VERDADERO = verdadero
FALSO = falso

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
MEI = {MEW}{IGUAL}
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

{PRINCIPAL}     { return symbol(Parsersym.PRINCIPAL); }{PRINCIPAL}     { return symbol(Parsersym.PRINCIPAL); }
{ID}            { return symbol(sym.ID, this.yytext()); }
{DIGITO}        { return symbol(ParserSym.valor, Integer.parseInt(this.yytext())); }
{ZERO}          { return symbol(ParserSym.valor, 0.0); }

// TIPOS
{ENTERO}        { return symbol(Parsersym.ENTERO); }
{BOOLEANO}      { return symbol(Parsersym.BOOLEANO); }
{CARACTER}      { return symbol(Parsersym.CARACTER); }
{CONSTANTE}     { return symbol(Parsersym.CONSTANTE); }

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
{ESPACIO}       { /* Ignorar espacios */ }
{SALTO_LINEA}   { return symbol(Parsersym.SALTO_LINEA); }


[@]               { return symbol(ParserSym.error); }








//Este es otro trabajo de github
/*
  package CompiladoresMuerte2.Lexic;

  import java.io.*;
  import java_cup.runtime.*;
  import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
  import CompiladoresMuerte2.Sintactic.ParserSym;
  import java_cup.runtime.ComplexSymbolFactory.Location;

  %%
  %standalone
  %cup

  %public
  %class Scanner

  //Tipo tokens identificados
  %char
  %line
  %column

  //End of File
  %eofval{
    return symbol(ParserSym.EOF);
  %eofval}

  //Declaraciones

  espacio = [' '|'\t'|'\r'|'\n']+
  digits = [0-9][0-9]*

  /* Palabras reservadas */
  Int = int
  String = string
  Bool = bool
  Const = const
  If = if
  Else = else
  Switch = switch
  Case = case
  Break = break
  Default = default
  For = for
  While = while
  Function = function
  Return = return
  End = end
  Print = print
  ReadInt = readint
  ReadString = readstring
  True = true
  False = false


  /* Operaciones */
  Igual = \=
  Suma = \+
  Resta = \-
  Mult = \*
  Div = \/
  And = and
  Or = or
  Mayor = \>
  Menor = \<
  Equal = ==
  Not_equal = \!=
  Mayor_equal = \>=
  Menor_equal = \<=
  Increment = \++
  Decrement = \--

  /* Símbolos */
  Parentesis_a = \(
  Parentesis_c = \)
  Llave_a = \{
  Llave_c = \}
  Corchete_a = \[
  Corchete_c = \]

  Main = main

  P_coma = ;
  Coma = ,
  Dos_puntos = :
  Comillas = \"

  Cadena = {Comillas}.*{Comillas}
  id = [A-Za-z_][A-Za-z0-9_]*
  Entero = [-]?{digits}

  %{
      /***
         Mecanismo de gestión de símbolos basado en ComplexSymbol
       ***/
      /**
         Construcción de un símbolo sin valor asociado.
       **/
      private ComplexSymbol symbol(int type) {
          Location l = new Location(yyline+1, yycolumn+1); // primera posiciÃ³ del token
          Location r = new Location(yyline+1, yycolumn+1+yylength()); // ultima posiciÃ³ del token
          ComplexSymbol val = new ComplexSymbol(ParserSym.terminalNames[type], type, l, r);
          ComplexSymbol c = new ComplexSymbol(ParserSym.terminalNames[type], type, l, r, val);
          c.left = yyline+1;
          c.right = yycolumn;
          return  c;
      }

      /**
         Construcción de un simbolo con atributo asociado.
       **/
      private ComplexSymbol symbol(int type, Object value){
          Location l = new Location(yyline+1, yycolumn+1); // primera posiciÃ³ del token
          Location r = new Location(yyline+1, yycolumn+1+yylength()); // ultima posiciÃ³ del token
          ComplexSymbol c = new ComplexSymbol(ParserSym.terminalNames[type], type, l, r, value);
          c.left = yyline+1;
          c.right = yycolumn;
          return  c;
      }

      public int lexema;
  %}

  %%
  // Reglas/acciones

  // Tipos
  {Int}           { return symbol(ParserSym.Int, this.yytext()); }
  {String}        { return symbol(ParserSym.String, this.yytext()); }
  {Bool}          { return symbol(ParserSym.Bool, this.yytext()); }
  {Const}         { return symbol(ParserSym.Const, this.yytext()); }

  // Condicionales
  {If}            { return symbol(ParserSym.If, this.yytext()); }
  {Else}          { return symbol(ParserSym.Else, this.yytext()); }
  {Switch}        { return symbol(ParserSym.Switch, this.yytext()); }
  {Case}          { return symbol(ParserSym.Case, this.yytext()); }
  {Break}         { return symbol(ParserSym.Break, this.yytext()); }
  {Default}       { return symbol(ParserSym.Default, this.yytext()); }

  // Bucles
  {For}           { return symbol(ParserSym.For, this.yytext()); }
  {While}         { return symbol(ParserSym.While, this.yytext()); }
  {Function}      { return symbol(ParserSym.Function, this.yytext()); }
  {Return}        { return symbol(ParserSym.Return, this.yytext()); }
  {End}           { return symbol(ParserSym.End, this.yytext()); }
  {Print}         { return symbol(ParserSym.Print, this.yytext()); }
  {ReadInt}       { return symbol(ParserSym.ReadInt, this.yytext()); }
  {ReadString}    { return symbol(ParserSym.ReadString, this.yytext()); }
  {Igual}         { return symbol(ParserSym.Igual, this.yytext()); }
  {Suma}          { return symbol(ParserSym.Suma, this.yytext()); }
  {Resta}         { return symbol(ParserSym.Resta, this.yytext()); }
  {Mult}          { return symbol(ParserSym.Mult, this.yytext()); }
  {Div}           { return symbol(ParserSym.Div, this.yytext()); }
  {And}           { return symbol(ParserSym.And, this.yytext()); }
  {Or}            { return symbol(ParserSym.Or, this.yytext()); }
  {Mayor}         { return symbol(ParserSym.Mayor, this.yytext()); }
  {Menor}         { return symbol(ParserSym.Menor, this.yytext()); }
  {Equal}         { return symbol(ParserSym.Equal, this.yytext()); }
  {Not_equal}     { return symbol(ParserSym.Not_equal, this.yytext()); }
  {Mayor_equal}   { return symbol(ParserSym.Mayor_equal, this.yytext()); }
  {Menor_equal}   { return symbol(ParserSym.Menor_equal, this.yytext()); }
  {Increment}     { return symbol(ParserSym.Increment, this.yytext()); }
  {Decrement}     { return symbol(ParserSym.Decrement, this.yytext()); }
  {Parentesis_a}  { return symbol(ParserSym.Parentesis_a, this.yytext()); }
  {Parentesis_c}  { return symbol(ParserSym.Parentesis_c, this.yytext()); }
  {Llave_a}       { return symbol(ParserSym.Llave_a, this.yytext()); }
  {Llave_c}       { return symbol(ParserSym.Llave_c, this.yytext()); }
  {Corchete_a}    { return symbol(ParserSym.Corchete_a, this.yytext()); }
  {Corchete_c}    { return symbol(ParserSym.Corchete_c, this.yytext()); }
  {True}          { return symbol(ParserSym.True, this.yytext()); }
  {False}         { return symbol(ParserSym.False, this.yytext()); }

  {Main}          { return symbol(ParserSym.Main, this.yytext()); }

  {P_coma}        { return symbol(ParserSym.P_coma, this.yytext()); }
  {Dos_puntos}    { return symbol(ParserSym.Dos_puntos, this.yytext()); }
  {Coma}          { return symbol(ParserSym.Coma, this.yytext()); }

  /* id es la última porque podría hacer incompatibles las palabras reservadas */
  /* al contener todos los caractéres */
  {Cadena}        { return symbol(ParserSym.Cadena, this.yytext()); }
  {id}            { return symbol(ParserSym.id, this.yytext()); }
  {Entero}        { return symbol(ParserSym.Entero, Integer.parseInt(this.yytext())); }
  {espacio}       { /* Ignorar los espacios */  }

  [^]             { return symbol(ParserSym.error); }*/