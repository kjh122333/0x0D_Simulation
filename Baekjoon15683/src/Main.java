import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int N, M, answer;
    private static int[][] room;
    private static ArrayList<CCTV> cctvList;
    // 1번 : RIGHT -> DOWN -> LEFT -> UP
    // 2번 : LEFT, RIGHT -> UP, DOWN -> LEFT, RIGHT -> UP, DOWN
    // 3번 : UP, RIGHT -> RIGHT, DOWN -> DOWN, LEFT -> LEFT, UP
    // 4번 : UP, LEFT, RIGHT -> UP, RIGHT, DOWN -> RIGHT, DOWN, LEFT -> DOWN, LEFT, UP
    private static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, WALL = 6, CAN_SEE = 9;
    private static int[][][] d = {{}, {{RIGHT},{DOWN},{LEFT},{UP}}, {{LEFT, RIGHT}, {UP, DOWN}, {LEFT, RIGHT}, {UP,DOWN}}, {{UP,RIGHT}, {RIGHT, DOWN}, {DOWN, LEFT}, {LEFT, UP}}, {{UP,LEFT,RIGHT},{ UP,RIGHT,DOWN},{RIGHT,DOWN,LEFT},{DOWN,LEFT,UP}}};

    private static class CCTV {
        int x, y, type;

        public CCTV(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }

    public static void main(String[] args) throws IOException {
        input();
        solve();
    }

    private static void input() throws IOException {
        StringTokenizer token = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(token.nextToken());
        M = Integer.parseInt(token.nextToken());

        room = new int[N][M];
        answer = Integer.MAX_VALUE;
        cctvList = new ArrayList<>();

        for(int i = 0 ; i < N; i++) {
            StringTokenizer line = new StringTokenizer(reader.readLine());
            for(int j = 0 ; j < M; j++) {
                room[i][j] = Integer.parseInt(line.nextToken());
                if(room[i][j] > 0 && room[i][j] < 6) {
                    cctvList.add(new CCTV(i,j, room[i][j]));
                }
            }
        }

        ArrayList<CCTV> newCCTV = new ArrayList<>();
        for(CCTV c : cctvList) {
            if(c.type == 5) {
                show(room, c.x, c.y, UP);
                show(room, c.x, c.y, DOWN);
                show(room, c.x, c.y, RIGHT);
                show(room, c.x, c.y, LEFT);
            } else {
                newCCTV.add(c);
            }
        }
        cctvList = newCCTV;
    }

    private static int getEdgeCount(int[][] room) {
        int sum = 0;
        for(int i = 0 ; i < N; i++) {
            for(int j = 0 ; j < M; j++) {
                if(room[i][j] == 0) sum++;
            }
        }
        return sum;
    }

    private static int[][] copyRoom(int[][] room) {
        int[][] result = new int[N][M];
        for(int i = 0 ; i < N; i++) {
            result[i] = room[i].clone();
        }
        return result;
    }


    private static void show(int[][] room, int cctvX, int cctvY, int showDir) {
        if(showDir == UP) {
            for(int x = cctvX; x >= 0; x--) {
                if(room[x][cctvY] == WALL) break;
                if(room[x][cctvY] == 0 || room[x][cctvY] == CAN_SEE) {
                    room[x][cctvY] = CAN_SEE;
                }
            }
        } else if(showDir == DOWN) {
            for(int x = cctvX; x < N; x++) {
                if(room[x][cctvY] == WALL) break;
                if(room[x][cctvY] == 0 || room[x][cctvY] == CAN_SEE) {
                    room[x][cctvY] = CAN_SEE;
                }
            }
        }else if(showDir == LEFT) {
            for(int y = cctvY; y >= 0; y--) {
                if(room[cctvX][y] == WALL) break;
                if(room[cctvX][y] == 0 ||room[cctvX][y] == CAN_SEE) {
                    room[cctvX][y] = CAN_SEE;
                }
            }
        }else if(showDir == RIGHT) {
            for(int y = cctvY; y < M; y++) {
                if(room[cctvX][y] == WALL) break;
                if(room[cctvX][y] == 0 ||room[cctvX][y] == CAN_SEE) {
                    room[cctvX][y] = CAN_SEE;
                }
            }
        }
    }

    private static void dfs(int idx, int[][] room) {
        if(idx == cctvList.size()) {
            answer = Math.min(answer, getEdgeCount(room));
            return;
        }
        CCTV cctv = cctvList.get(idx);
        int x = cctv.x;
        int y = cctv.y;
        int[][] tempRoom = new int[N][M];

        for(int i = 0 ; i < 4 ; i++) {
            int[] next = d[cctv.type][i];
            tempRoom = copyRoom(room);
            for(int j = 0; j < next.length; j++) {
                show(tempRoom, x, y, next[j]);
            }
            dfs(idx + 1, tempRoom);
        }
    }

    private static void solve() throws IOException {
        dfs(0, room);
        System.out.println(answer);
    }
}