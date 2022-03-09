/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;

%%

%{

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
%}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

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
%eofval}


%class CoolLexer
%cup

%state COMMENT_MULTI_LINE
%state COMMENT_SINGLE_LINE

%state STRING
%state STRING_LENGTH_ERROR
%state STRING_NULL_CHAR_ERROR


A = [a][A]
B = [b][B]
C = [c][C]
D = [d][D]
E = [e][E]
F = [f][F]
G = [g][G]
H = [h][H]
I = [i][I]
J = [j][J]
K = [k][K]
L = [l][L]
M = [m][M]
N = [n][N]
O = [o][O]
P = [p][P]
Q = [q][Q]
R = [r][R]
S = [s][S]
T = [t][T]
U = [u][U]
V = [v][V]
W = [w][W]
X = [x][X]
Y = [y][Y]
Z = [z][Z]
f = [f]
t = [t]

DIGIT = [0-9]
INT = {DIGIT}+
CAPITAL = [A-Z]
LOWER = [a-z]
CHAR = ({CAPITAL}|{LOWER})
TYPE_IDENT = {CAPITAL}({CHAR}|{DIGIT}|_)*
OBJE_IDENT = {LOWER}({CHAR}|{DIGIT}|_)*

NEWLINE = \n
TAB = \t
FROMFEED = \f
CARRIAGE_RETURN = \r
VERTICAL_TAB = \013
BACKSPACE = \b
NULL_CHARACTER = \0
SPACE = " "
WHITESPACE = ({SPACE}|{FROMFEED}|{CARRIAGE_RETURN}|{TAB}|{VERTICAL_TAB})+

STRING = ({CHAR}|{DIGIT}|{WHITESPACE})*

SINGLE_LINE_COMMENT = --
MULTI_LINE_COMMENT_START = "(*"
MULTI_LINE_COMMENT_END = "*)"
BACKSLASH = \\
ESCAPED_CHARACTER = {BACKSLASH}([^b]|[^t]|[^n]|[^f])
ESCAPED_NEWLINE = {BACKSLASH}{NEWLINE}



%%


<YYINITIAL> {WHITESPACE} {}

<YYINITIAL> {NEWLINE} {curr_lineno+=1;}
<YYINITIAL> {VERTICAL_TAB} {curr_lineno+=1;}
<YYINITIAL> {CARRIAGE_RETURN} {curr_lineno+=1;}

<YYINITIAL> {C}{L}{A}{S}{S}         {return new Symbol(TokenConstants.CLASS); }
<YYINITIAL> {E}{L}{S}{E}            {return new Symbol(TokenConstants.ELSE); }
<YYINITIAL> {E}{S}{L}{E}            {return new Symbol(TokenConstants.ELSE); }
<YYINITIAL> {I}{F}                  {return new Symbol(TokenConstants.IF); }
<YYINITIAL> {F}{I}                  {return new Symbol(TokenConstants.FI); }
<YYINITIAL> {L}{O}{O}{P}            {return new Symbol(TokenConstants.LOOP); }
<YYINITIAL> {P}{O}{O}{L}            {return new Symbol(TokenConstants.POOL); }
<YYINITIAL> {f}{A}{L}{S}{E}         {return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.FALSE); }
<YYINITIAL> {t}{R}{U}{E}            {return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.FALSE); }
<YYINITIAL> {I}{N}                  {return new Symbol(TokenConstants.IN); }
<YYINITIAL> {L}{E}{T}               {return new Symbol(TokenConstants.LET); }
<YYINITIAL> {W}{H}{I}{L}{E}         {return new Symbol(TokenConstants.WHILE); }
<YYINITIAL> {N}{E}{W}               {return new Symbol(TokenConstants.NEW); }
<YYINITIAL> {C}{A}{S}{E}            {return new Symbol(TokenConstants.CASE); }
<YYINITIAL> {E}{S}{A}{C}            {return new Symbol(TokenConstants.ESAC); }
<YYINITIAL> {N}{O}{T}               {return new Symbol(TokenConstants.NOT); }
<YYINITIAL> {O}{F}                  {return new Symbol(TokenConstants.OF); }
<YYINITIAL> {I}{S}{V}{O}{I}{D}      {return new Symbol(TokenConstants.ISVOID); }
<YYINITIAL> {I}{N}{H}{E}{R}{I}{T}{S} {return new Symbol(TokenConstants.INHERITS); }

