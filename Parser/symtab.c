/*
 * symtab.c -- symbol table routines
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

extern struct he *symtab[];

/*
 * InitSymTab() -- initialize the symbol table
 */
void InitSymTab()
{
    int i;

    for (i = 0; i < iheMax; i++)
        symtab[i] = NULL;
}
