import org.junit.Test;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase.*;

import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.assertj.swing.junit.jupiter.AssertJSwingJUnitTestCase;
import org.assertj.swing.junit.jupiter.AssertJSwingJUnitRunner;

@ExtendWith(AssertJSwingJUnitTestCase.class)
public class GameTest {
    private FrameFixture window;

    @BeforeEach
    public void setUp() {
        application(Game.class).start();
        window = findFrame(JFrame.class).using(robot());
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

    @Test
    //Überprüft, ob der Fenstertitel korrekt gesetzt ist.
    public void testGameTitle() {
        window.requireTitle("Minesweeper");
    }

    @Test
    //Überprüft, ob das Drücken der Schaltfläche "Zurück zum Menü" das Fenster schließt.
    public void testBackToMenuButton() {
        window.button(JButtonMatcher.withIcon(new ImageIcon("images/menu.png"))).click();
        assertFalse(window.target().isVisible());
    }

    @Test
    //Überprüft, ob das Drücken der Schaltfläche "Hinweis geben" funktioniert (zusätzliche Überprüfungen können hinzugefügt werden, um sicherzustellen, dass ein Hinweis gegeben wurde).
    public void testGiveHintButton() {
        window.button(JButtonMatcher.withIcon(new ImageIcon("images/hint.png"))).click();
        // Hier können zusätzliche Überprüfungen hinzugefügt werden, um sicherzustellen, dass ein Hinweis gegeben wurde.
    }

    @Test
    //Überprüft, ob das Drücken der Schaltfläche "Tutorial anzeigen" das Tutorial-Fenster mit der richtigen Nachricht anzeigt.
    public void testShowTutorialButton() {
        window.button(JButtonMatcher.withIcon(new ImageIcon("images/tutorial.png"))).click();
        window.optionPane().requireMessage("In Minesweeper, your objective is to navigate through a minefield without setting off any explosives...");
    }
}