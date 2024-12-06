#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <ctype.h>
#include <string.h>
#include <unistd.h>

// Defining Constants for the size of the library
#define SEAT_ROWS 6 // size of 6 for ignoring the 0th row
#define SEAT_COLS 13 // Size of t 13 for ignoring the 13th row

// Declare global mutex lock
pthread_mutex_t lock;

// Customer's info containing their name, the number of seats they want, and 2 arrays for the rows and columns
// of seats they want
struct customer_info{
	int *desired_seat_row; // Customer's desired seat row
	int *desired_seat_col; // Customer's desired seat columw
	int name; // Customer's name
	int num_seats_wanted;
};

// Seat info, storing if its available and its occupant
struct seat_info{
	int occupant; // Initalize no occupant
	int available; // seat availablity 1 for true, 0 for false
};

struct seat_info theater[SEAT_ROWS][SEAT_COLS]; // Global theater array

// Counts the number of lines in the files for keeping track the number of requests
int count_lines(FILE* file){
	if (file == NULL) {
		perror("Error opening file");
		return -1;
	}
	int line_count = 0;
	char ch;
	while ((ch = fgetc(file)) != EOF) {
		if (ch == '\n') {
			line_count++;
		}
	}
	rewind(file);
	return line_count;
}

// Removes whitespace from a txt file to properly parse information
void remove_whitespace(char *str) {
	int read_index = 0, write_index = 0;
	// While the read_index character is not the null character, and not whitespace, write whats in the write
	// index into the read index and increment both
	while(str[read_index]!='\0') {
		if (!isspace(str[read_index])) {
			str[write_index++] = str[read_index];
		}
		read_index++;
	}
	str[write_index] = '\0';
	//end the string with the null character to signify end
}

// Used by the customers to book a seat;
void* book_seat(void* arg){
	pthread_mutex_lock(&lock); // Create the mutex lock
	struct customer_info* customer = (struct customer_info*)arg; // Convert the arg into the customer's info
	struct seat_info* seat; // Create a seat struct for referencing locations in the theater
	int successful_booking = 1; //Initalize 1 for true

	// For each seat the customer wants check if they're available, if not signify their success as false (0)
	// and break the loop to avoid checking any other seats
	for (int i = 0; i < customer->num_seats_wanted; i++){
		seat = &theater[customer->desired_seat_row[i]][customer->desired_seat_col[i]];
		if (seat->available == 0){
			successful_booking = 0;
			break;
		}
	}
	// If the booking was unsuccessful, print the fail message along with customer name and seats wanted
	if (successful_booking == 0) {
		printf("Customer %d - Fail -", customer->name);
		for(int i = 0; i < customer->num_seats_wanted; i++){
			printf(" Asile %d, Seat %d", customer->desired_seat_row[i], customer->desired_seat_col[i]);
		}
		printf("\n");
	} else {
		// If the boooking was sucessfull sleep for 1-3 seconds then print the success message including the
		// customer name and desired seats and book their seats
		int sleep_time = rand() %3 + 1;
		sleep(sleep_time);
		printf("Customer %d - Successful -", customer->name);
		for(int i = 0; i < customer->num_seats_wanted; i++){
			seat = &theater[customer->desired_seat_row[i]][customer->desired_seat_col[i]];
			seat->occupant = customer->name;
			seat->available = 0;
			printf(" Asile %d, Seat %d", customer->desired_seat_row[i], customer->desired_seat_col[i]);
		}
		printf("\n");
	}

	pthread_mutex_unlock(&lock); // Unlock the mutex for the next thread
	return NULL; // Return nothing
}

