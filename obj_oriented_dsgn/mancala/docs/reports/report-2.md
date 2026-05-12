Monday, 4/13 ~ Friday, 4/17

Team Name: Group 4
Team leader of the week: Edward Cui
Team members: Zaeracami Carino, Edward Cui, Connor Petri

Summary
This week, Edward worked on the controller architecture, consisting of `MancalaController`, `UndoManager`, 
`MoveResult`, and the `GameListener` GUI interface. Connor added helper functions to `BoardModel`, wrote 
`BoardModelTests` to simulate a game, and patched discovered bugs. Zaeracami reviewed `BoardModel` and suggested 
changes to game logic. Edward and Zaeracami struggled to get the JUnit testing files working natively within VS 
Code due to an issue with the standalone jar. Zaeracami spent an hour troubleshooting the testing files, while 
Edward eventually managed to resolve the issue by manually downloading required JUnit components. With tests 
working, Edward wrote `ControllerTests` to verify game mechanics.

Zaeracami Carino
M, 4/13: NONE
T, 4/14: NONE
W, 4/15: NONE
R, 4/16: NONE
F, 4/17: Spent 1 hour troubleshooting VS coding and 30 minutes reviewing `BoardModel` for changes

Edward Cui
M, 4/13: NONE
T, 4/14: NONE
W, 4/15: Drafted game mechanic logic and undo constraints
R, 4/16: Refined move results and added game listener
F, 4/17: Fixed VS Code testing and wrote controller tests

Connor Petri
M, 4/13: NONE
T, 4/14: Expanded the model with additional helper functions
W, 4/15: Wrote a large test in JUnit and fixed bugs the test caught
R, 4/16: PR goes live
F, 4/17: Working on implementing feedback