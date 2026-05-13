package utils;

import model.Cell;
import model.GameBoard;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //这是为了2折准备的，就是说在各个方向寻找空格
    public static List<Cell> getReachablePointsInFourDirections(GameBoard gameBoard, Position posA) {
        List<Cell> res = new ArrayList<>();
        for (int i = posA.getRow() + 1; i < gameBoard.getRowCnt(); i++) {
            if (gameBoard.getCell(i, posA.getCol()).isEmpty()) {
                res.add(gameBoard.getCell(i, posA.getCol()));
            } else {
                break;
            }
        }
        for (int i = posA.getRow() - 1; i >= 0; i--) {
            if (gameBoard.getCell(i, posA.getCol()).isEmpty()) {
                res.add(gameBoard.getCell(i, posA.getCol()));
            } else {
                break;
            }
        }
        for (int i = posA.getCol() + 1; i < gameBoard.getColCnt(); i++) {
            if (gameBoard.getCell(posA.getRow(), i).isEmpty()) {
                res.add(gameBoard.getCell(posA.getRow(), i));
            } else {
                break;
            }
        }
        for (int i = posA.getCol() - 1; i >= 0; i--) {
            if (gameBoard.getCell(posA.getRow(), i).isEmpty()) {
                res.add(gameBoard.getCell(posA.getRow(), i));
            } else {
                break;
            }
        }
        return res;
    }
    public static boolean findZeroTurn(GameBoard gameBoard, Position posA, Position posB) {
        // 同行判断
        if (posA.getRow() == posB.getRow()) {
            int minCol = Math.min(posA.getCol(), posB.getCol());
            int maxCol = Math.max(posA.getCol(), posB.getCol());
            for (int col = minCol + 1; col < maxCol; col++) {
                if (!gameBoard.getCell(posA.getRow(), col).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        // 同列判断
        if (posA.getCol() == posB.getCol()) {
            int minRow = Math.min(posA.getRow(), posB.getRow());
            int maxRow = Math.max(posA.getRow(), posB.getRow());
            for (int row = minRow + 1; row < maxRow; row++) {
                if (!gameBoard.getCell(row, posA.getCol()).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        // 既不同行也不同列，无法0折连接
        return false;
    }
    //上一段是AI写的！！！等待修改
    /*public static boolean findZeroTurn(GameBoard gameBoard, Position posA, Position posB) {
        boolean tmpRes0 = true;
        //同一个column
        if (posA.getCol() == posB.getCol()) {
            int smallLine = Math.min(posA.getRow(), posB.getRow());
            int largeLine = Math.max(posA.getRow(), posB.getRow());
            for (int i = smallLine + 1; i < largeLine - 1; i++) {
                if (!gameBoard.getCell(i, posA.getCol()).isEmpty()) {
                    tmpRes0 = false;
                    break;
                }
            }
            if (tmpRes0) {
                return true;
            }
        }
        //同一个row
        if (posA.getRow() == posB.getRow()) {
            int smallCol = Math.min(posA.getCol(), posB.getCol());
            int largeCol = Math.max(posA.getCol(), posB.getCol());
            for (int i = smallCol + 1; i < largeCol - 1; i++) {
                if (!gameBoard.getCell(posA.getRow(), i).isEmpty()) {
                    tmpRes0 = false;
                    break;
                }
            }
            if (tmpRes0) {
                return true;
            }
        }
        return false;
    }*/
    public static boolean findOneTurn(GameBoard gameBoard, Position posA, Position posB) {
        if (posA.getCol() != posB.getCol() && posA.getRow() != posB.getCol()) {
            Position cornerPoint1 = new Position(posA.getRow(), posB.getCol());
            Position cornerPoint2 = new Position(posB.getRow(), posA.getCol());
            if (findZeroTurn(gameBoard, posA, cornerPoint1) && findZeroTurn(gameBoard, posB, cornerPoint1)) {
                return true;
            }
            if (findZeroTurn(gameBoard, posA, cornerPoint2) && findZeroTurn(gameBoard, posB, cornerPoint2)) {
                return true;
            }
        }
        return false;
    }
    public static boolean findTwoTurn(GameBoard gameBoard, Position posA, Position posB) {
        List<Cell> reachablePoints = getReachablePointsInFourDirections(gameBoard, posA);
        for (Cell c: reachablePoints) {
            if (findOneTurn(gameBoard, c.getPos(), posB)) {
                return true;
            }
        }
        return false;
    }

        public static boolean canLinkAB(GameBoard gameBoard, Position posA, Position posB){
        if (findZeroTurn(gameBoard, posA, posB)) {
            return true;
        }
        // 判断1折，检查两个拐点
        if (findOneTurn(gameBoard, posA, posB)) {
            return true;
        }
        // 判断2折
        if (findTwoTurn(gameBoard, posA, posB)) {
            return true;
        }
        return false;
    }
}
