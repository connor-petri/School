# ```class BoardModel```
Represents the state of the board and allows for easy mutation and accessing of board.

## ```enum BoardModel.Player```
- ```BoardModel.Player.ONE``` represents player 1
- ```BoardModel.Player.TWO``` represents player 2

## ```class Pit```
### ```String name()```
Retrieves the name of the pit (i.e. A4, B6, Bank1)

### ```BoardModel.Player getOwner()```
Returns the ```BoardModel.Player``` that owns the pit

### ```int numStones()```
Returns the number of stones in the pit

### ```Pit next()```
Returns a reference to the next pit in the order of sowing, including banks

---

### ```BoardModel(int stonesPerPit)```
Constructs a new ```BoardModel``` with the specified number of stones per pit. Will throw an ```IllegalArgumentException``` if this value is not 3 or 4

### ```Pit getBank(BoardModel.Player player)```
Returns the pit representing the specified player's bank

### ```boolean undo()```
- Undoes the previous turn. Cannot be used more than once per turn as the
board state is only saved for the previous turn. Returns true if undo was successful
and false if it was not.

### ```Pit getPit(String name)```
Retrieves a reference to a pit by name (i.e. A3, B4) as a string.

### ```ArrayList<Pit> getPits(BoardModel.Player player)```
Retrieves all of the pits owned by a player. The bank is the first element, followed by each pit in numerical order (i.e. bank, A1, A2, ...)

### ```void reset()```
Resets the board to a new game state

### ```void setStonesPerPit(int stonesPerPit)```
Resets the board and updates the number of stones per pit. Throws ```IllegalArgumentException``` if ```stonesPerPit``` is not 3 or 4

### ```boolean turn(BoardModel.Player player, int pitNum)```
Executes a turn for a specified player given the pit number they have selected. Throws ```IllegalArgumentException``` if this value is not 1-6. Returns true if a free turn is achieved by the specified player and false if not.

