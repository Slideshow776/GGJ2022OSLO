package no.sandramoen.ggj2022oslo;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;

public class LevelScreen extends BaseScreen {
    private Turtle turtle;
    private boolean win;

    private ArrayList<Starfish> starFishes;
    private ArrayList<Rock> rocks;

    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);
        BaseActor.setWorldBounds(ocean);

        starFishes = new ArrayList<>();
        starFishes.add(new Starfish(400, 400, mainStage));
        starFishes.add(new Starfish(500, 100, mainStage));
        starFishes.add(new Starfish(100, 450, mainStage));
        starFishes.add(new Starfish(200, 250, mainStage));

        rocks = new ArrayList<>();
        rocks.add(new Rock(200, 150, mainStage));
        rocks.add(new Rock(100, 300, mainStage));
        rocks.add(new Rock(300, 350, mainStage));
        rocks.add(new Rock(450, 200, mainStage));

        turtle = new Turtle(20, 20, mainStage);

        win = false;
    }

    public void update(float dt) {
        for (BaseActor rockActor : rocks)
            turtle.preventOverlap(rockActor);

        for (int i = starFishes.size()-1; i >= 0; i--) {
            if (turtle.overlaps(starFishes.get(i)) && !starFishes.get(i).collected) {
                starFishes.get(i).collected = true;
                starFishes.get(i).clearActions();
                starFishes.get(i).addAction(Actions.fadeOut(1));
                starFishes.get(i).addAction(Actions.after(Actions.removeActor()));

                Whirlpool whirl = new Whirlpool(0, 0, mainStage);
                whirl.centerAtActor(starFishes.get(i));
                whirl.setOpacity(0.25f);
                starFishes.remove(starFishes.get(i));
            }
        }

        if (starFishes.size() == 0 && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, uiStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}