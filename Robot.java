/**
 * ロボットを表すクラス
 */
public class Robot {
  
  /**
   * ロボットオブジェクトを生成する
   * @param sx スタート位置のＸ座標
   * @param sy スタート位置のＹ座標
   */
  public Robot(int sx, int sy)
  {
    // 初期位置を登録
    x = sx;
    y = sy;
  }
  
  /**
   * ロボット位置のＸ座標を設定する
   * @param x ロボット位置のＸ座標
   */
  public void setX(int x)
  {
    this.x = x;
  }
  
  /**
   * ロボット位置のＹ座標を設定する
   * @param y ロボット位置のＹ座標
   */
  public void setY(int y)
  {
    this.y = y;
  }
  
  /**
   * ロボット位置のＸ座標を取得する
   * @return ロボット位置のＸ座標
   */
  public int getX()
  {
    return x;
  }
  
  /**
   * ロボット位置のＹ座標を取得する
   * @return ロボット位置のＹ座標
   */
  public int getY()
  {
    return y;
  }
  
  /** ロボット位置の X 座標 */
  private int x = 0;
  /** ロボット位置の Y 座標 */
  private int y = 0;

}
