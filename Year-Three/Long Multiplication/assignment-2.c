#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

//Structure for partitioning numbers
typedef struct
{

	int top_half; //a1/b1
	int bottom_half; //a2/b2

} partitioned_values;

//Method that takes a value, splits a 4 digit value into 2 halves, top half containing the top 2 digits and the bottom half containign the bottom 2 digits
partitioned_values partition(int value)
{
	partitioned_values result; //Creating struct to return

	result.top_half = value/100; //a1/b1
	result.bottom_half = value%100; //a2/b2

	return result;
}

//Prints the parent sending a number to the child process
void print_parent_sending(int id, int num)
{
	printf("Parent (PID %d): Sending %d to child\n", id, num);
}

//Prints the parent recieving a number from the child process
void print_parent_recieving(int id, int num)
{
	printf("Parent (PID %d): Recieved %d from child\n", id, num);
}

//Prints the child sending a number to the parent process
void print_child_sending(int id, int num)
{
	printf("Child (PID %d): Sending %d to parent\n", id, num);
}

//Prints the child recieving a number from the parent process
void print_child_recieving(int id, int num)
{
	printf("Child (PID %d): Recieved %d from parent\n", id, num);
}


//Calculates the final product
void product_of_two_numbers(int a, int b, int c, int d)
{
	int x = a * 10000; //Caculates X
	int y = (b + c) * 100; //Calculates Y
	int z = d; //Calculates Z
	int sum = x + y + z; //Calculates the product
	printf("%d + %d + %d == %d\n", x, y, z, sum);
}


