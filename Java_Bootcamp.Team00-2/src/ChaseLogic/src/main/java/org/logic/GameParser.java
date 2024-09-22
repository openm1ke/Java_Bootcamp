package org.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.util.*;

public class GameParser {
    protected int row, col;
    protected int[][] field;
    protected Player player;
    protected Door door;
    protected ArrayList<Enemy> enemy;

    protected ArrayList<Wall> walls;
    protected int enemiesCount = 3;
    protected int wallsCount = 4;
    protected Parser parse;
    protected Parametrs parametrs;

    protected String lastError = null;

    private static final double MAX_FULLNESS_PERCENTAGE = 0.75;
    private static final int LEFT = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;
    private static final int UP = 5;
    private static final int SKIP = 8;
    private static final int EXITGAME = 9;

    enum GAME_SUBJECTS {
        SPACE(" ", 0, "YELLOW"),
        PLAYER("@", 1, "GREEN"),
        DOOR("D", 2, "BLUE"),
        ENEMY("E", 3, "RED"),
        WALL("#", 8, "MAGENTA"),
        BORDER("*", 9, "CYAN");

        private String s;
        private String color;
        private final int ordinal;

        GAME_SUBJECTS(String s, int ordinal, String color) {
            this.s = s;
            this.ordinal = ordinal;
            this.color = color;
        }

        public String getValue() {
            return s;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public void setValue(String s, String color) {
            if(s != null) {
                this.s = s;
            }

            if(color != null) {
                this.color = color;
            }
        }
    }

    private static final String GAME_PROMPT = "Введите направление движения (" + LEFT + " - влево, " + DOWN
            + " - вниз, "
            + RIGHT + " - вправо, " + UP + " - вверх, " + SKIP + " - пропустить ход, " + EXITGAME
            + " - выход из игры): ";

    private static final String WRONG_INPUT = "Некорректный ввод. Попробуйте еще раз.";
    private static final String CANT_MOVE = "Невозможно переместиться в этом направлении.";
    private static final String SKIP_MOVE = "Пропуск хода.";
    private static final String GAME_EXIT = "Игра завершена.";
    private static final String GAME_WIN = "Вы выиграли! :)";
    private static final String GAME_LOSE = "Вы проиграли :(";

    class Player extends Point {

        public Player(int row, int col) {
            super(row, col);
        }
    }

    class Door extends Point {

        public Door(int row, int col) {
            super(row, col);
        }
    }

    class Enemy extends Point {

        public Enemy(int row, int col) {
            super(row, col);
        }
    }

    class Wall extends Point {

