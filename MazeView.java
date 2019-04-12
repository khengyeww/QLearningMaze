import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

/**
 * 描画用のビュークラス
 */
public class MazeView extends JPanel {
	
  /**
   * 描画用オブジェクトの生成
   * @param mazeModel 迷路のモデルオブジェクト
   */
  public MazeView(MazeModel model)
  {
    // 描画用にロボットデータ・迷路データを保存
    this.robot    = model.getRobot();
    this.mazeData = model.getMazeData();

    // ビューの適切なサイズを設定する
    width  = model.getMazeData().getWidth();
    height = model.getMazeData().getHeight();
    setPreferredSize(new Dimension(width * UNITSIZE, height * UNITSIZE));

    try {
      // 画像の読み込み
      blockImage  = ImageIO.read(new File("block.png" ));
      robot1Image = ImageIO.read(new File("robot1.png"));
      robot2Image = ImageIO.read(new File("robot2.png"));
      spaceImage  = ImageIO.read(new File("space.png" ));
      startImage  = ImageIO.read(new File("start.png" ));
      goalImage   = ImageIO.read(new File("goal.png"  ));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
  
  /**
   * 描画する
   * @param g グラフィックスオブジェクト
   */
  public void paintComponent(Graphics g)
  {
    // 迷路データの描画
    for (int y=0; y < height; y++) {
      for (int x=0; x < width; x++) {
        BufferedImage image = null;
        switch (mazeData.get(x,y)) {
        case MazeData.SPACE: image = spaceImage; break;
        case MazeData.BLOCK: image = blockImage; break;
        case MazeData.START: image = startImage; break;
        case MazeData.GOAL:  image = goalImage;  break;
        }
        g.drawImage(image, x * UNITSIZE, y * UNITSIZE, this);
      }
    }
    // ロボットの描画（ゴールとその他で画像を変える）
    if (robot.getX() == mazeData.getGX() && robot.getY() == mazeData.getGY())
      g.drawImage(robot2Image, robot.getX() * UNITSIZE, robot.getY() * UNITSIZE, this);
    else
      g.drawImage(robot1Image, robot.getX() * UNITSIZE, robot.getY() * UNITSIZE, this);
  }
  
  /** ロボットオブジェクト */
  private Robot robot = null;
  /** 迷路データオブジェクト */
  private MazeData mazeData = null;
  /** 迷路の横方向のサイズ */
  private int width = 0;
  /** 迷路の縦方向のサイズ */
  private int height = 0;

  /** 壁や通路のサイズ */
  private final static int UNITSIZE = 50;
  
  /** 壁の画像 */
  public BufferedImage blockImage = null;
  /** ロボットの画像１ */
  public BufferedImage robot1Image = null;
  /** ロボットの画像２ */
  public BufferedImage robot2Image = null;
  /** 通路の画像 */
  public BufferedImage spaceImage = null;
  /** スタート位置の画像 */
  public BufferedImage startImage = null;
  /** ゴール位置の画像 */
  public BufferedImage goalImage  = null;
  
}
