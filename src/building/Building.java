package building;
import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import elevator.Elevator;
import util.EventBarrier;


public class Building
{
	private static final int FLOORS = 8;
	private static final int ELEVATORS = 4;
	private Elevator[] myElevators = new Elevator[ELEVATORS];
	private Map<Integer, ArrayList<EventBarrier>> myEventBarriers = new HashMap<Integer, ArrayList<EventBarrier>>();

	public Building()
	{
		for(int i = 0; i < ELEVATORS; i++)
		{
			myElevators[i] = new Elevator(this, i, i);
			System.out.println("Elevator " + i + " Starting At Floor " + i +".");
			new Thread(myElevators[i]).start();
		}

		for (int i=0; i < FLOORS; i++)
		{
			myEventBarriers.put(i, new ArrayList<EventBarrier>());
			for(int j = 0; j < ELEVATORS; j++)
			{
				myEventBarriers.get(i).add(new EventBarrier());
			}
		}
	}

	public void visitFloor(int floor, Elevator elevator)
	{
		System.out.println("Elevator " + elevator.getName() + " visiting floor " + floor);
		//takes awhile at the floor
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myEventBarriers.get(floor).get(elevator.getName()).signal();
	}
	
	public void complete(int floor, Elevator elevator)
	{
		myEventBarriers.get(floor).get(elevator.getName()).complete();
	}

	public Elevator callUp(int floor, int person)
	{
		//find the nearing elevator going up
		int distance = Integer.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < ELEVATORS; i++)
		{
			if (myElevators[i].getDirection()==0 && myElevators[i].getCurrentFloor()==floor && !myElevators[i].isFull())
			{
				index=i;
				break;
			}
			if (myElevators[i].getCurrentFloor() <= floor && myElevators[i].getDirection()==1 && !myElevators[i].isFull())
			{
				int temp = floor - myElevators[i].getCurrentFloor();
				if(temp < distance)
				{
					distance = temp;
					index = i;
				}
			}
			
		}
		
		if (index==-1)
		{
			for (int i=0;i<ELEVATORS;i++)
			{
				if (myElevators[i].getDirection()==0 && !myElevators[i].isFull())
				{
					int temp = Math.abs(floor - myElevators[i].getCurrentFloor());
					if(temp < distance)
					{
						distance = temp;
						index = i;
					}
				}
			}
		}
		
		if (index==-1)
		{
			for (int i=0;i<ELEVATORS;i++)
			{

				int temp = Math.abs(floor - myElevators[i].getCurrentFloor());
				if(temp < distance)
				{
					distance = temp;
					index = i;
				}
			
			}
		}

		//if nothing matches that requirement call the closest one


		myElevators[index].requestFloor(floor, -1);
		

		//wait on the correct floors event
		System.out.println("Passenger " + person + " Waiting For Elevator " + index + " to Floor " + floor + "!");
		myEventBarriers.get(floor).get(index).hold();
		//otherwise call the one that was found
		return myElevators[index];
	}

	public void awaitUp(int floor, Elevator elevator)
	{
		myEventBarriers.get(floor).get(elevator.getName()).hold();
	}


	public Elevator callDown(int floor, int person)
	{
		//find the nearing elevator going up
		int distance = Integer.MAX_VALUE;
		int index = -1;
		for(int i = 0; i < ELEVATORS; i++)
		{
			if (myElevators[i].getDirection()==0 && myElevators[i].getCurrentFloor()==floor && !myElevators[i].isFull())
			{
				index=i;
				break;
			}
			if (myElevators[i].getCurrentFloor() >= floor && myElevators[i].getDirection()==-1 && !myElevators[i].isFull())
			{
				int temp = floor - myElevators[i].getCurrentFloor();
				if(temp < distance)
				{
					distance = temp;
					index = i;
				}
			}
			
		}
		
		if (index==-1)
		{
			for (int i=0;i<ELEVATORS;i++)
			{
				if (myElevators[i].getDirection()==0 && !myElevators[i].isFull())
				{
					int temp = Math.abs(floor - myElevators[i].getCurrentFloor());
					if(temp < distance)
					{
						distance = temp;
						index = i;
					}
				}
			}
		}
		
		if (index==-1)
		{
			for (int i=0;i<ELEVATORS;i++)
			{

				int temp = Math.abs(floor - myElevators[i].getCurrentFloor());
				if(temp < distance)
				{
					distance = temp;
					index = i;
				}
			
			}
		}

		//if nothing matches that requirement call the closest one


		myElevators[index].requestFloor(floor, -1);
		

		//wait on the correct floors event
		System.out.println("Passenger " + person + " Waiting For Elevator " + index + " to Floor " + floor + "!");
		myEventBarriers.get(floor).get(index).hold();
		//otherwise call the one that was found
		return myElevators[index];
	}
	public void awaitDown(int floor, Elevator elevator)
	{
		myEventBarriers.get(floor).get(elevator.getName()).hold();
	}
}
