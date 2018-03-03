import java.util.Scanner;

public class Maxconnect4 {



    public static Scanner scanner = null;
    public static GameBoard currentBoard = null;
    public static AiPlayer aiPlayer = null;



    public static final int ONE = 1;
    public static final int TWO = 2;
    public static int HUMAN_PIECE;
    public static int COMPUTER_PIECE;
    public static int INVALID = 99;
    
    public static final String FILEPATH_PREFIX = "../";
    public static final String COMP_OUTPUT = "computer.txt";
    public static final String HUMAN_OUTPUT = "human.txt";



    public enum MODE {
        INTERACTIVE,
        ONE_MOVE
    };



    public enum PLAYER_TYPE {
        HUMAN,
        COMPUTER
    };



    public static void main(String[] args) throws CloneNotSupportedException {
    	
        // checking for the correct no. of args
        if (args.length != 4) {
            System.out.println("Four command-line arguments are needed:\n"

             + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"

             + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

            exit_function(0);
        }
        
        // parse the input arguments
        String gameMode = args[0].toString(); 
        String inputFile = args[1].toString(); 
        int depthLevel = Integer.parseInt(args[3]); 


        // create and initialize the game board
        currentBoard = new GameBoard(inputFile);

        // create the Ai Player
        aiPlayer = new AiPlayer(depthLevel, currentBoard);

        if (gameMode.equalsIgnoreCase("interactive")) {

            currentBoard.setGameMode(MODE.INTERACTIVE);

            if (args[2].toString().equalsIgnoreCase("computer-next") 
            		|| args[2].toString().equalsIgnoreCase("C")) {

                // if it is computer next, make the computer make a move
                currentBoard.setFirstTurn(PLAYER_TYPE.COMPUTER);
                ComputerMoveForInteractiveMode();

            } else if (args[2].toString().equalsIgnoreCase("human-next")
            		|| args[2].toString().equalsIgnoreCase("H")){

                currentBoard.setFirstTurn(PLAYER_TYPE.HUMAN);
                HumanMove();

            } else {
                System.out.println("\n" + "value for 'next turn' doesn't recognized.  \n try again. \n");
                exit_function(0);
            }


        } else if (!gameMode.equalsIgnoreCase("one-move")) {
            System.out.println("\n" + gameMode + " is an unrecognized game mode \n try again. \n");
            exit_function(0);

        } else {
            // /////////// one-move mode ///////////
            currentBoard.setGameMode(MODE.ONE_MOVE);
            String outputFileName = args[2].toString(); // the output game file
            ComputerMoveForOneMoveMode(outputFileName);
        }
    } // end of main()

  
    // makes computer's move for one-move mode
    private static void ComputerMoveForOneMoveMode(String outputFileName) throws CloneNotSupportedException {
        // variables to keep up with the game
        int playColumn = INVALID; // resetting the players choice of column to play

        System.out.print("\nMaxConnect-4 game:\n");
        System.out.print("Game state before move:\n");

        // print the current game board
        currentBoard.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentBoard.getScore(ONE) + ", Player-2 = "
        + currentBoard.getScore(TWO) + "\n ");

        if (currentBoard.isBoardFull()) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }
        
     
        int current_player = currentBoard.getCurrentTurn();
        //actual move
        playColumn = aiPlayer.findBestMove(currentBoard);

        if (playColumn == INVALID) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        currentBoard.playPiece(playColumn);
        
        // display the output after the move
        System.out.println("-----------------------------------");
        System.out.println("move " + currentBoard.getPieceCount() + ": Player " 
        + current_player + ", column " + (playColumn + 1));
        System.out.print("Game state after move:\n");

        currentBoard.printGameBoard();

        // printing the scores
        System.out.println("Score: Player-1 = " + currentBoard.getScore(ONE) + ", Player-2 = "
        + currentBoard.getScore(TWO) + "\n ");
        currentBoard.printGameBoardToFile(outputFileName);
    }

    // makes computer's move for interactive-mode
    private static void ComputerMoveForInteractiveMode() throws CloneNotSupportedException {
        printBoardAndScore();
        System.out.println("---------------------------------------");
        System.out.println("\n Computer's turn:\n");

        int playColumn = INVALID; // resetting the playColumn variable

        playColumn = aiPlayer.findBestMove(currentBoard);

        if (playColumn == INVALID) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // actual move
        currentBoard.playPiece(playColumn);
 
        System.out.println("move: " + currentBoard.getPieceCount() + " , Player: Computer , Column: "
        + (playColumn + 1));
        currentBoard.printGameBoardToFile(COMP_OUTPUT);

        if (currentBoard.isBoardFull()) {
            printBoardAndScore();
            printResult();
        } else {
            HumanMove();
        }
    }


    private static void printResult() {
        int human_score = currentBoard.getScore(Maxconnect4.HUMAN_PIECE);
        int comp_score = currentBoard.getScore(Maxconnect4.COMPUTER_PIECE);
        System.out.println("--------------------------------------------");
        System.out.println("\n Final Result:");

        if(human_score > comp_score){

            System.out.println("\n Congratulations!! You won this game."); 

        } else if (human_score < comp_score) {

            System.out.println("\n You lost!! Good luck for next game.");

        } else {
            System.out.println("\n Game is tie!!");
        }

    }

    /**

     * This method handles human's move for interactive mode.

     * 

     */

  

    private static boolean isValidPlay(int playColumn) {

        if (currentBoard.isValidPlay(playColumn - 1)) {
            return true;
        }

        System.out.println("Opps!!...Invalid column , Kindly enter column value between 1 to 7.");
        return false;
    }

    // takes human input and makes move on behalf of human
    private static void HumanMove() throws CloneNotSupportedException {

        printBoardAndScore();
        System.out.println("--------------------------------------------");
        System.out.println("\n Human's turn:\nKindly play your move here(1-7):");

        scanner = new Scanner(System.in);

        int playColumn = INVALID;
        do {
            playColumn = scanner.nextInt();
            
        } while (!isValidPlay(playColumn));
        
        // actual move
        currentBoard.playPiece(playColumn - 1);
        System.out.println("move: " + currentBoard.getPieceCount() + " , Player: Human , Column: " 
        + playColumn);

        currentBoard.printGameBoardToFile(HUMAN_OUTPUT);

        if (currentBoard.isBoardFull()) {
            printBoardAndScore();
            printResult();

        } else {
            ComputerMoveForInteractiveMode();
        }
    }

    // printing final result
    public static void printBoardAndScore() {

        System.out.print("Game state :\n");

        // print the current game board
        currentBoard.printGameBoard();

        // print the current scores
        System.out.println("Score: Player-1 = " + currentBoard.getScore(ONE) + ", Player-2 = " 
        + currentBoard.getScore(TWO) + "\n ");

    }

    // exiting before the end of the game
    private static void exit_function(int value) {

        System.out.println("exiting from MaxConnectFour.java!\n\n");
        System.exit(value);
    }

}