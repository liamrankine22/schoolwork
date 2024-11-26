#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// Struct containing each process and its information
typedef struct {
	char id[50];
	int burst, arrival_time, turnaround_time, wait_time;
	bool completed;
} Process;

// Counts the number of lines in a file
int count_lines(FILE *file) {
	int lines = 0;
	char ch;

	// Checks if the character is a newline character, if so increments the number of lines
	while ((ch = fgetc(file)) != EOF) {
		if (ch == '\n') {
			lines++;
		}
	}

	// resets the file to the beginning to be used in the main program
	rewind(file);
	return lines;
}

// Calculates the remaining burst for the round robin
int calculate_remaining_burst(Process *process, int quantum) {
        int result = 0;
	//If the difference of the process burst and the quantum number is 0 or less set the the result to 0
        if (quantum >= process->burst) {
                result = 0;
        } else {
                result = process->burst - quantum; // set the result to be the difference between the burst and the quantum number
        }
        return result;
}

// Simulates the CPU scheduling by first come first served
void first_job(Process *processes, int num_lines) {
	int time = 0;
	Process *active_process;

	for (int i = 0; i < num_lines; i++) {
		active_process = &processes[i];
		while (active_process->burst > 0) { // Goes until the process has fully completed before movign to the next
			printf("T%d : %s - Burst left %d, Wait time %d, Turnaround time %d\n", time, active_process->id, active_process->burst, active_process->wait_time, active_process->turnaround_time);
			active_process->burst--;
			active_process->turnaround_time++;
			for (int j = i; j < num_lines; j++) {
				if (time >= processes[j].arrival_time) { // Goes through all ready process and increments its turnaround time and wait time 
					if (processes[j].arrival_time != active_process->arrival_time) {
						processes[j].turnaround_time++;
						processes[j].wait_time++;
					}
				}
			}
			time++;
		}
	}
}

// Simulates the CPU scheduling shortest available job
void shortest_job(Process *processes, int num_lines) {
	int time = 0;
	Process *active_process = &processes[0];
	int available_processes = 0;
	int num_completed = 0;

	while(num_completed < num_lines) { // Continues until all jobs have finished
		printf("T%d : %s - Burst left %d, Wait time %d, Turnaround time %d\n", time, active_process->id, active_process->burst, active_process->wait_time, active_process->turnaround_time);
		active_process->burst--;
		active_process->turnaround_time++;
		for (int j = 0; j < num_lines; j++) {
			// Goes through all processes in the ready queue and increments its turnaround and wait times
			if ((time >= processes[j].arrival_time) && (processes[j].arrival_time != active_process->arrival_time) && !processes[j].completed) {
				processes[j].turnaround_time++;
				processes[j].wait_time++;
			}
		}
		time++;
		//  Checks if the current process as completed
		if (active_process->burst == 0) {
			active_process->completed = true;
			num_completed++; // Increments the number of completed jobs
		}
		// Adds processes that become available to the CPU as time increments
		if (time < num_lines) {
			available_processes = time;
		}
		// Checks if the current job is finished if so the next uncompleted process becomes the active process then switches it to a shorter process if there is one
		for (int i = 0; i <= available_processes; i++) {
			if (!processes[i].completed && active_process->completed) {
				active_process = &processes[i];
			} else if (!processes[i].completed && processes[i].burst < active_process->burst) {
				active_process = &processes[i];
			}
		}
	}
}

// Simulates the CPU round robin scheduling
void round_robin(Process *processes, int num_lines, int quantum) {
	int time = 0;
        Process *active_process;
	int num_completed = 0;
	int remaining_burst;

	while (num_completed < num_lines) { // Continues until all jobs have completed
		for (int i = 0; i < num_lines; i++) {
			if (!processes[i].completed) { // If the current process has completed changes the active process to the next process
				active_process = &processes[i];
			} else {
				continue;
			}
			remaining_burst = calculate_remaining_burst(active_process, quantum); // Calculates how long the process should run
			while (active_process->burst != remaining_burst) {
				printf("T%d : %s - Burst left %d, Wait time %d, Turnaround time %d\n", time, active_process->id, active_process->burst, active_process->wait_time, active_process->turnaround_time);
				active_process->burst--;
				active_process->turnaround_time++;
				for (int j = 0; j < num_lines; j++) {
					// Goes through all processes in the ready queue and increments its turnaround and wait times
					if (time >= processes[j].arrival_time && processes[j].arrival_time != active_process->arrival_time && !processes[j].completed) {
						processes[j].turnaround_time++;
						processes[j].wait_time++;
					}
				}
				if (active_process->burst == 0) { // Checks if the process has finished and marks it completed if so
					active_process->completed = true;
					num_completed++;
				}
				time++;
			}
		}
	}
}