// Used for parsing the file and properly allocating customer information before booking
void booking_values(char *str, struct customer_info *customer){
	char *token = strtok(str,","); // Split by comma
	int nums[60]; // Max 60 for available seats
	int i = 0; // Index
	// While the token is not null, if the token begins with a # stop parsing, otherwise convert the token into
	// An integer and inccrement the index I, continue.
	while (token != NULL) {
		if(token[0] == '#'){
			break;
		}
		nums[i]  = atoi(token);
		//printf("%d\n", nums[i]);
		i++;
		token = strtok(NULL, ",");
	}
	customer->name = nums[0]; // Set the customer's name to the first number in the line
	// If the number of numbers in the line was even, it means the number of seats ordered was odd and allocates
	// An extra number of bytes to ensure theres enough space to contain all desired seats
	if (i % 2 == 0){
		customer->desired_seat_row = malloc((i+1)/2 * sizeof(int));
		customer->desired_seat_col = malloc((i+1)/2 * sizeof(int));
	// Otherwise the number of seats is even and i is enough space to allocate the seats
	} else {
		customer->desired_seat_row = malloc(i/2 * sizeof(int));
		customer->desired_seat_col = malloc(i/2 * sizeof(int));
	}
	// Makes sure the arrays were properly allocated
	if (customer->desired_seat_row == NULL || customer->desired_seat_col == NULL) {
		printf("Failed to allocate seat row or column\n");
		exit(-1);
	}
	int b2 = 0; // Index for keeping track the number of seats
	for (int b = 0; b < i; b++){
		if(b == 0){
			continue; // Skip the customer name
		} else if (b % 2 == 1){
			customer->desired_seat_row[b2] = nums[b]; // Fills the array with the rows of each seat wanted
		} else {
			customer->desired_seat_col[b2] = nums[b]; // Fills the array with the cols of each seat wanted
			b2++; //Increment to next seat
		}
	}
	customer->num_seats_wanted = b2; // set the number of desired seats to the index found before
}

// Main method for executing the simulated customer seat booking for assignment-5
int main(int argc, char *argv[]) {
	if (argc < 2 || argc >2){ // Checks if the number of arguments is correct, if not prints how to use the program
					// to the user
		printf("Usage: ./assignment-5.c FILENAME\n");
		return -1; // -1 to signify improper execution
	}
	srand(time(NULL)); // Initalize random function with NULL seed

	FILE *file; // Creates pointer to FILE
	file = fopen(argv[1], "r"); // Opens the file to read only
	char readOrder[1024]; // Buffer for parsing file
	int num_customers = count_lines(file) - 1;	// Counts the number of lines to find the number of customers
							// Ignoring the first line which has no useful info
	struct customer_info* customers[num_customers]; // Creates an array of customer_infos enough to contain all
							// customer's info
	int i = 0; // index

	fgets(readOrder, 1024, file); // Skip first iteration
	// Reads each line in the file and allocates a customer_info struct fo them, if it's value is NULL it signifies
	// the allocation didnt properly execute and exits
	while(fgets(readOrder, 1024, file)) {
		customers[i] = (struct customer_info*) malloc(sizeof(struct customer_info));
		if(customers[i] == NULL){
			printf("Memory allocation failed\n");
			return -1;
		}
		remove_whitespace(readOrder); // Removes the whitespace from the line
		booking_values(readOrder, customers[i]); // Gets the customer's info using the booking_values function
		i++; // Increments the index
	}

	// Initalizes the theater with empty seats and its occupant as 000
	for (int row = 0; row < SEAT_ROWS; row++){
		for(int col = 0; col < SEAT_COLS; col++){
			theater[row][col].available = 1;
			theater[row][col].occupant = 000;
		}
	}

	pthread_t threads[num_customers]; // Creates threads for each customer
	// Initalizes the lock, exits if the initalization fails
	if (pthread_mutex_init(&lock, NULL) != 0) {
		printf("Mutex initalization failed\n");
		return -1;
	}

	// Creates threads for each customer passing the book_seat as the thread function and customers[index]
	// as the argument, exits if a thread fails to be created
	for (int thread = 0; thread < num_customers; thread++){
		if(pthread_create(&threads[thread], NULL, book_seat, (void*)customers[thread]) != 0) {
			printf("thread creation failed\n");
			return -1;
		}
	}

	// Joins the threads back after execution, exits if a thread fails to join
	for (int thread = 0; thread < num_customers; thread++){
		if (pthread_join(threads[thread], NULL) != 0) {
			printf("Failed to join thread\n");
			return -1;
		}
	}
	pthread_mutex_destroy(&lock); // Destroys the mutex lock as its no longer needed

	// Prints the theater showing all the seats and their occupants along with each seats location in a grid
	printf("          1   2   3   4   5   6   7   8   9  10  11  12\n");
	for (int i = 0; i < SEAT_ROWS - 1; i++){
		printf("Asile %d", i+1);
		for (int n = 0; n < SEAT_COLS - 1; n++){
			if (theater[i+1][n+1].occupant == 0){
				printf(" 000");
			} else {
				printf(" %d", theater[i+1][n+1].occupant);
			}
		}
		printf("\n");
	}

	// Frees formeerly allocated memory for the customer's desired seats and the customer itself
	for (int i = 0; i < num_customers; i++){
		free(customers[i]->desired_seat_row);
		free(customers[i]->desired_seat_col);
		free(customers[i]);
	}
	fclose(file); // Closes the file as its no longer needed
	return 0; // Returns 0 signifying proper execution of the program
}
