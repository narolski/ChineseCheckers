import java.util.ArrayList;

public class Board implements BoardInterface {

    private int numberOfPlayers;
    private ArrayList<ArrayList<Field>> gameBoard;
    private ArrayList<Color> usedColorByPlayers; // colors of pawns

    Board(int numberOfPlayers) throws IllegalArgumentException { //TODO: Own exception?

        if(numberOfPlayers != 2 && numberOfPlayers != 3 &&
                numberOfPlayers != 4 && numberOfPlayers != 6) {
            throw new IllegalArgumentException();
        }

        this.numberOfPlayers = numberOfPlayers;
        this.gameBoard = assignFieldsToBoard();
        usedColorByPlayers = setColorsOfPlayers();

        assignPawnsToBaseFields();
    }

    /*
    The gameBoard (rectangular 2D array of size 19x19) is like:

    ["null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "RED_", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "RED_", "RED_", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "RED_", "RED_", "RED_", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "RED_", "RED_", "RED_", "RED_", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"],
    ["null", "BLAC", "BLAC", "BLAC", "BLAC", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "GREE", "GREE", "GREE", "GREE", "null", "null", "null", "null", "null"],
    ["null", "null", "BLAC", "BLAC", "BLAC", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "GREE", "GREE", "GREE", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "BLAC", "BLAC", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "GREE", "GREE", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "BLAC", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "GREE", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "ORAN", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "BLUE", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "ORAN", "ORAN", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "BLUE", "BLUE", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "ORAN", "ORAN", "ORAN", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "BLUE", "BLUE", "BLUE", "null", "null"],
    ["null", "null", "null", "null", "null", "ORAN", "ORAN", "ORAN", "ORAN", "EMPT", "EMPT", "EMPT", "EMPT", "EMPT", "BLUE", "BLUE", "BLUE", "BLUE", "null"],
    ["null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "YELL", "YELL", "YELL", "YELL", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "YELL", "YELL", "YELL", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "YELL", "YELL", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "YELL", "null", "null", "null", "null", "null"],
    ["null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null"],

    RED_, BLAC, GREE, ORAN, YELL and BLUE are colors of BaseFields and EMPT is NeutralField.
    Null means that there is no Field.
     */

    // creates an array of fields, pattern is given up
    private ArrayList<ArrayList<Field>> assignFieldsToBoard() {
        ArrayList<ArrayList<Field>> newGameBoard = new ArrayList<>();
        ArrayList<Field> newRow;
        for (int i = 0; i < 19; i++) {
            newRow = new ArrayList<>();
            for(int j = 0; j < 19; j++) {
                if(i < 5 && j > 4 && j < i + 5) {
                    newRow.add(j, new BaseField(Color.Red));
                }
                else if(i >= 5 && i < 9) {
                    if(j > i - 5 && j < 5) {
                        newRow.add(j, new BaseField(Color.Black));
                    }
                    else if(j >= 5 && j < i + 5) {
                        newRow.add(j, new NeutralField());
                    }
                    else if(j >= i + 5 && j < 14) {
                        newRow.add(j, new BaseField(Color.Green));
                    }
                    else {
                        newRow.add(j, null);
                    }
                }
                else if(i == 9 && j > 4 && j < 14) {
                    newRow.add(j, new NeutralField());
                }
                else if(i > 9 && i < 14) {
                    if(j > 4 && j < i - 4) {
                        newRow.add(j, new BaseField(Color.Orange));
                    }
                    else if(j >= i - 4 && j < 14) {
                        newRow.add(j, new NeutralField());
                    }
                    else if(j >= 14 && j < i + 5) {
                        newRow.add(j, new BaseField(Color.Blue));
                    }
                    else {
                        newRow.add(j, null);
                    }
                }
                else if(i >= 14 && j > i - 5 && j < 14) {
                    newRow.add(j, new BaseField(Color.Yellow));
                }
                else {
                    newRow.add(j, null);
                }
            }
            newGameBoard.add(i, newRow);
        }
        return newGameBoard;
    }

    // creates new pawns and places it on appropriate field
    private void assignPawnsToBaseFields() {
        ArrayList<Field> list;
        for(int i = 0; i < 19; i++) {
            list = gameBoard.get(i);
            for(Field f : list) {
                if(f != null && f.getColor() != null && usedColorByPlayers.contains(f.getColor())) {
                    f.setPawn(new Pawn(f.getColor()));
                }
            }
        }
    }

