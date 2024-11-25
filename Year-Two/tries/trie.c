/*
 * trie.c
 *
 * Computer Science 2211B - WInter 2024
 * Assignment 4
 * Student: Liam Rankine
 *
 * This program allows the creation of a trie along with several ways to
 * interact with said trie
 */
#include "trie.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define ALPHABET_SIZE 26 //Used for size number of children in trie

/*
 * Calucates the order of a letter in the alphabet
 *
 * Parameters:
 *      letter: contains the letter to check the ordering of in the alphabet
 *
 * Returns: The position of the letter in the alphabet
 */
int alphabet_order(char letter)
{

    int position = letter - 'a'; // Calculates position in alphabet and is stored
    return position;

}
/*
 * Creates a trie node using the input data
 *
 * Parameters
 *      data: Stores the data to be inserted to the trie node
 *
 * Returns: A new trie node storing the data
 */
trie_node* trienode_create(char data)
{

     trie_node* new_node = (trie_node*)malloc(sizeof(trie_node)); // Allocates spaces for the node
     new_node->data = data; // Sets the nodes data to the input data
     new_node->word = NULL; // Initializes the word to be NULL
     for (int i = 0; i < 0; i++)
     {
          new_node->children[i] = NULL; // Initializes node's children to be NULL
     }
     return new_node;

}
/*
 * Creates a trie
 *
 * Parameters:
 *      N/A
 *
 * Returns: An empty trie
 */
trie* trie_create()
{

    trie* new_trie = (trie*)malloc(sizeof(trie)); // Allocates space for trie
    new_trie->root = trienode_create('$'); // Creates the root node defined by its unique data point '$'
    new_trie->size = 0; // Sets the size to 0
    return new_trie;

}
/*
 * Inserts a word into the trie by creating either a new path of nodes or using a prexisting one
 *
 * Parameters:
 *      t: Stores the trie to insert a node into
 *      word: Stores the word to be inserted
 *
 * Returns: N/A
 */
void trie_insert(trie* t, char* word)
{
    int isin_trie = trie_contains(t, word); // Stores if the word exists in the trie
    if(isin_trie == 1)
    {
        printf("Word: %s, is already in trie\n", word); //Says the word already exists in trie and exits
        return;
    }

    trie_node* current_node = t->root; // Dereferencing the root node
    const char *current_char = word; // Stores the starting character of the input word
    char character; // Stores a character
    int order; // Stores the alphabetic order of a character
    while(*current_char != '\0')
    {
        character = *current_char; // Sets the character equal to the first letter in the word
        order = alphabet_order(character); // Finds the order of said character

        if(current_node->children[order] != NULL)
        {
            current_node = current_node->children[order]; // If the path to the word does exist it changes the node to the next character in the word
        }

        else
        {
            // If the current character is not a child of the current node then a new node is created and set to be the child of the current node
            trie_node* new_node = trienode_create(character);
            current_node->children[order] = new_node;
            current_node = new_node; // The new node is set to the current node
            t->size++; // Size of trie is incremented
        }

        current_char++; // Increments pointer of the word to the next character

    }

    // Allocates space for the newly input word
    current_node->word = malloc(strlen(word) + 1);
    strcpy(current_node->word, word);

}
/*
 * Checks if the trie contains a given word
 *
 * Parameters:
 *      t: Stores the trie to be used in search for word
 *      word: Stores the word to be searched for
 *
 * Rrturns: 1 if the word exists within the trie, 0 otherwise
 */
int trie_contains(trie* t, char* word)
{
    trie_node* current_node = t->root; // Derefrences the root node
    char *current_char = word; // Creates pointer to first character in the word
    char *next_char = current_char + 1; // Creates pointer to the next character after the current character
    int order; // used to store the order of the character
    while(*current_char != '\0')
    {

        order = alphabet_order(*current_char); // calculates order of the current character
        if(current_node->children[order] != NULL)
        {
            current_node = current_node->children[order]; // If node exits storeing the current character the node is switched
            if(*next_char == '\0' && current_node->word != NULL)
            {
                return 1; // If the new current node is not null and the nexct character is end of string
                          // by how the program works it means the word was foundit returns 1 signifiying that word does exist
            }
        }
        else
        {
            return 0; // If the node storing the current character doesnt exist, it means the word doesnt exist so it returns 0
        }
        current_char++; next_char++;// Increments pointer to point to next character
    }
    return 0; // Returns 0 signifiying the word wasn't found

}
/*
 * Checks if the given prefix exists within the trie
 *
 * Parameters:
 *      t: Stores trie to be used to search for prefix
 *      prefix: Stores the prefix to be searched for
 *
 * Returns: 1 if the trie contains the prefix, 0 otherwise
 */
