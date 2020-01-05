/*
 * scanner.l -- a lexical analyzer for P-
 *
 * J.Lewis Nikuze
 * Department of Computer Science
 * Trinity College
 *
 * Date: 10/14/2019
 * Modification History:
 *
 */

%{

#include <stdio.h>
#include "scanner.h"
#include "parser.tab.h"
%}

D                        [0-9]
string         		 ["]([^"\\]|\\.)*["]
comment         	 "/*"((.)|(\n))*"*/"
id                       [_a-zA-Z][_a-zA-Z0-9]*

%%m
						 
                        struct he *phe;
                        char msg[MAX_MSG_LEN];
                        char ch;

" "                     |
"\t"                    |
"\f"                    ;
"\n"                    cLine++;
array|ARRAY             return(ARRAY);
begin|BEGIN 	  	return (BEGINN);
program|PROGRAM     	return(PROGRAM);
elif|ELIF               return(ELIF);
end|END                 return(END);
FALSE|false             return(FALSE);
true|TRUE           	return(TRUE);
read|READ           	return(READ);
FLOAT|float             return(FLOAT);
(bool|BOOL)             return(BOOL);
int|INT                 return(INT);
ELSE|else               return(ELSE);
FOR|for                 return(FOR);
function|FUNCTION       return(FUNCTION);
if|IF		        return(IF);
in|IN                   return(IN);
of|OF                   return(OF);
procedure|PROCEDURE 	return(PROCEDURE);
record|RECORD       	return(RECORD);
return|RETURN       	return(RETURN);
then|THEN           	return(THEN);
while|WHILE         	return(WHILE);
write|WRITE         	return(WRITE);
","                     return (COMMA);
"="                     return (ASGN);
";"                     return (SEMI);
"&" 			return (AMP);
":" 			return (COLON);
"." 			return (DOT);
"[" 			return (LBRACK);
"]" 			return (RBRACK);
"(" 			return (LPAREN);
")" 			return (RPAREN);
"+"			return (PLUS);
"-" 			return (MINUS);
"*" 			return (STAR);
">"                     return (GT);
"<" 			return (LT);
"!" 			return (BANG);
"/" 			return (DIV);
"<="                    return (LE);
"=="			return (EQ);
"!="			return (NE);
">="			return (GE);
"&&"			return (DAMP);
"||"                    return (DBAR);


{D}+                        { yylval.w = atoi(yytext);
				return (ICONST);
			    }


({D}*"."{D}+)|({D}+"."{D}*) {
                             yylval.r = atoi(yytext);
                             return (FCONST);
			    }
                       

{string}                    {
                             yylval.sb = MakeString(yytext);
                             return(SCONST);                        
                            }

{id}                        {
                              if ((phe = Lookup(yytext)) == NULL) {
                                 phe = Insert(yytext);
                                 yylval.phe = phe;
                              }
                              return(IDENT);
                            }

{comment}		

.                           {
                              sprintf(msg, message[mnUnknownChar], yytext[0]);
                              PrintErrMsg(msg);
                            }
%%

