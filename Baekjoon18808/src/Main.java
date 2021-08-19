import java.io.*;

class Sticker {
    private int y, x;
    private int[][] shape;

    public Sticker(){ }

    public Sticker(int y, int x, int[][] shape){
        this.y = y;
        this.x = x;
        this.shape = shape;
    }
}

public class Main {

    static final int MAX_VALUE = 40;
    static int N, M, K;
    static int[][] notebook = new int[MAX_VALUE][MAX_VALUE];
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input;

        input = reader.readLine().split(" ");
        N = Integer.parseInt(input[0]); // 세로 : N(1 ≤ N ≤ 40)
        M = Integer.parseInt(input[1]); // 가로 : M(1 ≤ M ≤ 40)
        K = Integer.parseInt(input[2]); // 스티커의 개수 : K(1 ≤ K ≤ 100)

        for(int a=0; a<MAX_VALUE; a++){
            for(int b=0; b<MAX_VALUE; b++){
                if (a>=N && b>=M) {
                    notebook[a][b] = -999;
                } 
            }
        }

        Sticker[] arrayStickers = new Sticker[K];
        for(int a=0; a<K; a++){
            input = reader.readLine().split(" ");
            int y = Integer.parseInt(input[0]);
            int x = Integer.parseInt(input[1]);
            int[][] shape = new int[y][x];
            for(int b=0; b<y; b++){
                input = reader.readLine().split(" ");
                for(int c=0; c<input.length; c++){
                    shape[b][c] = Integer.parseInt(input[c]);
                }
            }
            arrayStickers[a] = new Sticker(y, x, shape);
        }
        

        // (1) 최우선 붙이기는 최상단 좌측부터 > 오른쪽으로 > 밑으로
        // (2) 붙일수 없을때 시도하는 액션
        // 1. 시계방향으로 스티커를 90도씩 돌린다.
        // 2. 스티커를 이동시킨다.(좌측에서 우측으로, 상단에서 하단으로)
        // 3. 현재 노트북의 붙일수 있는 영역 크기 확인, 현재 스티커크기보다 작으면 종료(종료 = 다음 스티커가 있으면 진행, 없으면 종료 >>> 1인 영역이 "정답")
        // (!!!)
        // a. 붙일수없을때, 스티커를 돌려야한다면 해당 모양을 어떻게... tmp? 모르곘음...
        // b. 모양이 울퉁불퉁? 한데 2번째 스티커부터 해당 값을 어떻게 비교해?ㄴ
    }
}
