package people;

import java.util.Queue;

import building.Building;

import elevator.Elevator;


public class Person implements Runnable
{
	private int currentFloor;
	private Queue<Integer> nextFloors;
	private Building myBuilding;
	private int passNo;

	public Person(int currentFloor,Queue<Integer> nextFloors, Building building, int passNo)
	{
		this.currentFloor=currentFloor;
		this.nextFloors=nextFloors;
		myBuilding= building;
		this.passNo=passNo;
	}

	//person needs to try to get on again
	public void run()
	{
		System.out.println("New Person: "+passNo);
		while(!nextFloors.isEmpty())
		{
			System.out.println(nextFloors);
			int nextFloor=nextFloors.poll();
			boolean down=nextFloor<currentFloor;
			Elevator e = null;
			System.out.println("Passenger: " + passNo + " called elevator to floor " + currentFloor);
			if (down)
			{
				e = myBuilding.callDown(currentFloor, passNo);
			}
			else
			{
				e = myBuilding.callUp(currentFloor, passNo);
			}
			//System.out.println("Person " + passNo + " returned elevator " + e.getName());
			getOnElevator(e,down, currentFloor);
			myBuilding.complete(currentFloor, e);

			//returns false if the request 
			if(e.requestFloor(nextFloor,passNo))
			{


				if(down)
				{
					myBuilding.awaitDown(nextFloor, e);
				}
				else
				{
					myBuilding.awaitUp(nextFloor, e);
				}
				getOffElevator(e);
				try
				{
					Thread.sleep(1500);
				} catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			myBuilding.complete(nextFloor, e);
			currentFloor=nextFloor;
		}

	}


	public void addNewFloor(int floor)
	{
		nextFloors.add(floor);
	}

	//people don't try to enter again if they fail the first time
	private void getOnElevator(Elevator e,boolean down, int floor)
	{
		while(!e.enter(passNo))
		{
			myBuilding.complete(floor, e);
			try
			{
				Thread.sleep(100);
			} catch (InterruptedException e1)
			{
				 //TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (down)
			{
				e = myBuilding.callDown(currentFloor, passNo);
			}
			else
			{
				e = myBuilding.callUp(currentFloor, passNo);
			}
			myBuilding.complete(floor, e);
		}
	}

	private void getOffElevator(Elevator e)
	{
		e.exit(passNo);
	}

}
