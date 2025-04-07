package ch.zhaw.prog2.bridge;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Controls the traffic passing the bridge
 */
public class TrafficController {

    private final ReentrantLock bridgeLock = new ReentrantLock();
    private final Condition accessFromLeft = bridgeLock.newCondition();
    private final Condition accessFromRight = bridgeLock.newCondition();
    boolean bridgeOccupied = false;

    /* Called when a car wants to enter the bridge form the left side */
    public void enterLeft() {
        bridgeLock.lock();
        try{
            while (bridgeOccupied){
                accessFromLeft.await();
            }
            bridgeOccupied = true;
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }finally {
            bridgeLock.unlock();
        }

    }

    /* Called when a wants to enter the bridge form the right side */
    public void enterRight() {
        bridgeLock.lock();
        try{
            while (bridgeOccupied){
                accessFromRight.await();
            }
            bridgeOccupied = true;
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }finally{
            bridgeLock.unlock();
        }
    }

    /* Called when the car leaves the bridge on the left side */
    public void leaveLeft() {

        bridgeLock.lock();
        try{
            bridgeOccupied = false;
            if (bridgeLock.hasWaiters(accessFromRight)) {
                accessFromRight.signal();
            }else{
                accessFromLeft.signal();
            }
        } finally {
            bridgeLock.unlock();
        }
    }

    /* Called when the car leaves the bridge on the right side */
    public void leaveRight() {
        bridgeLock.lock();
        try{
            bridgeOccupied = false;
            if (bridgeLock.hasWaiters(accessFromLeft)){
                accessFromLeft.signal();
            }else {
                accessFromRight.signal();
            }
        } finally {
            bridgeLock.unlock();
        }
    }
}
