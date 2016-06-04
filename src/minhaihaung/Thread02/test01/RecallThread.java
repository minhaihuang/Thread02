package minhaihaung.Thread02.test01;
/**
 * 复习一下Thraed中的一些基本知识
 * @author 黄帅哥
 *
 *当同步静态的方法时，锁定的是类.class字节码文件。
 *当同步非晶态方法时，锁定的是this，表示当前对象
 */
public class RecallThread {

	public static void main(String[] args) {
		RecallThread demo=new RecallThread();
		
		Input in=new Input(demo);
		Output out=new Output(demo);
		
		Thread t1=new Thread(in);
		Thread t2=new Thread(out);
		
		t1.start();
		t2.start();
	}
	
	//定义标识符
	boolean flag=true;//标识符为true时，生产者生产，消费者等待；为false时，两者反过来。
	String str=null;//物品
	public void producted(String str){
		if(!flag){
			try {
				this.wait();//线程等待
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			//生产东西
			System.out.println("生产了-->"+str);
			//生产一段时间
			Thread.sleep(500);
			//生产完毕，通知消费
			this.flag=!this.flag;//停下当前线程
			this.notify();//唤醒等待的线程
			this.str=str;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void consume(){
		if(flag){
			try {
				this.wait();//线程等待
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			
			//消费东西
			System.out.println("消费了-->"+str);
			//消费一段时间
			Thread.sleep(200);
			//消费完毕，通知生产
			this.flag=!this.flag;//停下当前线程
			this.notify();//唤醒等待的线程
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


//负责输入的类--》生产者
class Input implements Runnable{
	//定义一份资源，先不初始化，保证多个线程共享同一份资源
	RecallThread  demo;
	
	//在实例化对象时初始资源
	public Input(RecallThread demo) {
		super();
		this.demo = demo;
	}



	@Override
	public void run() {
		//同步代码块
		synchronized(demo){//锁定demo,demo实际上就是一个监视器，一把锁
			//生产者生产的东西
			for(int i=0;i<20;i++){
				if(i%2==0){
					demo.producted("黄海敏");
				}else{
					demo.producted("lxy");
				}
			}
		}
		
	}
	
}

//负责输出的类--》消费者
class Output implements Runnable{
	//定义一份资源，先不初始化，保证多个线程共享同一份资源
	RecallThread demo;
	
	
	public Output(RecallThread demo) {
		super();
		this.demo = demo;
	}

	@Override
	public void run() {
		//同步代码块
		synchronized(demo){//锁定demo,demo实际上就是一个监视器，一把锁
			for(int i=0;i<20;i++){//记住要不断消费
			demo.consume();
			}
		}	
	}
}
