package no.sandramoen.ggj2022oslo.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.Dimension;

import no.sandramoen.ggj2022oslo.GGJ2022OsloGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.height = 1040;
        config.width = 620;

        config.vSyncEnabled = true;
        config.useGL30 = true;

        // miscellaneous
        config.title = "GGJ2022!    Binary Non-binary";
        config.resizable = true;
        config.addIcon("images/excluded/start.png", Files.FileType.Internal);

        new LwjglApplication(new GGJ2022OsloGame(), config);
    }
}
