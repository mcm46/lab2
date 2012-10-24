
public class Person
{
	private int currentFloor;
	private int nextFloor;
	private Building myBuilding;
	private Elevator myElevator;
	
	public Person(int currentFloor,int nextFloor, Building building)
	{
		this.currentFloor=currentFloor;
		this.nextFloor=nextFloor;
		myBuilding= building;
	}
	
	public void run()
	{
		if (nextFloor<currentFloor)
		{
			Elevator e = myBuilding.callDown(currentFloor);
		}
		else
		{
			Elevator e = myBuilding.callUp(currentFloor);
		}
		
	}
	
	public void setNextFloor()
	{
		
	}
	
	public void getOnElevator(Elevator elevator)
	{
		myElevator=elevator;
	}

}
