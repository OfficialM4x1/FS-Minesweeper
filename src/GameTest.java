//@author class Lennart Bosse
package src;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;


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