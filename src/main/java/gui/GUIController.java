package gui;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import entity.FloorType;
import entity.StoreCategory;
import message.BuildFloorMessage;
import message.RestockFloorMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public GUIController(GUI gui, ActorRef tower) {
        this.tower = tower;
        this.gui = gui;
        this.gui.suscribeButtonsToActionListener(this);
    }

    /**
     * Process updates received from tower
     *
     * @param message the update to process
     * @throws Exception
     */
    @Override
    public void onReceive(Object message) throws Exception {
        //TODO: process updates from the tower
    }

    /**
     * Process action made on the GUI
     *
     * @param e
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

    private void restock() {
        tower.tell(new RestockFloorMessage(Integer.valueOf(gui.getFloorNumber()), 0), tower);
    }

    private void buildFloor(FloorType floorType, StoreCategory storeCategory) {
        tower.tell(new BuildFloorMessage(floorType, storeCategory), tower);
    }
}
