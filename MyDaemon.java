import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

public class MyDaemon implements Daemon {
    private Thread myThread;
    private boolean stopped = false;

    @Override
    public void init(DaemonContext daemonContext) throws DaemonInitException, Exception {

        myThread = new Thread(){

            @Override
            public synchronized void start() {
                MyDaemon.this.stopped = false;
                super.start();
            }

            @Override
            public void run() {
                while(!stopped){
                    System.out.println("Hello World!");
                }
            }
        };
    }

    @Override
    public void start() throws Exception {
        myThread.start();
    }

    @Override
    public void stop() throws Exception {
        stopped = true;
        try{
            myThread.join(2);
        }catch(InterruptedException e){
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void destroy() {
        myThread = null;
    }
}
