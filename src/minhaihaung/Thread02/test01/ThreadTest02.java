package minhaihaung.Thread02.test01;

public class ThreadTest02 {

	public static void main(String[] args) throws InterruptedException {
		Test test=new Test();
		
		Thread t=new Thread(test);
		t.start();
		
		Thread.sleep(500);
		for(int i=0;i<10;i++){
			System.out.println(Thread.currentThread().getName()+i);
		}
		
	}
}

class Test implements Runnable{

	@Override
	public void run() {
		for(int i=0;i<20;i++){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+i);
		}
	};
}