        public Wall(int row, int col) {
            super(row, col);
        }
    }
    public GameParser(int row, int col) {
        this.row = row;
        this.col = col;
        field = new int[row][col];
        enemy = new ArrayList<>();
        walls = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                field[i][j] = GAME_SUBJECTS.SPACE.getOrdinal();
            }
        }
    }

    public GameParser(Parser parse, Parametrs parametrs) {
        this(parse.size, parse.size);
        this.parse = parse;
        this.parametrs = parametrs;
    }

    private void loadSettings() {
        if(parametrs != null) {
            GAME_SUBJECTS.SPACE.setValue(parametrs.empty, parametrs.colorEmpty);
            GAME_SUBJECTS.PLAYER.setValue(parametrs.player, parametrs.colorPlayer);
            GAME_SUBJECTS.DOOR.setValue(parametrs.goal, parametrs.colorGoal);
            GAME_SUBJECTS.ENEMY.setValue(parametrs.enemy, parametrs.colorEnemy);
            GAME_SUBJECTS.WALL.setValue(parametrs.wall, parametrs.colorWall);
            if(parametrs.border != null) {
                GAME_SUBJECTS.BORDER.setValue(parametrs.border, parametrs.colorWall);
            } else {
                GAME_SUBJECTS.BORDER.setValue(GAME_SUBJECTS.BORDER.getValue(), parametrs.colorWall);
            }
        }

        if(parse != null) {
            this.enemiesCount = parse.enemiesCount;
            this.wallsCount = parse.wallsCount;

            double currentFill = checkFillFieldPercentage();
            if(currentFill > MAX_FULLNESS_PERCENTAGE) {
                System.out.println("Слишком много препятствий. попробуйте увеличить размер поля. (" + currentFill + "), макс. " + MAX_FULLNESS_PERCENTAGE);
                System.exit(1);
            }
        }

    }

    private double checkFillFieldPercentage() {
        int fieldsCount = row * col;
        int totalObjects = enemiesCount + wallsCount + 2; // (player and door)
        return totalObjects / (double) fieldsCount;
    }

    private void viewField() {
        if(parametrs != null && !parametrs.debug) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        if(lastError != null) {
            System.out.println(lastError);
            lastError = null;
        }

        ColoredPrinter printer = new ColoredPrinter();

        for (int i = -1; i <= row; i++) {
            for (int j = -1; j <= col; j++) {
                if(i < 0 || j < 0 || i >= row || j >= col) {
                    printer.print(GAME_SUBJECTS.BORDER.getValue(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(GAME_SUBJECTS.BORDER.color));
                    continue;
                }
                for (GAME_SUBJECTS subject : GAME_SUBJECTS.values()) {
                    if (field[i][j] == subject.getOrdinal()) {
                        printer.print(subject.getValue(), Ansi.Attribute.NONE, Ansi.FColor.NONE, Ansi.BColor.valueOf(subject.color));
                        break;
                    }
                }
            }
            System.out.println();
        }
    }

    public void setPlayer(int row, int col) throws AddPointException {
        try {
            this.player = new Player(row, col);
            field[row][col] = GAME_SUBJECTS.PLAYER.getOrdinal();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AddPointException("Неверные координаты точки для создания игрока.");
        }
    }

    public void setDoor(int row, int col) throws AddPointException {
        try {
            this.door = new Door(row, col);
            field[row][col] = GAME_SUBJECTS.DOOR.getOrdinal();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AddPointException("Неверные координаты точки для создания двери.");
        }
    }

    public void setEnemy(int row, int col) throws AddPointException {
        try {
            field[row][col] = GAME_SUBJECTS.ENEMY.getOrdinal();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AddPointException("Неверные координаты точки для создания врага.");
        }
        enemy.add(new Enemy(row, col));
    }

    public void setEnemy(List<Point> points) {
        for (Point point : points) {
            setEnemy(point.row, point.col);
        }
    }

    public void setWall(int row, int col) throws AddPointException {
        try {
            field[row][col] = GAME_SUBJECTS.WALL.getOrdinal();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AddPointException("Неверные координаты точки для создания стены.");
        }
        walls.add(new Wall(row, col));
    }

    public void setWall(List<Point> points) {
        for (Point point : points) {
            setWall(point.row, point.col);
        }
    }

    private void moveEnemy() {

        for (Enemy currentEnemy : enemy) {
            int prevEnemyRow = currentEnemy.row;
            int prevEnemyCol = currentEnemy.col;
            int playerRow = player.row;
            int playerCol = player.col;

            int dx = Integer.compare(playerRow, prevEnemyRow);
            int dy = Integer.compare(playerCol, prevEnemyCol);

            // Новые координаты врага
            int newRow = prevEnemyRow;
            int newCol = prevEnemyCol;

            if(field[prevEnemyRow + dx][prevEnemyCol] <= 1) {
                newRow += dx;
            } else if (field[prevEnemyRow][prevEnemyCol + dy] <= 1) {
                newCol += dy;
            }

            if(parametrs != null && parametrs.debug) {
                Scanner scanner = new Scanner(System.in);
                int confirm = 0;
                try {
                    System.out.println("Confirm enemy move by entering 8: From [" + prevEnemyRow + ", " + prevEnemyCol + "] -> To [" + newRow + ", " + newCol + "].");
                    confirm = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Wrong input");
                }
                if(confirm == 8) {
                    moveEnemy(currentEnemy, newRow, newCol);
                } else {
                    System.out.println("Enemy move not confirmed");
                }
            } else {
                moveEnemy(currentEnemy, newRow, newCol);
            }
        }
    }

    private void moveEnemy(Enemy currentEnemy, int newRow, int newCol) {
        if (newRow >= 0 && newRow < row && newCol >= 0 && newCol < col && field[newRow][newCol] <= 1) {
            // Обновляем положение врага
            field[currentEnemy.row][currentEnemy.col] = GAME_SUBJECTS.SPACE.getOrdinal();
            currentEnemy.row = newRow;
            currentEnemy.col = newCol;
            field[newRow][newCol] = GAME_SUBJECTS.ENEMY.getOrdinal();
        } else {
            System.out.println("Enemy can't move to [" + newRow + ", " + newCol + "]");
        }
    }

    private boolean movePlayer(int moveCode) {
        int prevPlayerRow = player.row;
        int prevPlayerCol = player.col;
        int newRow = prevPlayerRow;
        int newCol = prevPlayerCol;

        // Перемещение игрока в зависимости от выбранного направления
        switch (moveCode) {
            case LEFT:
                newCol--;
                break;
            case DOWN:
                newRow++;
                break;
            case RIGHT:
                newCol++;
                break;
            case UP:
                newRow--;
                break;
            case SKIP:
                lastError = SKIP_MOVE;
                break;
            default:
                lastError = WRONG_INPUT;
                return false;
        }

        // Проверка на выход за пределы поля
        if (newRow >= 0 && newRow < row && newCol >= 0 && newCol < col && field[newRow][newCol] <= 2) {
            field[prevPlayerRow][prevPlayerCol] = GAME_SUBJECTS.SPACE.getOrdinal();
            player.row = newRow;
            player.col = newCol;
            field[newRow][newCol] = GAME_SUBJECTS.PLAYER.getOrdinal();
            return true;
        }

        lastError = CANT_MOVE;
        return false;
    }

    private boolean canMovePlayer(Player player) {
        if(player.row + 1 < row && field[player.row + 1][player.col] <= 2) {
            return true;
        }
        if(player.row - 1 >= 0 && field[player.row - 1][player.col] <= 2) {
            return true;
        }
        if(player.col + 1 < col && field[player.row][player.col + 1] <= 2) {
            return true;
        }
        if(player.col - 1 >= 0 && field[player.row][player.col - 1] <= 2) {
            return true;
        }
        return false;
    }
    private void checkGameEnd() {
        if (player.row == door.row && player.col == door.col) {
            viewField();
            System.out.println(GAME_WIN);
            System.exit(0);
        }

        if(!canMovePlayer(player)) {
            viewField();
            System.out.println(GAME_LOSE);
            System.exit(0);
        }

        for (Enemy currentEnemy : enemy) {
            if (player.row == currentEnemy.row && player.col == currentEnemy.col) {
                viewField();
                System.out.println(GAME_LOSE);
                System.exit(0);
            }
        }
    }

    private boolean checkPathExist(Point start, Point end) {
        PathFinder maze = new PathFinder(field);
        List<Point> path = maze.getPath(start, end);
        return !path.isEmpty();
    }

    private void removeSubjectFromField(Point point) {
        field[point.row][point.col] = GAME_SUBJECTS.SPACE.getOrdinal();
    }
    private void fillField() {
        randomizeField(GAME_SUBJECTS.PLAYER, 1);
        randomizeField(GAME_SUBJECTS.DOOR, 1);

        for(int i = 0; i < wallsCount; i++) {
            randomizeField(GAME_SUBJECTS.WALL, 1);
            // будем ставить и разрушать стены пока не найдем рабочий путь от игрока до двери
            while(!checkPathExist(door.getPoint(), player.getPoint())) {
                Wall lastWall = walls.remove(walls.size() - 1);
                removeSubjectFromField(lastWall.getPoint());
            }
        }

        for(int i = 0; i < enemiesCount; i++) {
            randomizeField(GAME_SUBJECTS.ENEMY, 1);
//            while(!checkPathExist(door.getPoint(), player.getPoint())) {
//                Enemy lastEnemy = enemy.remove(enemy.size() - 1);
//                removeSubjectFromField(lastEnemy.getPoint());
//                randomizeField(GAME_SUBJECTS.ENEMY, 1);
//            }
//            не будем пытаться уместить всех врагов, т.к. может произойти вот такая ситуация
//            но из-за такого способа может не быть выигрыша
//            1 0 0 0
//            1 1 1 0
//            0 0 1 0
//            1 1 1 1
//
//            8 2 0 0
//            8 8 3 0
//            1 0 3 0
//            8 8 8 8

        }
    }

    private void randomizeField(GAME_SUBJECTS subject, int count) {
        Random random = new Random();
        int i = 0;
        for (i = 0; i < count; i++) {
            int r = random.nextInt(row);
            int c = random.nextInt(col);
            if (field[r][c] == GAME_SUBJECTS.SPACE.getOrdinal()) {
                field[r][c] = subject.getOrdinal();
            } else {
                i--;
                continue;
            }

            switch (subject) {
                case PLAYER:
                    setPlayer(r, c);
                    break;
                case DOOR:
                    setDoor(r, c);
                    break;
                case WALL:
                    setWall(r, c);
                    break;
                case ENEMY:
                    setEnemy(r, c);
                    break;
                default:
                    break;
            }
        }

    }
    public void gameCycle() {

        loadSettings();

        fillField();
        // Игровой цикл
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                viewField();
                System.out.println(GAME_PROMPT);
                int moveCode = 0;
                try {
                    moveCode = scanner.nextInt();
                } catch (InputMismatchException e) {
                    lastError = WRONG_INPUT;
                    scanner.next();
                }
                if (moveCode == EXITGAME) {
                    System.out.println(GAME_EXIT);
                    lastError = null;
                    break;
                }
                // Проверка на корректность ввода
                if (!movePlayer(moveCode))
                    continue;

                // Движение врага

                moveEnemy();
                checkGameEnd();
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Hello World!");
        GameParser gameParser = new GameParser(10, 10);
        gameParser.setPlayer(4, 4);
        gameParser.setDoor(8, 8);

        ArrayList<Point> enemyPoints = new ArrayList<>(
                Arrays.asList(
                        new Point(8, 1),
                        new Point(1, 1),
                        new Point(2, 7)
                        ));

        gameParser.setEnemy(enemyPoints);

        ArrayList<Point> wallPoints = new ArrayList<>(
                Arrays.asList(
                        // new Point(7, 1),
                        new Point(8, 2),
                        new Point(7, 2),
                        new Point(1, 5),
                        new Point(3, 8),
                        new Point(3, 7)));

        gameParser.setWall(wallPoints);
        gameParser.gameCycle();
    }
}
