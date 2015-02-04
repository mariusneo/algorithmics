package org.mg.catcoder.unblockme;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;

import static java.lang.Math.abs;

/**
 * The present algorithm can be used for finding the path within the game "Unblock me".
 * #unblockExitPath(int, int, org.mg.catcoder.unblockme.UnblockMe.Block[], int)
 * <p/>
 * The first levels are used for building reusable methods that can be used in building the
 * algorithm for unblocking the exit path.
 * The methods :
 * <p/>
 * #blocksOverlap(org.mg.catcoder.unblockme.UnblockMe.Block, org.mg.catcoder.unblockme.UnblockMe.Block)
 * #isValidMove(int, int, org.mg.catcoder.unblockme.UnblockMe.Block[], int, int)
 * <p/>
 * can be used for finding whether two blocks overlap, respectively whether the move of a specified block is valid
 * within a given matrix configuration.
 * <p/>
 * With the previously mentioned methods, the problem of finding a solution to unblock a specified prisoner block
 * is done by creating a <b>Breadth-First-Search</b> technique to cover all the possible configurations of the matrix
 * after moving the blocks. By saving the configurations of the matrix and discarding in the future iterations the
 * matrix configurations which are found in the already visited configurations, we avoid analyzing all the
 * subsequent configurations of the matrix.
 * For finding the path needed to unblock the prisoner block, I've used for inspiration a simplified version of the
 * algorithm described here : <a>http://users.softlab.ece.ntua.gr/~ttsiod/unblock.html</a>
 */
public class UnblockMe {

