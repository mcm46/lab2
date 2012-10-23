
public class EventBarrier 
{
	private boolean signaled = false;
	private int activeThreads = 0;
	
	public void hold()
	{
		if(signaled)
		{
			return;
		}
		
		//called by subscribing thread, wait until event is signaled
		synchronized(this)
		{
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
	}
	
	public void signal()
	{
		synchronized(this)
		{
			this.notifyAll();
		}
		signaled = true;
		
		while(activeThreads != 0){};
	}
	
	public void complete()
	{
		synchronized(this)
		{
			activeThreads--;
			
			if(activeThreads == 0)
			{
				signaled = false;
			}
		}
	}
	
	public int waiters()
	{
		return activeThreads;
	}	
}
