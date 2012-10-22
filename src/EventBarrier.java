
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
		
		activeThreads++;
		//called by subscribing thread, wait until event is signaled
		synchronized(this)
		{
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
		activeThreads--;
		if(activeThreads == 0)
		{
			signaled = false;
		}
	}
	
	public int waiters()
	{
		return activeThreads;
	}	
}
