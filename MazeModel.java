
/**
 * 強化学習によりゴールまでの経路を学習するクラス
 */
public class MazeModel implements Runnable {

	/**
	 * 強化学習によりゴールまでの経路を学習するオブジェクトを生成する
	 * @param mazeFile 迷路データのファイル名
	 */
	public MazeModel(String mazeFile)
	{
		// 迷路データを生成
		mazeData = new MazeData(mazeFile);
		// ロボットを生成
		robot = new Robot(mazeData.getSX(), mazeData.getSY());
	}

	/**
	 * 実行用関数
	 */
	public void run()
	{
		try {

			// ここをがんばって作る

			// step 1:	Q学習する
			// QLearningのインスタンスを作る	
			int states = mazeData.getWidth() * mazeData.getHeight(); // 状態数
			int actions = 4;	// 行動数
			double alpha = 0.5; // 学習率
			double gamma = 0.5; // 割引率

			// ゴール座標の取得
			int gx = mazeData.getGX();
			int gy = mazeData.getGY();

			QLearning ql = new QLearning(states, actions, alpha, gamma);

			int trials = 500; // 強化学習の試行回数 
			int steps = 100; // 1試行あたりの最大ステップ数

			// 試行回数だけ繰り返し
			for(int t = 0; t < trials; t++){
				//System.out.println("This is new loop testttttttttttt: " + t);

				/* ロボットを初期位置に戻す */
				robot.setX(mazeData.getSX());
				robot.setY(mazeData.getSY());

				// ステップ数だけ繰り返し
				for(int s = 0; s < steps; s++){

					// ロボットの現在位置を取得
					int x = robot.getX();
					int y = robot.getY();

					/*--------------- ε-Greedy 法により行動を選択 ---------------*/
					// 現在の状態番号を取得する
					int state = (y * mazeData.getWidth()) + x + 1;
					// ランダムに行動を選択する確率
					double epsilon = 0.5;

					// qlインスタンスから呼び出す
					// 次の行動を選択する
					/* Greedy 法では、だいたい 40回ぐらいの学習後で収束できる */
					//int action = ql.selectAction(state);
					/* epsilon-Greedy 法では、よりかかる(ランダムの行動を取っちゃうから？)という感じ */
					int action = ql.selectAction(state, epsilon);

					/*--------------- 選択した行動を実行 (ロボットを移動する) ---------------*/
					moveRobot(action, x, y);

					/*
					 * 学習状況を理解するために 中身の動作を見せる関数
					 *
					// 現在の状態を描画する
					mazeView.repaint();

					// 速すぎるので 500msec 寝る
					// 最初学習のところを skipする (だいたい 40回ぐらいの学習後、成果が見える)
					if(t > 40)
						Thread.sleep(500);
					else
						Thread.sleep(0);
					 //*/
					
					/*--------------- 新しい状態を観測＆報酬を得る ---------------*/
					// 移動後の新しい座標を取得
					x = robot.getX();
					y = robot.getY();
					
					//次の状態番号
					int	after = (y * mazeData.getWidth()) + x + 1; // 頑張って取得する
					//System.out.println(after);

					// 状態afterにおける報酬
					int reward = 0; // 頑張って取得する

					// Goal に到達したら 100 報酬を与え、元状態と変わらない (壁に移動する) なら -10、普通の通路なら -1
					if(x == gx && y == gy)
						reward = 100;
					//else if(mazeData.get(x, y) == MazeData.BLOCK) // 実際は使われていない
					else if(after == state)
						reward = -10;
					else
						reward = -1;

					/*--------------- Q 値を更新 ---------------*/
					// qlインスタンスから呼び出す
					ql.update(state, action, after, reward);

					/*--------------- もし時間差分誤差が十分小さくなれ䜀終了 ---------------*/
					// Goal に到達したから、また初期状態から学習
					if(x == gx && y == gy)
						break;

				}
			}
			
			// 学習終了で、最後の成果を出す
			/* ロボットを初期位置に戻す */
			robot.setX(mazeData.getSX());
			robot.setY(mazeData.getSY());
			
			// 状態数以内にゴールに到達できるはず
			for(int count = 0; count < states; count++) {
				// ロボットの現在位置を取得
				int x = robot.getX();
				int y = robot.getY();
				
				// 現在の状態番号を取得する
				int state = (y * mazeData.getWidth()) + x + 1;
				
				// 次の行動を選択する
				int action = ql.selectAction(state);
				
				// 選択した行動を実行 (ロボットを移動する)
				moveRobot(action, x, y);

				// 現在の状態を描画する
				mazeView.repaint();
				
				// 移動後の新しい座標を取得
				x = robot.getX();
				y = robot.getY();
				
				// 速すぎるので 500msec 寝る
				Thread.sleep(500);
				
				// もしゴールに到達すれば終了
				if(x == gx && y == gy)
					break;
			}

			/*
			// QTable の表示
			System.out.println("Final QTable!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			ql.showQTable(states);
			//*/
			
			// 終わり
			System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * ロボットを移動する
	 */
	public void moveRobot(int action, int x, int y)
	{
		// 0:UP 1:DOWN 2:LEFT 3:RIGHT
		// 壁がないことを確認して移動する
		if(action == 0 && mazeData.get(x, y-1) != MazeData.BLOCK) // UP
			y--;
		else if(action == 1 && mazeData.get(x, y+1) != MazeData.BLOCK) // DOWN
			y++;
		else if(action == 2 && mazeData.get(x-1, y) != MazeData.BLOCK) // LEFT
			x--;
		else if(action == 3 && mazeData.get(x+1, y) != MazeData.BLOCK) // RIGHT
			x++;
		
		// ロボットの位置座標を更新
		robot.setX(x);
		robot.setY(y);
	}
	
	/**
	 * 描画用のビューを登録
	 */
	public void setView(MazeView view)
	{
		mazeView = view;
	}

	/**
	 * ロボットオブジェクトを取得する
	 * @return ロボットオブジェクト
	 */
	public Robot getRobot()
	{
		return robot;
	}

	/**
	 * 迷路データオブジェクトを取得する
	 * @return 迷路データオブジェクト
	 */
	public MazeData getMazeData()
	{
		return mazeData;
	}

	/** 迷路データ */
	private MazeData mazeData = null;
	/** ロボットデータ */
	private Robot robot = null;

	/** 描画用オブジェクト */
	private MazeView mazeView = null;
}
