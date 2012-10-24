
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
		
	}
	
	public Elevator callUp(int floor)
	{
		return null;
	}
	
	
	public Elevator callDown(int floor)
	{
		return null;
	}
}
