/*
 * hash.c -- hash structure routines
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
#include <stdlib.h>
#include "dt.h"
#include "hash.h"

#define YES    1
#define NO     0

extern struct he *symtab[];

/* function prototypes */
int Hash(char *sb);
struct he *Lookup(char *sb);
struct he *Insert(char *sb);
char *MakeString(char *sb);

/*
 * Hash(char *sb) -- compute hash value of sb.
 */
int Hash(char *sb)
{
    int w;

    for (w = 0; *sb != '\0';)
        w += *sb++;
    return(w % iheMax);
}

/*
 * Lookup(char *sb) -- lookup sb in the hash table.
 */
struct he *Lookup(char *sb)
{
    int ihe;
    struct he *phe;

    ihe = Hash(sb);
    for (phe = symtab[ihe]; phe != NULL; phe = phe->phe)
        if (strcmp(sb, phe->sb) == 0)
            return(phe);
    return(NULL);     /* not found */
}

/*
 * Insert(char *sb) -- insert sb in the hash table.
 */
struct he *Insert(char *sb)
{
    struct he *phe;
    int ihe;

    phe = (struct he *) malloc(sizeof(*phe));
    phe->sb = MakeString(sb);
    phe->pat = NULL;
    ihe = Hash(sb);
    phe->phe = symtab[ihe];
    symtab[ihe] = phe;
    return(phe);
}

/*
 * MakeString(char *sb) -- create a copy of string sb.
 */
char *MakeString(char *sb)
{
    char *sbNew = malloc(strlen(sb) + 1);

    strcpy(sbNew, sb);
    return(sbNew);
}
