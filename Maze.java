import java.awt.*;
import javax.swing.*;

/**
 * 迷路アプリケーションクラス
 */
public class Maze extends JFrame {

  /**
   * 迷路アプリケーションの生成
   * @param mazeFile 迷路データのファイル名
   */
  public Maze(String mazeFile) {
    
    // ウィンドウタイトルの登録
    setTitle("Maze Application");
    
    // 迷路のモデルオブジェクトの生成
    MazeModel model = new MazeModel(mazeFile);

    // 迷路の描画画面の生成
    MazeView view = new MazeView(model);

    // モデルに描画オブジェクトを登録
    model.setView(view);
    
    // スレッドを生成して，モデルを並行して実行する
    new Thread(model).start();

    // 迷路の描画画面を中央に配置
    getContentPane().add(view, BorderLayout.CENTER);
    
    // サブコンポーネントの推奨サイズに合わせて、ウィンドウをリサイズ
    pack();
  }
  
  /**
   * 起動用 main 関数
   * @param arg[] コマンドライン引数の配列
   */
  public static void main(String[] args) {

    // コマンドライン引数のチェック
    if (args.length == 0) {
      System.out.println("Usage: java Maze FILENAME");
      System.exit(-1);
    }

    // 迷路アプリの生成
    Maze maze = new Maze(args[0]);
    
    // ウィンドウを閉じたときに終了する
    maze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // ウィンドウを表示する
    maze.setVisible(true);
  }
}
