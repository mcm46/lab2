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
			myElevators[i] = new Elevator();
			new Thread(myElevators[i]).start();
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
		
		//wait on the correct floors event
		try
		{
			myEventBarriers[floor].wait();
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if nothing matches that requirement call the closest one
		if(distance == Integer.MAX_VALUE)
		{
			int index2 = 0;
			for(int i = 0; i < ELEVATORS; i++)
			{
				if(floor - myElevators[i].getCurrentFloor() < distance)
				{
					index2 = i;
				}
			}
			return myElevators[index2];
		}
		//otherwise call the one that was found
		return myElevators[index];
	}
	
	public void awaitUp(int floor)
	{
		try
		{
			myEventBarriers[floor].wait();
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				
				//wait on the floors event than determine what to return
				try
				{
					myEventBarriers[floor].wait();
				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//if nothing matches that requirement call the closest one
				if(distance == Integer.MAX_VALUE)
				{
					int index2 = 0;
					for(int i = 0; i < ELEVATORS; i++)
					{
						int temp = floor - myElevators[i].getCurrentFloor();
						if(temp < distance)
						{
							distance = temp;
							index2 = i;
						}
					}
					return myElevators[index2];
				}
				//otherwise call the one that was found
				return myElevators[index];
	}
	
	public void awaitDown(int floor)
	{
		try
		{
			myEventBarriers[floor].wait();
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
