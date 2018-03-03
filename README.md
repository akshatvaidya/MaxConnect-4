# MaxConnect-4
This is a connect 4 game I developed for in my Artificial Intelligence class at UTA.

Programming language used - Java

Code structure: 
The code is divided into three files.
1) Maxconnect4.java
2) AiPlayer.java   (referred to the file provided by the professor)
3) GameBoard.java  (referred to the file provided by the professor)

1) Maxconnect4.java is the actual driver class and handles user input along with assigns
   proper turns and decides which mode of game to opt for. It has three important methods viz. 
   ComputerMoveForOneMoveMode, ComputerMoveForInteractiveMode and HumanMove. It also has one
   other method which prints the final result.

2) AiPlayer.java class is the class which handles the depth - limited Minimax algorithm with 
   alpha-beta pruning.structure of each city. It has a constructor which assigns the depth level
   based on the user input and instantiates the current game state or the game state as per input file.
   findbestplay() method makes use of two successor methods Calculate_Max_Utility and Calculate_Min_Utility 
   in order to find the best move to make.

3) GameBoard.java class is the class which creates a gameBoard and has various methods which helping in retrieving the 
   the current turn, current score, finding if a move is valid, finding if the board is full, actually making the move and
   printing the gameboard to console as well as an output file,  

How to run:
(Please keep all the .java files and .txt files together in one folder only.)

In the input files, a 0 stands for an empty spot, a 1 stands for a piece played by the first player, and a 2 stands for a piece played by the second player. The last number in the input file indicates which player plays NEXT (and NOT which player played last).

1) Compile the program using command:
   javac Maxconnect4.java
2) Run the program in following ways:
	1) For Single-Move mode:
		java Maxconnect4 one-move inputFileName.txt outputFileName.txt depth
	2) For Interactive mode:
		java Maxconnect4 interactive inputFileName.txt computer-next/human-next depth
		(computer-next and human-next can be abbreviated to c and h)
   		(game-modes are not case-sensitive)
