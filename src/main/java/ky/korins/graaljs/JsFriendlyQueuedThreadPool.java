package ky.korins.graaljs;

import org.eclipse.jetty.util.thread.QueuedThreadPool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;

public class JsFriendlyQueuedThreadPool extends QueuedThreadPool {
    public JsFriendlyQueuedThreadPool() {
    }

    public JsFriendlyQueuedThreadPool(int maxThreads) {
        super(maxThreads);
    }

    public JsFriendlyQueuedThreadPool(int maxThreads, int minThreads) {
        super(maxThreads, minThreads);
    }

    public JsFriendlyQueuedThreadPool(int maxThreads, int minThreads, BlockingQueue<Runnable> queue) {
        super(maxThreads, minThreads, queue);
    }

    public JsFriendlyQueuedThreadPool(int maxThreads, int minThreads, int idleTimeout) {
        super(maxThreads, minThreads, idleTimeout);
    }

    public JsFriendlyQueuedThreadPool(int maxThreads, int minThreads, int idleTimeout, BlockingQueue<Runnable> queue) {
        super(maxThreads, minThreads, idleTimeout, queue);
    }

    public JsFriendlyQueuedThreadPool(int maxThreads, int minThreads, int idleTimeout, BlockingQueue<Runnable> queue, ThreadGroup threadGroup) {
        super(maxThreads, minThreads, idleTimeout, queue, threadGroup);
    }

    public JsFriendlyQueuedThreadPool(int maxThreads, int minThreads, int idleTimeout, int reservedThreads, BlockingQueue<Runnable> queue, ThreadGroup threadGroup) {
        super(maxThreads, minThreads, idleTimeout, reservedThreads, queue, threadGroup);
    }

    public JsFriendlyQueuedThreadPool(int maxThreads, int minThreads, int idleTimeout, int reservedThreads, BlockingQueue<Runnable> queue, ThreadGroup threadGroup, ThreadFactory threadFactory) {
        super(maxThreads, minThreads, idleTimeout, reservedThreads, queue, threadGroup, threadFactory);
    }

    List<JsFriendlyServlet> wellKnowServlets = new LinkedList<>();

    @Override
    protected void removeThread(Thread thread) {
        super.removeThread(thread);
        // removeThread calls as `removeThread(Thread.currentThread)` so we can safely uses remove from ThreadLocal
        for (JsFriendlyServlet jsFriendlyServlet : wellKnowServlets) {
            jsFriendlyServlet.cleanupThread();
        }
    }

    public void addWellKnowServlet(JsFriendlyServlet jsFriendlyServlet) {
        wellKnowServlets.add(jsFriendlyServlet);
    }
}