// Main method for simulating the CPU scheduling
int main(int argc, char *argv[]) {
	int quantum, mode;
	char filename[50];
	if (argc < 3 || argc > 4) { // Checks for a correct number of arguments and tells the user the usage
		printf("Usage: ./assignment-4.c <-f, -s, -r> <integer if -r> <file_name>\n");
		return -1; // Return non-0 to signify error
	}
	if (strcmp(argv[1], "-r") == 0) { // Checks if the flag is -r for round robin
		if (argc != 4) { // Checks if the number of arguments for the round robin flag is 4
			printf("Incorrect number of arguments!\nUsage: ./assignment-4.c <-r> <integer> <file_name>\n");
			return -1;
		}
		mode = 2;
		quantum = atoi(argv[2]);
		strcpy(filename, argv[3]);
	} else if (strcmp(argv[1], "-f") == 0) { // Checks if the flag is -f for first come first serve
		mode = 0;
		strcpy(filename, argv[2]);
	} else if (strcmp(argv[1], "-s") == 0) { // Checks if the flag is -s for shortest job
		mode = 1;
		strcpy(filename, argv[2]);
	} else { // Error user did not properly enter a correct flag
		printf("Incorrect flag usage!\nUsage: ./assignment-4.c <-f, -s, -r> <integer if -r> <file_name>\n");
		return -1;
	}

	char line[256];
	int i = 0;
	FILE *file;
	// Opens the file and checks if it was properly opened
	if ((file = fopen(filename, "r")) == NULL){
		printf("Could not open file\n");
		return -1;
	}
	int num_lines = count_lines(file); // Counts the number of lines in the file
	// Allocates memory for all processes
	Process *processes = malloc(num_lines * sizeof(Process));
	if (processes == NULL) {
		printf("Error allocating memory\n");
		fclose(file);
		return -1;
	}

	// Gets each line and creates a process for according to the data within
	while (fgets(line, sizeof(line), file)) {
		//printf("baka %s\n", line);
		if(sscanf(line, "%49[^,],%d", processes[i].id, &processes[i].burst) == 2) {
			processes[i].arrival_time = i;
			processes[i].wait_time = 0;
			processes[i].turnaround_time = 0;
			processes[i].completed = false;
			i++;
		} else {
			printf("Error parsing line: %s\n", line);
			return -1;
		}
	}

	fclose(file); // Closes the file after use

	if (mode == 0) { // If the flag is -f it simulates the CPU scheduling: first come first serve
		first_job(processes, num_lines);
	} else if (mode == 1) { // If the flag is -s it simulates the CPU scheduling: shortest job
		shortest_job(processes, num_lines);
	} else { // If the flag is -r it simulates the CPU scheduling: round robin
		round_robin(processes, num_lines, quantum);
	}

	float average_waiting_time;
	float average_turnaround_time;
	for (int j = 0; j < num_lines; j++){
		printf("%s\n		Waiting time:		%d\n		Turnaround time		%d\n\n", processes[j].id, processes[j].wait_time, processes[j].turnaround_time);
		average_waiting_time+=processes[j].wait_time; // Increments the average waiting time with each processes wait time
		average_turnaround_time+=processes[j].turnaround_time; // Increments the average turnaround time with each processes turnaroudn itme
	}
	average_waiting_time = average_waiting_time/num_lines; // Calculates the proper average waiting time
	average_turnaround_time = average_turnaround_time/num_lines; // Caluclates the proper average turnaround time
	// Prints the total averages of each to the user
	printf("Total average waiting time: 	%.1f\n", average_waiting_time);
	printf("Total average turnaround time: 	%.1f\n", average_turnaround_time);

	free(processes); // Frees allocated memory used by processes
	return 0; // Return 0 to signify proper execution
}
