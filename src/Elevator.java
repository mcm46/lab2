import java.util.ArrayList;


public class Elevator implements Runnable
{
	private ArrayList<Person> listOfPeople = new ArrayList<Person>();
	
	private boolean doorOpened = false;
	private boolean canEnter = true;
	
	public void VisitFloor()
	{
		
	}
	
	public void OpenDoors()
	{
		doorOpened = true;
	}
	
	public void CloseDoors()
	{
		doorOpened = false;
	}
	
	public boolean Enter()

	{
		
		return canEnter;
	}
	
	public void Exit()
	{
		
	}
	
	public void RequestFloor()
	{
		
	}
	
	
	public void run()
	{
		
		
	}
}
