// wheel.c
// Will Laroche & Lewis Nikuze
// Outputs a list of "prime" numbers between 2 and an inputted int N
// Accomplishes this by removing all numbers between 2 and N which are factors of 2, 3, or 5
// Uses three, 5-slot bounded buffers to pipeline the filtration process

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <pthread.h>
#include <semaphore.h>

#define NUM_THREADS 4
#define BUFF_SZ 5
#define NUM_BUFF 3

// bounded buffer structure
typedef struct {
	int next_in;
	int next_out;
	int buff_array[BUFF_SZ];
} b_buffer;

// structure for accessing the three buffers
typedef struct {
	b_buffer b[NUM_BUFF];
} all_buffers;


sem_t d_0, d_1, d_2, e_0, e_1, e_2, m_0, m_1, m_2;
we
// initializing the three buffers globally so all threads can use them
all_buffers buffers;

int NUM_INTEGERS = 0;

// function for inserting a given int item into a selected buffer
// uses semaphores to make sure that operations can be done on the buffers,
// as well as each buffer is only used by one thread at a time
int insert_item(int item, long id) {
    switch(id) {
        case 0 :
            sem_wait(&e_0);
            sem_wait(&m_0);
            break;
        case 1 :
            sem_wait(&e_1);
            sem_wait(&m_1);
            break;
        case 2 :
            sem_wait(&e_2);
            sem_wait(&m_2);
            break;
        default :
            break;
    }

    buffers.b[id].buff_array[buffers.b[id].next_in] = item;
    buffers.b[id].next_in = (buffers.b[id].next_in + 1) % BUFF_SZ;
    switch(id) {
        case 0 :
            sem_post(&d_0);
            sem_post(&m_0);
            break;
        case 1 :
            sem_post(&d_1);
            sem_post(&m_1);
            break;
        case 2 :
            sem_post(&d_2);
            sem_post(&m_2);
            break;
        default :
            break;
    }


    return 0;
}

// function for removing the next item from a given buffer and returning its value
// uses semaphores to make sure both the operation can take place, and only one thread works on a buffer at once
int remove_item(long id) {

    int item = 0;
    switch(id) {
        case 0 :
            sem_wait(&d_0);
            sem_wait(&m_0);
            break;
        case 1 :
            sem_wait(&d_1);
            sem_wait(&m_1);
            break;
        case 2 :
            sem_wait(&d_2);
            sem_wait(&m_2);
            break;
        default :
            break;
    }
    item = buffers.b[id].buff_array[buffers.b[id].next_out];
    buffers.b[id].buff_array[buffers.b[id].next_out] = -1;
    buffers.b[id].next_out = (buffers.b[id].next_out + 1) % BUFF_SZ;
    switch(id) {
        case 0 :
            sem_post(&e_0);
            sem_post(&m_0);
            break;
        case 1 :
            sem_post(&e_1);
            sem_post(&m_1);
            break;
        case 2 :
            sem_post(&e_2);
            sem_post(&m_2);
            break;
        default :
            break;
    }
    
    return item;
}

// function for determining a thread's action based on its given ID
// thread 0 adds all integers from 2 to N to buffer 0
// thread 1 removes integers from the end of buffer 0
// thread 1 adds these integers to buffer 1 only if they are not multiples of 2
// thread 2 does the same as thread 1, but works on buffers 1 and 2, and filters multiples of 3
// thread 3 removes from buffer 2 and prints the values if they are not multiples of 5
void* data_filter(void* thr_id) {
	int i;
	long my_id = (long) thr_id;
	switch(my_id) {
        case 0 :
            for (i = 2; i < NUM_INTEGERS; i++) {
                insert_item(i, my_id);
            }
            insert_item(-1, my_id);
            break;
        case 1 :
            do {
                i = remove_item(my_id - 1);
                if (i % 2 != 0 || i == 2) {
                    insert_item(i, my_id);
                }
            } while (i != -1);
            insert_item(i, my_id);
            break;
        case 2 :
             do {
                i = remove_item(my_id - 1);
                if (i % 3 != 0 || i == 3) {
                    insert_item(i, my_id);
                }
            } while (i != -1);
             insert_item(i, my_id);
            break;
        case 3 :
             do {
                i = remove_item(my_id - 1);
                if (i % 5 != 0 && i != -1 || i == 5) {
                    printf("%d, ", i);
                }
            } while (i != -1);
            printf("\n");
            break;
        default :
            break;
	}
    return thr_id;
}

// main thread
// times program
// initializes all semaphores as well as creates and joins all threads
int main(int argc, char *argv[]) {
	clock_t start, end;
	void *status;
	long thread;
	int thrcreate;
   	start = clock();
    	sem_init(&d_0, 0, 0);
    	sem_init(&d_1, 0, 0);
    	sem_init(&d_2, 0, 0);
    	sem_init(&e_0, 0, BUFF_SZ);
    	sem_init(&e_1, 0, BUFF_SZ);
    	sem_init(&e_2, 0, BUFF_SZ);
    	sem_init(&m_0, 0, 1);
    	sem_init(&m_1, 0, 1);
    	sem_init(&m_2, 0, 1);
	int i;
	for (i = 0; i < NUM_BUFF; i++) {
		buffers.b[i].next_in = 0;
		buffers.b[i].next_out = 0;
	}
	NUM_INTEGERS = atoi(argv[1]);
	pthread_t* thread_handles;
	thread_handles = malloc(NUM_THREADS*sizeof(pthread_t));
	for (thread = 0; thread < NUM_THREADS; thread++) {
		thrcreate = pthread_create(&thread_handles[thread], NULL, data_filter, (void *)thread);
		if (thrcreate) {
			printf("Error, pthread_create return code %d\n", thrcreate);
			return 1;
		}
	}

	for (thread = 0; thread < NUM_THREADS; thread++) {
		pthread_join(thread_handles[thread], &status);
	}




	int sem_destroy(sem_t*	semaphore_p);
    	sem_destroy(&d_0);
    	sem_destroy(&d_1);
    	sem_destroy(&d_2);
    	sem_destroy(&e_0);
    	sem_destroy(&e_1);
    	sem_destroy(&e_2);
    	sem_destroy(&m_0);
    	sem_destroy(&m_1);
    	sem_destroy(&m_2);

	end = clock();
	printf("Elapsed Time - %f seconds\n", ((double)end - start) / (double)(CLOCKS_PER_SEC));
	pthread_exit(NULL);

	return 0;
}
