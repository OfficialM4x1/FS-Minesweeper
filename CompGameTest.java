import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompGameTest {

    @Test
    public void testTwoUsersCreated() {
        // Create the game with predefined usernames
        CompGame game = new CompGame(5, 5, 5);
        
        // Verify that the usernames are correctly set
        assertEquals("Player1", game.getNameinput1());
        assertEquals("Player2", game.getNameinput2());
    }
}
