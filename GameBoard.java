import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;

public class GameBoard implements Cloneable {

    // class fields

    private int[][] playBoard;
    private int pieceCount;
    private int currentTurn;
    private Maxconnect4.PLAYER_TYPE first_turn;
    private Maxconnect4.MODE game_mode;



    public static final int NOF_COLS = 7;
    public static final int NOF_ROWS = 6;
    public static final int MAX_PIECE_COUNT = 42;

    // constructor to create a game state based on the input file sent to it
    public GameBoard(String inputFile) {

        this.playBoard = new int[NOF_ROWS][NOF_COLS];
        this.pieceCount = 0;
        int counter = 0;

        BufferedReader input = null;
        String gameData = null;

        // open the input file
        try {
            input = new BufferedReader(new FileReader(inputFile));

        } catch (IOException e) {

            System.out.println("\nProblem opening the input file!\nTry again." + "\n");
            e.printStackTrace();

        }


        // reading the data from the input file

        for (int i = 0; i < NOF_ROWS; i++) {

            try {

                gameData = input.readLine();

                // read each piece from the input file

                for (int j = 0; j < NOF_COLS; j++) {

                    this.playBoard[i][j] = gameData.charAt(counter++) - 48;

                    // checking the input file for values except 1,2 or 0

                    if (!((this.playBoard[i][j] == 0) || (this.playBoard[i][j] == Maxconnect4.ONE) || (this.playBoard[i][j] == Maxconnect4.TWO))) {

                        System.out.println("\nProblems!\n--The piece read " + "from the input file was not a 1, a 2 or a 0");

                        this.exit_function(0);

                    }

                    if (this.playBoard[i][j] > 0) {

                        this.pieceCount++;

                    }

                }

            } catch (Exception e) {

                System.out.println("\nProblem reading the input file!\n" + "Try again.\n");

                e.printStackTrace();

                this.exit_function(0);

            }
            counter = 0;
        } 
        // reading the last line to get the player's turn
        try {
            gameData = input.readLine();

        } catch (Exception e) {

            System.out.println("\nProblem reading the next turn!\n" + "--Try again.\n");
            e.printStackTrace();

        }

        this.currentTurn = gameData.charAt(0) - 48;

        // making sure if the turn corresponds with the moves already made

        if (!((this.currentTurn == Maxconnect4.ONE) || (this.currentTurn == Maxconnect4.TWO))) {

            System.out.println("Problems!\n The current turn read is not a " + "1 or a 2!");

            this.exit_function(0);

        } else if (this.getCurrentTurn() != this.currentTurn) {

            System.out.println("Problems!\n the current turn read does not " + "correspond to the number of pieces played!");

            this.exit_function(0);

        }

    } 
    
    // defining player 1 and player 2 based on the input
    public void setPieceValue() {

        if ((this.currentTurn == Maxconnect4.ONE && first_turn == Maxconnect4.PLAYER_TYPE.COMPUTER)

            || (this.currentTurn == Maxconnect4.TWO && first_turn == Maxconnect4.PLAYER_TYPE.HUMAN)) {

            Maxconnect4.COMPUTER_PIECE = Maxconnect4.ONE;

            Maxconnect4.HUMAN_PIECE = Maxconnect4.TWO;

        } else {

            Maxconnect4.HUMAN_PIECE = Maxconnect4.ONE;

            Maxconnect4.COMPUTER_PIECE = Maxconnect4.TWO;

        }

        System.out.println("Human plays as : " + Maxconnect4.HUMAN_PIECE + " , "
        		+ "Computer plays as : " + Maxconnect4.COMPUTER_PIECE);

    }

//    public Object clone() throws CloneNotSupportedException {
//
//        return super.clone();
//
//    }

    // creating a gameBoard through  a double indexed array
    public GameBoard(int masterGame[][]) {

        this.playBoard = new int[NOF_ROWS][NOF_COLS];
        this.pieceCount = 0;

        for (int i = 0; i < NOF_ROWS; i++) {

            for (int j = 0; j < NOF_COLS; j++) {

                this.playBoard[i][j] = masterGame[i][j];

                if (this.playBoard[i][j] > 0) {
                    this.pieceCount++;

                }

            }

        }

    }

