// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: src/compiler/lexic/lexic.flex

package compiler.grammar;
import java.io.*;
import java.util.ArrayList;

import compiler.ErrorC;
import compiler.sintactic.Fase;
import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

import compiler.grammar.ParserSym;


// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class ScannerOld implements java_cup.runtime.Scanner {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0, 0
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\37\u0100\1\u0200\267\u0100\10\u0300\u1020\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\3\1\4\1\5\22\0\1\1"+
    "\1\6\1\7\1\10\1\0\1\11\1\0\1\1\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22"+
    "\11\23\1\24\1\25\1\26\1\27\1\30\2\0\1\31"+
    "\1\32\1\33\1\31\1\34\11\31\1\35\11\31\1\36"+
    "\1\31\1\37\1\0\1\40\1\0\1\41\1\0\1\42"+
    "\1\43\1\44\1\45\1\46\1\47\1\31\1\50\1\51"+
    "\2\31\1\52\1\53\1\54\1\55\1\56\1\31\1\57"+
    "\1\60\1\61\1\62\1\63\2\31\1\36\1\31\1\64"+
    "\1\65\1\66\7\0\1\3\u01a2\0\2\3\326\0\u0100\3";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1024];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\1\1\4\1\1\1\5"+
    "\1\6\1\7\1\10\1\11\1\12\1\13\1\14\2\15"+
    "\1\1\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\16\22\1\27\1\30\1\31\1\32\1\0"+
    "\1\33\1\34\1\35\13\22\1\36\3\22\1\37\2\22"+
    "\1\32\1\0\1\22\1\40\2\22\1\41\14\22\1\42"+
    "\11\22\1\43\2\22\1\44\2\22\1\45\2\22\1\46"+
    "\1\22\1\47\10\22\1\50\12\22\1\51\1\52\3\22"+
    "\1\53\10\22\1\54\1\55\1\22\2\0\1\56\2\22"+
    "\1\57\1\0\1\60\1\61\1\62\1\63\1\64";

  private static int [] zzUnpackAction() {
    int [] result = new int[156];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\67\0\156\0\67\0\245\0\67\0\334\0\67"+
    "\0\67\0\67\0\67\0\u0113\0\67\0\u0113\0\u014a\0\67"+
    "\0\u0181\0\u01b8\0\67\0\u01ef\0\67\0\u0226\0\u025d\0\u025d"+
    "\0\u025d\0\67\0\67\0\u0294\0\u02cb\0\u0302\0\u0339\0\u0370"+
    "\0\u03a7\0\u03de\0\u0415\0\u044c\0\u0483\0\u04ba\0\u04f1\0\u0528"+
    "\0\u055f\0\67\0\67\0\67\0\u0596\0\u05cd\0\67\0\67"+
    "\0\67\0\u0604\0\u063b\0\u0672\0\u06a9\0\u06e0\0\u0717\0\u074e"+
    "\0\u0785\0\u07bc\0\u07f3\0\u082a\0\u025d\0\u0861\0\u0898\0\u08cf"+
    "\0\u0906\0\u093d\0\u0974\0\67\0\u09ab\0\u09e2\0\u025d\0\u0a19"+
    "\0\u0a50\0\u025d\0\u0a87\0\u0abe\0\u0af5\0\u0b2c\0\u0b63\0\u0b9a"+
    "\0\u0bd1\0\u0c08\0\u0c3f\0\u0c76\0\u0cad\0\u0ce4\0\u025d\0\u0d1b"+
    "\0\u0d52\0\u0d89\0\u0dc0\0\u0df7\0\u0e2e\0\u0e65\0\u0e9c\0\u0ed3"+
    "\0\u025d\0\u0f0a\0\u0f41\0\u0f78\0\u0faf\0\u0fe6\0\u025d\0\u101d"+
    "\0\u1054\0\u025d\0\u108b\0\u025d\0\u10c2\0\u10f9\0\u1130\0\u1167"+
    "\0\u119e\0\u11d5\0\u120c\0\u1243\0\u025d\0\u127a\0\u12b1\0\u12e8"+
    "\0\u131f\0\u1356\0\u138d\0\u13c4\0\u13fb\0\u1432\0\u1469\0\u025d"+
    "\0\u025d\0\u14a0\0\u14d7\0\u150e\0\u025d\0\u1545\0\u157c\0\u15b3"+
    "\0\u15ea\0\u1621\0\u1658\0\u168f\0\u16c6\0\u025d\0\u025d\0\u16fd"+
    "\0\u1734\0\u176b\0\u025d\0\u17a2\0\u17d9\0\u025d\0\u1810\0\67"+
    "\0\67\0\u025d\0\u025d\0\67";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[156];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\2\2\3\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\2"+
    "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26"+
    "\4\27\1\30\1\31\1\32\1\33\1\2\1\27\1\34"+
    "\1\35\1\36\1\37\1\40\1\41\1\42\1\43\1\44"+
    "\1\45\1\30\1\46\1\47\1\50\2\27\1\51\1\52"+
    "\1\3\1\53\70\0\1\3\2\0\2\3\57\0\1\3"+
    "\30\0\1\54\47\0\1\55\101\0\1\21\57\0\1\56"+
    "\74\0\2\21\72\0\1\57\66\0\1\60\66\0\1\61"+
    "\61\0\2\27\5\0\6\27\2\0\23\27\25\0\2\27"+
    "\5\0\6\27\2\0\14\27\1\62\6\27\25\0\2\27"+
    "\5\0\6\27\2\0\1\27\1\63\12\27\1\64\6\27"+
    "\25\0\2\27\5\0\6\27\2\0\5\27\1\65\15\27"+
    "\25\0\2\27\5\0\6\27\2\0\13\27\1\66\7\27"+
    "\25\0\2\27\5\0\6\27\2\0\1\27\1\67\17\27"+
    "\1\70\1\27\25\0\2\27\5\0\6\27\2\0\1\27"+
    "\1\71\21\27\25\0\2\27\5\0\6\27\2\0\12\27"+
    "\1\72\10\27\25\0\2\27\5\0\6\27\2\0\5\27"+
    "\1\73\15\27\25\0\2\27\5\0\6\27\2\0\10\27"+
    "\1\74\12\27\25\0\2\27\5\0\6\27\2\0\14\27"+
    "\1\75\6\27\25\0\2\27\5\0\6\27\2\0\1\27"+
    "\1\76\14\27\1\77\4\27\25\0\2\27\5\0\6\27"+
    "\2\0\14\27\1\100\6\27\25\0\2\27\5\0\6\27"+
    "\2\0\10\27\1\101\12\27\25\0\2\27\5\0\6\27"+
    "\2\0\1\27\1\102\3\27\1\103\15\27\3\0\2\55"+
    "\1\104\2\0\1\104\61\55\2\0\1\56\2\0\1\56"+
    "\6\0\1\105\3\0\1\56\44\0\1\56\23\0\2\27"+
    "\5\0\6\27\2\0\14\27\1\106\6\27\25\0\2\27"+
    "\5\0\6\27\2\0\16\27\1\107\4\27\25\0\2\27"+
    "\5\0\6\27\2\0\13\27\1\110\7\27\25\0\2\27"+
    "\5\0\6\27\2\0\22\27\1\111\25\0\2\27\5\0"+
    "\6\27\2\0\20\27\1\112\2\27\25\0\2\27\5\0"+
    "\6\27\2\0\11\27\1\113\11\27\25\0\2\27\5\0"+
    "\6\27\2\0\13\27\1\114\7\27\25\0\2\27\5\0"+
    "\6\27\2\0\3\27\1\115\17\27\25\0\2\27\5\0"+
    "\6\27\2\0\15\27\1\116\5\27\25\0\2\27\5\0"+
    "\6\27\2\0\5\27\1\117\15\27\25\0\2\27\5\0"+
    "\6\27\2\0\5\27\1\120\15\27\25\0\2\27\5\0"+
    "\6\27\2\0\16\27\1\121\4\27\25\0\2\27\5\0"+
    "\6\27\2\0\10\27\1\122\12\27\25\0\2\27\5\0"+
    "\6\27\2\0\12\27\1\123\10\27\25\0\2\27\5\0"+
    "\6\27\2\0\13\27\1\124\7\27\25\0\2\27\5\0"+
    "\6\27\2\0\3\27\1\125\17\27\25\0\2\27\5\0"+
    "\6\27\2\0\16\27\1\126\4\27\24\0\1\104\67\0"+
    "\2\27\5\0\6\27\2\0\11\27\1\127\11\27\25\0"+
    "\2\27\5\0\6\27\2\0\17\27\1\130\1\131\2\27"+
    "\25\0\2\27\5\0\6\27\2\0\21\27\1\132\1\27"+
    "\25\0\2\27\5\0\6\27\2\0\17\27\1\133\3\27"+
    "\25\0\2\27\5\0\6\27\2\0\3\27\1\134\17\27"+
    "\25\0\2\27\5\0\6\27\2\0\5\27\1\135\15\27"+
    "\25\0\2\27\5\0\6\27\2\0\16\27\1\136\4\27"+
    "\25\0\2\27\5\0\6\27\2\0\16\27\1\137\4\27"+
    "\25\0\2\27\5\0\6\27\2\0\13\27\1\140\7\27"+
    "\25\0\2\27\5\0\6\27\2\0\1\27\1\141\21\27"+
    "\25\0\2\27\5\0\6\27\2\0\13\27\1\142\7\27"+
    "\25\0\2\27\5\0\6\27\2\0\15\27\1\143\5\27"+
    "\25\0\2\27\5\0\6\27\2\0\14\27\1\144\6\27"+
    "\25\0\2\27\5\0\6\27\2\0\10\27\1\145\12\27"+
    "\25\0\2\27\5\0\6\27\2\0\4\27\1\146\16\27"+
    "\25\0\2\27\5\0\6\27\2\0\20\27\1\147\2\27"+
    "\25\0\2\27\5\0\6\27\2\0\10\27\1\150\12\27"+
    "\25\0\2\27\5\0\6\27\2\0\5\27\1\151\15\27"+
    "\25\0\2\27\5\0\6\27\2\0\14\27\1\152\6\27"+
    "\25\0\2\27\5\0\6\27\2\0\10\27\1\153\12\27"+
    "\25\0\2\27\5\0\6\27\2\0\16\27\1\154\4\27"+
    "\25\0\2\27\5\0\6\27\2\0\10\27\1\155\12\27"+
    "\25\0\2\27\5\0\1\27\1\156\1\157\1\160\2\27"+
    "\2\0\23\27\25\0\2\27\5\0\6\27\2\0\20\27"+
    "\1\161\2\27\25\0\2\27\5\0\6\27\2\0\3\27"+
    "\1\162\17\27\25\0\2\27\5\0\6\27\2\0\5\27"+
    "\1\163\15\27\25\0\2\27\5\0\6\27\2\0\17\27"+
    "\1\164\3\27\25\0\2\27\5\0\6\27\2\0\14\27"+
    "\1\165\6\27\25\0\2\27\5\0\6\27\2\0\1\27"+
    "\1\166\21\27\25\0\2\27\5\0\6\27\2\0\13\27"+
    "\1\167\7\27\25\0\2\27\5\0\6\27\2\0\11\27"+
    "\1\170\11\27\25\0\2\27\5\0\6\27\2\0\14\27"+
    "\1\171\6\27\25\0\2\27\5\0\6\27\2\0\12\27"+
    "\1\172\10\27\25\0\2\27\5\0\6\27\2\0\14\27"+
    "\1\173\6\27\25\0\2\27\5\0\6\27\2\0\1\27"+
    "\1\174\21\27\25\0\2\27\5\0\6\27\2\0\13\27"+
    "\1\175\7\27\25\0\2\27\5\0\6\27\2\0\16\27"+
    "\1\176\4\27\25\0\2\27\5\0\6\27\2\0\10\27"+
    "\1\177\12\27\25\0\2\27\5\0\6\27\2\0\16\27"+
    "\1\200\4\27\25\0\2\27\5\0\6\27\2\0\10\27"+
    "\1\201\12\27\25\0\2\27\5\0\6\27\2\0\4\27"+
    "\1\202\16\27\25\0\2\27\5\0\6\27\2\0\21\27"+
    "\1\203\1\27\25\0\2\27\5\0\6\27\2\0\22\27"+
    "\1\204\25\0\2\27\5\0\6\27\2\0\13\27\1\205"+
    "\7\27\25\0\2\27\5\0\6\27\2\0\10\27\1\206"+
    "\12\27\25\0\2\27\5\0\6\27\2\0\14\27\1\207"+
    "\6\27\25\0\2\27\5\0\6\27\2\0\16\27\1\210"+
    "\4\27\25\0\2\27\5\0\6\27\2\0\20\27\1\211"+
    "\2\27\25\0\2\27\5\0\6\27\2\0\1\27\1\212"+
    "\21\27\25\0\2\27\5\0\6\27\2\0\15\27\1\213"+
    "\5\27\25\0\2\27\5\0\6\27\2\0\5\27\1\214"+
    "\15\27\25\0\2\27\5\0\6\27\2\0\1\27\1\215"+
    "\21\27\25\0\2\27\5\0\6\27\2\0\5\27\1\216"+
    "\15\27\25\0\2\27\5\0\6\27\2\0\16\27\1\217"+
    "\4\27\25\0\2\27\5\0\6\27\2\0\11\27\1\220"+
    "\11\27\15\0\1\221\7\0\2\27\5\0\6\27\2\0"+
    "\23\27\15\0\1\222\7\0\2\27\5\0\6\27\2\0"+
    "\23\27\25\0\2\27\5\0\6\27\2\0\17\27\1\223"+
    "\3\27\25\0\2\27\5\0\6\27\2\0\1\27\1\224"+
    "\21\27\25\0\2\27\5\0\6\27\2\0\16\27\1\225"+
    "\4\27\25\0\2\27\5\0\6\27\2\0\16\27\1\226"+
    "\4\27\15\0\1\227\7\0\2\27\5\0\6\27\2\0"+
    "\23\27\16\0\1\230\66\0\1\231\75\0\2\27\5\0"+
    "\6\27\2\0\11\27\1\232\11\27\25\0\2\27\5\0"+
    "\6\27\2\0\14\27\1\233\6\27\16\0\1\234\53\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[6215];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\1\1\1\11\1\1\1\11\1\1\4\11"+
    "\1\1\1\11\2\1\1\11\2\1\1\11\1\1\1\11"+
    "\4\1\2\11\16\1\3\11\1\1\1\0\3\11\22\1"+
    "\1\11\1\0\113\1\2\0\4\1\1\0\2\11\2\1"+
    "\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[156];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;

  /* user code: */
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
        return  c;
    }

    /**
       Construcción de un simbolo con atributo asociado.
    **/
    private ComplexSymbol symbol(int type, Object value){
        Location l = new Location(yyline+1, yycolumn+1); // primera posición del token
        Location r = new Location(yyline+1, yycolumn+1+yylength()); // ultima posición del token
        ComplexSymbol c = new ComplexSymbol(ParserSym.terminalNames[type], type, l, r, value);
        tokens.add(c);
        return  c;
    }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public ScannerOld(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
  yyclose();    }
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  @Override  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
          {   return symbol(ParserSym.EOF);
 }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { ErrorC.añadirError(new ErrorC("Token invalido", yyline + 1, Fase.LEXICO));
            }
            // fall through
          case 53: break;
          case 2:
            { /* Ignorar espacios */
            }
            // fall through
          case 54: break;
          case 3:
            { 
            }
            // fall through
          case 55: break;
          case 4:
            { return symbol(ParserSym.COMILLAS);
            }
            // fall through
          case 56: break;
          case 5:
            { return symbol(ParserSym.MOD);
            }
            // fall through
          case 57: break;
          case 6:
            { return symbol(ParserSym.PAR_A);
            }
            // fall through
          case 58: break;
          case 7:
            { return symbol(ParserSym.PAR_C);
            }
            // fall through
          case 59: break;
          case 8:
            { return symbol(ParserSym.MULT);
            }
            // fall through
          case 60: break;
          case 9:
            { return symbol(ParserSym.SUMA);
            }
            // fall through
          case 61: break;
          case 10:
            { return symbol(ParserSym.COMA);
            }
            // fall through
          case 62: break;
          case 11:
            { return symbol(ParserSym.RESTA);
            }
            // fall through
          case 63: break;
          case 12:
            { return symbol(ParserSym.DIV);
            }
            // fall through
          case 64: break;
          case 13:
            { return symbol(ParserSym.DIGITO, this.yytext());
            }
            // fall through
          case 65: break;
          case 14:
            { return symbol(ParserSym.P_COMA);
            }
            // fall through
          case 66: break;
          case 15:
            { return symbol(ParserSym.MEQ);
            }
            // fall through
          case 67: break;
          case 16:
            { return symbol(ParserSym.IGUAL);
            }
            // fall through
          case 68: break;
          case 17:
            { return symbol(ParserSym.MAQ);
            }
            // fall through
          case 69: break;
          case 18:
            { return symbol(ParserSym.ID, this.yytext());
            }
            // fall through
          case 70: break;
          case 19:
            { return symbol(ParserSym.O);
            }
            // fall through
          case 71: break;
          case 20:
            { return symbol(ParserSym.Y);
            }
            // fall through
          case 72: break;
          case 21:
            { return symbol(ParserSym.CORCHETE_A);
            }
            // fall through
          case 73: break;
          case 22:
            { return symbol(ParserSym.CORCHETE_C);
            }
            // fall through
          case 74: break;
          case 23:
            { return symbol(ParserSym.LLAVE_A);
            }
            // fall through
          case 75: break;
          case 24:
            { return symbol(ParserSym.LLAVE_C);
            }
            // fall through
          case 76: break;
          case 25:
            { return symbol(ParserSym.IGUALNT);
            }
            // fall through
          case 77: break;
          case 26:
            { /* Ignorar comentarios */
            }
            // fall through
          case 78: break;
          case 27:
            { return symbol(ParserSym.ASIG);
            }
            // fall through
          case 79: break;
          case 28:
            { return symbol(ParserSym.MEI);
            }
            // fall through
          case 80: break;
          case 29:
            { return symbol(ParserSym.MAI);
            }
            // fall through
          case 81: break;
          case 30:
            { return symbol(ParserSym.NO);
            }
            // fall through
          case 82: break;
          case 31:
            { return symbol(ParserSym.SI);
            }
            // fall through
          case 83: break;
          case 32:
            { return symbol(ParserSym.CARACTER);
            }
            // fall through
          case 84: break;
          case 33:
            { return symbol(ParserSym.ENTERO);
            }
            // fall through
          case 85: break;
          case 34:
            { return symbol(ParserSym.BOOLEANO);
            }
            // fall through
          case 86: break;
          case 35:
            { return symbol(ParserSym.PARA);
            }
            // fall through
          case 87: break;
          case 36:
            { return symbol(ParserSym.SINO);
            }
            // fall through
          case 88: break;
          case 37:
            { return symbol(ParserSym.CONST);
            }
            // fall through
          case 89: break;
          case 38:
            { return symbol(ParserSym.VAL_BOL, "falso");
            }
            // fall through
          case 90: break;
          case 39:
            { return symbol(ParserSym.HACER);
            }
            // fall through
          case 91: break;
          case 40:
            { return symbol(ParserSym.VACIO);
            }
            // fall through
          case 92: break;
          case 41:
            { return symbol(ParserSym.ROMPER);
            }
            // fall through
          case 93: break;
          case 42:
            { return symbol(ParserSym.SINOSI);
            }
            // fall through
          case 94: break;
          case 43:
            { return symbol(ParserSym.FUNCION);
            }
            // fall through
          case 95: break;
          case 44:
            { return symbol(ParserSym.DEVUELVE);
            }
            // fall through
          case 96: break;
          case 45:
            { return symbol(ParserSym.IMPRIMIR);
            }
            // fall through
          case 97: break;
          case 46:
            { return symbol(ParserSym.MIENTRAS);
            }
            // fall through
          case 98: break;
          case 47:
            { return symbol(ParserSym.CONTINUAR);
            }
            // fall through
          case 99: break;
          case 48:
            { return symbol(ParserSym.LEERCAR);
            }
            // fall through
          case 100: break;
          case 49:
            { return symbol(ParserSym.LEERENT);
            }
            // fall through
          case 101: break;
          case 50:
            { return symbol(ParserSym.PRINCIPAL);
            }
            // fall through
          case 102: break;
          case 51:
            { return symbol(ParserSym.VAL_BOL, "verdadero");
            }
            // fall through
          case 103: break;
          case 52:
            { return symbol(ParserSym.LEERBOOL);
            }
            // fall through
          case 104: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
