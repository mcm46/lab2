import java.util.ArrayList;


public class Elevator implements Runnable
{
	private static final int MAX_CAPACITY = 100;
	private int currentFloor = 1;
	private boolean doorOpened = false;
	private boolean canEnter = true;
	private boolean goingUp = true;
	private Building myBuilding = new Building();
	
	
	private void visitFloor()
	{
		// call the corresponding building method for visited floor
	}
	
	private void openDoor()
	{
		doorOpened = true;
	}
	
	private void closeDoor()
	{
		doorOpened = false;
	}
	
	public boolean enter()

	{
		
		return canEnter;
	}
	
	public void exit()
	{
		
	}
	
	public void requestFloor(int floorNum)
	{
		//called by the building
	}
	
	
	public void run()
	{
		
		
	}
}
