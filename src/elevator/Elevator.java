package elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import building.Building;


public class Elevator implements Runnable
{

	private static final int MAX_CAPACITY = 2;
	private int currentCapacity = 0;
	private int currentFloor;
	private boolean doorOpened = false;
	private boolean canEnter = true;
	//0 stationary, 1 up, -1 down
	private int direction = 0;
	private Building myBuilding;
	private int myName;
	private Object lockObject = new Object();
	private ArrayList<Integer> myRequests = new ArrayList<Integer>();

	public Elevator (Building b, int n, int floor)
	{
		myBuilding = b;
		myName = n;
		currentFloor = floor;
		
		Random rnd = new Random();
		float r = rnd.nextFloat();
		
	}


	public int getDirection()
	{
		return direction;
	}

	public int getCurrentFloor()
	{
		return currentFloor;
	}
	
	public int getName()
	{
		return myName;
	}


	private void visitFloor()
	{
		while(true)
		{
			synchronized(lockObject)
			{
				while (myRequests.size() == 0)
				{
					try
					{
						direction = 0;
						lockObject.wait();
					} 
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
				try
				{
					Thread.sleep(1000);
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				for (int i=0; i < myRequests.size(); i++)
				{
					System.out.println("Elevator || " + myName + "|| calling the Building visitFloor()");
					if(currentFloor < myRequests.get(i))
					{
						direction = 1;
					}
					else
					{
						direction = -1;
					}
					myBuilding.visitFloor(myRequests.get(i), this);
					currentFloor = myRequests.get(i);
					//System.out.println("Elevator current floor: " + currentFloor);
					ArrayList<Integer> temp = new ArrayList<Integer>();
					temp.add(myRequests.get(i));
					myRequests.removeAll(temp);
				}
		}
	}

	private void openDoor()
	{
		doorOpened = true;
		System.out.println("Elevator  || " + myName + "|| Door Opened!");
	}

	private void closeDoor()
	{
		doorOpened = false;
		System.out.println("Elevator  || " + myName + "|| Door Closed!");
	}

	
	public synchronized boolean enter(int passNumber)

	{
		openDoor();
		System.out.println("Person " + passNumber + " trying to enter... Elevator  || " + myName + "|| ");
		if (!isFull())
		{
			canEnter = true;
			currentCapacity++;
			System.out.println("Person " + passNumber+ " entered the Elevator! || " + myName + "|| ");
		}
		else
		{
			System.out.println("Elevator  || " + myName + "||  full!!");
			canEnter = false;
		}
		closeDoor();

		return canEnter;
	}

	public synchronized void exit(int passNumber)
	{
		openDoor();
		currentCapacity--;
		System.out.println("Person " + passNumber +  " EXITED! Elevator  || " + myName + "|| ");
		closeDoor();
	}

	public synchronized boolean requestFloor(int floorNum, int passNumber)
	{
		//if you request the floor your on...
		if(floorNum == currentFloor && passNumber != -1)
		{
			//to keep everything the same
			currentCapacity++;
			exit(passNumber);
			return false;
		}
		myRequests.add(floorNum);
		System.out.println("Passenger: " + passNumber + " requested: " + floorNum);

		
		synchronized(lockObject)
		{
			lockObject.notifyAll();
		}
		
		return true;

	}
	
	public boolean isFull()
	{
		if (Elevator.MAX_CAPACITY > currentCapacity)
			return false;
		else
			return true;
	}


	public void run()
	{
		visitFloor();
	}
}


