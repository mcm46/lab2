
public class BuildingEventBarrier 
{
	private volatile boolean signaled = false;
	private volatile int activeThreads = 0;
	private Object lockObject = new Object();
	private Elevator myElevator;
	public synchronized Elevator hold()
	{
		if(signaled)
		{
			return null;
		}

		//called by subscribing thread, wait until event is signaled

		activeThreads++;
		try
		{
			this.wait();
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myElevator;
		
	}
	
	public void signal(Elevator elevator)
	{
		
		synchronized(this)
		{
			this.notifyAll();
			myElevator = elevator;
			signaled = true;
		}
		
		synchronized(lockObject)
		{
			while(activeThreads != 0)
			{
				try
				{
					lockObject.wait();
				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void complete()
	{
		activeThreads--;

		if(activeThreads == 0)
		{
			signaled = false;
			synchronized(lockObject)
			{
				lockObject.notifyAll();
			}
		}
		
	}
	
	public int waiters()
	{
		return activeThreads;
	}	
}