int main(int argc, char *argv[])
{

	//Checks if the number of arguments is not 3 which should be the only one it accepts
	if (argc != 3)
	{
		//Lets the user know how the program should be typed into the command line to execute
		printf("Please enter in the format: ./assignment-2 x y\nwhere x and y are 4 digit numbers within the range 1000-9999\n");
		return -1;
	}

	else
	{
		//Checks if both strings are of length 4, lets the user know if not and exits
		if (strlen(argv[1]) != 4 || strlen(argv[2]) != 4)
		{
			printf("Please only use digits with a length of 4 only\n");
			return -1;
		}

		//Converts the strings to integers
		int number_one = atoi(argv[1]);
		int number_two = atoi(argv[2]);

		//Checks if both numbers are in the range of 1000 to 9999, if not lets the user know and returns
		if (number_one < 1000 || number_two < 1000)
		{
			printf("Please use numbers in the range 1000 - 9999\n");
			return -1;
		}

		//Tells the user their input integers
		printf("Your integers are %d %d\n", number_one, number_two);

		//Partions values into 2 halves
		partitioned_values a = partition(number_one);
		partitioned_values b = partition(number_two);

		//Variables storing all numbers needed to calculate X Y and Z
		int calculated_a;
		int calculated_b;
		int calculated_c;
		int calculated_d;

		int pipe_parent[2]; // Parent --> Child communication
		int pipe_child[2]; // Child --> Parent communation
		pid_t pid; //Main process ID

		//Creates a Parent to Child pipe
		if (pipe(pipe_parent) == -1)
		{
			//Tells the system the pipe failed to create and exits
			perror("Pipe failed");
			return -1;
		}

		//Creates a Child to Parent pipe
		if (pipe(pipe_child) == -1)
		{
			//Tells the system the pipe failed to create and exits
			perror("Pipe failed");
			return -1;
		}

		//Creates a child process
		pid = fork();

		//Checks if the fork failed to create
		if (pid < 0)
		{
			//If the fork failed to create it tells the system and exits
			perror("Fork failed");
			return -1;
		}
		//If the fork was created correctly it executes the following code
		if (pid == 0)
		{

			//Variables storing the child process id along with all halves of the 2 original integers
			int child_id = getpid();
			int a1;
			int b1;
			int a2;
			int b2;

			//Calculating A for X
			read(pipe_parent[0], &a1, sizeof(a1)); //Reads from parent to child pipe which should have the first half of the first integer
			print_child_recieving(child_id, a1);

			read(pipe_parent[0], &b1, sizeof(b1));
			print_child_recieving(child_id, b1);

			int result_a = a1 * b1;
			print_child_sending(child_id, result_a);
			write(pipe_child[1], &result_a, sizeof(result_a)); // Writes to the child to parent pipe the value of A

			//Calculating C for Y
			read(pipe_parent[0], &a1, sizeof(a1));
			print_child_recieving(child_id, a1);

			read(pipe_parent[0], &b2, sizeof(b2));
			print_child_recieving(child_id, b2);

			int result_c = a1 * b2;
			print_child_sending(child_id, result_c);
			write(pipe_child[1], &result_c, sizeof(result_c));

			//Calcualting B for Y
			read(pipe_parent[0], &a2, sizeof(a2));
			print_child_recieving(child_id, a2);

			read(pipe_parent[0], &b1, sizeof(b1));
			print_child_recieving(child_id, b1);

			int result_b = a2 * b1;
			print_child_sending(child_id, result_b);
			write(pipe_child[1], &result_b, sizeof(result_b));

			//Calculating D for Z
			read(pipe_parent[0], &a2, sizeof(a2));
			print_child_recieving(child_id, a2);

			read(pipe_parent[0], &b2, sizeof(b2));
			print_child_recieving(child_id, b2);

			int result_d = a2 * b2;
			print_child_sending(child_id, result_d);
			write(pipe_child[1], &result_d, sizeof(result_d));

			//Pipes are no longer needed at this point so they are closed
			close(pipe_child[0]);
			close(pipe_child[1]);

		}
		else
		{

			int parent_id = getpid(); //Stores the parent/original process id
			printf("Parent (PID %d): created child (PID %d)\n\n", parent_id, pid); //Lets the user know a child process was created

			//Calcualting X
			printf("###\n# Caluclating X\n###\n");

			print_parent_sending(parent_id, a.top_half);
			write(pipe_parent[1], &a.top_half, sizeof(a.top_half)); //Writes the value of a1 into the parent to child pipe

			print_parent_sending(parent_id, b.top_half);
			write(pipe_parent[1], &b.top_half, sizeof(b.top_half));

			read(pipe_child[0], &calculated_a, sizeof(calculated_a)); //Reads the value of A from the child to parent pipe
			print_parent_recieving(parent_id, calculated_a);

			//Calculating Y
			printf("\n###\n# Calculating Y\n###\n");

			print_parent_sending(parent_id, a.top_half);
			write(pipe_parent[1], &a.top_half, sizeof(a.top_half));

			print_parent_sending(parent_id, b.bottom_half);
			write(pipe_parent[1], &b.bottom_half, sizeof(b.bottom_half));

			read(pipe_child[0], &calculated_c, sizeof(calculated_c));
			print_parent_recieving(parent_id, calculated_c);

			print_parent_sending(parent_id, a.bottom_half);
			write(pipe_parent[1], &a.bottom_half, sizeof(a.bottom_half));

			print_parent_sending(parent_id, b.top_half);
			write(pipe_parent[1], &b.top_half, sizeof(b.top_half));

			read(pipe_child[0], &calculated_b, sizeof(calculated_b));
			print_parent_recieving(parent_id, calculated_b);


			//Calculating Z
			printf("\n###\n# Calculating Z\n###\n");

			print_parent_sending(parent_id, a.bottom_half);
			write(pipe_parent[1], &a.bottom_half, sizeof(a.bottom_half));

			print_parent_sending(parent_id, b.bottom_half);
			write(pipe_parent[1], &b.bottom_half, sizeof(b.bottom_half));

			read(pipe_child[0], &calculated_d, sizeof(calculated_d));
			print_parent_recieving(parent_id, calculated_d);

			printf("\n%d*%d == ", number_one, number_two); //Prints the two orginal integers
			product_of_two_numbers(calculated_a, calculated_b, calculated_c, calculated_d); //Prints the full formula along with the final product

			//Parent to child pipes are no longer needed at this point so they are closed
			close(pipe_parent[0]);
			close(pipe_parent[1]);

			wait(NULL); //Waits for child process to end
		}

	}

	return 0;

}

