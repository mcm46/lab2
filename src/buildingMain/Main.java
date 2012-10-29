package buildingMain;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.management.Query;

import people.Person;

import building.Building;

public class Main
{
	public static void main(String[] args)
	{

		int numPeople=3;
		Building building = new Building();
		int startFloor= 2;

		for (int i=0;i<numPeople;i++)
		{
			Queue<Integer> q = new LinkedList<Integer>();

			q.add(startFloor+1);
			q.add(startFloor-1);
			q.add(startFloor+2);
			q.add(startFloor-2);
			
			Person p = new Person(startFloor,q,building, i);
			Thread t = new Thread(p);
			t.start();
			startFloor++;
		}
		
	}
}