    public int getScore(int player) {

        int playerScore = 0;
        
        // check horizontally
        for (int i = 0; i < NOF_ROWS; i++) {

            for (int j = 0; j < 4; j++) {

                if ((this.playBoard[i][j] == player) && (this.playBoard[i][j + 1] == player)

                    && (this.playBoard[i][j + 2] == player) && (this.playBoard[i][j + 3] == player)) {

                    playerScore++;

                }

            }

        } 

        // check vertically
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < NOF_COLS; j++) {

                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j] == player)

                    && (this.playBoard[i + 2][j] == player) && (this.playBoard[i + 3][j] == player)) {

                    playerScore++;

                }

            }

        }

        // check diagonally from right bottom to top left
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 4; j++) {

                if ((this.playBoard[i][j] == player) && (this.playBoard[i + 1][j + 1] == player)

                    && (this.playBoard[i + 2][j + 2] == player) && (this.playBoard[i + 3][j + 3] == player)) {

                    playerScore++;

                }

            }

        }

        // check diagonally from bottom left to top right
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 4; j++) {

                if ((this.playBoard[i + 3][j] == player) && (this.playBoard[i + 2][j + 1] == player)

                    && (this.playBoard[i + 1][j + 2] == player) && (this.playBoard[i][j + 3] == player)) {

                    playerScore++;

                }

            }

        }

        return playerScore;
    } 


    
    public int getCurrentTurn() {

        return (this.pieceCount % 2) + 1;

    } 

    // getting the current no. of pieces played
    public int getPieceCount() {

        return this.pieceCount;

    }
    
    // this method returns the gameBoard as a double indexed arrays
    public int[][] getGameBoard() {

        return this.playBoard;

    }

    // checks if the column is full
    public boolean isValidPlay(int column) {

        if (!(column >= 0 && column < 7)) {
            return false;

        } else if (this.playBoard[0][column] > 0) {
            return false;

        } else {
            return true;

        }

    }

    // checks if the gameBoard is full or not
    boolean isBoardFull() {

        return (this.getPieceCount() >= GameBoard.MAX_PIECE_COUNT);

    }

    // method that actually makes the move
    public boolean playPiece(int column) {

        if (!this.isValidPlay(column)) {

            return false;

        } else {

        	// places the piece in the first empty spot in the column
            for (int i = 5; i >= 0; i--) {

                if (this.playBoard[i][column] == 0) {

                    this.playBoard[i][column] = getCurrentTurn();

                    this.pieceCount++;

                    return true;

                }

            }

            System.out.println("Something went wrong with playPiece()");
            return false;

        }

    } 

    // printing the game board in a readable format on console
    public void printGameBoard() {

        System.out.println(" -----------------");

        for (int i = 0; i < NOF_ROWS; i++) {

            System.out.print(" | ");

            for (int j = 0; j < NOF_COLS; j++) {

                System.out.print(this.playBoard[i][j] + " ");

            }
            System.out.println("| ");

        }
        System.out.println(" -----------------");

    } 

    // printing the gameBoard in a output file
    public void printGameBoardToFile(String outputFile) {

        try {

            BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));

            for (int i = 0; i < 6; i++) {

                for (int j = 0; j < 7; j++) {

                    output.write(this.playBoard[i][j] + 48);

                }

                output.write("\r\n");

            }

            // writing whose turn is next
            output.write(this.getCurrentTurn() + "\r\n");

            output.close();

        } catch (IOException e) {

            System.out.println("\nProblem writing to the output file!\n" + "Try again.");

            e.printStackTrace();

        }

    }

    private void exit_function(int value) {

        System.out.println("exiting from GameBoard.java!\n\n");
        System.exit(value);

    }

    //set first turn
    public void setFirstTurn(Maxconnect4.PLAYER_TYPE turn) {

        first_turn = turn;
        setPieceValue();

    }


    public Maxconnect4.PLAYER_TYPE getFirstTurn() {

        return first_turn;
    }


    public void setGameMode(Maxconnect4.MODE mode) {

        game_mode = mode;

    }


    public Maxconnect4.MODE getGameMode() {

        return game_mode;

    }



}