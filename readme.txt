TITLE: GroceryStore Semaphores and Mutex Simulation

AUTHOR: Jordi Castro

ID: 010974536

Description:
This java program simulates the operation of a GroceryStore during the COVID-19 pandemic. 
In this simulation, each customer is a separate thread running through the protected sections of the store guarded by semaphores and mutexes.

Java GroceryStore 200 50 executes the program for 200 time units and creates 50 threads or customers.

if the total time the user specifies in the execution line is used up, the for loop breaks and customers are not longer allowed to enter the store.
if the area a customer attempts to enter is full, the thread sleeps for 2 seconds then try to enter again.
as each thread instance enters and leaves the store, their corresponding semaphore and mutex for each section is acquired and released.

Customers line up outside the store before entering. A total of 40 people can wait outside.
upon entering the store, the customer enters the produce section. A total of 20 people can be in the produce section at once, and their average time in section is 10 time units (1 second).
next, customers enter the general grocery section. A total of 25 people can be in this section at once, and the random average time in this section is 15 time units.
next, customers enter the frozen section. A total of 30 people can be in this section at once, and the random average time in this section is 30 time units.
next, customers enter the cash register section. A total of 10 people can checkout at once, and the random average time in this section is 10 time units
finally, the thread is released as the customer leaves the store.
