/////////////////////////////
// OS PA2 : Grocery Store  //
// Author : Jordi Castro   //
// IDUA   : 010974536      //
/////////////////////////////

// imports
//import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.List;
import java.util.ArrayList;



// class definitions

class Customer implements Runnable 
{
    // Class attributes here, including:
    // id
    private int id;
    private static int numInWait, numInProduce, numInGeneral, numInFrozen, numInRegisters;
    
    // Semaphores, mutexes for each section
    private Semaphore swait, sproduce, sgeneral, sfrozen, sregisters;
    private Semaphore mwait, mproduce, mgeneral, mfrozen, mregisters;
    // variables for counting customers

    // Class constructor
    Customer(int id, Semaphore swait, Semaphore sproduce, Semaphore sgeneral, Semaphore sfrozen, Semaphore sregisters, Semaphore mwait, Semaphore mproduce, Semaphore mgeneral, Semaphore mfrozen, Semaphore mregisters) 
    {
        // Insert here
        this.id = id;
        this.swait = swait;
        this.sproduce = sproduce;
        this.sgeneral = sgeneral;
        this.sfrozen = sfrozen;
        this.sregisters = sregisters;
        this.mwait = mwait;
        this.mproduce = mproduce;
        this.mfrozen = mfrozen;
        this.mgeneral = mgeneral;
        this.mregisters = mregisters;
        numInWait =0;
        numInProduce =0;
        numInFrozen =0;
        numInRegisters = 0;
    }

