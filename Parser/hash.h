/*
 * hash.h -- define the structures used in the symbol table.
 *
 * J.Lewis Nikuze
 * Department of Computer Science
 * Trinity College
 *
 * Date: 10/14/2019
 * Modification History:
 *
 */

#define iheMax        211
#define VAR             1
#define VAL_PARAM       2
#define VAR_PARAM       3

struct he {                    /* hash entries */
    char *sb;                  /* name of entry */
    struct he *phe;            /* next hash entry */
    struct at *pat;            /* attributes of name */
};

/* FOR PHASE 3 AND 4 */
struct at {
    struct he *phe;            /* the name for the attributes */
    struct at *patBlock;       /* points to the next symbol in the same block */
    struct at *patSymbol;      /* points to the symbol in outer block */
    struct dt *pdt;            /* data type */
    int lv;                    /* level of the scope */
    int fType;                 /* true if the indentifier is a type name */
    int tfParam;               /* ternary flag having the values: 
                                  VAR, VAL_PARAM, VAR_PARAM */
    int offset;                /* only for the local variables for the 
                                  subprograms */
};

struct bh {
    struct bh *pbh;            /* next block */
    struct at *pat;    
};

