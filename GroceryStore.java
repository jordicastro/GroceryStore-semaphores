//////////////////////////
// Name : Jordi Castro  //
// IDUA : 010974536     //
//////////////////////////

// imports


// class definitions

class Customer implements Runnable 
{
    // Class attributes here, including:
    // id
    // Semaphores, mutexes for each section
    // variables for counting customers

    // Class constructor
    Customer() 
    {
        // Insert here
    }

    // Get waiting room
    private void getWaitingRoom() 
    {
        // Insert here
    }

    // Produce section
    private void getProduceSection() 
    {
        // Insert Here
    }

    // General Grocery Section
    private void getGeneralSection() 
    {
        // Insert Here
    }

    // Frozen Section
    private void getFrozenSection() 
    {
        // Insert Here
    }

    // Cashier Section
    private void getCashierSection()
    {
        // Insert Here
    }

    // Exit Grocery Store
    private void exitGroceryStore() 
    {
        // Insert Here
    }

    // Implement run() method
    public void run() 
    {
        // Time to call all implemented sub-methods
    }
}

// Grocery Store Class
public class GroceryStore 
{
    // Arguments parsers

    // Shared semaphores, mutexes, variables
    // Create Customer objects using for-loop
    // Call start() method
    public static void main (String args[])
    {
        for (int i =0; i<-1; i++) 
        {
            // Insert here
        }
    }
    // Sleep the program thread using Thread.sleep()
    // Insert here
}

/*
    System description: java with threads

    outside store: waiting area (max 40)

    inside store: 
        1) produce section (max 20 people, random avg 10 units of wait time inside (0-20?)), 
        2) general grocery section (max 25 people, random avg 15 units of wait time inside (1-30)), 
        3) frozen section (max 30 people, rand avg 30 units of wait time inside (0-60))
        4) cash registers (max 10 people, rand avg 10 units of wait time inside (0-20))
        5) leave the store

    conditionals:
        if next section isFull(), customer must wait in currSection before entering next one

    parameters:
        1. amount of time we permit customers in the system
        2. the number of customers
            

    store limits the number of people allowed inside ach section



 */