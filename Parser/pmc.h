/*
 * pmc.h -- main definitions
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
#include "hash.h"
#include "msg.h"

#define   MAX_FILE_NAME    256
struct he *symtab[iheMax]; /* symbol table */
extern FILE *yyin;
int cErrors;
char *infile;
