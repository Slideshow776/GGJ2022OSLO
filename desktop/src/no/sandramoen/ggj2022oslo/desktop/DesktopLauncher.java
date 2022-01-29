package no.sandramoen.ggj2022oslo.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import java.awt.Dimension;

import no.sandramoen.ggj2022oslo.GGJ2022OsloGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        // resolution
        /*int LGg8ThinQHeight = 3120;
        int LGg8ThinQWidth = 1440;
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        config.height = (int) (dimension.height / 1.1); // 10% less than height of screen
        config.width = (int) (config.height / (LGg8ThinQHeight / LGg8ThinQWidth)); // width to mimic a phone*/

        config.height = 1040;
        config.width = 620;

        config.vSyncEnabled = true;
        config.useGL30 = true;

        // miscellaneous
        config.title = "GGJ2022!    Binary Non-binary";
        config.resizable = true;
        // config.addIcon("images/excluded/ic_launcher-desktop.png", Files.FileType.Internal);


        new LwjglApplication(new GGJ2022OsloGame(), config);
    }
}