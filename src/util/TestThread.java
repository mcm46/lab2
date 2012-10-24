package util;


public class TestThread implements Runnable
{
	private EventBarrier myBarrier;
	
	public TestThread(EventBarrier barrier)
	{
		myBarrier = barrier;
	}

	@Override
	public void run()
	{
		myBarrier.hold();
		System.out.println("poop");
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myBarrier.complete();
	}

}
