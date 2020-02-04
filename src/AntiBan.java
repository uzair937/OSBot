import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AntiBan extends MethodProvider {

    public int antiCount;

    public void execute() {
        antiCount++;
        Random random = new Random();
        Random random2 = new Random();

        int randomValue = random2.nextInt(100);

        switch (randomValue) {
            default:
                break;
            case 1:
                getCamera().moveYaw(random(12, 14) + random.nextInt(random(12, 14) + random.nextInt(random(30, 35))));
                break;
            case 22:
            case 24:
            case 27:
            case 52:
                getCamera().movePitch(random2.nextInt(random(200, 400)));
                break;
            case 38:
            case 99:
            case 90:
            case 0:
                try {
                    sleep(random(250, 500));
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                break;
            case 6:
            case 29:
            case 33:
            case 91:
            case 42:
            case 77:
            case 21:
                getCamera().movePitch(random2.nextInt(random(330, 660)));
                getCamera().moveYaw(random(18, 22) + random.nextInt(random(18, 22) + random.nextInt(random(40, 45))));
                break;
            case 9:
            case 10:
            case 53:
            case 71:
            case 82:
            case 73:
            case 11:
                try {
                    randomRightClick();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
                break;
            case 43:
            case 30:
                if (getTabs().isOpen(Tab.MAGIC)) {
                    getTabs().open(Tab.QUEST);
                } else {
                    getTabs().open(Tab.MAGIC);
                }
                break;
            case 69:
                if (getTabs().isOpen(Tab.SKILLS)) {
                    getTabs().open(Tab.EQUIPMENT);
                } else {
                    getTabs().open(Tab.SKILLS);
                }
                break;
            case 14:
            case 80:
            case 56:
                if (getTabs().isOpen(Tab.FRIENDS)) {
                    getTabs().open(Tab.CLANCHAT);
                } else {
                    getTabs().open(Tab.FRIENDS);
                }
                break;
            case 66:
                if (getTabs().isOpen(Tab.ATTACK)) {
                    getTabs().open(Tab.PRAYER);
                } else {
                    getTabs().open(Tab.ATTACK);
                }
                break;
        }
    }

    private void randomRightClick() throws InterruptedException {
        List<RS2Object> visibleObjs = getObjects().getAll().stream().filter(o -> o.isVisible()).collect(Collectors.toList());
        // select a random object
        int index = random(0, visibleObjs.size() - 1);
        RS2Object obj = visibleObjs.get(index);
        if (obj != null) {
            // hover the object and right click
            sleep(random(50, 100));
            obj.hover();
            getMouse().click(true);
            // while the menu is still open, move the mouse to a new location
            while (getMenuAPI().isOpen()) {
                sleep(random(50, 100));
                moveMouseRandomly(random(0, 2));
            }
        }
    }

    private void moveMouseRandomly(int numberOfPositions) {
        Random random = new Random();
        Point[] pointArray = new Point[numberOfPositions];
        for (int i = 0; i < pointArray.length; i++) {
            pointArray[i] = new Point(-10 + random.nextInt(850), -10 + random.nextInt(550));
        }
        for (int i = 0; i < pointArray.length; i++) {
            getMouse().move(pointArray[i].x, pointArray[i].y);
            try {
                sleep(random(100, 300));
            } catch (InterruptedException e) {
            }
        }
    }
}