<YYINITIAL> "("                      {return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL> ")"                      {return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL> "{"                      {return new Symbol(TokenConstants.LBRACE); }
<YYINITIAL> "}"                      {return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL> "-"                      {return new Symbol(TokenConstants.MINUS); }
<YYINITIAL> "+"                      {return new Symbol(TokenConstants.PLUS); }
<YYINITIAL> "*"                      {return new Symbol(TokenConstants.MULT); }
<YYINITIAL> "/"                      {return new Symbol(TokenConstants.DIV); }
<YYINITIAL> "="                      {return new Symbol(TokenConstants.EQ); }
<YYINITIAL> "<"                      {return new Symbol(TokenConstants.LT); }
<YYINITIAL> "."                      {return new Symbol(TokenConstants.DOT); }
<YYINITIAL> ","                      {return new Symbol(TokenConstants.COMMA); }
<YYINITIAL> ";"                      {return new Symbol(TokenConstants.SEMI); }
<YYINITIAL> ":"                      {return new Symbol(TokenConstants.COLON); }
<YYINITIAL> "~"                      {return new Symbol(TokenConstants.NEG); }
<YYINITIAL> "@"                      {return new Symbol(TokenConstants.AT); }
<YYINITIAL> "=>"                     {return new Symbol(TokenConstants.DARROW); }
<YYINITIAL> "<="                     {return new Symbol(TokenConstants.LE); }
<YYINITIAL> "<-"                     {return new Symbol(TokenConstants.ASSIGN); }

<YYINITIAL> {INT}                    {AbstractSymbol intEntry = AbstractTable.inttable.addString(yytext());
                                        return new Symbol(TokenConstants.INT_CONST, intEntry);}
<YYINITIAL> {TYPE_IDENT}             {AbstractSymbol typeIdEntry = AbstractTable.idtable.addString(yytext());
                                        return new Symbol(TokenConstants.TYPEID, typeIdEntry);}
<YYINITIAL> {OBJE_IDENT}             {AbstractSymbol objIdEntry = AbstractTable.idtable.addString(yytext());
                                        return new Symbol(TokenConstants.OBJECTID, objIdEntry);}

<YYINITIAL> \"                       {yybegin(STRING);
                                      string_buf.setLength(0);}

<STRING> \"                          {string_buf.append(yytext()); 
                                      yybegin(YYINITIAL);
                                      AbstractSymbol strEntry = AbstractTable.stringtable.addString(string_buf.toString());
                                      return new Symbol(TokenConstants.STR_CONST, strEntry);}

<STRING> \\\"                       {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else { string_buf.append(yytext().substring(1));}}

<STRING> \\{NEWLINE}                {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    } else {
                                        string_buf.append(yytext().substring(1));
                                        curr_lineno+=1;}}

<STRING> \\t                        {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else {
                                        string_buf.append("\t");}}

<STRING>\\f                         {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else {
                                        string_buf.append("\f");}}

<STRING>\\n {                       if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else {
                                        string_buf.append("\n");}}

<STRING>\\b {                       if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else {
                                        string_buf.append("\b");}}

<STRING> {VERTICAL_TAB}             {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else {
                                        string_buf.append(yytext());}}

<STRING>{CARRIAGE_RETURN}           {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else {
                                        string_buf.append(yytext());}}

<STRING> \\{NULL_CHARACTER}          {yybegin(STRING_NULL_CHAR_ERROR);
                                    return new Symbol(TokenConstants.ERROR, "String contains escaped null character");}

<STRING> {NULL_CHARACTER}            {yybegin(STRING_NULL_CHAR_ERROR);
                                    return new Symbol(TokenConstants.ERROR, "String contains null character");}
 
<STRING> {NEWLINE}                   {curr_lineno+=1;
                                    yybegin(YYINITIAL);
                                    return new Symbol(TokenConstants.ERROR, "Unterminated string constant");}

<STRING> {ESCAPED_CHARACTER}         {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else {string_buf.append(yytext().substring(1));}}

<STRING> \\{DIGIT}                  {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else {string_buf.append(yytext().substring(1));}}

<STRING> \\                         {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else { string_buf.append(yytext().substring(1));}}

<STRING> .                           {if (string_buf.length() >= MAX_STR_CONST-1) {
                                        yybegin(STRING_LENGTH_ERROR);
                                        return new Symbol(TokenConstants.ERROR, "String constant too long");
                                    }else {string_buf.append(yytext().substring(1));}}


<STRING_LENGTH_ERROR>\"               {yybegin(YYINITIAL);}

<STRING_LENGTH_ERROR> .               {}


<STRING_NULL_CHAR_ERROR>\"            {yybegin(YYINITIAL);}

<STRING_NULL_CHAR_ERROR>{NEWLINE}     {curr_lineno+=1;
                                       yybegin(YYINITIAL);}
<STRING_NULL_CHAR_ERROR>.             {}

<YYINITIAL> {SINGLE_LINE_COMMENT}     {yybegin(COMMENT_SINGLE_LINE);}

<COMMENT_SINGLE_LINE> .               {}
<COMMENT_SINGLE_LINE> {NEWLINE}       {curr_lineno+=1;
                                        yybegin(YYINITIAL);}

<YYINITIAL>{MULTI_LINE_COMMENT_START} {yybegin(COMMENT_MULTI_LINE);
                                        comment_depth+=1;}

<COMMENT_MULTI_LINE>.                 {}
<COMMENT_MULTI_LINE>{NEWLINE}         {curr_lineno+=1;}
<COMMENT_MULTI_LINE>{CARRIAGE_RETURN} {curr_lineno+=1;}
<COMMENT_MULTI_LINE> {MULTI_LINE_COMMENT_END} {comment_depth-=1;
                                                if (get_comment_depth()==0){
                                                    yybegin(YYINITIAL);
                                                }}

<YYINITIAL>{MULTI_LINE_COMMENT_END}   {return new Symbol(TokenConstants.ERROR, "Unmatched *)");}

.                                     {System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
                                  
