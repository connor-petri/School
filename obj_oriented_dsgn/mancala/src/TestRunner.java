public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Starting test run...");
        try {
            System.out.println("Running BoardModelTests...");
            BoardModelTests tests = new BoardModelTests();
            tests.fullGameTest();
            tests.undoTest();

            System.out.println("Running ControllerTests...");
            ControllerTests controllerTests = new ControllerTests();
            controllerTests.testInitialization();
            controllerTests.testGameListenerNotifications();
            controllerTests.testMoveResultsAndTurnSwitching();
            controllerTests.testExtraTurn();
            controllerTests.testUndoManagerConstraints();
            controllerTests.testUndoThreeLimit();
            controllerTests.testGameOverAndWinner();

            System.out.println("Running BoardPanelTests...");
            BoardPanelTests boardPanelTests = new BoardPanelTests();
            boardPanelTests.createsAllPitButtons();
            boardPanelTests.clickingOwnPit();
            boardPanelTests.clickingOpponentPit();

            System.out.println("Running MancalaFrameTests...");
            MancalaFrameTests mancalaFrameTests = new MancalaFrameTests();
            mancalaFrameTests.undoButtonCallsController();
            mancalaFrameTests.newGame3ButtonResetsBoard();
            mancalaFrameTests.newGame4ButtonResetsBoard();

            System.out.println("All tests passed!");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
