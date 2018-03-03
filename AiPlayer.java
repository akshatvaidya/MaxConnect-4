//import java.util.*;

public class AiPlayer {

    public int level;
    public GameBoard gameBoardShallow;

    public AiPlayer(int depth, GameBoard currentBoard) {
        this.level = depth;
        this.gameBoardShallow = currentBoard;
    }

        //This method makes the decision to make a move for the computer using 
		//the min and max value from the below given two functions.

    public int findBestMove(GameBoard currentBoard) throws CloneNotSupportedException {

        int ColumnToPlay = Maxconnect4.INVALID;

        if (currentBoard.getCurrentTurn() == Maxconnect4.ONE) {

            int v = Integer.MAX_VALUE;

            for (int i = 0; i < GameBoard.NOF_COLS; i++) {

                if (currentBoard.isValidPlay(i)) {

                    GameBoard boardAfterNextMove = new GameBoard(currentBoard.getGameBoard());

                    boardAfterNextMove.playPiece(i);

                    int utility = Cal_Max_Utility(boardAfterNextMove, level, Integer.MIN_VALUE, Integer.MAX_VALUE);

                    if (v > utility) {

                        ColumnToPlay = i;

                        v = utility;

                    }

                }

            }

        } else {

            int v = Integer.MIN_VALUE;

            for (int i = 0; i < GameBoard.NOF_COLS; i++) {

                if (currentBoard.isValidPlay(i)) {

                    GameBoard boardAfterNextMove = new GameBoard(currentBoard.getGameBoard());

                    boardAfterNextMove.playPiece(i);

                    int utility = Cal_Min_Utility(boardAfterNextMove, level, Integer.MIN_VALUE, Integer.MAX_VALUE);

                    if (v < utility) {

                        ColumnToPlay = i;

                        v = utility;

                    }

                }

            }

        }

        return ColumnToPlay;

    }


     // Calculating Min value


    private int Cal_Min_Utility(GameBoard gameBoard, int level, int alpha, int beta)

        throws CloneNotSupportedException {

        // TODO Auto-generated method stub

        if (!gameBoard.isBoardFull() && level > 0) {

            int v = Integer.MAX_VALUE;

            for (int i = 0; i < GameBoard.NOF_COLS; i++) {

                if (gameBoard.isValidPlay(i)) {

                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());

                    board4NextMove.playPiece(i);

                    int utility = Cal_Max_Utility(board4NextMove, level - 1, alpha, beta);

                    if (v > utility) {

                        v = utility;

                    }

                    if (v <= alpha) {

                        return v;

                    }

                    if (beta > v) {

                        beta = v;

                    }

                }

            }

            return v;

        } else {

            // this is a terminal state so calculating the utility of the state

            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);

        }

    }


    // calculating max value
    
    private int Cal_Max_Utility(GameBoard gameBoard, int level, int alpha, int beta)

        throws CloneNotSupportedException {

        if (!gameBoard.isBoardFull() && level > 0) {

            int v = Integer.MIN_VALUE;

            for (int i = 0; i < GameBoard.NOF_COLS; i++) {

                if (gameBoard.isValidPlay(i)) {

                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());

                    board4NextMove.playPiece(i);

                    int utility = Cal_Min_Utility(board4NextMove, level - 1, alpha, beta);

                    if (v < utility) {

                        v = utility;

                    }

                    if (v >= beta) {

                        return v;

                    }

                    if (alpha < v) {

                        alpha = v;

                    }

                }

            }

            return v;

        } else {

            // since this is a terminal state calculating the utility

            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);

        }

    }



}