    private ArrayList<Color> setColorsOfPlayers() {
        ArrayList<Color> colors = new ArrayList<>();

        switch(numberOfPlayers) {
            case 2:
                colors.add(Color.Red);
                colors.add(Color.Yellow);
                break;
            case 3:
                colors.add(Color.Red);
                colors.add(Color.Blue);
                colors.add(Color.Orange);
                break;
            case 4:
                colors.add(Color.Green);
                colors.add(Color.Black);
                colors.add(Color.Orange);
                colors.add(Color.Blue);
                break;
            case 6:
                colors.add(Color.Green);
                colors.add(Color.Black);
                colors.add(Color.Orange);
                colors.add(Color.Blue);
                colors.add(Color.Red);
                colors.add(Color.Yellow);
        }

        return colors;
    }

    /**
     * The method moves the pawn into its new field.
     * @param oldPawnPosition previous position of the pawn
     * @param newPawnPosition new position of the pawn
     * @param player reference to player who wants to move the pawn
     * @throws NullFieldException throws if one or both of coordinates point to null
     * @throws WrongFieldStateException throws if there is a problem with pawn
     * @throws WrongMoveException throws if move is incorrect
     * @throws WrongPawnColorException throws if players wants to move somebody's else pawn
     */
    @Override
    public void movePawn(BoardCoordinates oldPawnPosition, BoardCoordinates newPawnPosition, Player player)
            throws NullFieldException, WrongFieldStateException, WrongMoveException, WrongPawnColorException {

        Field newFieldForPawn = gameBoard.get(newPawnPosition.getRow()).get(newPawnPosition.getColumn());
        Field oldFieldForPawn = gameBoard.get(oldPawnPosition.getRow()).get(oldPawnPosition.getColumn());

        if(newFieldForPawn == null || oldFieldForPawn == null) {
            throw new NullFieldException();
        }
        else if(newFieldForPawn.isOccupied() || !oldFieldForPawn.isOccupied()) {
            throw new WrongFieldStateException();
        }
        else if(!oldFieldForPawn.getPawn().getColor().equals(player.getColor())) {
            throw new WrongPawnColorException();
        }
        else if(checkDistanceIsOneField(oldPawnPosition, newPawnPosition)) {
            movePawnToNewField(oldPawnPosition, newPawnPosition);
        }
        else if(checkMultiMoveIsCorrect(oldPawnPosition, newPawnPosition)) {
            movePawnToNewField(oldPawnPosition, newPawnPosition);
        }
        else {
            throw new WrongMoveException();
        }
    }

    // can be use when we know the move is correct
    private void movePawnToNewField(BoardCoordinates oldField, BoardCoordinates newField) {
        gameBoard.get(newField.getRow()).get(newField.getColumn())
                .setPawn(gameBoard.get(oldField.getRow()).get(oldField.getColumn()).getPawn());
        gameBoard.get(oldField.getRow()).get(oldField.getColumn()).setPawn(null);
    }

    // checks whether we move the pawn by one field
    private boolean checkDistanceIsOneField(BoardCoordinates oldField, BoardCoordinates newField) {
        if((Math.abs(oldField.getRow() - newField.getRow()) + Math.abs(oldField.getColumn() - newField.getColumn())) == 1 ||
                ((oldField.getColumn() - newField.getColumn()) == 1 && (oldField.getRow() - newField.getRow()) == 1) ||
                ((oldField.getColumn() - newField.getColumn()) == -1 && (oldField.getRow() - newField.getRow()) == -1)) {
            return true;
        }
        else {
            return false;
        }
    }

