/*
 * dt.h -- define the data structures used in the parse tree.
 *
 * J.Lewis Nikuze
 * Department of Computer Science
 * Trinity College
 *
 * Date: 10/14/2019
 * Modification History:
 *
 */

enum ty {
    tyArray,            /* array type */
    tyBool,             /* boolean type */
    tyFloat,            /* real type */
    tyFunc,             /* function tag */
    tyInt,              /* integer type */
    tyProc,             /* procedure tag */
    tyRecord,           /* record type */
    tyNull,             /* unknown tag */
};

struct dt {             /* tree node declaration */
    enum ty ty;            /* type of data item */
    struct he *phe;        /* pointer to name of type */
    int db;                /* size of type in bytes */

    union {

        struct {        /* if ty = tyArray */
            int cDim;            /* dimension */
            struct rl *prl;      /* range list */
            struct dt *pdt;      /* type of elements */
            int fParm;           /* true if unconstrained parameter array */
        } Array;

        struct {        /* if ty = tyFunc */
            struct al *pal;      /* argument list */
            struct dt *pdt;      /* return type */
            struct et *pet;      /* code for function */
            struct at *pat;      /* list of local identifiers */
            int iSubId;          /* function id in the program */
        } Func;

        struct {       /* if ty = tyProc */
            struct al *pal;      /* argument list */
            struct et *pet;      /* code for procedure */
            struct at *pat;      /* list of local identifiers */
            int iSubId;          /* procedure id in the program */
        } Proc;

        struct il *pilFieldList; /* field list if ty = tyRecord */

    } un;
};

struct il {            /* identifier list */
    struct at *pat;        /* pointer to attribute */
    struct il *pil;        /* next identifier */
};

struct al {            /* argument list */
    struct at *pat;        /* attributes of teh parameter */
    struct al *pal;        /* next parameter */
};

struct sl {            /* subprogram list for abstractions */
    struct at *pat;        /* attribute structure of subprogram tyProc 
                              or tyFunc */
    struct sl *psl;        /* next subprogram */
};

struct rl {            /* range list for arrays */
    struct rl *prl;        /* next rl */
    int iLb;               /* lower bound */
    int iUb;               /* upper bound */
    int iSize;             /* offset size */
};

    
/* Expression tree structure */

enum op {
    opIConst,         /* constants */
    opFConst,
    opBConst,
    opSConst,
    opIdent,
    opLower,
    opUpper,
    opIndex,          /* index into arrays */
    opField,          /* record field */
    opBang,           /* operators */
    opEq,
    opGe,
    opGt,
    opLe,
    opLt,
    opNe,
    opUPlus,
    opUMinus,
    opBPlus,
    opBMinus,
    opDBar,
    opStar,
    opDiv,
    opDAmp,
    opRecord,
    opAsgn,
    opExpr,         /* for expression lists */
    opFunc,
    opIf,
    opFor,
    opNull,
    opProc,
    opRead,
    opReturn,
    opSeq,
    opWhile,
    opWrite,
};

struct et {            /* expression tree */
    enum op op;
    struct dt *pdt;

    union {

        int w;                   /* int constant */
        double r;                /* float constant */
        int b;                   /* boolean constant */
        char *sb;                /* string constant */
        struct at *patIdent;     /* indentifier */

        struct {                 /* unary operator */
            struct et *pet;            /* argument */
        } unUnOp;

        struct {                 /* binary operator */
            struct et *petLeft;        /* argument 1 */
            struct et *petRight;       /* argument 2 */
        } unBinOp;

        struct {                 /* opLower */
            struct et *pet;            /* array name */
            struct et *petDim;         /* dimension number */
        } unLower;

        struct {                 /* opUpper */
            struct et *pet;            /* array name */
            struct et *petDim;         /* dimension number */
        } unUpper;

        struct {                 /* index into array */
            struct et *pet;            /* array name */
            struct et *petOffset;      /* offset */
        } unIndex;

        struct {                 /* record filed */
            struct et *petBase;        /* record base */
            struct et *petField;       /* subfield, e.g., b.y */
        } unRecord;

        struct {                 /* assignement statement */
            struct et *petLeft;        /* left side */
            struct et *petRight;       /* right side */
        } unAsgn;

        struct {                 /* if statement */
            struct et *petCond;        /* condition */
            struct et *petTrue;        /* true branch */
            struct et *petFalse;       /* false branch */
        } unIf;

        struct {                 /* return statement */
            struct et *pet;            /* value to return */
        } unReturn;

        struct {                 /* for loop */
            struct et *pet;            /* loop variable */
            struct et *petLower;       /* lower bound */
            struct et *petUpper;       /* upper bound */
            struct et *petBody;        /* loop body */
        } unFor;

        struct {                /* while loop */
            struct et *petCond;        /* loop condition */
            struct et *petBody;        /* loop body */
        } unWhile;

        struct {                /* subprogram call */
            struct at *patSub;         /* subprogram */
            struct et *petExpr;        /* expression list */
        } unSubCall;

        struct {                /* read statement */
            struct et *petSb;          /* string */
            struct et *petExpr;        /* expression list */
        } unRead;

        struct {                /* write statement */
            struct et *petSb;          /* string */
            struct et *petExpr;        /* expression list */
        } unWrite;

        struct {                /* expression list */
            struct et *pet;            /* expression */
            struct et *petNext;        /* next expression */
        } unExpr;

        struct {                /* statement list */
            struct et *pet;            /* statement */
            struct et *petNext;        /* next expression */
            struct et *petLast;        /* last expression */
        } unSeq;
    } un;
};
