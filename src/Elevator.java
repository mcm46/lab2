import java.util.ArrayList;


public class Elevator implements Runnable
{
	private static final int MAX_CAPACITY = 100;
	private int currentCapacity = 0;
	private int currentFloor = 1;
	private int requestedFloor = 0;
	private boolean doorOpened = false;
	private boolean canEnter = true;
	private boolean goingUp = true;
	private Building myBuilding = new Building();
	private Object lockObject = new Object();
	private ArrayList<Integer> myRequests = new ArrayList<Integer>();
	
	
	private void visitFloor(int desiredFloor)
	{
		// call the corresponding building method for visited floor
		//i think the buildings visitFloor should take an argument for which floor we need to visit with this
		//elevator
//		myBuilding.visitFloor(desiredFloor);
	}
	
	private void openDoor()
	{
		doorOpened = true;
		System.out.println("Door Opened!");
	}
	
	private void closeDoor()
	{
		doorOpened = false;
		System.out.println("Door Closed!");
	}
	
	public boolean enter()

	{
		openDoor();
		if (Elevator.MAX_CAPACITY > currentCapacity)
		{
			canEnter = true;
			currentCapacity++;
		}
		else
		{
			closeDoor();
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
		requestedFloor = floorNum;
		currentFloor = floorNum;
		
	}
	
	public boolean getDirection()
	{
		return goingUp;
	}
	
	
	public void run()
	{
		visitFloor(requestedFloor);
		
	}
}
