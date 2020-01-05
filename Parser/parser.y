/*
 * parser.y -- bison rules to generate parser for P-
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
#include "msg.h"

%}

%start program

%union {
    int w;                 /* ICONST */
    double r;              /* FCONST */
    char *sb;              /* SCONST */
    struct he *phe;        /* IDENT  */

    /* more to be added during Phase 3 */
}

%token AMP ASGN BANG COLON COMMA DAMP DBAR DIV DOT EQ GE GT LBRACK LE 
%token LPAREN LT MINUS NE PLUS RBRACK RPAREN SEMI STAR ARRAY BEGINN BOOL  
%token ELSE ELIF END FALSE FOR FLOAT FUNCTION IF IN INT OF PROCEDURE PROGRAM
%token READ RECORD RETURN THEN TRUE WHILE WRITE
%token FCONST    
%token ICONST
%token IDENT
%token SCONST
%nonassoc ASGN
%nonassoc DBAR
%nonassoc DAMP
%nonassoc EQ GE GT LE LT NE
%left PLUS MINUS
%left STAR DIV
%right BANG

%%

program         : PROGRAM IDENT decl_part body

                | PROGRAM error
                  {
                     yyerror(message[mnMissingIdent]);
                  }
                   decl_part body
                  {
                    yyerrok;
                  }
                ; 

body            : BEGINN sub_stat END
                | BEGINN sub_stat error
                 {
                   yyerror(message[bodyWithoutEnd]);
                 }
                 {yyerrok;}
		;

decl_part       :   
                |decl_part var_decl
                |decl_part proc_decl
                |decl_part fun_decl
                |decl_part error
		;        
               
var_decl        : v1 COLON type
	        | IDENT ASGN type
		| IDENT ASGN error
                {
                  yyerror(message[missingType]);
                }
		{yyerrok;}
                ;

v1              : IDENT | v1 COMMA IDENT;

proc_decl       : PROCEDURE IDENT LPAREN RPAREN decl_part body
                | PROCEDURE IDENT LPAREN param_list RPAREN decl_part body
                ;

fun_decl       : FUNCTION IDENT LPAREN RPAREN COLON simple_type decl_part body
               | FUNCTION IDENT LPAREN param_list RPAREN COLON simple_type decl_part body
               ;

param_list     : p2 COLON param_type param_loop;
p2             : p1 | p2 COMMA p1; 
p1             : IDENT | AMP IDENT;

param_loop     : | SEMI param_list;

param_type     : simple_type | IDENT;

type           : simple_type 
	       | IDENT 
	       | ARRAY LBRACK index RBRACK OF type 
	       | RECORD field_list END
	       ;

simple_type    : INT|FLOAT|BOOL;

index          : ICONST COLON ICONST index_loop;
	       
index_loop     : |COMMA index;

field_list     : f1 COLON type field_loop;
field_loop     : |field_list;
f1	       : IDENT | f1 COMMA IDENT;

sub_stat       : 
	       | sub_stat assn_stat 
	       | sub_stat sub_call 
	       | sub_stat ret_stat 
	       | sub_stat for_stat
	       | sub_stat while_stat
	       | sub_stat if_stat
	       | sub_stat read_stat
	       | sub_stat write_stat
	       ;

assn_stat      : variable ASGN expr
               | variable ASGN error
                {
                  yyerror(message[noExprAfterASGN]);
                }
                 
                expr
                {yyerrok;}            
               ;

sub_call       : IDENT LPAREN sub RPAREN;

sub	       : 
	       | expr
	       | sub COMMA expr;

ret_stat       : RETURN expr
               | RETURN error
                {
                  yyerror(message[badReturn]);
                }
                {yyerrok;}
                ;

for_stat       : FOR IDENT IN expr COLON expr sub_stat END
		| FOR IDENT IN expr COLON expr sub_stat error
                {
                  yyerror(message[missingEnd]);
                }
		{yyerrok;}
		;

while_stat     : WHILE LPAREN expr RPAREN sub_stat END
		| WHILE LPAREN expr RPAREN sub_stat error
                {
                  yyerror(message[missingEnd]);
                }
		{yyerrok;}
		;

if_stat        : IF LPAREN expr RPAREN THEN sub_stat END
	       | IF LPAREN expr RPAREN THEN sub_stat elif_part END
	       | IF LPAREN expr RPAREN THEN sub_stat else_part END
               | IF LPAREN expr RPAREN THEN sub_stat elif_part else_part END
               | IF LPAREN expr RPAREN THEN sub_stat error
                { 
                  yyerror(message[missingEnd]);
                }

                {yyerrok;}      
		;

else_part      : ELSE sub_stat;

elif_part      : ELIF LPAREN expr RPAREN THEN sub_stat after_elif
		| ELIF LPAREN error RPAREN THEN sub_stat after_elif
                {
                  yyerror(message[noExprBetweenParens]);
                }
                {yyerrok;}
		;
after_elif     : | elif_part;

write_stat     : WRITE LPAREN SCONST sub_part RPAREN;
sub_part        : |COMMA expr | sub_part COMMA expr;
 
read_stat      : READ LPAREN SCONST r1 RPAREN;
r1 	       : COMMA expr | r1 COMMA expr;

expr           : and_expr| and_expr DBAR and_expr;

and_expr       : logic_expr | logic_expr DAMP logic_expr;

logic_expr     : simple_expr 
	       | simple_expr symbols simple_expr
	       | simple_expr error simple_expr
                { 
                  yyerror(message[missingSymbol]);
                }

                {yyerrok;}      
                
               ;
symbols        : LT | LE | EQ | NE |GE |GT;

simple_expr    : plus_minus term t2
	       | term t2;

t2             : | t2 plus_minus term;
plus_minus     : PLUS | MINUS;

term           : factor 
	       | term mult_div factor
	       ; 
mult_div       : STAR | DIV;

factor         : const | variable | sub_call | LPAREN expr RPAREN | BANG factor;

variable       : IDENT
               | IDENT arrayvar
               | IDENT recvar
               | IDENT arrayvar recvar
	       ;

arrayvar       : LBRACK arr RBRACK;
arr            : expr| arr COMMA expr;

recvar         : DOT variable | recvar DOT variable;

const          : ICONST | FCONST | TRUE | FALSE;

%%
