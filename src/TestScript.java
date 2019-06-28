import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest(name = "TestScript", author = "Uzair", version = 2.0, info = "Pathing", logo = "")

public final class TestScript extends Script {

    private ArrayList<Position> arrayList = new ArrayList<>();

    @Override

    public void onStart() {
        //Code here will execute before the loop is started
        log("The script has started.");
/*        getBank();
        arrayList.add(new Position(3092, 3245, 0));
        arrayList.add(new Position(3102, 3255, 0));
        arrayList.add(new Position(3103, 3272, 0));
        arrayList.add(new Position(3109, 3284, 0));
        arrayList.add(new Position(3116, 3293, 0));
        arrayList.add(new Position(3137, 3307, 0));
        arrayList.add(new Position(3147, 3310, 0));
        arrayList.add(new Position(3160, 3312, 0));
        arrayList.add(new Position(3176, 3315, 0));*/
    }



    @Override

    public void onExit() {
        //Code here will execute after the script ends
        log("The script has ended.");
    }

    @Override

    public final int onLoop() throws InterruptedException {
        if (!myPlayer().isAnimating()) {
            getWalking().walkPath(arrayList);
            Entity gate = getObjects().closest(883);
            Entity openGate = getObjects().closest(23918);
            if (gate!=null) {
                gate.interact("Open");
            } else {
                getWalking().walk(new Position(3176,3316,0));
                openGate.interact("Close");
            }
        }
        if (Banks.DRAYNOR.contains(myPosition())) {
            bank.open();
            if (getBank().contains("Trout") && getInventory().isEmpty()) {
                bank.withdraw("Trout", 28);
            } else if (!getInventory().isEmpty()) {
                bank.close();
                getWalking().walkPath(arrayList);
            } else {
                stop();
            }
        }
        //getWalking().webWalk(Banks.DRAYNOR);
        return 0;
    }

    private void mineRock() {
        Entity rock  = getObjects().closest("Rocks");
        if (rock != null) {
            rock.interact("Mine");
        }
        if (rock != null && rock.interact("Mine")) {
            Sleep.sleepUntil(() -> myPlayer().isAnimating() || !rock.exists(), 5000);
        }
    }

    @Override

    public void onPaint(Graphics2D g) {

        //This is where you will put your code for paint(s)

    }

}