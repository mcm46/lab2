import util.EventBarrier;
import util.TestThread;


public class Main
{
	public static void main(String[] args)
	{
		EventBarrier event = new EventBarrier();
		Thread t1 = new Thread(new TestThread(event));
		Thread t2 = new Thread(new TestThread(event));
		Thread t3 = new Thread(new TestThread(event));
		
		t1.start();
		t2.start();
		t3.start();
		
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("first waiters: "+event.waiters());
		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.signal();
		System.out.println("done");
	}
}
