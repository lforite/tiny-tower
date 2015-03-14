package gui;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import entity.FloorType;
import entity.StoreCategory;
import message.AskReportMoneyMessage;
import message.BuildFloorMessage;
import message.ReportMoneyMessage;
import message.RestockFloorMessage;
import scala.concurrent.duration.Duration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * User: louis.forite
 * Date: 24/02/15
 * Time: 21:47
 */
public class GUIController extends UntypedActor implements ActionListener {

    /**
     * For now just one tower
     */
    private ActorRef tower;
    private GUI gui;

    public GUIController(GUI gui, final ActorRef tower) {
        this.tower = tower;
        this.gui = gui;
        this.gui.suscribeButtonsToActionListener(this);
        scheduleMoneyUpdates();
    }

    /**
     * Process updates received from tower
     *
     * @param message the update to process
     * @throws Exception
     */
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof ReportMoneyMessage) {
            updateMoneyAmount((ReportMoneyMessage) message);
        }
    }

    /**
     * Process action made on the GUI
     *
     * @param e the triggered event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "restock":
                restock();
                break;
            case "buildRecreationStore":
                buildFloor(FloorType.STORE, StoreCategory.RECREATION);
                break;
            case "buildServiceStore":
                buildFloor(FloorType.STORE, StoreCategory.SERVICE);
                break;
            case "buildFoodStore":
                buildFloor(FloorType.STORE, StoreCategory.FOOD);
                break;
            case "buildCreativeStore":
                buildFloor(FloorType.STORE, StoreCategory.CREATIVE);
                break;
            case "buildRetailStore":
                buildFloor(FloorType.STORE, StoreCategory.RETAIL);
                break;
            case "buildApartment":
                buildFloor(FloorType.APARTMENT, null);
                break;
            default:
                System.err.println("ERROR LAWL");
        }
    }

    /**
     * Ask money updates from tower every sec
     */
    private void scheduleMoneyUpdates() {
        getContext().system().scheduler().schedule(
                Duration.Zero(),
                Duration.create(500, TimeUnit.MILLISECONDS),
                new Runnable() {
                    @Override
                    public void run() {
                        tower.tell(new AskReportMoneyMessage(), getSelf());
                    }
                }, getContext().system().dispatcher()
        );
    }

    private void restock() {
        tower.tell(new RestockFloorMessage(Integer.valueOf(gui.getFloorNumber()), 0), tower);
    }

    private void buildFloor(FloorType floorType, StoreCategory storeCategory) {
        tower.tell(new BuildFloorMessage(floorType, storeCategory), tower);
    }

    private void updateMoneyAmount(ReportMoneyMessage message) {
        gui.refreshMoneyAmount(message.getAmount());
    }
}
