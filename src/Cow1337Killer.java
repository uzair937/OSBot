import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
import java.util.ArrayList;

@ScriptManifest(name = "Cow 1337 Leet Killer", author = "Uzair", version = 1.0, info = "Kills cows and calf's, eating Trout for food and banking.", logo = "")

public final class Cow1337Killer extends Script {

    private ArrayList<Position> arrayList = new ArrayList<>();
    private Position finalPosition = new Position(3177,3316,0);

    @Override

    public void onStart() {
        //Code here will execute before the loop is started

        log("The script has started.");
        getPlayers(); // returns an instance of Players
        getInventory(); //returns the player inventory
        getNpcs(); // returns an instance of NPCS
        getGroundItems(); // returns an instance of GroundItems
        getObjects(); // returns an instance of Objects
        getBank();

        arrayList.add(new Position(3092, 3245, 0));
        arrayList.add(new Position(3102, 3255, 0));
        arrayList.add(new Position(3103, 3272, 0));
        arrayList.add(new Position(3109, 3284, 0));
        arrayList.add(new Position(3116, 3293, 0));
        arrayList.add(new Position(3124, 3300, 0));
        arrayList.add(new Position(3137, 3307, 0));
        arrayList.add(new Position(3147, 3310, 0));
        arrayList.add(new Position(3160, 3312, 0));
        arrayList.add(new Position(3176, 3315, 0));

        if (myPosition() != finalPosition) {
            walker();
        }
    }

    @Override

    public void onExit() {
        //Code here will execute after the script ends
        log("The script has ended.");
    }

    @Override

    public final int onLoop() throws InterruptedException {
        if (myPlayer().getInteracting() == null) {
            handler();
        }
        return random(0, 100); //The amount of time in milliseconds before the loop starts over
    }

    private void handler() {
        if (myPlayer().getHealthPercent() <= 30 && getInventory().contains("Trout")) {
            getInventory().getItem("Trout").interact("Eat");
            Sleep.sleepUntil(() -> myPlayer().isAnimating(), 500);
        } else if (!getInventory().contains("Trout")){
            try {
                goBank();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        attackCows();
    }

    private void attackCows() {
        Entity npc  = getNpcs().closest("Cow", "Cow calf");
        if (myPlayer().getInteracting() == null) {
            if (npc == null) {
                walker();
            } else if (npc != null && npc.interact("Attack")) {
                Sleep.sleepUntil(() -> myPlayer().isAnimating(), 1000);
            }
        }
    }

    private void goBank() throws InterruptedException {
        getWalking().webWalk(Banks.DRAYNOR);
        if (Banks.DRAYNOR.contains(myPosition())) {
            bank.open();
            if (getBank().contains("Trout") && getInventory().isEmpty()) {
                bank.withdraw("Trout", 28);
            } else if (!getInventory().isEmpty()) {
                bank.close();
                walker();
            } else {
                stop();
            }
        }
    }

    private void walker() {
        getWalking().walkPath(arrayList);
        Entity gate = getObjects().closest(883);
        Entity openGate = getObjects().closest(23918);
        if (gate!=null) {
            gate.interact("Open");
        } else if (openGate != null) {
            getWalking().walk(finalPosition);
            openGate.interact("Close");
            handler();
        }
    }

    @Override

    public void onPaint(Graphics2D g) {
        //This is where you will put your code for paint(s)

    }

}