int trie_contains_prefix(trie* t, char* prefix)
{

    trie_node* current_node = t->root; // Derefrences root node
    char *current_char = prefix; // Derefrences prefix
    char *next_char = current_char + 1; // Stores the next letter in the prefix
    while(*current_char != '\0')
    {
        char character = *current_char; // Stores the current character
        int order = alphabet_order(character); // Stores where the current character is in the alphabet ordering
        if(current_node->children[order] == NULL)
        {
            return 0; // If the current character does not exist within the current_node's children then the prefix does not exist
        }
        else
        {
            current_node = current_node->children[order]; // Sets the node to the child node containing the current character
            if(*next_char == '\0')
            {
                return 1; // If the next letter in the prefix is the end character then the prefix exists
            }
        }
        current_char++; next_char++; //increments pointers of the current character and next character
    }

}

/*
 * Prints all nodes starting from a given node recursivly and in alphabetic order
 *
 * Parameters:
 *      node: Stores the node to print all nodes related to it
 *
 * Returns: All words stemming from the input node
 */
void trienode_print(trie_node* node)
{
    trie_node* current_node = node; // Derefrences the node
    if(current_node->word != NULL)
    {
        printf("%s\n", current_node->word); // If the node's word is not null it prints it
    }
    for(int i = 0; i < ALPHABET_SIZE; i++)
    {
        if(current_node->children[i] != NULL)
            {
            trienode_print(current_node->children[i]); // Recursively calls the function for every node that is a child to the input node
            }
    }

}
/*
 * Prints all words contained within the trie
 */
void trie_print(trie* t)
{

    trienode_print(t->root); // calls the trienode_print on the root node so that it prints the entire trie

}

/*
 * Prints all words containing the given prefix
 *
 * Parameters:
 *      t: Stores the trie to be used
 *      prefix: Stores the prefix to print from
 *
 * Returns: All words that contain the prefix
 */
void trie_print_prefix(trie* t, char* prefix)
{
    int contains = trie_contains_prefix(t, prefix); // Checks if the prefix is in the trie
    if(contains == 0)
    {
        return; // Returns if the prefix doesnt exist in the trie
    }

    trie_node* current_node = t->root; // Derefrences the root node
    char *current_char = prefix; // Stores the first letter of the prefix
    char *next_char = current_char + 1; // Stores the next letter of the current character
    while(*current_char != '\0')
    {
        char character = *current_char; // Stores the current_character
        int order = alphabet_order(character); // Stores the order of the current_character
        if(current_node->children[order] != NULL)
        {
             current_node = current_node->children[order]; // If node storing the current characgter is a child of the current node the current node is switched to that node
             if(*next_char == '\0')
             {
                 trienode_print(current_node); // If the next character is a null character every word from the current node is printed
             }
        }
        // Current character and next character pointers are incremented
        current_char++;
        next_char++;
    }

}

/*
 * Free all memory allocated to a node and the nodes children recursively
 *
 * Parameters:
 *      node: stores the node to recursively free memory for all related nodes and itself
 *
 * Returns: N/A
 */
void trienode_free(trie_node* node)
{
    if(node == NULL)
    {
        return; // Base case, if not is null no memory needs to be allocated
    }

    for(int i = 0; i < ALPHABET_SIZE; i++)
    {
        trienode_free(node->children[i]); // Recursively calls for all children of a node
    }

    free(node->word); // Frees the memory allocated to a word
    node->word = NULL; // Sets word to null
    free(node); // Frees the memory allocated to the node

}

/*
 * Frees all memory allocated in a trie
 *
 * Parameters:
 *      t: stores the trie to be freed
 *
 * Returns: N/A
 */
void trie_free(trie* t)
{
    trienode_free(t->root); // Calls the trienode_free method on the root node to recursively free all nodes
    free(t); // Frees memory allocated by the trie itself
}
