import java.util.Random;
import java.util.Scanner;

public class Main {
    public static char map[][];


    public static final int SIZE = 3;
    public static final int DOTS_TO_WIN = 3;

    public static final char DOT_EMPTY = '•';
    public static final char DOT_X = 'X';
    public static final char DOT_O = '0';

    static final Scanner sc = new Scanner(System.in);
    static final Random random = new Random();


    public static void main(String[] args) {

        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            int result = checkWinNew();
            if (result == DOT_X+DOTS_TO_WIN) {
                System.out.println("Вы одолели бездушную машину!");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья");
                break;
            }

            aiTurn();
            printMap();
            if (result == DOT_O+DOTS_TO_WIN) {
                System.out.println("Бездушная машина выиграла =(");
                break;
            }
            if (isFull()) {
                System.out.println("Ничья");
                break;
            }
        }

    }

    public static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%d ", i + 1);
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%d ", i + 1);
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%c ", map[i][j]);
            }
            System.out.println();
        }
    }

    public static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты Х У");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;

        } while (!isCellValid(x, y));
        map[y][x] = DOT_X;
    }

    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE)
            return false;
        if (map[y][x] == DOT_EMPTY) return true;
        return false;
    }

    public static boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void aiTurn() {
        int x, y;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(x, y));
        System.out.println("Бездушная машина сделала ход в точку " + ( x + 1 ) + " " + ( y + 1 ));
        map[y][x] = DOT_O;
    }

 /*  public static boolean checkWin(char c) {
        if (map[0][0] == c && map[0][1] == c && map[0][2] == c) {
            return true;
        }
        if (map[1][0] == c && map[1][1] == c && map[1][2] == c) {
            return true;
        }
        if (map[2][0] == c && map[2][1] == c && map[2][2] == c) {
            return true;
        }

        if (map[0][0] == c && map[1][0] == c && map[2][0] == c) {
            return true;
        }
        if (map[0][1] == c && map[1][1] == c && map[2][1] == c) {
            return true;
        }
        if (map[0][2] == c && map[1][2] == c && map[2][2] == c) {
            return true;
        }

        if (map[0][0] == c && map[1][1] == c && map[2][2] == c) {
            return true;
        }
        if (map[0][2] == c && map[1][1] == c && map[2][0] == c) {
            return true;
        }

        return false;
    } */

    // Задание 2. Переделываем логику победы

    //Вариант 1. Не работает

    /* public static boolean checkDiagonalWin(char c) {
        boolean diagonal = true;

        for (int i = 0; i < SIZE; i++ ) {
            for (int j = 0; j < SIZE; j++) {
                diagonal = diagonal & ( map[i][j] == c );
            }
        }
        if (diagonal) return true;
        return false;

    }

    public static boolean checkLineWin(char c) {
        boolean x, y;
        x = true;
        y = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                x = x & ( map[i][j] == c );
                y = y & ( map[i][j] == c );
            }
        }
        if (x || y) return true;
        return false;
    } */


    // Вариант 2. Тоже не работает корректно

    public static int checkWinNew() {
        int diag = 0;
        int diag2 = 0;
        for (int i = 0; i < SIZE; i++) {
            diag += map[i][i];
            diag2 += map[i][2 - i];
        }
        if (diag == DOT_O * DOTS_TO_WIN || diag == DOT_X * DOTS_TO_WIN) {
            return diag;
        }
        if (diag2 == DOT_O * DOTS_TO_WIN || diag2 == DOT_X * DOTS_TO_WIN) {
            return diag2;
        }

        int check_i, check_j;
        boolean hasEmpty = false;

        for (int i = 0; i < SIZE; i++) {
            check_i = 0;
            check_j = 0;
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == 0) {
                    hasEmpty = true;
                }
                check_i += map[i][j];
                check_j += map[i][j];
            }
            if (check_i == DOT_O + DOTS_TO_WIN || check_i == DOT_X + DOTS_TO_WIN) {
                return check_i;
            }
            if (check_j == DOT_O + DOTS_TO_WIN || check_j == DOT_X + DOTS_TO_WIN) {
                return check_j;
            }
        }
        if (hasEmpty) return 0;
        else return -1;
    }

}
