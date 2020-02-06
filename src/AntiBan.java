import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AntiBan {

    public int antiCount;
    private MethodProvider methodProvider;

    public AntiBan(MethodProvider methodProvider) {
        this.methodProvider = methodProvider;
    }

    public void execute() {
        antiCount++;
        Random random = new Random();
        Random random2 = new Random();

        int randomValue = random2.nextInt(100);

        switch (randomValue) {
            default:
                break;
            case 1:
                methodProvider.getCamera().moveYaw(methodProvider.random(12, 14) + random.nextInt(methodProvider.random(12, 14) + random.nextInt(methodProvider.random(30, 35))));
                break;
            case 22:
            case 24:
            case 27:
            case 52:
                methodProvider.getCamera().movePitch(random2.nextInt(methodProvider.random(200, 400)));
                break;
            case 38:
            case 99:
            case 90:
            case 0:
                try {
                    methodProvider.sleep(methodProvider.random(250, 500));
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
                methodProvider.getCamera().movePitch(random2.nextInt(methodProvider.random(330, 660)));
                methodProvider.getCamera().moveYaw(methodProvider.random(18, 22) + random.nextInt(methodProvider.random(18, 22) + random.nextInt(methodProvider.random(40, 45))));
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
                if (methodProvider.getTabs().isOpen(Tab.MAGIC)) {
                    methodProvider.getTabs().open(Tab.QUEST);
                } else {
                    methodProvider.getTabs().open(Tab.MAGIC);
                }
                break;
            case 69:
                if (methodProvider.getTabs().isOpen(Tab.SKILLS)) {
                    methodProvider.getTabs().open(Tab.EQUIPMENT);
                } else {
                    methodProvider.getTabs().open(Tab.SKILLS);
                }
                break;
            case 14:
            case 80:
            case 56:
                if (methodProvider.getTabs().isOpen(Tab.FRIENDS)) {
                    methodProvider.getTabs().open(Tab.CLANCHAT);
                } else {
                    methodProvider.getTabs().open(Tab.FRIENDS);
                }
                break;
            case 66:
                if (methodProvider.getTabs().isOpen(Tab.ATTACK)) {
                    methodProvider.getTabs().open(Tab.PRAYER);
                } else {
                    methodProvider.getTabs().open(Tab.ATTACK);
                }
                break;
        }
    }

    private void randomRightClick() throws InterruptedException {
        List<RS2Object> visibleObjs = methodProvider.getObjects().getAll().stream().filter(o -> o.isVisible()).collect(Collectors.toList());
        // select a random object
        int index = methodProvider.random(0, visibleObjs.size() - 1);
        RS2Object obj = visibleObjs.get(index);
        if (obj != null) {
            // hover the object and right click
            methodProvider.sleep(methodProvider.random(50, 100));
            obj.hover();
            methodProvider.getMouse().click(true);
            // while the menu is still open, move the mouse to a new location
            while (methodProvider.getMenuAPI().isOpen()) {
                methodProvider.sleep(methodProvider.random(50, 100));
                moveMouseRandomly(methodProvider.random(0, 2));
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
            methodProvider.getMouse().move(pointArray[i].x, pointArray[i].y);
            try {
                methodProvider.sleep(methodProvider.random(100, 300));
            } catch (InterruptedException e) {
            }
        }
    }
}