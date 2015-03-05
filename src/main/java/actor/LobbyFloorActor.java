package actor;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import message.BuildFloorMessage;
import message.IncomingBitizenMessage;
import scala.concurrent.duration.Duration;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 19:07
 */
public class LobbyFloorActor extends FloorActor {

    private int numberOfFloors = 1;

    /**
     * Ever
     */
    private Cancellable scheduledEntry;

    public LobbyFloorActor(String name, final ActorRef tower) {
        //construction time of lobby is always 0
        super(name, 0);
        //simulate a bitizen entering in the tower every 5 secs.
        scheduledEntry = getContext().system().scheduler().schedule(Duration.Zero(),
                Duration.create(5, TimeUnit.SECONDS),
                new Runnable() {
                    private Random random = new Random();

                    @Override
                    public void run() {
                        int floorNumber = random.nextInt(numberOfFloors) + 1 + 1;
                        tower.tell(new IncomingBitizenMessage(floorNumber), getSelf());
                    }
                }, getContext().system().dispatcher());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof BuildFloorMessage) {
            numberOfFloors++;
        }
    }
}
