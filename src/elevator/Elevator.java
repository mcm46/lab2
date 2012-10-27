package elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import building.Building;


public class Elevator implements Runnable
{

	private static final int MAX_CAPACITY = 1;
	private int currentCapacity = 0;
	private int currentFloor;
	private boolean doorOpened = false;
	private boolean canEnter = true;
	private boolean goingUp;
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
		
		if (r >= 0.5)
			goingUp = true;
		else
			goingUp = false;
		
	}


	public boolean getDirection()
	{
		return goingUp;
	}

	public int getCurrentFloor()
	{
		return currentFloor;
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
					myBuilding.visitFloor(myRequests.get(i));
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

	//multi person bottleneck is here!
	public synchronized boolean enter(int passNumber)

	{
		openDoor();
		System.out.println("Person " + passNumber + " trying to enter... Elevator  || " + myName + "|| ");
		if (Elevator.MAX_CAPACITY > currentCapacity)
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

	//notify all happens before the next wait up above? thus the upper piece waits for a notify that will never come?
	public synchronized void requestFloor(int floorNum, int passNumber)
	{
		myRequests.add(floorNum);
		System.out.println("Passenger: " + passNumber + " requested: " + floorNum);
		if (floorNum > currentFloor)
		{
			goingUp = true;
		}
		else
		{
			goingUp = false;
		}

		//top thing holds the lockObject, thus this can't return and the other threads can't enter
		synchronized(lockObject)
		{
			lockObject.notifyAll();
		}

	}


	public void run()
	{
		visitFloor();
	}
}


