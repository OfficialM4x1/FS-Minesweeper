package src;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameTest {
    public Game game = new Game(5, 5, 5, "Standard");;

    @Test
    public void testMenuIconExists() {
        // Check whether the menu icon was loaded correctly.
        assertNotNull(game.menuicon);
        assertNotNull(game.menuicon.getImage());
    }

    @Test
    public void testHintIconExists() {
        // Check whether the menu icon was loaded correctly.
        assertNotNull(game.hinticon);
        assertNotNull(game.hinticon.getImage());
    }

    @Test
    public void testTutorialIconExists() {
        // Check whether the menu icon was loaded correctly.
        assertNotNull(game.tutorialicon);
        assertNotNull(game.tutorialicon.getImage());
    }

}