    private static void printMatrix(int rows, int cols, List<Block> blocks) {
        String[][] matrix = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = ".";
            }
        }
        for (Block block : blocks) {
            for (int i = 0; i < block.length; i++) {
                if (block.orientation == Orientation.HORIZONTAL)
                    matrix[rows - block.y][block.x + i - 1] = block.id + "";
                else
                    matrix[rows - block.y - i][block.x - 1] = block.id + "";
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%3s", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static List<Block> cloneList(List<Block> blockList) {
        List<Block> cloneList = new ArrayList<>(blockList.size());
        for (Block block : blockList) {
            try {
                cloneList.add((Block) block.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return cloneList;
    }

    public boolean blocksOverlap(Block block1, Block block2) {
        int x1, y1;
        int x2, y2;
        if (block1.orientation == Orientation.HORIZONTAL) {
            x1 = block1.x + block1.length;
            y1 = block1.y;
        } else {
            x1 = block1.x;
            y1 = block1.y + block1.length;
        }

        if (block2.orientation == Orientation.HORIZONTAL) {
            x2 = block2.x + block2.length;
            y2 = block2.y;
        } else {
            x2 = block2.x;
            y2 = block2.y + block2.length;
        }

        if (block1.orientation == Orientation.HORIZONTAL) {
            if (block2.orientation == Orientation.HORIZONTAL) {
                if ((block1.y == block2.y) &&
                        ((block1.x <= block2.x && x1 > block2.x) || (block1.x < x2 && x1 > x2)))
                    return true;
            } else {
                if ((block1.x <= block2.x && x1 > block2.x) && (block2.y <= block1.y && y2 > block1.y))
                    return true;

            }
        } else {
            if (block2.orientation == Orientation.HORIZONTAL) {
                if ((block1.y <= block2.y && y1 > block2.y) && (block2.x <= block1.x && x2 > block1.x))
                    return true;
            } else {
                if ((block1.x == block2.x) &&
                        ((block1.y <= block2.y && y1 > block2.y) || (block1.y < y2 && y1 > y2)))
                    return true;
            }

        }

        return false;
    }

    public boolean isValidMove(int rows, int cols, Block[] blocks, int moveId, int moveLength) {
        Block moveBlock = Arrays.stream(blocks).filter(b -> b.id == moveId).findFirst().get();

        // check if the movement keeps the block within the matrix borders
        if (moveLength > 0) {
            if (moveBlock.orientation == Orientation.HORIZONTAL) {
                if (moveBlock.x + moveBlock.length + moveLength > cols + 1) return false;
            } else {
                if (moveBlock.y + moveBlock.length + moveLength > rows + 1) return false;
            }
        } else {
            // numbering of the rows and columns starts from 1
            if (moveBlock.orientation == Orientation.HORIZONTAL) {
                if (moveBlock.x + moveLength <= 0) return false;
            } else {
                if (moveBlock.y + moveLength <= 0) return false;
            }
        }

        Block moveBlockWithMoveLength = null;
        if (moveLength > 0) {
            moveBlockWithMoveLength = new Block(moveId, moveBlock.orientation, moveBlock.x, moveBlock.y, moveBlock
                    .length + moveLength);
        } else {
            if (moveBlock.orientation == Orientation.HORIZONTAL) {
                moveBlockWithMoveLength = new Block(moveId, moveBlock.orientation, moveBlock.x + moveLength,
                        moveBlock.y, moveBlock.length + abs(moveLength));
            } else {
                moveBlockWithMoveLength = new Block(moveId, moveBlock.orientation, moveBlock.x, moveBlock.y +
                        moveLength, moveBlock.length + abs(moveLength));
            }
        }

        boolean isValidMove = true;
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            if (block.id != moveBlockWithMoveLength.id) {
                if (blocksOverlap(moveBlockWithMoveLength, block)) {
                    isValidMove = false;
                    break;
                }
            }
        }
        return isValidMove;
    }

    public List<Block> unmoveableBlocks(int rows, int cols, Block[] blocks) {
        List<Block> unmoveableBlocks = new ArrayList<>();
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            if (!isValidMove(rows, cols, blocks, block.id, 1) && !isValidMove(rows, cols, blocks, block.id, -1))
                unmoveableBlocks.add(block);
        }

        unmoveableBlocks.sort((b1, b2) -> Integer.compare(b1.id, b2.id));
        return unmoveableBlocks;
    }

    public List<Move> unblockExitPath(int rows, int cols, Block[] blocks, int selectId) {
        Map<List<Block>, Move> previousMoves = new HashMap<>();
        Queue<BlocksAndMove> queue = new LinkedList<>();
        Set<List<Block>> visited = new HashSet<>();

        List<Block> blockList = new ArrayList<>();
        for (Block b : blocks) {
            blockList.add(b);
        }


        queue.add(new BlocksAndMove(blockList, new Move(-1, 0)));

        while (!queue.isEmpty()) {
            BlocksAndMove currentBlocksAndMove = queue.poll();
            List<Block> currentBlocks = currentBlocksAndMove.blocks;
            Move currentMove = currentBlocksAndMove.move;

            if (visited.contains(currentBlocks))
                continue;
            visited.add(currentBlocks);

            // store previous move for backtracking later
            previousMoves.put(currentBlocks, currentMove);

            Block prisonerBlock = currentBlocks.stream().filter(b -> b.id == selectId).findFirst().get();
            int moveLength = prisonerBlock.orientation == Orientation.HORIZONTAL ?
                    cols + 1 - (prisonerBlock.x + prisonerBlock.length) :
                    rows + 1 - (prisonerBlock.y + prisonerBlock.length);


            Block selectedBlockWithMoveLength = new Block(prisonerBlock.id, prisonerBlock.orientation, prisonerBlock.x,
                    prisonerBlock.y, prisonerBlock.length + moveLength);

            boolean allClear = true;
            for (Block currentBlock : currentBlocks) {
                if (currentBlock.id != prisonerBlock.id && blocksOverlap(selectedBlockWithMoveLength, currentBlock)) {
                    allClear = false;
                    break;
                }
            }

            if (allClear) {
                // solution was found iterate through previous moves to find the solution
                Move move = previousMoves.get(currentBlocks);
                List<Move> solutionMoves = new ArrayList<>();

//                System.out.println(move.blockId + " " + move.length);
//                printMatrix(rows, cols, currentBlocks);
                while (move.blockId != -1) {
                    solutionMoves.add(move);
                    Block currentBlock = null;
                    for (Block block : currentBlocks) {
                        if (block.id == move.blockId) {
                            currentBlock = block;
                            break;
                        }
                    }
                    assert currentBlock != null;
                    if (currentBlock.orientation == Orientation.HORIZONTAL)
                        currentBlock.x -= move.length;
                    else
                        currentBlock.y -= move.length;
                    move = previousMoves.get(currentBlocks);
//                    System.out.println(move.blockId + " " + move.length);
//                    printMatrix(rows, cols, currentBlocks);
                }
                Collections.reverse(solutionMoves);
                return solutionMoves;
            } else {
                // make a BFS for all the possible moves in this configuration of the matrix
                for (Block block : currentBlocks) {
                    int beforeBlockMaxValue, afterBlockMaxValue;
                    if (block.orientation == Orientation.HORIZONTAL) {
                        beforeBlockMaxValue = block.x - 1;
                        afterBlockMaxValue = cols + 1 - (block.x + block.length);
                    } else {
                        beforeBlockMaxValue = block.y;
                        afterBlockMaxValue = rows + 1 - (block.y + block.length);
                    }

                    for (int length = 1; length <= afterBlockMaxValue; length++) {
                        if (!isValidMove(rows, cols, currentBlocks.toArray(new Block[currentBlocks.size()]), block.id,
                                length)) {
                            break;
                        } else {
                            List<Block> newBlockList = cloneList(currentBlocks);
                            Block newBlock = newBlockList.stream().filter(b -> b.id == block.id).findFirst().get();
                            if (newBlock.orientation == Orientation.HORIZONTAL)
                                newBlock.x += length;
                            else
                                newBlock.y += length;
                            queue.add(new BlocksAndMove(newBlockList, new Move(block.id, length)));
                        }
                    }
                    for (int length = 1; length <= beforeBlockMaxValue; length++) {
                        if (!isValidMove(rows, cols, currentBlocks.toArray(new Block[currentBlocks.size()]), block.id,
                                -length)) {
                            break;
                        } else {
                            List<Block> newBlockList = cloneList(currentBlocks);
                            Block newBlock = newBlockList.stream().filter(b -> b.id == block.id).findFirst().get();
                            if (newBlock.orientation == Orientation.HORIZONTAL)
                                newBlock.x += -length;
                            else
                                newBlock.y += -length;
                            queue.add(new BlocksAndMove(newBlockList, new Move(block.id, -length)));
                        }
                    }
                }
            }
        }

        return null;
    }

    @Test
    public void quizOverlapLevel1() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(UnblockMe.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/unblockme/input-level1.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");

                if (st.hasMoreTokens()) {
                    List<Block> blocks = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        int id = Integer.parseInt(st.nextToken());
                        Orientation orientation = Orientation.parse(st.nextToken());
                        int x = Integer.parseInt(st.nextToken());
                        int y = Integer.parseInt(st.nextToken());
                        int length = Integer.parseInt(st.nextToken());

                        blocks.add(new Block(id, orientation, x, y, length));
                    }

                    UnblockMe unblockMe = new UnblockMe();
                    Block block1 = blocks.get(0);
                    Block block2 = blocks.get(1);
                    boolean result = unblockMe.blocksOverlap(block1, block2);
                    System.out.println(result);
                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void quizIsInvalidMove() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(UnblockMe.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/unblockme/input-level2.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");

                if (st.hasMoreTokens()) {
                    int cols = Integer.parseInt(st.nextToken());
                    int rows = Integer.parseInt(st.nextToken());
                    int blocksCount = Integer.parseInt(st.nextToken());
                    List<Block> blocks = new ArrayList<>();
                    int blockIndex = 0;
                    while (blockIndex < blocksCount) {
                        int id = Integer.parseInt(st.nextToken());
                        Orientation orientation = Orientation.parse(st.nextToken());
                        int x = Integer.parseInt(st.nextToken());
                        int y = Integer.parseInt(st.nextToken());
                        int length = Integer.parseInt(st.nextToken());

                        blocks.add(new Block(id, orientation, x, y, length));
                        blockIndex++;
                    }

                    int moveId = Integer.parseInt(st.nextToken());
                    int moveLength = Integer.parseInt(st.nextToken());

                    UnblockMe unblockMe = new UnblockMe();
                    boolean isValidMove = unblockMe.isValidMove(rows, cols, blocks.toArray(new Block[blocks.size()]),
                            moveId, moveLength);
                    System.out.println(!isValidMove);
                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void quizUnmoveableBlocks() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(UnblockMe.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/unblockme/input-level3.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");

                if (st.hasMoreTokens()) {
                    int cols = Integer.parseInt(st.nextToken());
                    int rows = Integer.parseInt(st.nextToken());
                    int blocksCount = Integer.parseInt(st.nextToken());
                    List<Block> blocks = new ArrayList<>();
                    int blockIndex = 0;
                    while (blockIndex < blocksCount) {
                        int id = Integer.parseInt(st.nextToken());
                        Orientation orientation = Orientation.parse(st.nextToken());
                        int x = Integer.parseInt(st.nextToken());
                        int y = Integer.parseInt(st.nextToken());
                        int length = Integer.parseInt(st.nextToken());

                        blocks.add(new Block(id, orientation, x, y, length));
                        blockIndex++;
                    }

                    UnblockMe unblockMe = new UnblockMe();
                    List<Block> unmoveableBlocks = unblockMe.unmoveableBlocks(rows, cols, blocks.toArray(new
                            Block[blocks.size()
                            ]));
                    StringBuilder sb = new StringBuilder();
                    for (Block block : unmoveableBlocks) {
                        if (sb.length() > 0) sb.append(" ");
                        sb.append(block.id);
                    }
                    System.out.println(sb.toString());
                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void quizIncorrectMove() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(UnblockMe.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/unblockme/input-level4.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");

                if (st.hasMoreTokens()) {
                    int cols = Integer.parseInt(st.nextToken());
                    int rows = Integer.parseInt(st.nextToken());
                    int blocksCount = Integer.parseInt(st.nextToken());
                    List<Block> blocksList = new ArrayList<>();
                    int blockIndex = 0;
                    while (blockIndex < blocksCount) {
                        int id = Integer.parseInt(st.nextToken());
                        Orientation orientation = Orientation.parse(st.nextToken());
                        int x = Integer.parseInt(st.nextToken());
                        int y = Integer.parseInt(st.nextToken());
                        int length = Integer.parseInt(st.nextToken());

                        blocksList.add(new Block(id, orientation, x, y, length));
                        blockIndex++;
                    }
                    UnblockMe unblockMe = new UnblockMe();

                    int movesCount = Integer.parseInt(st.nextToken());

                    Block[] blocks = blocksList.toArray(new Block[blocksList.size()]);
                    int moveIndex = 0;
                    while (moveIndex < movesCount) {
                        int moveId = Integer.parseInt(st.nextToken());
                        int moveLength = Integer.parseInt(st.nextToken());

                        Block moveBlock = Arrays.stream(blocks).filter(b -> b.id == moveId).findFirst().get();

                        boolean isValidMove = unblockMe.isValidMove(rows, cols,
                                blocks, moveId, moveLength);
                        if (!isValidMove) break;
                        if (moveBlock.orientation == Orientation.HORIZONTAL) {
                            moveBlock.x += moveLength;
                        } else {
                            moveBlock.y += moveLength;
                        }
                        moveIndex++;
                    }

                    System.out.println(moveIndex);
                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void quizFindExitPath() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(UnblockMe.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/unblockme/input-level5.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");

                if (st.hasMoreTokens()) {
                    int cols = Integer.parseInt(st.nextToken());
                    int rows = Integer.parseInt(st.nextToken());
                    int blocksCount = Integer.parseInt(st.nextToken());
                    List<Block> blocks = new ArrayList<>();
                    int blockIndex = 0;
                    while (blockIndex < blocksCount) {
                        int id = Integer.parseInt(st.nextToken());
                        Orientation orientation = Orientation.parse(st.nextToken());
                        int x = Integer.parseInt(st.nextToken());
                        int y = Integer.parseInt(st.nextToken());
                        int length = Integer.parseInt(st.nextToken());

                        blocks.add(new Block(id, orientation, x, y, length));
                        blockIndex++;
                    }

                    UnblockMe unblockMe = new UnblockMe();
                    List<Move> solutionMoves = unblockMe.unblockExitPath(rows, cols,
                            blocks.toArray(new Block[blocks.size()]), 0);
                    if (solutionMoves == null) {
                        System.out.println("NO SOLUTION");
                    } else {
                        for (Move move : solutionMoves) {
                            System.out.print(move.blockId + " " + move.length + " ");
                        }
                        System.out.println();
                    }

                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    private enum Orientation {
        HORIZONTAL,
        VERTICAL;

        public static Orientation parse(String value) {
            if ("h".equals(value)) {
                return HORIZONTAL;
            } else if ("v".equals(value)) {
                return VERTICAL;
            }

            return null;
        }
    }

    private class Block implements Cloneable {
        private int id;
        private Orientation orientation;
        private int x, y;
        private int length;

        public Block(int id, Orientation orientation, int x, int y, int length) {
            this.id = id;
            this.orientation = orientation;
            this.x = x;
            this.y = y;
            this.length = length;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Block block = (Block) o;

            if (id != block.id) return false;
            if (length != block.length) return false;
            if (x != block.x) return false;
            if (y != block.y) return false;
            if (orientation != block.orientation) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + orientation.hashCode();
            result = 31 * result + x;
            result = 31 * result + y;
            result = 31 * result + length;
            return result;
        }

        @Override
        public String toString() {
            return "Block{" +
                    "id=" + id +
                    ", orientation=" + orientation +
                    ", x=" + x +
                    ", y=" + y +
                    ", length=" + length +
                    '}';
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    private class Move {
        private int blockId;
        private int length;

        public Move(int blockId, int length) {
            this.blockId = blockId;
            this.length = length;
        }
    }

    private class BlocksAndMove {
        private List<Block> blocks;
        private Move move;

        public BlocksAndMove(List<Block> blocks, Move move) {
            this.blocks = blocks;
            this.move = move;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BlocksAndMove that = (BlocksAndMove) o;

            if (blocks != null ? !blocks.equals(that.blocks) : that.blocks != null) return false;
            if (move != null ? !move.equals(that.move) : that.move != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = blocks != null ? blocks.hashCode() : 0;
            result = 31 * result + (move != null ? move.hashCode() : 0);
            return result;
        }
    }
}