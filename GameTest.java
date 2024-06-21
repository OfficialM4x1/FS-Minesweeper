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
        // Überprüfen, ob das Menü-Icon korrekt geladen wurde.
        assertNotNull(game.menuicon);
        assertNotNull(game.menuicon.getImage());
    }

    @Test
    public void testHintIconExists() {
        // Überprüfen, ob das Hint-Icon korrekt geladen wurde.
        assertNotNull(game.hinticon);
        assertNotNull(game.hinticon.getImage());
    }

    @Test
    public void testTutorialIconExists() {
        // Überprüfen, ob das Tutorial-Icon korrekt geladen wurde.
        assertNotNull(game.tutorialicon);
        assertNotNull(game.tutorialicon.getImage());
    }

    /*@Test
    public void testGiveHintFunctionality() {
        // Testet die Funktionalität der giveHint-Methode.
        // Bevor der Tipp gegeben wird, sollten keine Felder aufgedeckt sein.
        boolean[][] revealedBeforeHint = game.board.isRevealed;

        // Simuliere den giveHint Button Click.
        for (ActionListener al : giveahint.getActionListeners()) {
            al.actionPerformed(new ActionEvent(giveahint, ActionEvent.ACTION_PERFORMED, null));
        }

        // Nach dem Tipp sollte mindestens ein Feld aufgedeckt sein.
        boolean hintGiven = false;
        boolean[][] revealedAfterHint = game.board.isRevealed;
        for (int i = 0; i < revealedAfterHint.length; i++) {
            for (int j = 0; j < revealedAfterHint[i].length; j++) {
                if (revealedAfterHint[i][j] && !revealedBeforeHint[i][j]) {
                    hintGiven = true;
                    break;
                }
            }
            if (hintGiven) break;
        }

        assertTrue(hintGiven, "Ein Tipp sollte gegeben worden sein und mindestens ein Feld aufgedeckt sein.");
    } */
}






































































































































































































