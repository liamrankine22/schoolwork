#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

// Struct containing low and high for the thread function
struct thread_args
{
	int low;
	int high;
};

// Struct containing the results of the thread function (sum and count)
struct thread_result
{
	long sum;
	int count;
};

// Thread function which calculates all prime numbers within the given low and high and returns the amount and their sum
void* thread_function(void* arg)
{
	// Casts input to be of thread_args struct
	struct thread_args* input = (struct thread_args*)arg;

	int low = input->low;
	int high = input->high;
	int flag, i;

	// Allocates space for the result struct
	struct thread_result* result = malloc(sizeof(struct thread_result));
	// Checks if memory was correctly allocated
	if (result == NULL)
	{
		perror("Failed allocating memory");
		exit(-1);
	}

	// Loops until low = high
	while(low < high)
	{
		// sets the flag for if the number is a prime number to 0
		flag = 0;

		// Ignore numbers less than 2
		if (low <= 1)
		{
			++low;
			continue;
		}

		// If low is a non-prime number flag will be 1
		for (i = 2; i <= low / 2; ++i)
		{
			if (low % i == 0)
			{
				flag = 1;
				break;
			}
		}

		// if low is a prime number add the result to the result object's sum and increase its count
		if (flag == 0)
		{
			result->sum+=low;
			result->count++;
		}

		// increment low
		++low;
	}

	// Returns the result
	return (void*)result;
}


int main(int args, char* argv[])
{

	// Checks if the number of arguments is the correct amount
	if (args < 3)
	{
		// Tells the user how to use the program and exits
		printf("Usage: ./assignment-3 NUM_THREADS MAX_NUMBER\n");
		return -1;
	}

	int max_num = atoi(argv[2]); // contains the number to compute primes to
	int num_threads = atoi(argv[1]); // Contains the number of threads
	int quotient = max_num / num_threads; // Contains the number of times the number of threads can go into the max numbers
	int remainder = max_num % num_threads; // Contains the remainder to check if there is not an even division
	long grand_sum = 0; // Variable containing the grand sum
	int grand_count = 0; // Contains the grand count

	pthread_t threads[num_threads]; // Creates number of threads' threads
	struct thread_args arg[num_threads]; // Creates array of thread_args objects
	struct thread_result* results[num_threads]; // Creates array of thread_result objects
	for (int i = 0; i < num_threads; i++) // Sets thread arguments
	{
		arg[i].low = quotient * i; // Sets the low of the thread
		arg[i].high = quotient * (i + 1); // Sets the high of the thread
	}

	if (remainder != 0)
	{
		arg[num_threads-1].high = max_num; // Sets the high of the last thread to be the maximum number
	}

	for (int i = 0; i < num_threads; i++)
	{
		// Creates the threads and passes them the thread_function and its argument casted to void
		printf("Thread # %d finding primes from low = %d to high = %d\n", i, arg[i].low, arg[i].high);
		int complete_create = pthread_create(&threads[i], NULL, thread_function, (void*)&arg[i]);

		// Checks if the threads were created correctly, exits if not
		if(complete_create != 0)
		{
			perror("Failure creating thread");
			return -1;
		}
	}

	for (int i = 0; i < num_threads; i++)
	{
		// Joins the threads and catches their result
		int complete_join = pthread_join(threads[i], (void**)&results[i]);

		// Checks if the threads were joined correctly, exits if not
		if (complete_join != 0)
		{
			perror("Failure joining thread");
			return -1;
		}
		// Prints the result computed by thread # and adds its results to the total sum and count
		printf("Thread # %d Sum is %ld, Count is %d\n", i, results[i]->sum, results[i]->count);
		grand_sum+=results[i]->sum;
		grand_count+=results[i]->count;

		// Frees the allocated results after use
		free(results[i]);
	}
	// Prints the total sum and count
	printf("	GRAND SUM IS %ld, COUNT IS %d\n", grand_sum, grand_count);

	// Returns 0 signifying correct execution of program
	return 0;
}
