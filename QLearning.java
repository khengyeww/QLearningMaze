import java.util.ArrayList;
import java.util.Random;

/**
 * Ｑ学習を行うクラス
 */
public class QLearning {
  
  /**
   * Ｑ学習を行うオブジェクトを生成する
   * @param states  状態数
   * @param actions 行動数
   * @param alpha   学習率（0.0〜1.0）
   * @param gamma   割引率（0.0〜1.0）
   */
  public QLearning(int states, int actions, double alpha, double gamma)
  {
	  this.qTable = new double[states][actions];
	  this.alpha = alpha;
	  this.gamma = gamma;
	  this.actions = actions;
  }
  
  /**
   * epsilon-Greedy 法により行動を選択する
   * @param state   現在の状態
   * @param epsilon ランダムに行動を選択する確率（0.0〜1.0）
   * @return 選択された行動番号
   */
  public int selectAction(int state, double epsilon)
  {
	  // epsilon より小さい時、ランダムな行動をとる
	  if(random.nextDouble() < epsilon)
		  return random.nextInt(actions);

	  // そうでないと、一番大きい Ｑ値を持っている次の行動を選択する
	  return getNextActionFromMaxQ(state);
  }
  
  /**
   * Greedy 法により行動を選択する
   * @param state   現在の状態
   * @return 選択された行動番号
   */
  public int selectAction(int state)
  {
	  // 一番大きい Ｑ値を持っている次の行動を選択する
	  return getNextActionFromMaxQ(state);
  }
  
  /**
   * Ｑ値を更新する
   * @param before 状態
   * @param action 行動
   * @param after  遷移後の状態
   * @param reward 報酬
   */
  public void update(int before, int action, int after, double reward)
  {
	  // 数式と同じ
	  qTable[before][action] = qTable[before][action] + alpha * (reward + (gamma * getMaxQ(after)) - qTable[before][action]);
  }
  
  /**
   * 一番大きい Ｑ値を持っている次の行動を選択する
   * @param state 状態
   * @return 次の行動番号を返す
   */
  public int getNextActionFromMaxQ(int state)
  {
	  int nextAction = 0;
	  double maxQ = -100000000.0;
	  actionList.clear(); // 配列リストの中身を全部削除
	  
	  // 一番大きい Ｑ値を持っている action は配列リストに保存する
	  for(int index = 0; index < actions; index++) {
		  if(qTable[state][index] > maxQ) {
			  actionList.clear();
			  maxQ = qTable[state][index];
			  nextAction = index;
			  actionList.add(index);
		  }
		  else if(qTable[state][index] == maxQ) {
			  actionList.add(index);
			  nextAction = -1; // 「maxQ が複数ある場合、ランダムで決まることになる」 条件を設定する
		  }
	  }
	  
	  // 一番大きい Ｑ値を持っている複数のaction の中でランダムで一つを選ぶ
	  if(nextAction == -1)
		  nextAction = actionList.get(random.nextInt(actionList.size()));
	  
	  return nextAction;
  }
  
  /**
   * 一番大きい Ｑ値を探す
   * @param state 状態
   * @return その state 状態から次の行動で一番大きいＱ値を返す
   */
  public double getMaxQ(int state)
  {
	  double maxQ = -100000000.0;
	  
	  // maxQ で一番大きい Ｑ値を保存し、最後にそれを返す
	  for(int index = 0; index < actions; index++) {
		  if(qTable[state][index] > maxQ)
			  maxQ = qTable[state][index];
	  }
	  return maxQ;
  }
  
  /**
   * QTable を表示する
   * @param states 状態数
   */
  public void showQTable(int states) 
  {
	  System.out.println("Start of QTable:");
	  for (int y=0; y < states; y++) {
		  for (int x=0; x < actions; x++) {
			  System.out.print(qTable[y][x] + " ");
		  }
		  System.out.println();
	  }
	  System.out.println("End of QTable");
  }
  
   //フィールド
	private double qTable[][] = null;
	private double alpha = 0; // 学習率
	private double gamma = 0; // 割引率
	private int actions = 0;  // 行動数
	private ArrayList<Integer> actionList = new ArrayList<>(); // 行動の保存用のリスト
	private Random random = new Random(); // ランダム用の変数
}
