package building;
import elevator.Elevator;
import util.EventBarrier;


public class Building
{
	private static final int FLOORS = 5;
	private static final int ELEVATORS = 1;
	private Elevator[] myElevators = new Elevator[ELEVATORS];
	private EventBarrier[] myEventBarriers = new EventBarrier[FLOORS];
	
	public Building()
	{
		for(int i = 0; i < ELEVATORS; i++)
		{
			myElevators[i] = new Elevator(this);
			new Thread(myElevators[i]).start();
		}
		
		for (int i=0; i < FLOORS; i++)
		{
			myEventBarriers[i] = new EventBarrier();
		}
	}
	
	public void visitFloor(int floor)
	{
		System.out.println("Elevator visiting floor " + floor);
		myEventBarriers[floor].signal();
	}
	
	public Elevator callUp(int floor)
	{
		//find the nearing elevator going up
		int distance = Integer.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < ELEVATORS; i++)
		{
			//get direction returns true if its going up
			if(myElevators[i].getCurrentFloor() < floor && myElevators[i].getDirection())
			{
				int temp = floor - myElevators[i].getCurrentFloor();
				if(temp < distance)
				{
					distance = temp;
					index = i;
				}
			}
		}
		
		//if nothing matches that requirement call the closest one
		if(distance == Integer.MAX_VALUE)
		{
			for(int i = 0; i < ELEVATORS; i++)
			{
				int temp = Math.abs(floor - myElevators[i].getCurrentFloor());
				
				if(temp < distance)
				{
					index = i;
					distance = temp;
				}
			}
		}
		
		myElevators[index].requestFloor(floor);
		
		//wait on the correct floors event
		myEventBarriers[floor].hold();
		myEventBarriers[floor].complete();
		//otherwise call the one that was found
		return myElevators[index];
	}
	
	public void awaitUp(int floor)
	{

			myEventBarriers[floor].hold();
			myEventBarriers[floor].complete();
	}
	
	
	public Elevator callDown(int floor)
	{
		//find the nearing elevator going up
				int distance = Integer.MAX_VALUE;
				int index = 0;
				for(int i = 0; i < ELEVATORS; i++)
				{
					//get direction returns true if its going up
					if(myElevators[i].getCurrentFloor() > floor && !myElevators[i].getDirection())
					{
						int temp = myElevators[i].getCurrentFloor() - floor;
						if(temp < distance)
						{
							distance = temp;
							index = i;
						}
					}
				}
				
				//if nothing matches that requirement call the closest one
				if(distance == Integer.MAX_VALUE)
				{
					for(int i = 0; i < ELEVATORS; i++)
					{
						int temp = Math.abs(floor - myElevators[i].getCurrentFloor());
						if(temp < distance)
						{
							index = i;
							distance = temp;
						}
					}
				}
				
				myElevators[index].requestFloor(floor);
				
				//wait on the correct floors event
				myEventBarriers[floor].hold();
				myEventBarriers[floor].complete();
				//otherwise call the one that was found
				return myElevators[index];
	}
	
	public void awaitDown(int floor)
	{
			myEventBarriers[floor].hold();
			myEventBarriers[floor].complete();
	}
}
