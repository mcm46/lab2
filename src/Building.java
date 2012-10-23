
public class Building
{
	private static final int FLOORS = 5;
	private static final int ELEVATORS = 1;
	private Elevator[] myElevators = new Elevator[ELEVATORS];
	
	public Building()
	{
		for(int i = 0; i < ELEVATORS; i++)
		{
			myElevators[i] = new Elevator();
			new Thread(myElevators[i]).start();
		}
	}
	
	public void callUp()
	{
		
	}
	
	public void awaitUp()
	{
		
	}
	
	public void callDown()
	{
		
	}
	
	public void awaitDown()
	{
		
	}
}
