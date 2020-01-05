/*
 * scanner.h -- a lexical analyzer definitions
 *
 * J.Lewis Nikuze
 * Department of Computer Science
 * Trinity College
 *
 * Date: 10/14/2019
 * Modification History:
 *
 */

#include <stdlib.h>
#include "msg.h"
#include "parser.tab.h"

#define fError        -1
#define fMore         -2

int cLine = 1;
extern struct he *Insert(char *), *Lookup(char *);
extern char *MakeString(char *);
