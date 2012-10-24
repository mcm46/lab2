
public class Person
{
	private int currentFloor;
	private int nextFloor;
	private Building myBuilding;
	
	public Person(int currentFloor,int nextFloor, Building building)
	{
		this.currentFloor=currentFloor;
		this.nextFloor=nextFloor;
		myBuilding= building;
	}
	
	public void run()
	{
		boolean down=nextFloor<currentFloor;
		Elevator e = null;
		if (down)
		{
			e = myBuilding.callDown(currentFloor);
		}
		else
		{
			e = myBuilding.callUp(currentFloor);
		}
		getOnElevator(e);
		e.requestFloor(nextFloor);
		
		
		
	}
	
	private void setCurrentFloor(int floor)
	{
		currentFloor=floor;
	}
	
	private void setNextFloor(int floor)
	{
		nextFloor=floor;
	}
	
	public void getOnElevator(Elevator e)
	{
		e.enter();
	}

}
