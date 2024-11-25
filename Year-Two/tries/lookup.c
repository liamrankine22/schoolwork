#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "trie.h"
#define MAX_WORD_LENGTH 100

/*
 * Tells the user how to interact with the trie
 */
void usage()
{
    printf("Usage: lookup [OPTION] [ARGUMENT]\n");
    printf("Options:\n");
    printf("  p <prefix>   Print all words with the given prefix\n");
    printf("  c <prefix>   Check if the prefix is in the trie\n");
    printf("  w <word>     Check if the word is in the trie\n");
    printf("  (no option)  Print all words in the trie\n");
}
/*
 * Main method used for interteracting with the trie
 */
int main(int argc, char* argv[])
{
    trie* t = trie_create(); // Creates a new trie
    char word[MAX_WORD_LENGTH]; // Stores the word to be read from a file

    while(scanf("%s", word) != EOF)
    {
        trie_insert(t, word); // Inserts all words from a file into the trie
    }

    if(argc < 3)
    {
        // If no option is selected the trie is printed, freed then ends the program
        trie_print(t);
        trie_free(t);
        usage();
        return 0;
    }

    char *choice = argv[1]; // Stores the option the user input
    char *prefix = argv[2]; // Stores what the user is searching for with the given option

    if(*choice == 'p')
    {
        trie_print_prefix(t, prefix); // if the option the user choice is p it prints the prefix
    }
    else if(*choice == 'c')
    {
        int contains = trie_contains_prefix(t, prefix); // If the option is c it checks if the prefix is in the word
        printf("Prefix %s: %d\n", prefix, contains);
    }
    else if(*choice == 'w')
    {
        int contains = trie_contains(t, prefix); // If the option is w it prints if the word is in the trie
        printf("Word %s: %d\n", prefix, contains);
    }
    trie_free(t); // Frees the memory allocated by the trie
    usage();
    return 0;
}
