import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for the {@link BoardModel} class.
*
* @author Connor Petri
* @see BoardModel
*/
public class BoardModelTests {

    /**
    * Tests a full sequence of moves to ensure correct game behavior,
    * including stone distribution, captures, extra turns, and game end conditions.
    */

    @Test
    public void fullGameTest() {
        BoardModel b = new BoardModel(4);

        // Move 1: Player ONE picks A4 (4 stones) -> A5, A6, Bank1, B1
        assertFalse(b.turn(BoardModel.Player.ONE, 4));
        assertEquals(4, b.getPit("A1").numStones());
        assertEquals(4, b.getPit("A2").numStones());
        assertEquals(4, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(5, b.getPit("A5").numStones());
        assertEquals(5, b.getPit("A6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(5, b.getPit("B1").numStones());
        assertEquals(4, b.getPit("B2").numStones());
        assertEquals(4, b.getPit("B3").numStones());
        assertEquals(4, b.getPit("B4").numStones());
        assertEquals(4, b.getPit("B5").numStones());
        assertEquals(4, b.getPit("B6").numStones());
        assertEquals(0, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 2: Player TWO picks B5 (4 stones) -> B6, Bank2, A1, A2
        assertFalse(b.turn(BoardModel.Player.TWO, 5));
        assertEquals(5, b.getPit("A1").numStones());
        assertEquals(5, b.getPit("A2").numStones());
        assertEquals(4, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(5, b.getPit("A5").numStones());
        assertEquals(5, b.getPit("A6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(5, b.getPit("B1").numStones());
        assertEquals(4, b.getPit("B2").numStones());
        assertEquals(4, b.getPit("B3").numStones());
        assertEquals(4, b.getPit("B4").numStones());
        assertEquals(0, b.getPit("B5").numStones());
        assertEquals(5, b.getPit("B6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 3: Player ONE picks A2 (5 stones) -> A3, A4, A5, A6, Bank1 => FREE TURN
        assertTrue(b.turn(BoardModel.Player.ONE, 2));
        assertEquals(5, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(5, b.getPit("A3").numStones());
        assertEquals(1, b.getPit("A4").numStones());
        assertEquals(6, b.getPit("A5").numStones());
        assertEquals(6, b.getPit("A6").numStones());
        assertEquals(2, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(5, b.getPit("B1").numStones());
        assertEquals(4, b.getPit("B2").numStones());
        assertEquals(4, b.getPit("B3").numStones());
        assertEquals(4, b.getPit("B4").numStones());
        assertEquals(0, b.getPit("B5").numStones());
        assertEquals(5, b.getPit("B6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 4: Player ONE free turn, picks A5 (6 stones) -> A6, Bank1, B1, B2, B3, B4
        // No capture
        assertFalse(b.turn(BoardModel.Player.ONE, 5));
        assertEquals(5, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(5, b.getPit("A3").numStones());
        assertEquals(1, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(7, b.getPit("A6").numStones());
        assertEquals(3, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(6, b.getPit("B1").numStones());
        assertEquals(5, b.getPit("B2").numStones());
        assertEquals(5, b.getPit("B3").numStones());
        assertEquals(5, b.getPit("B4").numStones());
        assertEquals(0, b.getPit("B5").numStones());
        assertEquals(5, b.getPit("B6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 5: Player TWO picks B3 (5 stones) -> B4, B5, B6, Bank2, A1
        // No capture
        assertFalse(b.turn(BoardModel.Player.TWO, 3));
        assertEquals(6, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(5, b.getPit("A3").numStones());
        assertEquals(1, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(7, b.getPit("A6").numStones());
        assertEquals(3, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(6, b.getPit("B1").numStones());
        assertEquals(5, b.getPit("B2").numStones());
        assertEquals(0, b.getPit("B3").numStones());
        assertEquals(6, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(6, b.getPit("B6").numStones());
        assertEquals(2, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 6: Player ONE picks A4 (1 stone) -> A5
        // Capture: Bank1 += B2(5) + A5(1) = 3+6 = 9. B2 -> 0, A5 -> 0.
        assertFalse(b.turn(BoardModel.Player.ONE, 4));
        assertEquals(6, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(5, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(7, b.getPit("A6").numStones());
        assertEquals(9, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(6, b.getPit("B1").numStones());
        assertEquals(0, b.getPit("B2").numStones());
        assertEquals(0, b.getPit("B3").numStones());
        assertEquals(6, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(6, b.getPit("B6").numStones());
        assertEquals(2, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 7: Player TWO picks B6 (6 stones) -> Bank2, A1, A2, A3, A4, A5
        // No capture
        assertFalse(b.turn(BoardModel.Player.TWO, 6));
        assertEquals(7, b.getPit("A1").numStones());
        assertEquals(1, b.getPit("A2").numStones());
        assertEquals(6, b.getPit("A3").numStones());
        assertEquals(1, b.getPit("A4").numStones());
        assertEquals(1, b.getPit("A5").numStones());
        assertEquals(7, b.getPit("A6").numStones());
        assertEquals(9, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(6, b.getPit("B1").numStones());
        assertEquals(0, b.getPit("B2").numStones());
        assertEquals(0, b.getPit("B3").numStones());
        assertEquals(6, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(0, b.getPit("B6").numStones());
        assertEquals(3, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 8: Player ONE picks A3 (6 stones) -> A4, A5, A6, Bank1, B1, B2
        // No capture
        assertFalse(b.turn(BoardModel.Player.ONE, 3));
        assertEquals(7, b.getPit("A1").numStones());
        assertEquals(1, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(2, b.getPit("A4").numStones());
        assertEquals(2, b.getPit("A5").numStones());
        assertEquals(8, b.getPit("A6").numStones());
        assertEquals(10, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(7, b.getPit("B1").numStones());
        assertEquals(1, b.getPit("B2").numStones());
        assertEquals(0, b.getPit("B3").numStones());
        assertEquals(6, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(0, b.getPit("B6").numStones());
        assertEquals(3, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 9: Player TWO picks B2 (1 stone) -> B3
        // Capture: Bank2 += A4(2) + B3(1) = 3+3 = 6. A4 -> 0, B3 -> 0.
        assertFalse(b.turn(BoardModel.Player.TWO, 2));
        assertEquals(7, b.getPit("A1").numStones());
        assertEquals(1, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(2, b.getPit("A5").numStones());
        assertEquals(8, b.getPit("A6").numStones());
        assertEquals(10, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(7, b.getPit("B1").numStones());
        assertEquals(0, b.getPit("B2").numStones());
        assertEquals(0, b.getPit("B3").numStones());
        assertEquals(6, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(0, b.getPit("B6").numStones());
        assertEquals(6, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 10: Player ONE picks A2 (1 stone) -> A3
        // Capture: Bank1 += B4(6) + A3(1) = 10+7 = 17. B4 -> 0, A3 -> 0.
        assertFalse(b.turn(BoardModel.Player.ONE, 2));
        assertEquals(7, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(2, b.getPit("A5").numStones());
        assertEquals(8, b.getPit("A6").numStones());
        assertEquals(17, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(7, b.getPit("B1").numStones());
        assertEquals(0, b.getPit("B2").numStones());
        assertEquals(0, b.getPit("B3").numStones());
        assertEquals(0, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(0, b.getPit("B6").numStones());
        assertEquals(6, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 11: Player TWO picks B5 (1 stone) -> B6
        // Capture: Bank2 += A1(7) + B6(1) = 6+8 = 14. A1 -> 0, B6 -> 0.
        assertFalse(b.turn(BoardModel.Player.TWO, 5));
        assertEquals(0, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(2, b.getPit("A5").numStones());
        assertEquals(8, b.getPit("A6").numStones());
        assertEquals(17, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(7, b.getPit("B1").numStones());
        assertEquals(0, b.getPit("B2").numStones());
        assertEquals(0, b.getPit("B3").numStones());
        assertEquals(0, b.getPit("B4").numStones());
        assertEquals(0, b.getPit("B5").numStones());
        assertEquals(0, b.getPit("B6").numStones());
        assertEquals(14, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 12: Player ONE picks A5 (2 stones) -> A6, Bank1 => FREE TURN
        assertTrue(b.turn(BoardModel.Player.ONE, 5));
        assertEquals(0, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(9, b.getPit("A6").numStones());
        assertEquals(18, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(7, b.getPit("B1").numStones());
        assertEquals(0, b.getPit("B2").numStones());
        assertEquals(0, b.getPit("B3").numStones());
        assertEquals(0, b.getPit("B4").numStones());
        assertEquals(0, b.getPit("B5").numStones());
        assertEquals(0, b.getPit("B6").numStones());
        assertEquals(14, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 13: Player ONE free turn, picks A6 (9 stones) -> Bank1, B1, B2, B3, B4, B5, B6, A1, A2
        // Capture: Bank1 += B5(1) + A2(1) = 18+2 = 21. B5 -> 0, A2 -> 0.
        assertFalse(b.turn(BoardModel.Player.ONE, 6));
        assertEquals(1, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(0, b.getPit("A6").numStones());
        assertEquals(21, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(8, b.getPit("B1").numStones());
        assertEquals(1, b.getPit("B2").numStones());
        assertEquals(1, b.getPit("B3").numStones());
        assertEquals(1, b.getPit("B4").numStones());
        assertEquals(0, b.getPit("B5").numStones());
        assertEquals(1, b.getPit("B6").numStones());
        assertEquals(14, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 14: Player TWO picks B6 (1 stone) -> Bank2 => FREE TURN
        assertTrue(b.turn(BoardModel.Player.TWO, 6));
        assertEquals(1, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(0, b.getPit("A6").numStones());
        assertEquals(21, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(8, b.getPit("B1").numStones());
        assertEquals(1, b.getPit("B2").numStones());
        assertEquals(1, b.getPit("B3").numStones());
        assertEquals(1, b.getPit("B4").numStones());
        assertEquals(0, b.getPit("B5").numStones());
        assertEquals(0, b.getPit("B6").numStones());
        assertEquals(15, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 15: Player TWO free turn, picks B1 (8 stones) -> B2, B3, B4, B5, B6, Bank2, A1, A2
        // No capture.
        assertFalse(b.turn(BoardModel.Player.TWO, 1));
        assertEquals(2, b.getPit("A1").numStones());
        assertEquals(1, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(0, b.getPit("A6").numStones());
        assertEquals(21, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(0, b.getPit("B1").numStones());
        assertEquals(2, b.getPit("B2").numStones());
        assertEquals(2, b.getPit("B3").numStones());
        assertEquals(2, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(1, b.getPit("B6").numStones());
        assertEquals(16, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 16: Player ONE picks A2 (1 stone) -> A3
        // Capture: Bank1 += B4(2) + A3(1) = 21+3 = 24. B4 -> 0, A3 -> 0.
        assertFalse(b.turn(BoardModel.Player.ONE, 2));
        assertEquals(2, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(0, b.getPit("A6").numStones());
        assertEquals(24, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(0, b.getPit("B1").numStones());
        assertEquals(2, b.getPit("B2").numStones());
        assertEquals(2, b.getPit("B3").numStones());
        assertEquals(0, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(1, b.getPit("B6").numStones());
        assertEquals(16, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 17: Player TWO picks B2 (2 stones) -> B3, B4
        // No capture
        assertFalse(b.turn(BoardModel.Player.TWO, 2));
        assertEquals(2, b.getPit("A1").numStones());
        assertEquals(0, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(0, b.getPit("A6").numStones());
        assertEquals(24, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(0, b.getPit("B1").numStones());
        assertEquals(0, b.getPit("B2").numStones());
        assertEquals(3, b.getPit("B3").numStones());
        assertEquals(1, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(1, b.getPit("B6").numStones());
        assertEquals(16, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 18: Player ONE picks A1 (2 stones) -> A2, A3
        // Capture: Bank1 += B4(1) + A3(1) = 24+2 = 26. B4 -> 0, A3 -> 0.
        assertFalse(b.turn(BoardModel.Player.ONE, 1));
        assertEquals(0, b.getPit("A1").numStones());
        assertEquals(1, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(0, b.getPit("A6").numStones());
        assertEquals(26, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(0, b.getPit("B1").numStones());
        assertEquals(0, b.getPit("B2").numStones());
        assertEquals(3, b.getPit("B3").numStones());
        assertEquals(0, b.getPit("B4").numStones());
        assertEquals(1, b.getPit("B5").numStones());
        assertEquals(1, b.getPit("B6").numStones());
        assertEquals(16, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 19: Player TWO picks B3 (3 stones) -> B4, B5, B6
        assertFalse(b.turn(BoardModel.Player.TWO, 3));
        assertEquals(0, b.getPit("A1").numStones());
        assertEquals(1, b.getPit("A2").numStones());
        assertEquals(0, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(0, b.getPit("A5").numStones());
        assertEquals(0, b.getPit("A6").numStones());
        assertEquals(26, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(0, b.getPit("B1").numStones());
        assertEquals(0, b.getPit("B2").numStones());
        assertEquals(0, b.getPit("B3").numStones());
        assertEquals(1, b.getPit("B4").numStones());
        assertEquals(2, b.getPit("B5").numStones());
        assertEquals(2, b.getPit("B6").numStones());
        assertEquals(16, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 20: Player ONE picks A2 (1 stone) -> A3
        // Capture: Bank1 += B4(1) + A3(1) = 26+2 = 28. B4 -> 0, A3 -> 0.
        // All of Player ONE's pits are now empty -> GAME OVER.
        assertFalse(b.turn(BoardModel.Player.ONE, 2));

        // Verify game-over condition: all playing pits are empty after sweep
        for (BoardModel.Pit p : b.getPits(BoardModel.Player.ONE)) {
            if (p.equals(b.getBank(BoardModel.Player.ONE))) continue;
            assertEquals(0, p.numStones(), "All P1 pits should be empty at game end");
        }
        for (BoardModel.Pit p : b.getPits(BoardModel.Player.TWO)) {
            if (p.equals(b.getBank(BoardModel.Player.TWO))) continue;
            assertEquals(0, p.numStones(), "All P2 pits should be empty at game end");
        }

        // Final score: Player ONE = 28, Player TWO = 20
        assertEquals(28, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(20, b.getBank(BoardModel.Player.TWO).numStones());
    }

    @Test
    public void undoTest() {
        BoardModel b = new BoardModel(4);

        // Move 1: Player ONE picks A4 (4 stones) -> A5, A6, Bank1, B1
        assertFalse(b.turn(BoardModel.Player.ONE, 4));
        assertEquals(4, b.getPit("A1").numStones());
        assertEquals(4, b.getPit("A2").numStones());
        assertEquals(4, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(5, b.getPit("A5").numStones());
        assertEquals(5, b.getPit("A6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(5, b.getPit("B1").numStones());
        assertEquals(4, b.getPit("B2").numStones());
        assertEquals(4, b.getPit("B3").numStones());
        assertEquals(4, b.getPit("B4").numStones());
        assertEquals(4, b.getPit("B5").numStones());
        assertEquals(4, b.getPit("B6").numStones());
        assertEquals(0, b.getBank(BoardModel.Player.TWO).numStones());

        // Move 2: Player TWO picks B5 (4 stones) -> B6, Bank2, A1, A2
        assertFalse(b.turn(BoardModel.Player.TWO, 5));
        assertEquals(5, b.getPit("A1").numStones());
        assertEquals(5, b.getPit("A2").numStones());
        assertEquals(4, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(5, b.getPit("A5").numStones());
        assertEquals(5, b.getPit("A6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(5, b.getPit("B1").numStones());
        assertEquals(4, b.getPit("B2").numStones());
        assertEquals(4, b.getPit("B3").numStones());
        assertEquals(4, b.getPit("B4").numStones());
        assertEquals(0, b.getPit("B5").numStones());
        assertEquals(5, b.getPit("B6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.TWO).numStones());

        // UNDO
        assertTrue(b.undo());
        assertEquals(4, b.getPit("A1").numStones());
        assertEquals(4, b.getPit("A2").numStones());
        assertEquals(4, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(5, b.getPit("A5").numStones());
        assertEquals(5, b.getPit("A6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(5, b.getPit("B1").numStones());
        assertEquals(4, b.getPit("B2").numStones());
        assertEquals(4, b.getPit("B3").numStones());
        assertEquals(4, b.getPit("B4").numStones());
        assertEquals(4, b.getPit("B5").numStones());
        assertEquals(4, b.getPit("B6").numStones());
        assertEquals(0, b.getBank(BoardModel.Player.TWO).numStones());

        // Undo again should fail
        assertFalse(b.undo());

        // Check board state is the same as turn 1
        assertEquals(4, b.getPit("A1").numStones());
        assertEquals(4, b.getPit("A2").numStones());
        assertEquals(4, b.getPit("A3").numStones());
        assertEquals(0, b.getPit("A4").numStones());
        assertEquals(5, b.getPit("A5").numStones());
        assertEquals(5, b.getPit("A6").numStones());
        assertEquals(1, b.getBank(BoardModel.Player.ONE).numStones());
        assertEquals(5, b.getPit("B1").numStones());
        assertEquals(4, b.getPit("B2").numStones());
        assertEquals(4, b.getPit("B3").numStones());
        assertEquals(4, b.getPit("B4").numStones());
        assertEquals(4, b.getPit("B5").numStones());
        assertEquals(4, b.getPit("B6").numStones());
        assertEquals(0, b.getBank(BoardModel.Player.TWO).numStones());
    }
}
