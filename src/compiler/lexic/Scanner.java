// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: src/compiler/lexic/lexic.flex

import java.io.*;

import java_cup.runtime.*;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.ComplexSymbolFactory.Location;

import compiler.sintactic.ParserSym;


// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class Scanner implements java_cup.runtime.Scanner {

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
    "\12\0\1\1\1\2\2\3\24\0\1\4\1\5\1\0"+
    "\1\6\1\0\1\7\1\10\1\11\1\12\1\13\1\14"+
    "\1\15\1\0\1\16\1\17\11\20\1\21\1\22\1\23"+
    "\1\24\1\25\1\0\1\26\1\27\1\30\1\31\1\27"+
    "\1\32\11\27\1\33\11\27\1\34\1\27\1\35\1\36"+
    "\1\37\1\0\1\40\1\0\1\41\1\42\1\43\1\44"+
    "\1\45\1\46\1\27\1\47\1\50\2\27\1\51\1\52"+
    "\1\53\1\54\1\55\1\27\1\56\1\57\1\60\1\61"+
    "\1\62\2\27\1\34\1\27\1\63\1\0\1\64\7\0"+
    "\1\2\u01a2\0\2\2\326\0\u0100\2";

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
    "\1\6\1\7\1\10\1\11\1\12\1\1\1\13\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\27\1\30\15\23\1\31\1\32\1\33"+
    "\2\0\1\34\1\35\1\0\1\36\1\37\1\40\1\41"+
    "\1\42\1\0\17\23\1\43\2\23\1\0\1\23\1\44"+
    "\2\23\1\45\14\23\1\46\11\23\1\47\2\23\1\50"+
    "\2\23\1\51\2\23\1\52\1\23\1\53\10\23\1\54"+
    "\12\23\1\55\1\56\3\23\1\57\10\23\1\60\1\61"+
    "\1\23\2\0\1\62\2\23\1\63\1\0\1\64\1\65"+
    "\1\66\1\67\1\70";

  private static int [] zzUnpackAction() {
    int [] result = new int[161];
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
    "\0\0\0\65\0\152\0\237\0\324\0\65\0\u0109\0\65"+
    "\0\65\0\u013e\0\65\0\65\0\65\0\u0173\0\65\0\u01a8"+
    "\0\u01dd\0\65\0\u0212\0\65\0\u0247\0\65\0\u027c\0\u027c"+
    "\0\u027c\0\65\0\65\0\65\0\u02b1\0\u02e6\0\u031b\0\u0350"+
    "\0\u0385\0\u03ba\0\u03ef\0\u0424\0\u0459\0\u048e\0\u04c3\0\u04f8"+
    "\0\u052d\0\65\0\65\0\152\0\u0109\0\237\0\237\0\65"+
    "\0\u0562\0\65\0\65\0\65\0\65\0\65\0\u0597\0\u05cc"+
    "\0\u0601\0\u0636\0\u066b\0\u06a0\0\u06d5\0\u070a\0\u073f\0\u0774"+
    "\0\u07a9\0\u07de\0\u0813\0\u0848\0\u087d\0\u08b2\0\u08e7\0\u091c"+
    "\0\u0951\0\u0986\0\u09bb\0\u027c\0\u09f0\0\u0a25\0\u027c\0\u0a5a"+
    "\0\u0a8f\0\u0ac4\0\u0af9\0\u0b2e\0\u0b63\0\u0b98\0\u0bcd\0\u0c02"+
    "\0\u0c37\0\u0c6c\0\u0ca1\0\u027c\0\u0cd6\0\u0d0b\0\u0d40\0\u0d75"+
    "\0\u0daa\0\u0ddf\0\u0e14\0\u0e49\0\u0e7e\0\u027c\0\u0eb3\0\u0ee8"+
    "\0\u0f1d\0\u0f52\0\u0f87\0\u027c\0\u0fbc\0\u0ff1\0\u027c\0\u1026"+
    "\0\u027c\0\u105b\0\u1090\0\u10c5\0\u10fa\0\u112f\0\u1164\0\u1199"+
    "\0\u11ce\0\u027c\0\u1203\0\u1238\0\u126d\0\u12a2\0\u12d7\0\u130c"+
    "\0\u1341\0\u1376\0\u13ab\0\u13e0\0\u027c\0\u027c\0\u1415\0\u144a"+
    "\0\u147f\0\u027c\0\u14b4\0\u14e9\0\u151e\0\u1553\0\u1588\0\u15bd"+
    "\0\u15f2\0\u1627\0\u027c\0\u027c\0\u165c\0\u1691\0\u16c6\0\u027c"+
    "\0\u16fb\0\u1730\0\u027c\0\u1765\0\65\0\65\0\u027c\0\u027c"+
    "\0\65";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[161];
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
    "\1\2\1\3\2\2\1\4\1\5\1\6\1\7\1\10"+
    "\1\11\1\12\1\13\1\14\1\15\1\16\1\17\1\20"+
    "\1\21\1\22\1\23\1\24\1\25\1\26\4\27\1\30"+
    "\1\31\1\32\1\33\1\34\1\2\1\27\1\35\1\36"+
    "\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\27"+
    "\1\30\1\46\1\47\1\50\2\27\1\51\1\52\1\53"+
    "\66\0\1\54\5\0\1\55\55\0\1\56\3\0\1\57"+
    "\60\56\5\0\1\60\62\0\1\61\3\0\1\54\73\0"+
    "\1\62\60\0\1\63\71\0\2\20\70\0\1\64\64\0"+
    "\1\65\64\0\1\66\47\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\22\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\13\27\1\71\6\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\1\72\12\27\1\73\6\27"+
    "\11\0\1\67\10\0\1\70\6\0\6\27\4\0\4\27"+
    "\1\74\15\27\11\0\1\67\10\0\1\70\6\0\6\27"+
    "\4\0\12\27\1\75\7\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\1\76\17\27\1\77\1\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\1\100\21\27"+
    "\11\0\1\67\10\0\1\70\6\0\6\27\4\0\11\27"+
    "\1\101\10\27\11\0\1\67\10\0\1\70\6\0\6\27"+
    "\4\0\4\27\1\102\15\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\7\27\1\103\12\27\11\0\1\67"+
    "\10\0\1\70\6\0\6\27\4\0\1\104\14\27\1\105"+
    "\4\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\13\27\1\106\6\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\7\27\1\107\12\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\1\110\3\27\1\111\15\27"+
    "\11\0\1\54\115\0\1\112\33\0\1\67\7\0\2\70"+
    "\6\0\6\27\4\0\22\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\13\27\1\113\6\27\11\0\1\67"+
    "\10\0\1\70\6\0\6\27\4\0\15\27\1\114\4\27"+
    "\11\0\1\67\10\0\1\70\6\0\6\27\4\0\12\27"+
    "\1\115\7\27\11\0\1\67\10\0\1\70\6\0\6\27"+
    "\4\0\21\27\1\116\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\17\27\1\117\2\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\10\27\1\120\11\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\12\27\1\121"+
    "\7\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\2\27\1\122\17\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\14\27\1\123\5\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\4\27\1\124\15\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\4\27\1\125"+
    "\15\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\15\27\1\126\4\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\7\27\1\127\12\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\11\27\1\130\10\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\12\27\1\131"+
    "\7\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\2\27\1\132\17\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\15\27\1\133\4\27\11\0\1\27\64\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\10\27\1\134"+
    "\11\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\16\27\1\135\1\136\2\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\20\27\1\137\1\27\11\0\1\67"+
    "\10\0\1\70\6\0\6\27\4\0\16\27\1\140\3\27"+
    "\11\0\1\67\10\0\1\70\6\0\6\27\4\0\2\27"+
    "\1\141\17\27\11\0\1\67\10\0\1\70\6\0\6\27"+
    "\4\0\4\27\1\142\15\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\15\27\1\143\4\27\11\0\1\67"+
    "\10\0\1\70\6\0\6\27\4\0\15\27\1\144\4\27"+
    "\11\0\1\67\10\0\1\70\6\0\6\27\4\0\12\27"+
    "\1\145\7\27\11\0\1\67\10\0\1\70\6\0\6\27"+
    "\4\0\1\146\21\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\12\27\1\147\7\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\14\27\1\150\5\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\13\27\1\151"+
    "\6\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\7\27\1\152\12\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\3\27\1\153\16\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\17\27\1\154\2\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\7\27\1\155"+
    "\12\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\4\27\1\156\15\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\13\27\1\157\6\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\7\27\1\160\12\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\15\27\1\161"+
    "\4\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\7\27\1\162\12\27\11\0\1\67\10\0\1\70\6\0"+
    "\1\27\1\163\1\164\1\165\2\27\4\0\22\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\17\27\1\166"+
    "\2\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\2\27\1\167\17\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\4\27\1\170\15\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\16\27\1\171\3\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\13\27\1\172"+
    "\6\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\1\173\21\27\11\0\1\67\10\0\1\70\6\0\6\27"+
    "\4\0\12\27\1\174\7\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\10\27\1\175\11\27\11\0\1\67"+
    "\10\0\1\70\6\0\6\27\4\0\13\27\1\176\6\27"+
    "\11\0\1\67\10\0\1\70\6\0\6\27\4\0\11\27"+
    "\1\177\10\27\11\0\1\67\10\0\1\70\6\0\6\27"+
    "\4\0\13\27\1\200\6\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\1\201\21\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\12\27\1\202\7\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\15\27\1\203"+
    "\4\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\7\27\1\204\12\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\15\27\1\205\4\27\11\0\1\67\10\0"+
    "\1\70\6\0\6\27\4\0\7\27\1\206\12\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\3\27\1\207"+
    "\16\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\20\27\1\210\1\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\21\27\1\211\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\12\27\1\212\7\27\11\0\1\67"+
    "\10\0\1\70\6\0\6\27\4\0\7\27\1\213\12\27"+
    "\11\0\1\67\10\0\1\70\6\0\6\27\4\0\13\27"+
    "\1\214\6\27\11\0\1\67\10\0\1\70\6\0\6\27"+
    "\4\0\15\27\1\215\4\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\17\27\1\216\2\27\11\0\1\67"+
    "\10\0\1\70\6\0\6\27\4\0\1\217\21\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\14\27\1\220"+
    "\5\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\4\27\1\221\15\27\11\0\1\67\10\0\1\70\6\0"+
    "\6\27\4\0\1\222\21\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\4\27\1\223\15\27\11\0\1\67"+
    "\10\0\1\70\6\0\6\27\4\0\15\27\1\224\4\27"+
    "\11\0\1\67\10\0\1\70\6\0\6\27\4\0\10\27"+
    "\1\225\11\27\11\0\1\67\1\226\7\0\1\70\6\0"+
    "\6\27\4\0\22\27\11\0\1\67\1\227\7\0\1\70"+
    "\6\0\6\27\4\0\22\27\11\0\1\67\10\0\1\70"+
    "\6\0\6\27\4\0\16\27\1\230\3\27\11\0\1\67"+
    "\10\0\1\70\6\0\6\27\4\0\1\231\21\27\11\0"+
    "\1\67\10\0\1\70\6\0\6\27\4\0\15\27\1\232"+
    "\4\27\11\0\1\67\10\0\1\70\6\0\6\27\4\0"+
    "\15\27\1\233\4\27\11\0\1\67\1\234\7\0\1\70"+
    "\6\0\6\27\4\0\22\27\13\0\1\235\64\0\1\236"+
    "\62\0\1\67\10\0\1\70\6\0\6\27\4\0\10\27"+
    "\1\237\11\27\11\0\1\67\10\0\1\70\6\0\6\27"+
    "\4\0\13\27\1\240\6\27\13\0\1\241\53\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[6042];
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
    "\1\0\1\11\3\1\1\11\1\1\2\11\1\1\3\11"+
    "\1\1\1\11\2\1\1\11\1\1\1\11\1\1\1\11"+
    "\3\1\3\11\15\1\2\11\1\1\2\0\1\1\1\11"+
    "\1\0\5\11\1\0\22\1\1\0\113\1\2\0\4\1"+
    "\1\0\2\11\2\1\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[161];
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


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Scanner(java.io.Reader in) {
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
            { System.out.print(yytext());
            }
            // fall through
          case 57: break;
          case 2:
            { return symbol(Parsersym.SALTO_LINEA);
            }
            // fall through
          case 58: break;
          case 3:
            { return symbol(Parsersym.COMILLAS);
            }
            // fall through
          case 59: break;
          case 4:
            { return symbol(Parsersym.MOD);
            }
            // fall through
          case 60: break;
          case 5:
            { return symbol(Parsersym.PAR_A);
            }
            // fall through
          case 61: break;
          case 6:
            { return symbol(Parsersym.PAR_C);
            }
            // fall through
          case 62: break;
          case 7:
            { return symbol(Parsersym.MULT);
            }
            // fall through
          case 63: break;
          case 8:
            { return symbol(Parsersym.SUMA);
            }
            // fall through
          case 64: break;
          case 9:
            { return symbol(Parsersym.COMA);
            }
            // fall through
          case 65: break;
          case 10:
            { return symbol(Parsersym.RESTA);
            }
            // fall through
          case 66: break;
          case 11:
            { return symbol(ParserSym.valor, 0.0);
            }
            // fall through
          case 67: break;
          case 12:
            { return symbol(ParserSym.valor, Integer.parseInt(this.yytext()));
            }
            // fall through
          case 68: break;
          case 13:
            { return symbol(Parsersym.D_PUNTOS);
            }
            // fall through
          case 69: break;
          case 14:
            { return symbol(Parsersym.P_COMA);
            }
            // fall through
          case 70: break;
          case 15:
            { return symbol(Parsersym.MEQ);
            }
            // fall through
          case 71: break;
          case 16:
            { return symbol(Parsersym.IGUAL);
            }
            // fall through
          case 72: break;
          case 17:
            { return symbol(Parsersym.MAQ);
            }
            // fall through
          case 73: break;
          case 18:
            { return symbol(ParserSym.error);
            }
            // fall through
          case 74: break;
          case 19:
            { return symbol(sym.ID, this.yytext());
            }
            // fall through
          case 75: break;
          case 20:
            { return symbol(Parsersym.O);
            }
            // fall through
          case 76: break;
          case 21:
            { return symbol(Parsersym.Y);
            }
            // fall through
          case 77: break;
          case 22:
            { return symbol(Parsersym.CORCHETE_A);
            }
            // fall through
          case 78: break;
          case 23:
            { return symbol(Parsersym.DIV);
            }
            // fall through
          case 79: break;
          case 24:
            { return symbol(Parsersym.CORCHETE_C);
            }
            // fall through
          case 80: break;
          case 25:
            { return symbol(Parsersym.LLAVE_A);
            }
            // fall through
          case 81: break;
          case 26:
            { return symbol(Parsersym.LLAVE_C);
            }
            // fall through
          case 82: break;
          case 27:
            { /* Ignorar espacios */
            }
            // fall through
          case 83: break;
          case 28:
            { return symbol(Parsersym.CADENA, yytext());
            }
            // fall through
          case 84: break;
          case 29:
            { return symbol(Parsersym.INI_COMT);
            }
            // fall through
          case 85: break;
          case 30:
            { return symbol(Parsersym.C_COMT_M);
            }
            // fall through
          case 86: break;
          case 31:
            { return symbol(Parsersym.A_COMT_M);
            }
            // fall through
          case 87: break;
          case 32:
            { return symbol(Parsersym.ASIG);
            }
            // fall through
          case 88: break;
          case 33:
            { return symbol(Parsersym.MEI);
            }
            // fall through
          case 89: break;
          case 34:
            { return symbol(Parsersym.MAI);
            }
            // fall through
          case 90: break;
          case 35:
            { return symbol(Parsersym.SI);
            }
            // fall through
          case 91: break;
          case 36:
            { return symbol(Parsersym.CARACTER);
            }
            // fall through
          case 92: break;
          case 37:
            { return symbol(Parsersym.ENTERO);
            }
            // fall through
          case 93: break;
          case 38:
            { return symbol(Parsersym.BOOLEANO);
            }
            // fall through
          case 94: break;
          case 39:
            { return symbol(Parsersym.PARA);
            }
            // fall through
          case 95: break;
          case 40:
            { return symbol(Parsersym.SINO);
            }
            // fall through
          case 96: break;
          case 41:
            { return symbol(Parsersym.CONST);
            }
            // fall through
          case 97: break;
          case 42:
            { return symbol(Parsersym.FALSO);
            }
            // fall through
          case 98: break;
          case 43:
            { return symbol(Parsersym.HACER);
            }
            // fall through
          case 99: break;
          case 44:
            { return symbol(Parsersym.VACIO);
            }
            // fall through
          case 100: break;
          case 45:
            { return symbol(Parsersym.ROMPER);
            }
            // fall through
          case 101: break;
          case 46:
            { return symbol(Parsersym.SINOSI);
            }
            // fall through
          case 102: break;
          case 47:
            { return symbol(Parsersym.FUNCION);
            }
            // fall through
          case 103: break;
          case 48:
            { return symbol(Parsersym.DEVUELVE);
            }
            // fall through
          case 104: break;
          case 49:
            { return symbol(Parsersym.IMPRIMIR);
            }
            // fall through
          case 105: break;
          case 50:
            { return symbol(Parsersym.MIENTRAS);
            }
            // fall through
          case 106: break;
          case 51:
            { return symbol(Parsersym.CONTINUAR);
            }
            // fall through
          case 107: break;
          case 52:
            { return symbol(Parsersym.LEERCAR);
            }
            // fall through
          case 108: break;
          case 53:
            { return symbol(Parsersym.LEERENT);
            }
            // fall through
          case 109: break;
          case 54:
            { return symbol(Parsersym.PRINCIPAL);
            }
            // fall through
          case 110: break;
          case 55:
            { return symbol(Parsersym.VERDADERO);
            }
            // fall through
          case 111: break;
          case 56:
            { return symbol(Parsersym.LEERBOOL);
            }
            // fall through
          case 112: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }

  /**
   * Runs the scanner on input files.
   *
   * This is a standalone scanner, it will print any unmatched
   * text to System.out unchanged.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String[] argv) {
    if (argv.length == 0) {
      System.out.println("Usage : java Scanner [ --encoding <name> ] <inputfile(s)>");
    }
    else {
      int firstFilePos = 0;
      String encodingName = "UTF-8";
      if (argv[0].equals("--encoding")) {
        firstFilePos = 2;
        encodingName = argv[1];
        try {
          // Side-effect: is encodingName valid?
          java.nio.charset.Charset.forName(encodingName);
        } catch (Exception e) {
          System.out.println("Invalid encoding '" + encodingName + "'");
          return;
        }
      }
      for (int i = firstFilePos; i < argv.length; i++) {
        Scanner scanner = null;
        try {
          java.io.FileInputStream stream = new java.io.FileInputStream(argv[i]);
          java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
          scanner = new Scanner(reader);
          while ( !scanner.zzAtEOF ) scanner.next_token();
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv[i]+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv[i]+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
      }
    }
  }


}