package elevator;

import java.util.ArrayList;

import building.Building;


public class Elevator implements Runnable
{
	
	private static final int MAX_CAPACITY = 100;
	private int currentCapacity = 0;
	private int currentFloor = 0;
	private boolean doorOpened = false;
	private boolean canEnter = true;
	private boolean goingUp = true;
	private Building myBuilding;
	private Object lockObject = new Object();
	private ArrayList<Integer> myRequests = new ArrayList<Integer>();
	
	public Elevator (Building b)
	{
		myBuilding = b;
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
					myBuilding.visitFloor(myRequests.get(i));
					currentFloor = i;
					myRequests.remove(i);
				}
			}
		}
	}
	
	private void openDoor()
	{
		doorOpened = true;
		System.out.println("Door Opened!");
	}
	
	private void closeDoor()
	{
		doorOpened = false;
		System.out.println("Door Closed!");
	}
	
	public synchronized boolean enter()

	{
		openDoor();
		System.out.println("Person trying to enter...");
		if (Elevator.MAX_CAPACITY > currentCapacity)
		{
			canEnter = true;
			currentCapacity++;
			System.out.println("Entered the elevator!");
		}
		else
		{
			System.out.println("Elevator full!!");
			canEnter = false;
		}
		closeDoor();
		
		return canEnter;
	}
	
	public synchronized void exit()
	{
		openDoor();
		currentCapacity--;
		System.out.println("Person Exited!");
		closeDoor();
	}
	
	public synchronized void requestFloor(int floorNum)
	{
		myRequests.add(floorNum);
		if (floorNum > currentFloor)
		{
			goingUp = true;
		}
		else
		{
			goingUp = false;
		}
		
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