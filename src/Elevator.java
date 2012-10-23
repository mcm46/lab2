import java.util.ArrayList;


public class Elevator implements Runnable
{
	private static final int MAX_CAPACITY = 100;
	private int currentCapacity = 0;
	private int currentFloor = 1;
	private boolean doorOpened = false;
	private boolean canEnter = true;
	private boolean goingUp = true;
	private Building myBuilding = new Building();
	
	
	private void visitFloor()
	{
		// call the corresponding building method for visited floor
		myBuilding.visitFloor();
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
		if (Elevator.MAX_CAPACITY > currentCapacity)
		{
			canEnter = true;
			currentCapacity++;
		}
		else
		{
			canEnter = false;
		}
		
		return canEnter;
	}
	
	public void exit()
	{
		currentCapacity--;
	}
	
	public void requestFloor(int floorNum)
	{
		//called by the building
	}
	
	
	public void run()
	{
		//when elevator gets called --> ?
		if (enter())
		{
			//people can enter and the currentCapacity can be incremented	
		}
		
		
	}
}
