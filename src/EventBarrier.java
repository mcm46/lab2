public class EventBarrier 
{
	private volatile boolean signaled = false;
	private volatile int activeThreads = 0;
<<<<<<< HEAD
	private Object lockObject = new Object();
=======
>>>>>>> changed print statements
	
	public synchronized void hold()
	{
		if(signaled)
		{
			return;
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
		
	}
	
	public void signal()
	{
		
		synchronized(this)
		{
			this.notifyAll();
			signaled = true;
		}
<<<<<<< HEAD
		
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
=======
		while(activeThreads!=0){

>>>>>>> changed print statements
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
