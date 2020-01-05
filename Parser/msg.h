/*
 * msg.h -- List of messages for P- compiler
 *
 * J.Lewis Nikuze
 * Department of Computer Science
 * Trinity College
 *
 * Date: 10/14/2019
 * Modification History:
 *
 */

#define MAX_MSG_LEN     256

/* error definitions for the scanner */
#define mnUnknownChar                      0

/* error definitions for the parser */
#define mnMissingIdent                     1
#define noExprAfterASGN                    2
#define badReturn			   3
#define noExprBetweenParens	           4
#define missingEnd                         5
#define bodyWithoutEnd                     6
#define programWithoutBody                 7
#define missingSymbol                      8
#define missingType                        9


/* Actual messages */
static char *message[] = {
    "Unknown character",
    "missing identifier",
    "missing expression after the assignment symbol",
    "return should be followed by an expression",
    "expression required between parantheses",
    "required 'END' at the end of the loop",
    "the body requires'END' at the end", 
    "the program requires a complete body", 
    "missing symbol between expressions", 
    "missing type after the colon",
   
};
