
public class EventBarrier 
{
	private volatile boolean signaled = false;
	private volatile int activeThreads = 0;
	private Object lockObject = new Object();
	
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
