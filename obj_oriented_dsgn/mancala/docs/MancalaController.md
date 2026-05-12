# ```class MancalaController```
Orchestrates the interaction between the Mancala Model and View. It enforces game rules, turn logic, and manages the undo functionality.

## ```enum MancalaController.MoveResult```
Resulting action after a player attempts a move.
- ```SUCCESS```: Move executed, turn passed.
- ```EXTRA_TURN```: Move executed, player gets another turn.
- ```GAME_OVER```: Move executed and game has ended.
- ```INVALID_PIT```: Move failed (e.g. empty pit or wrong player's side).
- ```UNDO_SUCCESS```: Undo operation succeeded.
- ```UNDO_LIMIT_REACHED```: Undo failed (3-undo limit reached for this turn).
- ```UNDO_ALREADY_USED```: Undo failed (must make a move before undoing again).
- ```UNDO_INVALID```: Undo failed (no move to undo or other state error).

## ```interface MancalaController.GameListener```
Observer interface for components that need to react to game state changes.
- ```void onGameStateChanged()```: Triggered when the board updates or player turn changes.
- ```void onGameOver(BoardModel.Player winner)```: Triggered when the game finishes.

---

### ```MancalaController(BoardModel model)```
Constructs a controller linked to the specified ```BoardModel```.

### ```void startGame(int stonesPerPit)```
Initializes or resets the game with the given number of stones per pit.

### ```void addGameListener(GameListener listener)```
Attaches a listener to receive game state updates.

### ```MoveResult pitClicked(BoardModel.Player pitSide, int pitNum)```
Handles a pit selection. Validates if the pit belongs to the current player and contains stones. Executes the turn and transitions state.

### ```MoveResult playPit(int pitNum)```
A convenience wrapper for ```pitClicked``` that uses the current player's side.

### ```boolean undo()```
Attempts to undo the previous move. Enforces the limit of 3 undos per turn and ensures a move has actually been made. Returns true if successful.

### ```BoardModel.Player getCurrentPlayer()```
Returns the player whose turn it currently is.

### ```BoardModel.Player getWinner()```
Calculates and returns the winning player based on bank scores, or ```null``` if the game is a tie.

### ```boolean isGameOver()```
Returns true if the game has reached an end state.

### ```MoveResult getLastMoveResult()```
Returns the result of the most recent move attempt.

### ```BoardModel getModel()```
Returns a reference to the underlying ```BoardModel```.
