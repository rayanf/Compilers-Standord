/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */
    // Max size of string constants
    static int MAX_STR_CONST = 1025;
    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    private int curr_lineno = 1;
    int get_curr_lineno() {
	    return curr_lineno;
    }
    private int comment_depth = 0;
    int get_comment_depth() {
       return comment_depth;
    }
    private AbstractSymbol filename;
    void set_filename(String fname) {
	    filename = AbstractTable.stringtable.addString(fname);
    }
    AbstractSymbol curr_filename() {
	    return filename;
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int STRING = 3;
	private final int STRING_NULL_CHAR_ERROR = 5;
	private final int YYINITIAL = 0;
	private final int COMMENT_SINGLE_LINE = 2;
	private final int STRING_LENGTH_ERROR = 4;
	private final int COMMENT_MULTI_LINE = 1;
	private final int yy_state_dtrans[] = {
		0,
		76,
		79,
		81,
		83,
		85
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NOT_ACCEPT,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NO_ANCHOR,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NO_ANCHOR,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NO_ANCHOR,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"63,64:8,1,4,3,1,2,64:18,1,64,60,64:5,39,40,45,44,50,43,49,46,56:10,52,51,48" +
",47,55,64,54,10,57,6,38,14,18,57,34,16,57:2,8,57,29,20,22,57,25,12,30,27,36" +
",32,57:3,64,61,64:2,59,64,9,62,5,37,13,17,58,33,15,58:2,7,58,28,19,21,58,24" +
",11,23,26,35,31,58:3,41,64,42,53,64,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,189,
"0,1,2,1,3,4,5,1:3,6,1,7,1,8,9,1:7,10,1:7,11,12,11:17,1:11,13,1:14,14,15,16," +
"17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41," +
"42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66," +
"67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91," +
"92,93,94,95,96,97,98,99,100,101,102,11,103,104,105,106,107,108,109,110,111," +
"112,11,113,114,115,116,117,118,119,120,121,122,123,124")[0];

	private int yy_nxt[][] = unpackFromString(125,65,
"1,2:3,3,4,5,150,5,176,5,176,5,178,5,119,5,120,5,121,5,180,5,163,176,5,176,5" +
",151,5:2,182,5,176,5,176,5,176,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,2" +
"1,22,23,5,176,22,24,22,176,22:2,-1:66,2:3,-1:66,176,164,176,165,176,165,176" +
",165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,17" +
"6,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,5:34,-1:17," +
"5:4,-1:2,5,-1:47,25,-1:62,26,-1:61,27,-1:79,28,-1:52,29,-1:3,30,-1:73,23,-1" +
":13,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165" +
",176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165," +
"176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165" +
",176,165,176,165,176:2,165,176,165,176,165:2,176,165,172,165,176,165,176,16" +
"5,-1:17,176,165,176:2,-1:2,176,-1:3,63:3,64,63:12,65,63:5,66,63:4,67,63:31," +
"68,63,69,70,63,1,50,51,50,52,50:40,78,50:19,-1:5,176,165,176,165,176,165,17" +
"6,165,176,165,176,165,176,31,176,165,176,165,176:2,165,176,165,176,165:2,17" +
"6,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:42,53,-1:24,1" +
",54,-1,54,55,54:60,-1:5,176,165,176,165,176,165,176,165,176,165,176,165,176" +
",165,176,165,176,165,176:2,165,176,165,176,32,165,176,165,176,165,176,165,1" +
"76,165,-1:17,176,165,176:2,-1:2,176,-1:2,1,56,57,58,59,56:55,60,61,56,62,56" +
",-1:5,176,165,176,165,176,165,176,165,176,165,176,33,176,165,176,165,176,16" +
"5,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165" +
",176:2,-1:2,176,-1:2,1,71,-1,71,-1,71:55,72,71:4,-1:5,176,165,176,165,176,1" +
"65,176,165,176,165,176,165,176,34,176,165,176,165,176:2,165,176,165,176,165" +
":2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:2,1,73,-" +
"1,73,74,73:55,75,73:4,-1:5,176,165,176,165,176,165,176,165,176,165,176,165," +
"176,165,176,165,176,165,176:2,165,176,165,176,165,35,176,165,176,165,176,16" +
"5,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165" +
",176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,36" +
",176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165," +
"176,165,176,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,1" +
"76,165,37,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7" +
",176,165,176,165,176,165,176,165,176,38,176,165,176,165,176,165,176,165,176" +
":2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:" +
"2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,39,176,165,176,165,176," +
"165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:" +
"17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176," +
"165,176,165,176,165,176,40,176:2,165,176,165,176,165:2,176,165,176,165,176," +
"165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,1" +
"65,176,41,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,1" +
"65,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,16" +
"5,176,165,176,165,176,42,176,165,176,165,176,165,176,165,176:2,165,176,165," +
"176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7" +
",176,43,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176" +
":2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:" +
"2,-1:2,176,-1:7,176,165,176,44,176,165,176,165,176,165,176,165,176,165,176," +
"165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:" +
"17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,45,176,1" +
"65,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176," +
"165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,4" +
"6,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,1" +
"65,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,16" +
"5,176,165,176,165,176,47,176,165,176,165,176,165,176,165,176:2,165,176,165," +
"176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7" +
",176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,17" +
"6:2,165,176,165,176,165:2,176,165,176,165,176,165,176,48,-1:17,176,165,176:" +
"2,-1:2,176,-1:7,176,165,176,165,176,165,176,49,176,165,176,165,176,165,176," +
"165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:" +
"17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,168,165,176,165,176," +
"165,77,165,176,165,176,165,176:2,165,176,165,80,165:2,176,165,176,165,176,1" +
"65,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,16" +
"5,176,165,82,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,16" +
"5,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165" +
",176,165,176,165,176,165,176,165,84,165,176,165,176,165,176:2,165,176,165,1" +
"76,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7," +
"176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,86," +
"176,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176" +
":2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165,17" +
"6,165,176,165,176:2,165,176,165,176,165:2,87,165,176,165,176,165,176,165,-1" +
":17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176" +
",165,176,165,176,165,176,165,88,176,165,176,165,176,165:2,176,165,176,165,1" +
"76,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,17" +
"6,165,89,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,17" +
"6,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176" +
",165,176,165,176,165,90,165,176,165,176,165,176,165,176,165,176:2,165,176,1" +
"65,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-" +
"1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,91,165," +
"176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,1" +
"76:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,92,165,176,165,176,165,1" +
"76,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165," +
"-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,93,165,17" +
"6,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,1" +
"76,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,94,165,176,165,176,165,176" +
",165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,17" +
"6,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,95," +
"165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,1" +
"65,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-" +
"1:7,176,165,176,165,176,165,176,165,96,165,176,165,176,165,176,165,176,165," +
"176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,1" +
"76:2,-1:2,176,-1:7,176,165,176,165,176,165,97,165,176,165,176,165,176,165,1" +
"76,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165," +
"-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,98,165,17" +
"6,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,1" +
"76,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,17" +
"6,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,1" +
"76,165,176,165,176,165,99,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176" +
",165,176,165,100,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176," +
"165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176," +
"-1:7,176,165,176,165,176,165,176,165,176,165,176,101,176,165,176,165,176,16" +
"5,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165" +
",176:2,-1:2,176,-1:7,176,165,176,165,177,165,176,165,176,165,176,165,176,10" +
"2,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,1" +
"65,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,16" +
"5,176,165,176,165,176,103,176,165,176:2,165,176,165,176,165:2,176,165,176,1" +
"65,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,16" +
"5,176,165,176,104,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165" +
":2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,16" +
"5,176,165,176,165,176,165,176,105,176,165,176,165,176,165,176,165,176:2,165" +
",176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2" +
",176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,106,1" +
"76,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,17" +
"6,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,1" +
"76,165,176,165,176,165,176:2,165,176,107,176,165:2,176,165,176,165,176,165," +
"176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,108,1" +
"76,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165," +
"176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,1" +
"76,165,176,165,176,165,176,165,176,165,176,109,176,165,176:2,165,176,165,17" +
"6,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,1" +
"76,165,176,165,176,165,176,110,176,165,176,165,176,165,176,165,176,165,176:" +
"2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2" +
",-1:2,176,-1:7,176,165,176,111,176,165,176,165,176,165,176,165,176,165,176," +
"165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:" +
"17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,112,176,165,176,165,176," +
"165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176" +
",165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176," +
"165,176,165,176,165,176,165,176,113,176,165,176:2,165,176,165,176,165:2,176" +
",165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176," +
"165,176,165,176,114,176,165,176,165,176,165,176,165,176,165,176:2,165,176,1" +
"65,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-" +
"1:7,176,165,176,165,176,165,176,115,176,165,176,165,176,165,176,165,176,165" +
",176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165," +
"176:2,-1:2,176,-1:7,176,165,176,116,176,165,176,165,176,165,176,165,176,165" +
",176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,16" +
"5,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165" +
",176,117,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,16" +
"5,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165" +
",176,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165," +
"118,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,1" +
"65,176,165,176,165,176,165,122,165,176,165,176,165,154,165,176,165,176:2,16" +
"5,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:" +
"2,176,-1:7,176,165,176,165,176,165,176,165,123,165,176,165,176,165,124,165," +
"176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,1" +
"76,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165," +
"176,165,176,165,176,165,176:2,165,125,165,176,165:2,176,165,176,165,176,165" +
",176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,126,165," +
"176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165" +
",176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165," +
"176,165,176,165,176,165,176,165,176,165,127,165,176,165,176:2,165,176,165,1" +
"76,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7," +
"176,165,176,165,176,165,128,165,176,165,176,165,176,165,176,165,176,165,176" +
":2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:" +
"2,-1:2,176,-1:7,176,165,129,165,130,165,176,165,176,165,176,165,176,165,176" +
",165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1" +
":17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176" +
",165,176,165,131,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,17" +
"6,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,132" +
",165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,17" +
"6,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176" +
",165,176,165,133,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176," +
"165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176," +
"-1:7,176,165,134,165,176,165,176,165,176,165,176,165,176,165,176,165,176,16" +
"5,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165" +
",176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,135,165,176,16" +
"5,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,1" +
"65,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,16" +
"5,176,165,176,165,176,165,176,165,136,176,165,176,165,176,165:2,176,165,176" +
",165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,137,176," +
"165,176,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,1" +
"65:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176," +
"165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176:2,1" +
"65,176,165,176,138,165,176,165,176,165,176,165,176,165,-1:17,176,165,176:2," +
"-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,1" +
"65,176,165,176:2,139,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:1" +
"7,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,140,176,165,176,165,176,1" +
"65,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176," +
"165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,1" +
"65,176,165,176,165,176,165,176,141,176,165,176:2,165,176,165,176,165:2,176," +
"165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,1" +
"42,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,16" +
"5,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1" +
":7,176,165,176,165,176,165,176,143,176,165,176,165,176,165,176,165,176,165," +
"176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,1" +
"76:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165," +
"176,144,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165" +
",-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,145,176,165,176,165,176,165," +
"176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165" +
",176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,146," +
"176,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2" +
",176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165," +
"176,165,176,165,176,165,176,165,176,147,176,165,176,165,176,165,176:2,165,1" +
"76,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,1" +
"76,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,148,176" +
",165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176," +
"165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,149,176" +
",165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,17" +
"6,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176" +
",165,176,165,176,165,176,165,176,165,176,152,165,176,165,176,165:2,176,165," +
"176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,179,165,1" +
"53,165,176,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,17" +
"6,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,1" +
"76,165,155,165,176,165,156,165,176,165,176,165,176,165,176,165,176,165,176:" +
"2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2" +
",-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165,157," +
"165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:" +
"17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,186,176,165,176," +
"165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176" +
",165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,158,165,176,165,176," +
"165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176" +
",165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176," +
"165,159,165,176,165,176,165,176,165,176,165,176,165,176,165,176:2,165,176,1" +
"65,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-" +
"1:7,176,165,176,165,176,165,176,165,176,165,160,165,176,165,176,165,176,165" +
",176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165," +
"176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165" +
",176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,187,176,165,176,16" +
"5,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165" +
",176,165,176,165,161,165,176,165,176:2,165,176,165,176,165:2,176,165,176,16" +
"5,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165" +
",176,165,176,188,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:" +
"2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165" +
",176,165,176,165,176,165,176,165,162,165,176,165,176,165,176,165,176:2,165," +
"176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2," +
"176,-1:7,176,165,176,165,176,169,176,165,176,165,176,165,176,165,176,165,17" +
"6,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165,-1:17,176" +
",165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,166,176,165,17" +
"6,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,1" +
"76,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,170,176,165,176,165,17" +
"6,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,1" +
"76,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,17" +
"6,165,176,165,176,165,176,165,176,165,176,165,176,167,176:2,165,176,165,176" +
",165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,17" +
"6,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176:2" +
",165,176,165,176,165:2,176,165,176,171,176,165,176,165,-1:17,176,165,176:2," +
"-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,1" +
"65,176,165,176:2,165,176,165,176,165:2,176,184,176,165,176,165,176,165,-1:1" +
"7,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165,176,1" +
"65,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176," +
"173,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,1" +
"65,176,165,176,165,176,165,176,165,176,165,176:2,165,176,165,176,165:2,176," +
"165,181,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,1" +
"65,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176:2,175,176,16" +
"5,176,165:2,176,165,176,165,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1" +
":7,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165,176,165," +
"176:2,165,176,165,176,165:2,176,165,176,165,183,165,176,165,-1:17,176,165,1" +
"76:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,174,165,176,165,176,165," +
"176,165,176,165,176:2,165,176,165,176,165:2,176,165,176,165,176,165,176,165" +
",-1:17,176,165,176:2,-1:2,176,-1:7,176,165,176,165,176,165,176,165,176,165," +
"176,165,176,165,176,165,176,165,176,185,165,176,165,176,165:2,176,165,176,1" +
"65,176,165,176,165,-1:17,176,165,176:2,-1:2,176,-1:2");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */
    switch(yy_lexical_state) {
        case YYINITIAL:
            break;        /* nothing special to do in the initial state */
        case STRING:
            yybegin(YYINITIAL);
            return new Symbol(TokenConstants.ERROR, "EOF in String Constant");
        case COMMENT_MULTI_LINE:
            yybegin(YYINITIAL);
            return new Symbol(TokenConstants.ERROR, "EOF in Comment");
    }
    return new Symbol(TokenConstants.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{}
					case -3:
						break;
					case 3:
						{curr_lineno+=1;}
					case -4:
						break;
					case 4:
						{AbstractSymbol objIdEntry = AbstractTable.idtable.addString(yytext());
                                        return new Symbol(TokenConstants.OBJECTID, objIdEntry);}
					case -5:
						break;
					case 5:
						{AbstractSymbol typeIdEntry = AbstractTable.idtable.addString(yytext());
                                        return new Symbol(TokenConstants.TYPEID, typeIdEntry);}
					case -6:
						break;
					case 6:
						{return new Symbol(TokenConstants.LPAREN); }
					case -7:
						break;
					case 7:
						{return new Symbol(TokenConstants.RPAREN); }
					case -8:
						break;
					case 8:
						{return new Symbol(TokenConstants.LBRACE); }
					case -9:
						break;
					case 9:
						{return new Symbol(TokenConstants.RBRACE); }
					case -10:
						break;
					case 10:
						{return new Symbol(TokenConstants.MINUS); }
					case -11:
						break;
					case 11:
						{return new Symbol(TokenConstants.PLUS); }
					case -12:
						break;
					case 12:
						{return new Symbol(TokenConstants.MULT); }
					case -13:
						break;
					case 13:
						{return new Symbol(TokenConstants.DIV); }
					case -14:
						break;
					case 14:
						{return new Symbol(TokenConstants.EQ); }
					case -15:
						break;
					case 15:
						{return new Symbol(TokenConstants.LT); }
					case -16:
						break;
					case 16:
						{return new Symbol(TokenConstants.DOT); }
					case -17:
						break;
					case 17:
						{return new Symbol(TokenConstants.COMMA); }
					case -18:
						break;
					case 18:
						{return new Symbol(TokenConstants.SEMI); }
					case -19:
						break;
					case 19:
						{return new Symbol(TokenConstants.COLON); }
					case -20:
						break;
					case 20:
						{return new Symbol(TokenConstants.NEG); }
					case -21:
						break;
					case 21:
						{return new Symbol(TokenConstants.AT); }
					case -22:
						break;
					case 22:
						{System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
					case -23:
						break;
					case 23:
						{AbstractSymbol intEntry = AbstractTable.inttable.addString(yytext());
                                        return new Symbol(TokenConstants.INT_CONST, intEntry);}
					case -24:
						break;
					case 24:
						{yybegin(STRING);
                                      string_buf.setLength(0);}
					case -25:
						break;
					case 25:
						{yybegin(COMMENT_MULTI_LINE);
                                        comment_depth+=1;}
					case -26:
						break;
					case 26:
						{yybegin(COMMENT_SINGLE_LINE);}
					case -27:
						break;
					case 27:
						{return new Symbol(TokenConstants.ERROR, "Unmatched *)");}
					case -28:
						break;
					case 28:
						{return new Symbol(TokenConstants.DARROW); }
					case -29:
						break;
					case 29:
						{return new Symbol(TokenConstants.ASSIGN); }
					case -30:
						break;