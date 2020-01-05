/*
 * report.c -- report the detected errors
 *
 * J.Lewis Nikuze
 * Department of Computer Science
 * Trinity College
 *
 * Date: 10/14/2019
 * Modification History:
 *
 */

#include <stdio.h>

extern int cLine;        
extern char *infile;
extern int cErrors;

void PrintErrMsg(char *msg)
{
    fprintf(stderr, "\n%s: %d: %s", infile, cLine, msg);
    cErrors++;
}

void yyerror(char *msg)
{
    if (strcmp(msg, "parse error") != 0) {
        fprintf(stderr, "\n%s: %d: %s", infile, cLine, msg);
        cErrors++;
    }
}