    // Get waiting room
    private void getWaitingRoom() 
    {
        // Insert here
        if (numInWait >= GroceryStore.OUTSIDE_WAIT) 
        {
            mwait.release();
            System.out.printf("\t\tCustomer %d leaves! there are no spots outside of the store!\n", id);
        
        } else 
        {
            try {
                mwait.acquire();
                swait.acquire();

                numInWait++;
                System.out.printf("\tCustomer %d enters the waiting area and is waiting to enter the grocery store. there are %d customers waiting\n", id, numInWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                swait.release();
                mwait.release();
            }
        }
        
    }

    // Produce section
    private void getProduceSection() 
    {
        // Insert Here
        if (numInProduce >= GroceryStore.PRODUCE_WAIT)
        {
            System.out.printf("\t\tPRODUCE has %d out of %d people. customer %d WILL RETURN in 2 seconds\n", numInProduce, GroceryStore.PRODUCE_WAIT, id);
            try {
                Thread.sleep(2000);
                getProduceSection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
        else
        {
            try {
                sproduce.acquire();
                mproduce.acquire();
                mwait.acquire();

                numInProduce++;
                numInWait--;

                System.out.printf("\t\tCustomer %d enters the produce section. there are %d customers looking at produce\n", id, numInProduce);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mproduce.release();
                mwait.release();
                swait.release();
            }
        }
    }

    // General Grocery Section
    private void getGeneralSection() 
    {
        // Insert Here
        if (numInGeneral >= GroceryStore.GROCERY_WAIT)
        {
            System.out.printf("\t\tGENERAL SECTION has %d out of %d people. customer %d WILL RETURN in 2 seconds\n", numInGeneral, GroceryStore.GROCERY_WAIT, id);
            try {
                Thread.sleep(2000);
                getGeneralSection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                sgeneral.acquire();
                mgeneral.acquire();
                mproduce.acquire();

                numInGeneral++;
                numInProduce--;
                System.out.printf("\t\t\tCustomer %d enters the general section. there are %d customers looking at general groceries.\n", id, numInGeneral);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mgeneral.release();
                mproduce.release();
                sproduce.release();
            }
        }
    }

    // Frozen Section
    private void getFrozenSection() 
    {
        // Insert Here
        if (numInFrozen >= GroceryStore.FROZEN_WAIT)
        {
            System.out.printf("\t\tFROZEN SECTION has %d out of %d people. customer %d WILL RETURN in 2 seconds\n", numInFrozen, GroceryStore.FROZEN_WAIT, id);
            try {
                Thread.sleep(2000);
                getFrozenSection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                sfrozen.acquire();
                mfrozen.acquire();
                mgeneral.acquire();

                numInFrozen++;
                numInGeneral--;
                System.out.printf("\t\t\t\tCustomer %d enters the frozen section. there are %d customers looking at frozen foods.\n", id, numInFrozen);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mfrozen.release();
                mgeneral.release();
                sgeneral.release();
            }
        }
    }

    // Cashier Section
    private void getCashierSection()
    {
        // Insert Here
        if (numInRegisters >= GroceryStore.REGISTERS_WAIT)
        {
            System.out.printf("\t\tREGISTERS has %d out of %d people. customer %d WILL RETURN in 2 seconds\n", numInRegisters, GroceryStore.REGISTERS_WAIT, id);
            try {
                Thread.sleep(2000);
                getCashierSection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                sregisters.acquire();
                mregisters.acquire();
                mfrozen.acquire();

                numInRegisters++;
                numInFrozen--;
                System.out.printf("\t\t\t\tCustomer %d enters CHECKOUT AREA. there are %d customers checking out.\n", id, numInRegisters);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mregisters.release();
                mfrozen.release();
                sfrozen.release();
            }
        }
    }

    // Exit Grocery Store
    private void exitGroceryStore() 
    {
        // Insert Here
        try {
            mregisters.acquire();
            numInRegisters--;
            System.out.printf("\t\t\t\t\t Customer %d exits the system.\n", id);
        } catch (InterruptedException e) 
        {
            e.printStackTrace();
        } finally {
            mregisters.release();
            sregisters.release();
        }
    }

    // Implement run() method
    @Override
    public void run() 
    {
        int n =11;
        // Time to call all implemented sub-methods
        sleeps(n);

        System.out.printf("\nCustomer %d enters the system\n", id);

        getWaitingRoom();

        if (!Thread.currentThread().isInterrupted())
        {
            getProduceSection();
            n = 21;
            sleeps(n);

            getGeneralSection();
            n=31;
            sleeps(n);

            getFrozenSection();
            n=61;
            sleeps(n);

            getCashierSection();
            n=21;
            sleeps(n);

            exitGroceryStore();
        }
    }

    public void sleeps(int n)
    {
        try {
        double randSleepTime = Math.floor(Math.random() * n);
        long miliSleepTime = (long) (randSleepTime * 100);
        Thread.sleep(miliSleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


// Grocery Store Class
public class GroceryStore 
{
    // Arguments parsers

    // Shared semaphores, mutexes, variables
    // Create Customer objects using for-loop
    // Call start() method
    static boolean continueLoop = true;

    static final int OUTSIDE_WAIT = 40;
    static final int PRODUCE_WAIT = 20;
    static final int GROCERY_WAIT = 25;
    static final int FROZEN_WAIT = 30;
    static final int REGISTERS_WAIT = 10;
    static int elapsedTime = 0;


    public static void main (String args[])
    {

        // default values
        int sleepTime = 100;
        int numCustomers = 50;

        Semaphore swait = new Semaphore(OUTSIDE_WAIT); // outside wait, max 40
        Semaphore sproduce = new Semaphore(PRODUCE_WAIT); // produce wait, max 20
        Semaphore sgeneral = new Semaphore(GROCERY_WAIT); // general grocery wait, max 25
        Semaphore sfrozen = new Semaphore(FROZEN_WAIT); // frozen section wait, max 30
        Semaphore sregisters = new Semaphore(REGISTERS_WAIT); // cash registers wait, max 10
        Semaphore mwait = new Semaphore(1);
        Semaphore mproduce = new Semaphore(1);
        Semaphore mgeneral = new Semaphore(1);
        Semaphore mfrozen = new Semaphore(1);
        Semaphore mregisters = new Semaphore(1);

        if (args.length == 2)
        {
            sleepTime = Integer.parseInt(args[0]);
            numCustomers = Integer.parseInt(args[1]);
        }
        else
        {

        }

        long startTime = System.currentTimeMillis();

        List<Thread> customerThreads = new ArrayList<>();

        // static boolean continueLoop = true;

        for (int i =0; i< numCustomers && continueLoop; i++) 
        {
            // Insert here
            if (elapsedTime < sleepTime)
            {
                Customer myCustomer = new Customer(i, swait, sproduce, sgeneral, sfrozen, sregisters, mwait, mproduce, mgeneral, mfrozen, mregisters);

                Thread c = new Thread(myCustomer);

                c.start();

                //myCustomer.setThread(c);

                customerThreads.add(c);
            }
            else
            {
                System.out.println("Grocery Store is closed. No new customers allowed.\n");
                continueLoop = false;
                break;
            }

            elapsedTime = (int) (System.currentTimeMillis() - startTime);
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
            

    store limits the number of people allowed inside each section



 */