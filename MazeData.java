import java.io.*;

/**
 * 迷路を表すクラス
 */
public class MazeData {
	
  /**
   * 迷路を表すオブジェクトを生成する
   * @param mazeFile 迷路データのファイル名
   */
  public MazeData(String mazeFile)
  {
    try {
      // ファイルを開く
      BufferedReader in = new BufferedReader(new FileReader(mazeFile));

      // 迷路のサイズを取得する
      String line;
      while ((line = in.readLine()) != null) {
        width = Math.max(width, line.length());
        height++;
      }

      // 迷路配列を生成する
      data = new int[width][height];

      // ファイルを開き直す
      in.close();
      in = new BufferedReader(new FileReader(mazeFile));

      int x = 0;
      int y = 0;
      while ((line = in.readLine()) != null) {
        for (x=0; x < line.length(); x++) {
          switch (line.charAt(x)) {
          case '0': data[x][y] = SPACE; break;
          case '1': data[x][y] = BLOCK; break;
          case 'S': data[x][y] = START; sx = x; sy = y; break;
          case 'G': data[x][y] = GOAL;  gx = x; gy = y; break;
          }
        }
        y++;
      }

      in.close();

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }    
  }

  /**
   * 指定座標の迷路データを取得する
   * @param x Ｘ座標
   * @param y Ｙ座標
   * @return 指定座標の迷路のデータ
   */
  public int get(int x, int y)
  {
    if (0 <= x && x < width && 0 <= y && y < height)
      return data[x][y];
    return BLOCK;
  }  
  
  /**
   * スタート位置のＸ座標を取得する
   * @return スタート位置のＸ座標
   */
  public int getSX()
  {
    return sx;
  }
  
  /**
   * スタート位置のＹ座標を取得する
   * @return スタート位置のＹ座標
   */
  public int getSY()
  {
    return sy;
  }
  
  /**
   * ゴール位置のＸ座標を取得する
   * @return ゴール位置のＸ座標
   */
  public int getGX()
  {
    return gx;
  }
  
  /**
   * ゴール位置のＹ座標を取得する
   * @return ゴール位置のＹ座標
   */
  public int getGY()
  {
    return gy;
  }
  
  /**
   * 迷路データの横方向のサイズを取得する
   * @return 迷路データの横方向のサイズ
   */
  public int getWidth()
  {
    return width;
  }
  
  /**
   * 迷路データの縦方向のサイズを取得する
   * @return 迷路データの縦方向のサイズ
   */
  public int getHeight()
  {
    return height;
  }
  
  /**
   * このオブジェクトの値を表す文字列を返す
   * @return オブジェクトの文字列表示
   */
  public String toString() 
  {
    // 迷路データを文字列化
    String str = "";
    for (int y=0; y < height; y++) {
      for (int x=0; x < width; x++) {
        switch (data[x][y]) {
        case SPACE: str += ' '; break;
        case BLOCK: str += '#'; break;
        case START: str += 'S'; break;
        case GOAL:  str += 'G'; break;
        }
      }
      str += '\n';
    }
    return str;
  }
  
  /**
   * 検査用 main 関数
   * @param arg[] コマンドライン引数の配列
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Usage: java MazeData FILENAME");
      System.exit(-1);
    }
    // 迷路データオブジェクトの生成
    MazeData model = new MazeData(args[0]);
    // 迷路データオブジェクトの表示
    System.out.println(model);
  }

  /** 迷路の幅 */
  private int width = 0;
  /** 迷路の高さ */
  private int height = 0;

  /** 迷路のデータ配列 */
  private int[][] data = null;
  
  /** スタート位置の X 座標 */
  private int sx = 0;
  /** スタート位置の Y 座標 */
  private int sy = 0;

  /** ゴール位置の X 座標 */
  private int gx = 0;
  /** ゴール位置の Y 座標 */
  private int gy = 0;
  
  /** 通路を表す定数 */
  public final static int SPACE = 0;
  /** 壁を表す定数 */
  public final static int BLOCK = 1;
  /** スタート位置を表す定数 */
  public final static int START = 2;
  /** ゴール位置を表す定数 */
  public final static int GOAL  = 3;
}
