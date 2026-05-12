# Task Distributution

## Member 1 (Connor): Model + controller + backend integration

    Owns backend foundation

    Suggested files: `BoardModel.java`, `MancalaTest.java`

    - Board data structure
    - Pit/Mancala stone storage
    - Initial game setup
    - Current player tracking
    - Core move execution
    - Counter clockwise sowing logic
    - Skip opponent logic
    - Extra turn logic
    - Methods needed to read board state

## Member 2 (Cami): GUI / view + board styles

    Own the visual side of the project

    Suggested files: `MancalaFrame.java`, `BoardPanel.java`, `StartScreenPanel.java`, `BoardStyle.java`

    - Main window/frame
    - Start screen
    - Style-selection screen/buttons
    - Board drawing
    - Pit drawing
    - Mancala drawing
    - Stone drawing
    - Labels
    - Undo button placement
    - Status displays
    - Layout of all components
    - Board refresh/repaint behavior
    - Strategy patten for styles

## Member 3: Rule features + undo + controller/event logic

    Owns remaining game logic and interaction logic

    Suggested files: `MancalaController.java`, `UndoManager.java`, `MoveResult.java`

    - Mouse click handling
    - Mapping clicks to split selection
    - Calling model methods when a player moves
    - Rejecting invalid pit clicks\
    - Turn transitions between players
    - Capture rule
    - End-game detection flow
    - Collect-remaining-stones-at-end logic
    - Winner determination/display trigger
    - Undo feature logic & restrictions
        - only before other player moves
        - no multiple undos in a row
        - at most 3 undos per turn
    - Integration of undo button with model/view
    - App flow from start screen to game board