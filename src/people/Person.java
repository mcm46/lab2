package people;

import java.util.Queue;

import building.Building;

import elevator.Elevator;


public class Person implements Runnable
{
	private volatile int currentFloor;
	private volatile Queue<Integer> nextFloors;
	private Building myBuilding;
	private volatile int passNo;
	
	public Person(int currentFloor,Queue<Integer> nextFloors, Building building, int passNo)
	{
		this.currentFloor=currentFloor;
		this.nextFloors=nextFloors;
		myBuilding= building;
		this.passNo=passNo;
	}
	
	public void run()
	{
		System.out.println("New Person: "+passNo);
		while(!nextFloors.isEmpty())
		{
			System.out.println("Passenger: " + passNo + " got to elevator");
			int nextFloor=nextFloors.poll();
			boolean down=nextFloor<currentFloor;
			Elevator e = null;
			System.out.println("Passenger: " + passNo + " called elevator");
			if (down)
			{
				e = myBuilding.callDown(currentFloor);
			}
			else
			{
				e = myBuilding.callUp(currentFloor);
			}
			getOnElevator(e,down);
			
			e.requestFloor(nextFloor);
			System.out.println("Passenger: " + passNo + " requested floor");
			if(down)
			{
				myBuilding.awaitDown(nextFloor);
			}
			else
			{
				myBuilding.awaitUp(nextFloor);
			}
			getOffElevator(e);
			
			currentFloor=nextFloor;
		}
		
	}
	

	public void addNewFloor(int floor)
	{
		nextFloors.add(floor);
	}
	
	private void getOnElevator(Elevator e,boolean down)
	{
		System.out.println("Passenger: " + passNo + " tried to get on elevator");
		if (!e.enter())
		{
			System.out.println("Passenger: " + passNo + " couldn't get on elevator");
			try
			{
				Thread.sleep(3000);
			} catch (InterruptedException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (down)
			{
				myBuilding.callDown(currentFloor);
			}
			else
			{
				myBuilding.callUp(currentFloor);
			}
		}
	}
	
	private void getOffElevator(Elevator e)
	{
		System.out.println("Passenger: " + passNo + " got off elevator");
		e.exit();
	}

}