    /* The method finds possible ways for the pawn over other pawns,
        there is 6 ways to check:
                       xx
                       xPx
                        xx
        where P - pawn, x - fields where pawn can move to.
        There are two free spaces - top right corner and bottom left corner.
        These two spaces cannot be use, because gameBoard is imagination of real board in different way.
     */
    private ArrayList<BoardCoordinates> checkPossibleWaysForPawn (BoardCoordinates coordinates) {
        ArrayList<BoardCoordinates> possibleWays = new ArrayList<>();
        int X = coordinates.getColumn();
        int Y = coordinates.getRow();

        if(gameBoard.get(Y - 1).get(X) != null && gameBoard.get(Y - 1).get(X).isOccupied() &&
                gameBoard.get(Y - 2).get(X) != null && !gameBoard.get(Y - 2).get(X).isOccupied()) {
            possibleWays.add(new BoardCoordinates(Y - 2, X));
        }

        if(gameBoard.get(Y).get(X + 1) != null && gameBoard.get(Y).get(X + 1).isOccupied() &&
                gameBoard.get(Y).get(X + 2) != null && !gameBoard.get(Y).get(X + 2).isOccupied()) {
            possibleWays.add(new BoardCoordinates(Y, X + 2));
        }

        if(gameBoard.get(Y + 1).get(X + 1) != null && gameBoard.get(Y + 1).get(X + 1).isOccupied() &&
                gameBoard.get(Y + 2).get(X + 2) != null && !gameBoard.get(Y + 2).get(X + 2).isOccupied()) {
            possibleWays.add(new BoardCoordinates(Y + 2, X + 2));
        }

        if(gameBoard.get(Y + 1).get(X) != null && gameBoard.get(Y + 1).get(X).isOccupied() &&
                gameBoard.get(Y + 2).get(X) != null && !gameBoard.get(Y + 2).get(X).isOccupied()) {
            possibleWays.add(new BoardCoordinates(Y + 2, X));
        }

        if(gameBoard.get(Y).get(X - 1) != null && gameBoard.get(Y).get(X  - 1).isOccupied() &&
                gameBoard.get(Y).get(X  - 2) != null && !gameBoard.get(Y).get(X  - 2).isOccupied()) {
            possibleWays.add(new BoardCoordinates(Y, X - 2));
        }

        if(gameBoard.get(Y - 1).get(X - 1) != null && gameBoard.get(Y - 1).get(X - 1).isOccupied() &&
                gameBoard.get(Y - 2).get(X - 2) != null && !gameBoard.get(Y - 2).get(X - 2).isOccupied()) {
            possibleWays.add(new BoardCoordinates(Y - 2, X - 2));
        }

        return possibleWays;
    }

    // checks that the move over other pawns is correct - recursive method
    private boolean checkMultiMoveIsCorrect(BoardCoordinates oldField, BoardCoordinates newField) {
        if(oldField.getRow() == newField.getRow() && oldField.getColumn() == newField.getColumn()) {
            return true;
        }

        ArrayList<BoardCoordinates> ways = checkPossibleWaysForPawn(oldField.getBoardCoordinates());
        boolean isMoveCorrect = false;

        for(BoardCoordinates bc : ways) {
            if(newField.getRow() == bc.getRow() && newField.getColumn() == bc.getColumn() || isMoveCorrect) {
                isMoveCorrect = true;
                break;
            }
            else {
                isMoveCorrect = checkMultiMoveIsCorrect(new BoardCoordinates(bc.getRow(), bc.getColumn()), newField);
            }
        }

        return isMoveCorrect;
    }

    /**
     * The method returns current coordinates of pawns.
     * @return array of coordinates of pawns
     */
    @Override
    public ArrayList<BoardCoordinates> getPawnsCoordinates() {
        ArrayList<BoardCoordinates> pawnesCoordinates = new ArrayList<>();
        Field field;
        for(int i = 0; i < 19; i++) {
            for(int j = 0; j < 19; j++) {
                field = gameBoard.get(i).get(j);
                if(field != null && field.isOccupied()) {
                    pawnesCoordinates.add(new BoardCoordinates(i, j));
                }
            }
        }

        return pawnesCoordinates;
    }

    /**
     * The method returns color of particular pawn.
     * @param pawnPosition coordinates of pawn
     * @return color of pawn
     * @throws WrongFieldStateException throws if there is no pawn on the field (coordinates are wrong)
     */
    @Override
    public Color getPawnColor(BoardCoordinates pawnPosition) throws WrongFieldStateException {
        Field field = gameBoard.get(pawnPosition.getRow()).get(pawnPosition.getColumn());

        if(field == null || !field.isOccupied()) {
            throw new WrongFieldStateException();
        }
        else {
            return field.getPawn().getColor();
        }
    }
}