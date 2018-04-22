package yogur.jlex;

import java_cup.runtime.Symbol;
import yogur.cup.sym;
%%
%cup
%eofval{
  return new Symbol(sym.EOF);
%eofval}
%class YogurLex

letra = ([A-Z]|[a-z])
digito = [0-9]
entero = [\+,\-]?{digito}+
boolean = ((true)|(false))
separador = [ \t\b\r]
delimitador = \n
comentarioLinea = //[^\n]*
comentarioMultilinea = /\*(.|\n)*\*/
identificador = {letra}({letra}|{digito}|_)*
identificadorTipo = [A-Z]({letra}|{digito}|_)*
coma = \,

opAsignacion = "="
opSuma = \+
opResta = \-
opProducto = \*
opDivision = /
opMod = %
opAnd = ((and)|(&&))
opOr = ((or)|(\|\|))
opNot = (\!|(not))
opAcceso = \.
opRange = :
opEq = "=="
opNeq = "!="
opGeq = ">="
opGreater = ">"
opLeq = "<="
opLess = "<"

corcheteApertura = \[
corcheteCierre = \]
parentesisApertura = \(
parentesisCierre = \)
bloqueApertura = \{
bloqueCierre = \}

def = def
var = var
class = class
if = if
else = else
while = while
for = for
in = in
to = to

%%

{entero}				{return new Symbol(sym.INT, new Integer(yytext())); }
{boolean}				{return new Symbol(sym.BOOL, new Boolean(yytext())); }
{identificador} 		{return new Symbol(sym.ID, yytext()); }
{identificadorTipo}		{return new Symbol(sym.TYPE, yytext()); }
{delimitador}			{return new Symbol(sym.DELIMITER); }
{comentarioLinea}		{}
{comentarioMultilinea}	{}
{separador}				{}
{coma}					{return new Symbol(sym.COMMA); }

{opAsignacion}			{return new Symbol(sym.ASSIGN); }
{opSuma}				{return new Symbol(sym.SUM); }
{opResta}				{return new Symbol(sym.SUBS); }
{opProducto}			{return new Symbol(sym.PROD); }
{opDivision}			{return new Symbol(sym.DIV); }
{opMod}					{return new Symbol(sym.MOD); }
{opAnd}					{return new Symbol(sym.AND); }
{opOr}					{return new Symbol(sym.OR); }
{opNot}					{return new Symbol(sym.NOT); }
{opAcceso}				{return new Symbol(sym.ACCESS); }
{opRange}				{return new Symbol(sym.RANGE); }
{opEq}					{return new Symbol(sym.EQ); }
{opNeq}					{return new Symbol(sym.NEQ); }
{opGeq}					{return new Symbol(sym.GEQ); }
{opGreater}				{return new Symbol(sym.GT); }
{opLeq}					{return new Symbol(sym.LEQ); }
{opLess}				{return new Symbol(sym.LT); }

{corcheteApertura}		{return new Symbol(sym.LSQUARE); }
{corcheteCierre}		{return new Symbol(sym.RSQUARE); }
{parentesisApertura}	{return new Symbol(sym.LPAREN); }
{parentesisCierre}		{return new Symbol(sym.RPAREN); }
{bloqueApertura}		{return new Symbol(sym.LBRACKET); }
{bloqueCierre}			{return new Symbol(sym.RBRACKET); }

{def}					{return new Symbol(sym.DEF); }
{var}					{return new Symbol(sym.VAR); }
{class}					{return new Symbol(sym.CLASS); }
{if}					{return new Symbol(sym.IF); }
{else}					{return new Symbol(sym.ELSE); }
{while}					{return new Symbol(sym.WHILE); }
{for}					{return new Symbol(sym.FOR); }
{in}					{return new Symbol(sym.IN); }
{to}					{return new Symbol(sym.TO); }

. {System.err.println("Illegal character: " + yytext());}
