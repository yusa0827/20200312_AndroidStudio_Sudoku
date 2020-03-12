package samples.opencv.sudoku_3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static java.lang.String.valueOf;

//ボタンのIDは共有できる

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private static final String PREF_FILE_NAME = "samples.opencv.sudoku_3.PREF_FILE_NAME";



    public void layout_1_sudoku(){

        //画面サイズは以下のように取得
//        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
//        Display disp = wm.getDefaultDisplay();

        Display display = getWindowManager().getDefaultDisplay();
//
//
        //数独ホームの画面比をピッタリと合わせる
        // _1_sudoku_home.xml にはスペースが6,テキストが5,ボタンが5
        int _1_space = 6;//スペースの個数
        int _1_text = 5;//テキストの個数
        int _1_btn = 6;//ボタンの個数
        int wight_space_for_btn = 3;//ボタンのサイズに対するスペースのサイズの重み
        int wight_text_for_btn = 2;//ボタンのサイズに対するテキストのサイズの重み
        //全体の割合を指定
        int _1_sudoku_all_size = _1_space * wight_space_for_btn + _1_text * wight_text_for_btn + _1_btn;
        //スペースの大きさを設定
        Point size = new Point();
        display.getSize(size);

        //画面サイズを取得
        int screen_w = size.x;
        int screen_h = size.y;
        //表示サイズの割合
        int ration_of_space = wight_space_for_btn / _1_sudoku_all_size;//スペース
        int ration_of_text = wight_space_for_btn / _1_sudoku_all_size;//スペース
        int ration_of_btn = 1 / _1_sudoku_all_size;//ボタンの重みは１
//
        //スペースの高さを指定
        Space space_size = (Space) findViewById(R.id.layout_space);
        space_size.setMinimumHeight(screen_h * ration_of_space);
        //テキストの高さを指定
        TextView text_size = (TextView) findViewById(R.id.layout_text_);
        text_size.setMinimumHeight(screen_h * ration_of_text);
//
        //ボタンの高さを指定
        int[] _1_sudoku_btn = {R.id.button_sudoku_demo_demo,R.id.button_sudoku_train_,R.id.button_sudoku_original_,
                R.id.button_sudoku_quiz ,R.id.button_sudoku_maker_profile};
        for(int i_ = 0;i_<=_1_sudoku_btn.length;i_++){
            Button btn_size = (Button) findViewById(_1_sudoku_btn[i_]);
            btn_size.setMinimumHeight(screen_h * ration_of_btn);

        }




    }







    //アプリ開始
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._0_activity_main);//activity_mainを表示
        //3秒たったら、自動的にホーム画面に飛ばす
        SetSudokuHomeAuto();
    }
    //3秒間、skipボタンを押さなかったら数独ホームに移動する
    private void SetSudokuHomeAuto(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // ここに3秒後に実行したい処理 数独ホームの表示
                setContentView(R.layout._1_sudoku_home);

                //レイアウト表示ができるのか
                layout_1_sudoku();

                //データ保存 // 設定ファイルを開きます。
                SharedPreferences sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
                // 値の取得
                int intVal_ = sharedPref.getInt("MY_KEY", 1); // 既定値 123 を設定
                Log.d(TAG, "MY_VALUE: " + intVal_);

                //スマホ画面に出力
                Toast.makeText(getApplicationContext(), "起動回数 : " + intVal_, Toast.LENGTH_SHORT).show();
                // 値の設定 (起動する毎に値が +1 されます)
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("MY_KEY", intVal_ + 1);
                editor.apply();

                //SharedPreferences の値を削除する
                editor.remove("MY_KEY");
                editor.commit();

                //_1_sudoku_homeのボタンによって別のページに行く
                SetSudokuDemoTrainOriginal();
            }
        }, 3000);
    }
    //_1_sudoku_homeのボタンによって別のページに行く
    private void SetSudokuDemoTrainOriginal(){
        //sudoku_home内のボタンをクリックしたら移動する
        Button button_sudoku_demo_demo = this.findViewById(R.id.button_sudoku_demo_demo);
        button_sudoku_demo_demo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //クリックしたら飛ぶレイアウトページを選択する
                setContentView(R.layout._2_1_sudoku_demo);
                //fgm_sudoku_table_demo_();//フラグメント1 数独表
                fgm_sudoku_tv_demo_for_demo_();////フラグメント1 数独表 テキスト
                fgm_sudoku_analysis_states_demo_();//フラグメント2 数独の解析ボタン
            }
        });
        //sudoku_home内のbutton_sudoku_train_ボタンをクリックしたら移動する
        Button button_sudoku_train_ = this.findViewById(R.id.button_sudoku_train_);
        button_sudoku_train_.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //クリックしたら飛ぶレイアウトページを選択する トレーニング選択肢にいく
                setContentView(R.layout._2_2_sudoku_training_samples);


                //データの読み出し
                SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                //クリアしたトレーニング問題があったらそのボタンの色を変える
                for(int i=1;i<=SS.s_training_id.length+1;i++){
                    //もしクリア（値はtrue）したら、ボタンの色を変える
                    //boolean
                    boolean Level = data.getBoolean("s_training_clear_["+ Integer.toString(i)+"]",false);
                    //trueだったらボタンの色を変更
                    if(Level){
                        //数独トレーニングボタン
                        Button btn_s_training = findViewById(SS.s_training_id[i]);//今のボタン
                        //リソースから作成したDrawableのリソースを取得
                        Drawable btn_clr_now = ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rtgl_deep_emerald, null);
                        //ボタンにDrawableを適用する
                        btn_s_training.setBackground(btn_clr_now);
                    }
                }


            }
        });
        //sudoku_home内のbutton_sudoku_original_ボタンをクリックしたら移動する
        Button button_sudoku_original_ = this.findViewById(R.id.button_sudoku_original_);
        button_sudoku_original_.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //クリックしたら飛ぶレイアウトページを選択する オリジナル問題にいく
                setContentView(R.layout._2_3_sudoku_original_question);
                //初期化処理が必要
                SS.Button_No_back = 1;//今の値
                //_3_sudoku_original();//ただフラグメントを設置しただけ
                fgm_sudoku_table_();//フラグメント1 数独表
                fgm_sudoku_input_number_();//フラグメント2 数字入力
                fgm_sudoku_analysis_states_();//フラグメント3 解析スタートボタン（ページ移動）
            }
        });
        //sudoku_home内のbutton_sudoku_quiz_ボタンをクリックしたら移動する
        Button button_sudoku_quiz_ = this.findViewById(R.id.button_sudoku_quiz);
        button_sudoku_quiz_.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //クリックしたら飛ぶレイアウトページを選択する オリジナル問題にいく
                setContentView(R.layout._2_4_sudoku_quiz);
            }
        });
        //sudoku_home内のbutton_sudoku_quiz_ボタンをクリックしたら移動する
        Button button_sudoku_profile_ = this.findViewById(R.id.button_sudoku_maker_profile);
        button_sudoku_profile_.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //クリックしたら飛ぶレイアウトページを選択する オリジナル問題にいく
                setContentView(R.layout._2_5_sudoku_maker_profile);
            }
        });
    }

    //数独クラスを定義
    public class_sudoku SS = new class_sudoku();

    //数独表の入力変数
    int sudoku_input_number_ = 1;//とりあえずは初期値1

    //もし解析が終わった数字入力ボタン（delete）をクリックしたら無効にする







    /**数独のターゲット入力ボタンと解析ボタンの色を反映**/
    public void Input_1(View v) {//解析ボタン１だったら変更
        //解析した数字がtrueでなかったら、入力数字として認めない
        //解析可能な数字だったらGO //探査可能な入力数字なのか
        int input_number_ = 1;
        if(analysis_number[input_number_] && !SS.delete_btn_number[input_number_]) {
            sudoku_input_number_ = input_number_;//入力数値
            if (change_als_step_color_ == 1)
                OnBnClickedButtonNoChanger_assist(sudoku_input_number_);
        }
    }
    public void Input_2(View v) {
        //解析した数字がtrueでなかったら、入力数字として認めない
        //解析可能な数字だったらGO //探査可能な入力数字なのか
        int input_number_ = 2;
        if(analysis_number[input_number_] && !SS.delete_btn_number[input_number_]) {
            sudoku_input_number_ = input_number_;//入力数値
            if (change_als_step_color_ == 1)
                OnBnClickedButtonNoChanger_assist(sudoku_input_number_);
        }
    }
    public void Input_3(View v) {
        //解析した数字がtrueでなかったら、入力数字として認めない
        //解析可能な数字だったらGO //探査可能な入力数字なのか
        int input_number_ = 3;
        if(analysis_number[input_number_] && !SS.delete_btn_number[input_number_]) {
            sudoku_input_number_ = input_number_;//入力数値
            if (change_als_step_color_ == 1)
                OnBnClickedButtonNoChanger_assist(sudoku_input_number_);
        }
    }
    public void Input_4(View v) {
        //解析した数字がtrueでなかったら、入力数字として認めない
        //解析可能な数字だったらGO //探査可能な入力数字なのか
        int input_number_ = 4;
        if(analysis_number[input_number_] && !SS.delete_btn_number[input_number_]) {
            sudoku_input_number_ = input_number_;//入力数値
            if (change_als_step_color_ == 1)
                OnBnClickedButtonNoChanger_assist(sudoku_input_number_);
        }
    }
    public void Input_5(View v) {
        //解析した数字がtrueでなかったら、入力数字として認めない
        //解析可能な数字だったらGO //探査可能な入力数字なのか
        int input_number_ = 5;
        if(analysis_number[input_number_] && !SS.delete_btn_number[input_number_]) {
            sudoku_input_number_ = input_number_;//入力数値
            if (change_als_step_color_ == 1)
                OnBnClickedButtonNoChanger_assist(sudoku_input_number_);
        }
    }
    public void Input_6(View v) {
        //解析した数字がtrueでなかったら、入力数字として認めない
        //解析可能な数字だったらGO //探査可能な入力数字なのか
        int input_number_ = 6;
        if(analysis_number[input_number_] && !SS.delete_btn_number[input_number_]) {
            sudoku_input_number_ = input_number_;//入力数値
            if (change_als_step_color_ == 1)
                OnBnClickedButtonNoChanger_assist(sudoku_input_number_);
        }
    }
    public void Input_7(View v) {
        //解析した数字がtrueでなかったら、入力数字として認めない
        //解析可能な数字だったらGO //探査可能な入力数字なのか
        int input_number_ = 7;
        if(analysis_number[input_number_] && !SS.delete_btn_number[input_number_]) {
            sudoku_input_number_ = input_number_;//入力数値
            if (change_als_step_color_ == 1)
                OnBnClickedButtonNoChanger_assist(sudoku_input_number_);
        }

    }
    public void Input_8(View v) {
        //解析した数字がtrueでなかったら、入力数字として認めない
        //解析可能な数字だったらGO //探査可能な入力数字なのか
        int input_number_ = 8;
        if(analysis_number[input_number_] && !SS.delete_btn_number[input_number_]) {
            sudoku_input_number_ = input_number_;//入力数値
            if (change_als_step_color_ == 1)
                OnBnClickedButtonNoChanger_assist(sudoku_input_number_);
        }
    }
    public void Input_9(View v) {
        //解析した数字がtrueでなかったら、入力数字として認めない
        //解析可能な数字だったらGO //探査可能な入力数字なのか
        int input_number_ = 9;
        if(analysis_number[input_number_] && !SS.delete_btn_number[input_number_]) {
            sudoku_input_number_ = input_number_;//入力数値
            if (change_als_step_color_ == 1)
                OnBnClickedButtonNoChanger_assist(sudoku_input_number_);
        }
    }


    //デモ入力が押されていなかったら、解析を発動しない変数
    boolean input_for_demo_sudoku = false;

    /*数独デモの入力*/
    public void demo_input(View v){
        input_for_demo_sudoku = true;//デモ入力が押されたらtrue
        //数独の初期値を代入
        SS.set_demo_input();
        //for文でボタンに数独を
        for (int tate_ = 1; tate_ <= 9; tate_++) {
            for (int yoko_ = 1; yoko_ <= 9; yoko_++) {
                //もし0であったら入力しない
                TextView t = findViewById(SS.tv_demo_s[tate_][yoko_]);

                if (SS.s[tate_][yoko_] != 0) {
                    //初期値の値をボタンに反映
                    t.setText(valueOf(SS.s[tate_][yoko_]));
                }

                //数独票の入力数字を記録 一致していたら赤文字
                if (SS.s_cover[tate_][yoko_] != 0) {
                    //初期値の値をテキストに反映
                    t.setTextColor(Color.RED);
                }
            }
        }
    }
    //解析マップを表示
    public void show_rlt_map(int number_) {
        for (int tate_ = 1; tate_ <= 9; tate_++) {
            for (int yoko_ = 1; yoko_ <= 9; yoko_++) {
                //反映させるTextViewを指定
                TextView t = findViewById(SS.tv_rlt_s[tate_][yoko_]);
                //初期化されたnum_workingの値と解析の値が一致していたら空文字
                if(SS.num_working[tate_][yoko_][number_].equals(valueOf(number_)))
                {
                    t.setText("");
                }
                else{
                    t.setText(SS.num_working[tate_][yoko_][number_]);
                }
                //指定した数字があると、それは書き残しておく
                if (SS.s[tate_][yoko_] == number_) {
                    t.setText(valueOf(SS.s[tate_][yoko_]));
                }

                //数独票の入力数字を記録 一致していたら赤文字
                if (SS.s_cover[tate_][yoko_] != 0) {
                    t.setTextColor(Color.RED);//初期値の値をテキストに反映
                }
            }
        }
    }
    //解析マップを表示
    public void show_rlt_map_with_specified_number(int number_) {
        for (int tate_ = 1; tate_ <= 9; tate_++) {
            for (int yoko_ = 1; yoko_ <= 9; yoko_++) {
                //反映させるTextViewを指定
                TextView t = findViewById(SS.tv_rlt_s[tate_][yoko_]);
                //解析した文字列を代入
                t.setText(SS.num_working[tate_][yoko_][number_]);
                //指定した数字があると、それは書き残しておく
                if (SS.s[tate_][yoko_] == number_) {
                    t.setText(valueOf(SS.s[tate_][yoko_]));
                }
                //初期化されたnum_workingの値と解析の値が一致していたら空文字
                if(SS.num_working[tate_][yoko_][number_].equals("M"))
                {
                    t.setText("確");
                }

                //数独票の入力数字を記録 一致していたら赤文字
                if (SS.s_cover[tate_][yoko_] != 0) {
                    t.setTextColor(Color.RED);//初期値の値をテキストに反映
                }


            }
        }
    }
    //解析マップを表示
    public void show_rlt_map_with_all_specified_number_() {
        for(int number_ = 1; number_ <= 9; number_++) {
            for (int tate_ = 1; tate_ <= 9; tate_++) {
                for (int yoko_ = 1; yoko_ <= 9; yoko_++) {
                    //反映させるTextViewを指定
                    TextView t = findViewById(SS.tv_rlt_s[tate_][yoko_]);
                    //指定した数字があると、それは書き残しておく
                    if (SS.s[tate_][yoko_] == number_) {
                        t.setText(valueOf(SS.s[tate_][yoko_]));
                    }
                    //初期化されたnum_workingの値と解析の値が一致していたら空文字
                    if (SS.num_working[tate_][yoko_][number_].equals("M")) {
                        t.setText("確");
                    }

                    //数独票の入力数字を記録 一致していたら赤文字
                    if (SS.s_cover[tate_][yoko_] != 0) {
                        t.setTextColor(Color.RED);//初期値の値をテキストに反映
                    }

                }
            }
        }
    }
    //解析マップを初期化して表示
    public void show_rls_map_init(){
        for (int tate_ = 1; tate_ <= 9; tate_++) {
            for (int yoko_ = 1; yoko_ <= 9; yoko_++) {
                //特徴マップ 初期化
                TextView t = findViewById(SS.tv_rlt_s[tate_][yoko_]);
                //文字数字が確定していたら、その値を代入
                if (SS.s[tate_][yoko_] == 0) {
                    t.setText("");
                }
                else{
                    t.setText(valueOf(SS.s[tate_][yoko_]));
                }

                //数独票の入力数字を記録 一致していたら赤文字
                if (SS.s_cover[tate_][yoko_] != 0) {
                    t.setTextColor(Color.RED);//初期値の値をテキストに反映
                }

            }
        }
    }


    //数独が何%埋まっているのかを確認
    public void show_tv_of_sudoku_percent(){
        int sudoku_percent = 0;//数独表で0以外だったらカウント
        for (int tate_ = 1; tate_ <= 9; tate_++) {
            for (int yoko_ = 1; yoko_ <= 9; yoko_++) {
                //文字数字が確定していたら、その値を代入
                if (SS.s[tate_][yoko_] != 0) {
                    sudoku_percent++;
                }
            }
        }
        //数独が何%埋まっているのかを確認
        TextView t = findViewById(R.id.tv_sudoku_percent);
        t.setText("数独の完成度\n"+valueOf(sudoku_percent) + "/81");
    }


    //解析できなかった数字はクリックできないようにする
    boolean[] analysis_number = {true,true,true,true,true,true,true,true,true,true};

    //解析結果をテキストに表示
    public void show_als_tv_states(int SS_comment_result_num){
        TextView t = findViewById(R.id.tv_als_states);
        if(SS_comment_result_num > 0){
            t.setText("解析結果　解析できました");
//            //解析できたら、すべてを初期化する
            for(int i=1;i<=9;i++){//解析できなかった数字だったら、解析できないようにストップさせる
                analysis_number[i] = true;
            }
        }
        else{
            t.setText("解析結果　解析できませんでした");
            //解析できなかった数字だけが加算されていく
            //解析できなかったら、数字を変えない限り解析手順を踏ませない
            analysis_number[sudoku_input_number_] = false;
        }
    }

    //数独内でその数字が9個使われたらボタンを消去
    public void delete_button_number_name(int sudoku_input_number_) {
        int delete_number_counter = 0;
        for (int tate_ = 1; tate_ <= 9; tate_++) {
            for (int yoko_ = 1; yoko_ <= 9; yoko_++) {
                //番号と数字が一致した回数
                if (SS.s[tate_][yoko_] == sudoku_input_number_) {
                    delete_number_counter++;
                }
            }
        }
        //数字のカウンターが9個だったら、ボタンの文字を消す
        if (delete_number_counter == 9) {
            //解析数字ボタンを指定
            Button btn = findViewById(SS.input_number[sudoku_input_number_]);
            btn.setText("");//空文字を入力
            //ボタンを消去するようにうごく
            SS.delete_btn_number[sudoku_input_number_] = true;
        }
    }

    //1~9までのボタンの簡単処理をまとめる
    public void btn_number_1_9_easy(View v) {
        for (int time_ = 1; time_ <= 10; time_++) {
            for (int number_ = 1; number_ <= 9; number_++) {//1~9

                SS._1_initialize();
                SS._2_1_Nth_0_1_checker_1_TEISU(number_);
                SS._2_2_Nth_0_1_checker_2_area_3_3(number_);
                SS._2_3_Nth_0_1_checker_3_row_col_3_3(number_);
                SS._2_4_special_delete_ver2(number_);
                SS._2_5_change_1_to_number_in_3_3_blocks(number_);

            }
        }
        show_rls_map_init();
    }


    int sudoku_traing_number_;

    //トレーニング問題1
    public void btn_trn_1(View v) {
        /**前半**/
        //数独の初期値を代入
        SS.set_demo_input_1();
        sudoku_traing_number_ = 1;//数独トレーニングの問題番号


        /**後半**/
        //数独の解析マップの初期化をする
        input_for_demo_sudoku = true;

        //数独の解析マップの初期化をする
        SS.Button_No_back = 1;//今の値
        //レイアウトだけ変えたいので、これで問題ない
        setContentView(R.layout._3_1_2_result_map_for_demo);//通常解析
        fgm_sudoku_rlt_map_demo_demo_();//解析結果マップ
        fgm_sudoku_input_table_3_3_();//3*3インプット数独表
        fgm_sudoku_analysis_step_demo_for_demo();//解析手順　ただの解析ボタンの状態
        show_tv_of_sudoku_percent();//数独が何%埋まっているのかを確認

        //解析手順をクリックしては消してという、フラグメントオブフラグメントの処理をする
        fgm_sudoku_view_step_1_();//1つ目のボタンを表示
        //3秒間、skipボタンを押さなかったら数独ホームに移動する
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // ここに5秒後に実行したい処理 数独ホームの表示
                //数独の解析テキストにマルチスレッドで書き込んでいる
                for (int tate_1 = 1; tate_1 <= 9; tate_1++) {
                    for (int yoko_1 = 1; yoko_1 <= 9; yoko_1++) {
                        //もし0であったら入力しない
                        if (SS.s[tate_1][yoko_1] != 0) {
                            //初期値の値をテキストに反映
                            TextView t = findViewById(SS.tv_rlt_s[tate_1][yoko_1]);
                            t.setText(valueOf(SS.s[tate_1][yoko_1]));
                            SS._1_initialize();//初期化
                        }

                        //数独票の入力数字を記録 一致していたら赤文字
                        if (SS.s_cover[tate_1][yoko_1] != 0) {
                            //初期値の値をテキストに反映
                            TextView t_ = findViewById(SS.tv_rlt_s[tate_1][yoko_1]);
                            t_.setTextColor(Color.RED);
                        }

                    }
                }
                //数独の解析マップの初期化をする
                SS.Button_No_back = 1;//今の値
            }
        }, 2000);
    }
    public void btn_trn_2(View v) {
        /**前半**/
        //数独の初期値を代入
        SS.set_demo_input_2();
        sudoku_traing_number_ = 2;//数独トレーニングの問題番号
        /**後半**/
        //数独の解析マップの初期化をする
        input_for_demo_sudoku = true;
        SS.Button_No_back = 1;//数独の解析マップの初期化をする//今の値
        //レイアウトだけ変えたいので、これで問題ない
        setContentView(R.layout._3_1_2_result_map_for_demo);//通常解析
        fgm_sudoku_rlt_map_demo_demo_();//解析結果マップ
        fgm_sudoku_input_table_3_3_();//3*3インプット数独表
        fgm_sudoku_analysis_step_demo_for_demo();//解析手順　ただの解析ボタンの状態
        show_tv_of_sudoku_percent();//数独が何%埋まっているのかを確認

        //解析手順をクリックしては消してという、フラグメントオブフラグメントの処理をする
        fgm_sudoku_view_step_1_();//1つ目のボタンを表示
        //3秒間、skipボタンを押さなかったら数独ホームに移動する
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // ここに5秒後に実行したい処理 数独ホームの表示
                //数独の解析テキストにマルチスレッドで書き込んでいる
                for (int tate_1 = 1; tate_1 <= 9; tate_1++) {
                    for (int yoko_1 = 1; yoko_1 <= 9; yoko_1++) {
                        //もし0であったら入力しない
                        if (SS.s[tate_1][yoko_1] != 0) {
                            //初期値の値をテキストに反映
                            TextView t = findViewById(SS.tv_rlt_s[tate_1][yoko_1]);
                            t.setText(valueOf(SS.s[tate_1][yoko_1]));
                            SS._1_initialize();//初期化
                        }

                        //数独票の入力数字を記録 一致していたら赤文字
                        if (SS.s_cover[tate_1][yoko_1] != 0) {
                            //初期値の値をテキストに反映
                            TextView t_ = findViewById(SS.tv_rlt_s[tate_1][yoko_1]);
                            t_.setTextColor(Color.RED);
                        }

                    }
                }
                //数独の解析マップの初期化をする
                SS.Button_No_back = 1;//今の値
            }
        }, 2000);
    }

    //デモ数独のデモの解析結果を示す
    public void lets_analysis_demo(View v){
        //数独表の入力が行われたら実行
        if(input_for_demo_sudoku){
            //数独の解析マップの初期化をする
            SS.Button_No_back = 1;//今の値
            //レイアウトだけ変えたいので、これで問題ない
            setContentView(R.layout._3_1_2_result_map_for_demo);//通常解析
            //setContentView(R.layout._3_1_1_demo_result_demo);//最強解析ボタンあり
            fgm_sudoku_rlt_map_demo_demo_();//解析結果マップ
            fgm_sudoku_input_table_3_3_();//3*3インプット数独表
            fgm_sudoku_analysis_step_demo_for_demo();//解析手順 ただの解析ボタンの状態

            show_tv_of_sudoku_percent();//数独が何%埋まっているのかを確認

            //解析手順をクリックしては消してという、フラグメントオブフラグメントの処理をする
            fgm_sudoku_view_step_1_(); //1つ目のボタンを表示
            //3秒間、skipボタンを押さなかったら数独ホームに移動する
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    // ここに5秒後に実行したい処理 数独ホームの表示 マルチスレッドで書き込み
                    for (int tate_1 = 1; tate_1 <= 9; tate_1++) {
                        for (int yoko_1 = 1; yoko_1 <= 9; yoko_1++) {
                            //もし0であったら入力しない
                            if (SS.s[tate_1][yoko_1] != 0) {
                                //初期値の値をテキストに反映
                                TextView t = findViewById(SS.tv_rlt_s[tate_1][yoko_1]);

                                t.setText(valueOf(SS.s[tate_1][yoko_1]));
                                t.setTextColor(Color.RED);
                                //これは必要なのか・・・
                                SS._1_initialize(); //初期化する
                            }



                        }
                    }

                    //sudoku_initial_input_for_color();//初期の入力ボタンの背景の色を変化させる


                    //数独の解析マップの初期化をする
                    SS.Button_No_back = 1;//今の値
                }
            }, 2000);
        }
    }


    /**オリジナル数独で、独立した解析を始めるボタンに独立した関数を定義し、初期化(true)し、いつもの関数を組み込む**/
    //オリジナル数独で、独立した解析を始めるボタンに独立した関数を定義し、初期化(true)し、いつもの関数を組み込む
    public void lets_analysis_by_reuse_demo(View v){
        input_for_demo_sudoku = true;//オリジナル数独なので改めてtrueを定義
        //オリジナル数独画面で、完全に数独が解析できるかできないかを表示
        lets_analysis_demo(v);
    }

    //そのマスに数字が1,2,3,4,5,6,7,8,9だっらた
    public void btn_special_als(View v){

        SS._1_initialize();//初期化
        for (int number_ = 1; number_ <= 9; number_++) {//1~9
            SS._2_1_Nth_0_1_checker_1_TEISU(number_);
            SS._2_2_Nth_0_1_checker_2_area_3_3(number_);
            SS._2_3_Nth_0_1_checker_3_row_col_3_3(number_);
            SS._2_4_special_delete_ver2(number_);
        }
        SS.decrement_3N_to_2N();
        for (int number_ = 1; number_ <= 9; number_++) {//1~9
            SS._2_5_change_1_to_number_in_3_3_blocks(number_);
        }
        //全体の特徴量マップの中に1があったら定数に変化
        SS.find_1_in_each_f_map();
        //SS.special_special_();//そのマスに数字が1,2,3,4,5,6,7,8,9だっらた
        show_rlt_map_with_all_specified_number_();
    }

    public void btn_als_perfectly(View v) {

    }

    //この変数がtrueになったら、解析実行ボタンをロックさせる
    boolean switch_auto_boolean_lock_for_als_step = false;


    int Sleeping_Time = 500;    //時間

    //クリックしたら実行してマルチスレッドで次のボタンを表示させる
    public void als_step_demo_1_(View v) {

        //スウィッチボタンがtrueが自動解析OFFにする 解析しない
        if (switch_auto_boolean ) {
            if (analysis_number[sudoku_input_number_]) {//解析可能な数字だったらGO
                //ターゲット数字がdeleteされたら解析させない
                /*****als_step_demo_1_*******/
                als_step_demo_1_code();

            }
        } else {//自動解析を開始させる switchをfalse
            if (analysis_number[sudoku_input_number_]) {//解析可能な数字だったらGO
                if (!SS.delete_btn_number[sudoku_input_number_]) {//解析可能な数字だったらGO
                    //この自動解析関数が実行されている間はtrue
                    switch_auto_boolean_lock_for_als_step = true;

                    //特別な実行ブール変数を定義する！！
                    /*****als_step_demo_1_*******/
                    als_step_demo_1_code();


                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            // ここに3秒後に実行したい処理 数独ホームの表示
                            /*****als_step_demo_2_*******/
                            als_step_demo_2_code();

                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    // ここに3秒後に実行したい処理 数独ホームの表示
                                    /*****als_step_demo_3_*******/
                                    als_step_demo_3_code();//コード


                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            // ここに3秒後に実行したい処理 数独ホームの表示
                                            /*****als_step_demo_4_*******/
                                            als_step_demo_4_code();

                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    // ここに3秒後に実行したい処理 数独ホームの表示
                                                    /*****als_step_demo_5_*******/
                                                    als_step_demo_5_code();

                                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            // ここに3秒後に実行したい処理 数独ホームの表示
                                                            /*****als_step_demo_6_*******/
                                                            als_step_demo_6_code();

                                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                                @Override
                                                                public void run() {

                                                                    // ここに3秒後に実行したい処理 数独ホームの表示
                                                                    /*****als_step_demo_7_*******/
                                                                    als_step_demo_7_code();

                                                                    //ここに配置しないとすぐにfalseになってしまう
                                                                    //この変数がtrueになったら、解析実行ボタンをロックさせる
                                                                    switch_auto_boolean_lock_for_als_step = false;

                                                                }
                                                            }, Sleeping_Time);
                                                        }
                                                    }, Sleeping_Time);
                                                }
                                            }, Sleeping_Time);
                                        }
                                    }, Sleeping_Time);
                                }
                            }, Sleeping_Time);
                        }
                    }, Sleeping_Time);
                }
            }//解析部可能だった場合
        }//elseのかっこ

    }
    public void als_step_demo_1_code(){
        /*****als_step_demo_1_*******/
        SS._2_1_Nth_0_1_checker_1_TEISU(SS.Button_No_back);
        //それに対する関数処理をまとめる
        show_rlt_map(SS.Button_No_back);//解析マップを表示
        fgm_sudoku_view_step_2_();//ボタン１のフラグメントをボタン2のフラグメントに置換
        change_als_step_color_ = 2;
        change_als_step_color(change_als_step_color_);//解析状態の色を変化
    }
    public void als_step_demo_2_(View v) {//
        //自動解析ボタンが実行中だったら、ボタンによる実行を阻止
        if(!switch_auto_boolean_lock_for_als_step){
            als_step_demo_2_code();//コード
        }
    }
    public void als_step_demo_2_code(){
        //3*3エリアにあると表示
        SS._2_2_Nth_0_1_checker_2_area_3_3(SS.Button_No_back);
        show_rlt_map(SS.Button_No_back);//解析マップを表示
        fgm_sudoku_view_step_3_();//ボタン１のフラグメントをボタン2のフラグメントに置換
        change_als_step_color_ = 3;
        change_als_step_color(change_als_step_color_);//解析状態の色を変化
    }
    public void als_step_demo_3_(View v){//
        //自動解析ボタンが実行中だったら、ボタンによる実行を阻止
        if(!switch_auto_boolean_lock_for_als_step){
            als_step_demo_3_code();//コード
        }
    }
    public void als_step_demo_3_code(){
        //3*3エリアにあると表示
        SS._2_3_Nth_0_1_checker_3_row_col_3_3(SS.Button_No_back);
        show_rlt_map(SS.Button_No_back);//解析マップを表示
        fgm_sudoku_view_step_4_(); //ボタン１のフラグメントをボタン2のフラグメントに置換
        change_als_step_color_ = 4;
        change_als_step_color(change_als_step_color_);//解析状態の色を変化
    }
    public void als_step_demo_4_(View v){
        //自動解析ボタンが実行中だったら、ボタンによる実行を阻止
        if(!switch_auto_boolean_lock_for_als_step){
            als_step_demo_4_code();
        }
    }
    public void als_step_demo_4_code(){
        show_rlt_map_with_specified_number(SS.Button_No_back);
        fgm_sudoku_view_step_5_();//ボタン１のフラグメントをボタン2のフラグメントに置換
        change_als_step_color_ = 5;
        change_als_step_color(change_als_step_color_);//解析状態の色を変化
    }
    public void als_step_demo_5_(View v){
        //自動解析ボタンが実行中だったら、ボタンによる実行を阻止
        if(!switch_auto_boolean_lock_for_als_step){
            als_step_demo_5_code();
        }
    }
    public void als_step_demo_5_code(){
        SS._2_4_special_delete_ver2(SS.Button_No_back);
        //空白のマスに指定した数字が入る候補とする
        show_rlt_map_with_specified_number(SS.Button_No_back);
        fgm_sudoku_view_step_6_();//ボタン１のフラグメントをボタン2のフラグメントに置換
        change_als_step_color_ = 6;
        change_als_step_color(change_als_step_color_);//解析状態の色を変化

    }
    public void als_step_demo_6_(View v){
        //自動解析ボタンが実行中だったら、ボタンによる実行を阻止
        if(!switch_auto_boolean_lock_for_als_step){
            als_step_demo_6_code();
        }
    }
    public void als_step_demo_6_code(){
        //3*3ボックス内に指定数字が1つのみだったら確定
        SS._2_5_change_1_to_number_in_3_3_blocks(SS.Button_No_back);
        //確定した数字があるかどうかを表示
        show_als_tv_states(SS.comment_result_num);
        //数独内での数字で全て使われたらボタンに数字を消す
        delete_button_number_name(sudoku_input_number_);
        //空白のマスに指定した数字が入る候補とする
        show_rlt_map_with_specified_number(SS.Button_No_back);
        fgm_sudoku_view_step_7_();//ボタン１のフラグメントをボタン2のフラグメントに置換
        change_als_step_color_ = 7;
        change_als_step_color(change_als_step_color_);//解析状態の色を変化
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void als_step_demo_7_(View v){//初期化
        //実行解析ボタンが実行中だったら、ボタンによる実行を阻止
        if(!switch_auto_boolean_lock_for_als_step){
            als_step_demo_7_code();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void als_step_demo_7_code() {//初期化
        //ターゲット数独値がすべてなくなったら解析を終了させる
        int target_input_number = 0;
        for(int i = 1; i <= 9;i++){
            if(!SS.delete_btn_number[i]){//true(解析終了)だったら解析終了
                target_input_number++;
            }
        }
        //ターゲット数字がすべて解析し終わったら、[解析完了テキスト]を表示
        if(target_input_number == 0){//加算がされなかったら[終了]
            fgm_sudoku_view_step_8_perfect_();//完成したら終了





            //クリアしたら、テキストにクリア番号を書き込む



            //数独トレーニングの問題番号を代入する 問題番号は開始時に代入されている
            write_to_change_color_of_sudoku_training_for_clear(sudoku_traing_number_);



        }
        else{
            SS._1_initialize();//初期化
            fgm_sudoku_view_step_1_();//ボタン１のフラグメントをボタン2のフラグメントに置換
            change_als_step_color_ = 1;
            change_als_step_color(change_als_step_color_);//解析状態の色を変化
        }
        //数独の解析マップ　きれいに初期化する
        show_rls_map_init();

        //数独が何%埋まっているのかを確認
        show_tv_of_sudoku_percent();

    }


    //管理者用ページに移行 数独のテキストを確認
    public void Go_to_private_page(View v){
        //管理者用ページ
        setContentView(R.layout._3_5_private_page);//通常解析
    }

    //インクリメンター
    int plusplus = 0;

    //数独表テキストに書き込む 以下のコードが必要らしい 開発のバージョンによる
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void private_page_to_write(View v){

        //作成ファイル
        String fileName = "sudoku_training_table_csv.txt";//数独トレーニングのクリアかどうかのテキストファイル
        String str = "1,4,5,1,6,9,4,9,9,6,3";

        //初期値の値をテキストに反映
        TextView t = findViewById(R.id.check_to_write);
        TextView t_ = findViewById(R.id.check_to_write_sudoku);

        // try-with-resources テキストを書き込む
        try (FileOutputStream fileOutputstream = openFileOutput(fileName, Context.MODE_PRIVATE)){
            fileOutputstream.write(str.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(str.length() == 0){
            t.setText("書き込み：失敗");//読み込み
        }
        else{
            t.setText("書き込み：成功");
            t_.setText(str);
        }
    }

    //数独トレーニングでクリアした番号に相当する
    boolean[] s_training_clear_ = {false,false,false,false,false,false,false,false};

    boolean[] boolean_clear = new boolean[s_training_clear_.length];

    //数独トレーニングでクリアしたら、トレーニング数独をクリアに書き込む
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void write_to_change_color_of_sudoku_training_for_clear(int sudoku_training_num_){



        //データの保存方法 データ boolean型として保存
        SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putBoolean("s_training_clear_["+ Integer.toString(sudoku_training_num_)+"]", true);
        editor.apply();




        //データ保存 // 設定ファイルを開きます。
//        SharedPreferences sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
//        // 値の取得　数独トレニンーグクリア変数に1に変更 getBoolean getInt がある キーとして保存されている？
//        //getなので読み出し処理をしている
//        boolean_clear[sudoku_training_num_] = sharedPref.getBoolean("s_training_clear_["+ Integer.toString(sudoku_training_num_)+"]", true); // 既定値 123 を設定
        Log.d(TAG, "トレーニング番号"+Integer.toString(sudoku_training_num_)+"は"+ "クリア");

        //スマホ画面に出力
        Toast.makeText(getApplicationContext(), "トレーニング番号"+Integer.toString(sudoku_training_num_)+"は"+ "クリア", Toast.LENGTH_SHORT).show();
        // 値の設定 (起動する毎に値が +1 されます) 指定したキー

//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt("MY_KEY", intVal_ + 1);
//        editor.apply();

        //SharedPreferences の値を削除する
//        editor.remove("MY_KEY");
//        editor.commit();


//        //作成ファイル
//        String fileName = "sudoku_training_table_csv.txt";//数独トレーニングのクリアかどうかのテキストファイル
//
//        //テキスト内容
//        String training_csv_data = null;
//
//        //テキストファイルに書かれた文字を読み込む
//        try (FileInputStream fileInputStream = openFileInput(fileName);
//             BufferedReader reader= new BufferedReader(
//                     new InputStreamReader(fileInputStream, StandardCharsets.US_ASCII))) {
//
//            //読み込んだテキストファイルを文字列に落とし込む
//            String lineBuffer;
//            while( (lineBuffer = reader.readLine()) != null ) {
//                //ここでテキストを書き込む
//                training_csv_data = lineBuffer;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //初期値の値をテキストに反映
//        TextView t_ = findViewById(R.id.tv_sudoku_percent);
//        //t.setText("書き込み：失敗");
//
//        //nullではなかったらテキストビューに入力
//        if (training_csv_data != null) {
//            t_.setText("書き込み：成功");
//        } else {
//            //nullだったらエラー
//            t_.setText("書き込み：失敗");
//        }


    }

    //数独トレーニングのホームをクリックしたら読み込む、トレーニング数独をクリアを読み込む
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void read_to_change_color_of_sudoku_training_for_clear(int sudoku_training_number_){

        //作成ファイル
        String fileName = "sudoku_training_table_csv.txt";//数独トレーニングのクリアかどうかのテキストファイル
        String training_csv_data = "null,0,0,0,0,0,0,0,0,0";//0はfalse,1はtrue

        //数独トレーニングテキストのcsvデータをコンマで区切る
        String[] training_number_ = training_csv_data.split(",", 0);
        //初期値の値をテキストに反映
        TextView t_2 = findViewById(R.id.check_to_read2);
        TextView t_3 = findViewById(R.id.check_to_read3);

        //テキスト内容
        String text = null;

        // try-with-resources
        try (FileInputStream fileInputStream = openFileInput(fileName);
             BufferedReader reader= new BufferedReader(
                     new InputStreamReader(fileInputStream, StandardCharsets.US_ASCII))) {

            String lineBuffer;
            while( (lineBuffer = reader.readLine()) != null ) {
                //ここでテキストを書き込む
                text = lineBuffer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert text != null;
        String[] sudoku_reader = text.split(",");//コンマで区切る

        int i = 0;
        boolean working_reading = true;

        //i = sudoku_reader[1];
        String s = sudoku_reader[1];
        i = Integer.valueOf(s);

        t_2.setText(s);
        t_3.setText(i);
    }



    //数独クイズクラスからオブジェクトを生成
    public class_sudoku_quiz S_QUIZ = new class_sudoku_quiz();
    //数独クイズの問題番号
    int quiz_number_ = 0;


    //数独クイズから数独クイズの開始ボタンをタッチしたとき
    public void start_sudoku_quiz(View v) throws InterruptedException {
        //クリックしたら飛ぶレイアウトページを選択する オリジナル問題にいく
        setContentView(R.layout._3_4_start_sudoku_qiuz_);
        //数独クイズを設定して、表示させる
        set_and_show_sudoku_quiz(quiz_number_);
    }

    //数独クイズを設定して、表示させる
    public void set_and_show_sudoku_quiz(int quiz_number__) throws InterruptedException {
        //クイズの番号を表示
        TextView tv_N_quiz_th_ = findViewById(R.id.tv_N_quiz_th_);
        tv_N_quiz_th_.setText("第"+valueOf(quiz_number_ + 1)+"問");
        //[問題]クイズ内容をテキストに反映
        TextView t = findViewById(R.id.tv_s_quiz_problem);
        t.setText(S_QUIZ.s_quiz[quiz_number__][0]);
        //クイズの選択肢のボタンを配列化
        Button[] btn_choice = {null,findViewById(S_QUIZ.btn_sudoku_quiz[1]), findViewById(S_QUIZ.btn_sudoku_quiz[2]),
                findViewById(S_QUIZ.btn_sudoku_quiz[3]),findViewById(S_QUIZ.btn_sudoku_quiz[4])};
        //クイズの選択肢をランダムに初期化
        S_QUIZ.setS_quiz(quiz_number__);
        //ランダムにした選択肢を表示させる
        btn_choice[1].setText(S_QUIZ.set_s_quiz_[1]);
        btn_choice[2].setText(S_QUIZ.set_s_quiz_[2]);
        btn_choice[3].setText(S_QUIZ.set_s_quiz_[3]);
        btn_choice[4].setText(S_QUIZ.set_s_quiz_[4]);
        //正解か不正解を表示させる
        TextView t_ = findViewById(R.id.tv_sudoku_quiz_true_or_false);
        t_.setText("");
        //ボタンの色を変える
        //ボタンのカラーを定義
        Drawable btn_clr_now = ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rtgl_green_yellow, null);
        //ボタンのカラーを設定
        for(int i = 1;i<=4; i++){
            btn_choice[i].setBackground(btn_clr_now);
        }

    }

    //数独クイズの選択肢を表示しているときに、ボタンを作動させないように制御
    boolean sudoku_quiz_off_switch = true;

    //数独クイズのボタン選択肢を同じonClick関数名を使用
    public void btn_choice_sudoku_problem_1(View v) {
        //数独クイズの選択肢を表示しているときに、ボタンを作動させないように制御
        if(sudoku_quiz_off_switch){
            int choice_number = 1;//選択肢番号ボタン
            btn_choice_sudoku_problem(choice_number);
        }
    }
    //数独クイズのボタン選択肢を同じonClick関数名を使用
    public void btn_choice_sudoku_problem_2(View v) {
        //数独クイズの選択肢を表示しているときに、ボタンを作動させないように制御
        if(sudoku_quiz_off_switch){
            int choice_number = 2;//選択肢番号ボタン
            btn_choice_sudoku_problem(choice_number);
        }
    }
    //数独クイズのボタン選択肢を同じonClick関数名を使用
    public void btn_choice_sudoku_problem_3(View v) {
        //数独クイズの選択肢を表示しているときに、ボタンを作動させないように制御
        if(sudoku_quiz_off_switch){
            int choice_number = 3;//選択肢番号ボタン
            btn_choice_sudoku_problem(choice_number);
        }
    }
    //数独クイズのボタン選択肢を同じonClick関数名を使用
    public void btn_choice_sudoku_problem_4(View v) {
        //数独クイズの選択肢を表示しているときに、ボタンを作動させないように制御
        if(sudoku_quiz_off_switch){
            int choice_number = 4;//選択肢番号ボタン
            btn_choice_sudoku_problem(choice_number);
        }
    }
    //選択肢ボタンの処理
    public void btn_choice_sudoku_problem(int choice_number) {

        //クイズの選択肢のボタンを配列化
        Button[] btn_choice = {null,findViewById(S_QUIZ.btn_sudoku_quiz[1]), findViewById(S_QUIZ.btn_sudoku_quiz[2]),
                findViewById(S_QUIZ.btn_sudoku_quiz[3]),findViewById(S_QUIZ.btn_sudoku_quiz[4])};
        //もし正解のキー番号と一致したら
        if(choice_number == S_QUIZ.correct_number){
            //数独クイズの選択肢ボタンをfalseで無効化させる
            sudoku_quiz_off_switch = false;
            //ボタンの色を変えるカラーを定義
            Drawable btn_clr_now = ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rtgl_deep_emerald, null);
            //ボタンのカラーを設定
            btn_choice[S_QUIZ.correct_number].setBackground(btn_clr_now);

            //正解か不正解を表示させる
            TextView t_ = findViewById(R.id.tv_sudoku_quiz_true_or_false);
            t_.setText("正解です。よっ！天才！");

            //数独のクイズを次に行く
            quiz_number_++;
            //数独クイズがすべて終わったら、終了させる 11
            if(quiz_number_ <= 10){
                //他のボタンのクリックを無効にさせる
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //数独クイズを設定して、表示させる
                        try {
                            set_and_show_sudoku_quiz(quiz_number_);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //数独クイズの選択肢ボタンをtrueで有効化
                        sudoku_quiz_off_switch = true;

                    }
                }, 3000);
            }
            else{
                //正解か不正解を表示させる
                t_.setText("全問正解です。まじで天才！");
            }

        }
        else{

            //正解か不正解を表示させる
            TextView t_ = findViewById(R.id.tv_sudoku_quiz_true_or_false);
            t_.setText("残念。不正解です。");

            // ここに3秒後に実行したい処理 数独ホームの表示
            setContentView(R.layout._1_sudoku_home);
            SetSudokuDemoTrainOriginal();

        }

    }


    //数独表テキストに読み込む
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void private_page_to_read(View v){

        //初期値の値をテキストに反映
        TextView t = findViewById(R.id.check_to_read);
        TextView t_ = findViewById(R.id.check_to_read_sudoku);

        String text = null;

        //作成ファイル
        String fileName = "sudoku_training_table_csv.txt";

        // try-with-resources
        try (FileInputStream fileInputStream = openFileInput(fileName);
             BufferedReader reader= new BufferedReader(
                     new InputStreamReader(fileInputStream, StandardCharsets.US_ASCII))) {

            String lineBuffer;
            while( (lineBuffer = reader.readLine()) != null ) {
                //ここでテキストを書き込む
                text = lineBuffer;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //nullではなかったらテキストビューに入力
        if (text != null) {
            t.setText("読み込み：成功");
            t_.setText(text);
        } else {
            //nullだったらエラー
            t.setText("読み込み：失敗");
        }
    }

    //テキストを読み込んで、数独表に置き換える
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void private_page_to_read_sudoku(View v){

        //初期値の値をテキストに反映
        TextView t_2 = findViewById(R.id.check_to_read2);
        TextView t_3 = findViewById(R.id.check_to_read3);

        //テキスト内容
        String text = null;
        //作成ファイル
        String fileName = "sudoku_training_table_csv.txt";//数独トレーニングのクリアかどうかのテキストファイル
        // try-with-resources
        try (FileInputStream fileInputStream = openFileInput(fileName);
             BufferedReader reader= new BufferedReader(
                     new InputStreamReader(fileInputStream, StandardCharsets.US_ASCII))) {

            String lineBuffer;
            while( (lineBuffer = reader.readLine()) != null ) {
                //ここでテキストを書き込む
                text = lineBuffer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert text != null;
        String[] sudoku_reader = text.split(",");//コンマで区切る

        int i = 0;
        boolean working_reading = true;

        //i = sudoku_reader[1];
        String s = sudoku_reader[1];
        i = Integer.valueOf(s);

        t_2.setText(s);
        t_3.setText(i);


    }




    //解析中の際にターゲット数字を維持する
    int change_als_step_color_ = 1;//解析ボタンの色変えカウンター
    public void change_als_step_color(int change_als_step_color){
        //今のボタンをいつもの色に変えて、次のボタンを特別色にする
        TextView btn_set_now = findViewById(SS.fgm_fgm_btn_als_step_[change_als_step_color]);//今のボタン
        int change_als_step_color__ = change_als_step_color - 1;//新たに変数を加える
        //解析ボタンは合計7つ
        if(change_als_step_color__ == 0){
            change_als_step_color__ = 7;//8までいったら1に戻す
        }
        TextView btn_set_next = findViewById(SS.fgm_fgm_btn_als_step_[change_als_step_color__]);//次のボタン
        //リソースから作成したDrawableのリソースを取得
        Drawable btn_clr_now = ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rtgl_green_yellow, null);
        //ボタンにDrawableを適用する
        btn_set_next.setBackground(btn_clr_now);
        //今のボタンの名前に変更すし、前のボタンの名前も変更する
        //リソースから作成したDrawableのリソースを取得
        Drawable btn_color_back = ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rtgl_sky_blue, null);
        //ボタンにDrawableを適用する
        btn_set_now.setBackground(btn_color_back);
    }




    //以前と同じ数独値を入力するとき、置き換える
    public void btn_tv_s_table_(int tate_,int yoko_,int sudoku_input_number__) {
        //以前と同じ数独値を入力するとき、置き換える
        Button t = findViewById(SS.button_s[tate_][yoko_]);//数独ボタンに記入
         if(SS.s[tate_][yoko_] == sudoku_input_number__){ //以前と同じ値を入力すると置換
            SS.s[tate_][yoko_] = 0;//数独値を0
            t.setText("");//空文字を入力
        }
        else{
            //以前と同じ数独値を入力するとき、置き換える
            SS.s[tate_][yoko_] = sudoku_input_number__;//数独表に代入
            t.setText(valueOf(SS.s[tate_][yoko_]));//数字が確定していたら、値を代入
        }
    }

    //自動解析ボタンをbooleanで指定
    boolean switch_auto_boolean = true;
    boolean test_auto_switch = true;
    //自動解析スイッチのbooleanの状態を取得する
    public void switch_auto_als_(View v) {

        //1のときのみボタンナンバーカラーだったら、実行する
        CompoundButton switch_auto_als_ = (CompoundButton) findViewById(R.id.switch_auto_als);

        //入力数値1（解析状況１）の時のみ、自動解析をするかどうかを選択できる
        if (change_als_step_color_ == 1) {

            if (switch_auto_boolean) {//自動スイッチがonのとき
                switch_auto_boolean = false;//ボタンをfalse(自動スイッチがon)にする
                switch_auto_als_.setChecked(false);//クリックしても常にtrue
            } else {
                switch_auto_boolean = true;
                switch_auto_als_.setChecked(true);//クリックしても常にtrue
            }

        }

        //解析ステップが1以外の時、常に五反の状態を維持する
        if(change_als_step_color_ != 1) {
            if(!switch_auto_boolean){
                switch_auto_als_.setChecked(false);//クリックしても常にtrue
            }
            else{
                switch_auto_als_.setChecked(true);//クリックしても常にtrue
            }

        }

        //解析中に「自動解析ボタン」のOFFとONを交換できてしまう・・・
        //解析ステップ1の時に確定して、それ以降は確定
        Toast.makeText(getApplicationContext(), "自動解析ボタン : " + switch_auto_boolean, Toast.LENGTH_SHORT).show();

    }



    /***********************解析ボタン1番目の時のみ入力番号を変換***************************************/
    public void OnBnClickedButtonNoChanger_assist(int number_){
        if(SS.booleans_btn_als_step_[1]) {//解析ボタン1番目の時のみ入力番号を変換
            SS.Button_No_now = number_;//今のボタン
            OnBnClickedButtonNoChanger();//前のボタンと一致していなかったら色を変える
            SS.Button_No_back = number_;//前のボタンとして保存
        }
    }
    /***********************数字入力ボタン 他のボタンに移ったらボタンの色を維持*******************************/
    public void OnBnClickedButtonNoChanger() {
        Button btn_set_now = findViewById(SS.input_number[SS.Button_No_now]);//今のボタン
        Button btn_set_back = findViewById(SS.input_number[SS.Button_No_back]);//前のボタン
        //今の値と前の値が一致しなかったら、
        if (SS.Button_No_back != SS.Button_No_now) {
            //リソースから作成したDrawableのリソースを取得
            Drawable btn_clr_now = ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rtgl_green_yellow, null);
            //ボタンにDrawableを適用する
            btn_set_back.setBackground(btn_clr_now);
            //今のボタンの名前に変更すし、前のボタンの名前も変更する
            //リソースから作成したDrawableのリソースを取得
            Drawable btn_color_back = ResourcesCompat.getDrawable(getResources(), R.drawable.btn_rtgl_sky_blue, null);
            //ボタンにDrawableを適用する
            btn_set_now.setBackground(btn_color_back);
        }
    }
    /*********************************画面から元に戻るボタン********初期化が必要************/
    public void return_home(View v){//初期化が必要
        setContentView(R.layout._1_sudoku_home);
        SetSudokuDemoTrainOriginal();//数独ホーム画面
        SS.sudoku_initialize();//数独初期化
        change_als_step_color_ = 1;//数独ボタンの初期化(解析ボタン)
        input_for_demo_sudoku = false;
        switch_auto_boolean = true;//自動解析のbooleanを初期化
        switch_auto_boolean_lock_for_als_step = false;//自動解析ボタンのときに、解析ボタンを実行させない
        quiz_number_ = 0;//数独クイズの問題番号
        sudoku_quiz_off_switch = true;//数独クイズの選択肢ボタンをtrueで有効化
        //解析できたら、すべてを初期化する
        for(int i=1;i<=9;i++){//解析できなかった数字だったら、解析できないようにストップさせる
            analysis_number[i] = true;
        }
    }
    /*********************************数独表ボタンの関数*******************************/
    public void btn_s_table_11(View v){
        btn_tv_s_table_(1,1,sudoku_input_number_);
    }
    public void btn_s_table_12(View v){
        btn_tv_s_table_(1,2,sudoku_input_number_);
    }
    public void btn_s_table_13(View v){
        btn_tv_s_table_(1,3,sudoku_input_number_);
    }
    public void btn_s_table_14(View v){
        btn_tv_s_table_(1,4,sudoku_input_number_);
    }
    public void btn_s_table_15(View v){
        btn_tv_s_table_(1,5,sudoku_input_number_);
    }
    public void btn_s_table_16(View v){
        btn_tv_s_table_(1,6,sudoku_input_number_);
    }
    public void btn_s_table_17(View v){
        btn_tv_s_table_(1,7,sudoku_input_number_);
    }
    public void btn_s_table_18(View v){
        btn_tv_s_table_(1,8,sudoku_input_number_);
    }
    public void btn_s_table_19(View v){
        btn_tv_s_table_(1,9,sudoku_input_number_);
    }
    public void btn_s_table_21(View v){
        btn_tv_s_table_(2,1,sudoku_input_number_);
    }
    public void btn_s_table_22(View v){
        btn_tv_s_table_(2,2,sudoku_input_number_);
    }
    public void btn_s_table_23(View v){
        btn_tv_s_table_(2,3,sudoku_input_number_);
    }
    public void btn_s_table_24(View v){
        btn_tv_s_table_(2,4,sudoku_input_number_);
    }
    public void btn_s_table_25(View v){
        btn_tv_s_table_(2,5,sudoku_input_number_);
    }
    public void btn_s_table_26(View v){
        btn_tv_s_table_(2,6,sudoku_input_number_);
    }
    public void btn_s_table_27(View v){
        btn_tv_s_table_(2,7,sudoku_input_number_);
    }
    public void btn_s_table_28(View v){
        btn_tv_s_table_(2,8,sudoku_input_number_);
    }
    public void btn_s_table_29(View v){
        btn_tv_s_table_(2,9,sudoku_input_number_);
    }
    public void btn_s_table_31(View v){
        btn_tv_s_table_(3,1,sudoku_input_number_);
    }
    public void btn_s_table_32(View v){
        btn_tv_s_table_(3,2,sudoku_input_number_);
    }
    public void btn_s_table_33(View v){
        btn_tv_s_table_(3,3,sudoku_input_number_);
    }
    public void btn_s_table_34(View v){
        btn_tv_s_table_(3,4,sudoku_input_number_);
    }
    public void btn_s_table_35(View v){
        btn_tv_s_table_(3,5,sudoku_input_number_);
    }
    public void btn_s_table_36(View v){
        btn_tv_s_table_(3,6,sudoku_input_number_);
    }
    public void btn_s_table_37(View v){
        btn_tv_s_table_(3,7,sudoku_input_number_);
    }
    public void btn_s_table_38(View v){
        btn_tv_s_table_(3,8,sudoku_input_number_);
    }
    public void btn_s_table_39(View v){
        btn_tv_s_table_(3,9,sudoku_input_number_);
    }
    public void btn_s_table_41(View v){
        btn_tv_s_table_(4,1,sudoku_input_number_);
    }
    public void btn_s_table_42(View v){
        btn_tv_s_table_(4,2,sudoku_input_number_);
    }
    public void btn_s_table_43(View v){
        btn_tv_s_table_(4,3,sudoku_input_number_);
    }
    public void btn_s_table_44(View v){
        btn_tv_s_table_(4,4,sudoku_input_number_);
    }
    public void btn_s_table_45(View v){
        btn_tv_s_table_(4,5,sudoku_input_number_);
    }
    public void btn_s_table_46(View v){
        btn_tv_s_table_(4,6,sudoku_input_number_);
    }
    public void btn_s_table_47(View v){
        btn_tv_s_table_(4,7,sudoku_input_number_);
    }
    public void btn_s_table_48(View v){
        btn_tv_s_table_(4,8,sudoku_input_number_);
    }
    public void btn_s_table_49(View v){
        btn_tv_s_table_(4,9,sudoku_input_number_);
    }
    public void btn_s_table_51(View v){
        btn_tv_s_table_(5,1,sudoku_input_number_);
    }
    public void btn_s_table_52(View v){
        btn_tv_s_table_(5,2,sudoku_input_number_);
    }
    public void btn_s_table_53(View v){
        btn_tv_s_table_(5,3,sudoku_input_number_);
    }
    public void btn_s_table_54(View v){
        btn_tv_s_table_(5,4,sudoku_input_number_);
    }
    public void btn_s_table_55(View v){
        btn_tv_s_table_(5,5,sudoku_input_number_);
    }
    public void btn_s_table_56(View v){
        btn_tv_s_table_(5,6,sudoku_input_number_);
    }
    public void btn_s_table_57(View v){
        btn_tv_s_table_(5,7,sudoku_input_number_);
    }
    public void btn_s_table_58(View v){
        btn_tv_s_table_(5,8,sudoku_input_number_);
    }
    public void btn_s_table_59(View v){
        btn_tv_s_table_(5,9,sudoku_input_number_);
    }
    public void btn_s_table_61(View v){
        btn_tv_s_table_(6,1,sudoku_input_number_);
    }
    public void btn_s_table_62(View v){
        btn_tv_s_table_(6,2,sudoku_input_number_);
    }
    public void btn_s_table_63(View v){
        btn_tv_s_table_(6,3,sudoku_input_number_);
    }
    public void btn_s_table_64(View v){
        btn_tv_s_table_(6,4,sudoku_input_number_);
    }
    public void btn_s_table_65(View v){
        btn_tv_s_table_(6,5,sudoku_input_number_);
    }
    public void btn_s_table_66(View v){
        btn_tv_s_table_(6,6,sudoku_input_number_);
    }
    public void btn_s_table_67(View v){
        btn_tv_s_table_(6,7,sudoku_input_number_);
    }
    public void btn_s_table_68(View v){
        btn_tv_s_table_(6,8,sudoku_input_number_);
    }
    public void btn_s_table_69(View v){
        btn_tv_s_table_(6,9,sudoku_input_number_);
    }
    public void btn_s_table_71(View v){
        btn_tv_s_table_(7,1,sudoku_input_number_);
    }
    public void btn_s_table_72(View v){
        btn_tv_s_table_(7,2,sudoku_input_number_);
    }
    public void btn_s_table_73(View v){
        btn_tv_s_table_(7,3,sudoku_input_number_);
    }
    public void btn_s_table_74(View v){
        btn_tv_s_table_(7,4,sudoku_input_number_);
    }
    public void btn_s_table_75(View v){
        btn_tv_s_table_(7,5,sudoku_input_number_);
    }
    public void btn_s_table_76(View v){
        btn_tv_s_table_(7,6,sudoku_input_number_);
    }
    public void btn_s_table_77(View v){
        btn_tv_s_table_(7,7,sudoku_input_number_);
    }
    public void btn_s_table_78(View v){
        btn_tv_s_table_(7,8,sudoku_input_number_);
    }
    public void btn_s_table_79(View v){
        btn_tv_s_table_(7,9,sudoku_input_number_);
    }
    public void btn_s_table_81(View v){
        btn_tv_s_table_(8,1,sudoku_input_number_);
    }
    public void btn_s_table_82(View v){
        btn_tv_s_table_(8,2,sudoku_input_number_);
    }
    public void btn_s_table_83(View v){
        btn_tv_s_table_(8,3,sudoku_input_number_);
    }
    public void btn_s_table_84(View v){
        btn_tv_s_table_(8,4,sudoku_input_number_);
    }
    public void btn_s_table_85(View v){
        btn_tv_s_table_(8,5,sudoku_input_number_);
    }
    public void btn_s_table_86(View v){
        btn_tv_s_table_(8,6,sudoku_input_number_);
    }
    public void btn_s_table_87(View v){
        btn_tv_s_table_(8,7,sudoku_input_number_);
    }
    public void btn_s_table_88(View v){
        btn_tv_s_table_(8,8,sudoku_input_number_);
    }
    public void btn_s_table_89(View v){
        btn_tv_s_table_(8,9,sudoku_input_number_);
    }
    public void btn_s_table_91(View v){
        btn_tv_s_table_(9,1,sudoku_input_number_);
    }
    public void btn_s_table_92(View v){
        btn_tv_s_table_(9,2,sudoku_input_number_);
    }
    public void btn_s_table_93(View v){
        btn_tv_s_table_(9,3,sudoku_input_number_);
    }
    public void btn_s_table_94(View v){
        btn_tv_s_table_(9,4,sudoku_input_number_);
    }
    public void btn_s_table_95(View v){
        btn_tv_s_table_(9,5,sudoku_input_number_);
    }
    public void btn_s_table_96(View v){
        btn_tv_s_table_(9,6,sudoku_input_number_);
    }
    public void btn_s_table_97(View v){
        btn_tv_s_table_(9,7,sudoku_input_number_);
    }
    public void btn_s_table_98(View v){
        btn_tv_s_table_(9,8,sudoku_input_number_);
    }
    public void btn_s_table_99(View v){
        btn_tv_s_table_(9,9,sudoku_input_number_);
    }
    /*********************************フラグメント****************************************/
    //フラグメント 特徴マップ
    private void fgm_sudoku_view_step_8_perfect_() {//解析ボタンのフラグメントだから、これはいらない
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_of_fgm_btn_als_step_1, new fgm_sudoku_view_step_8_perfect());
        transaction.commit();//処理実行
    }
    //フラグメント 特徴マップ
    private void fgm_sudoku_tv_demo_for_demo_() {//解析ボタンのフラグメントだから、これはいらない
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_button_sudoku_demo, new fgm_sudoku_tv_demo_for_demo());
        transaction.commit();//処理実行
    }
    //フラグメント 特徴マップ
    private void fgm_sudoku_analysis_step_demo_for_demo() {//解析ボタンのフラグメントだから、これはいらない
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_btn_als_step_demo_for_demo, new fgm_sudoku_analysis_step_demo_for_demo());
        transaction.commit();//処理実行
    }
    //フラグメント 特徴マップ
    private void fgm_sudoku_analysis_step_demo() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_btn_als_step_demo, new fgm_sudoku_analysis_step_demo());
        transaction.commit();//処理実行
    }
    //フラグメント 特徴マップ
    private void fgm_sudoku_input_table_3_3_() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_sudoku_input_table_3_3, new fgm_sudoku_input_table_3_3());
        transaction.commit();//処理実行
    }
    //解析ステップをクリックと同時に遷移
    private void fgm_sudoku_view_step_1_() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_of_fgm_btn_als_step_1, new fgm_sudoku_view_step_1());
        transaction.commit();//処理実行
    }
    private void fgm_sudoku_view_step_2_() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_of_fgm_btn_als_step_1, new fgm_sudoku_view_step_2());
        transaction.commit();//処理実行
    }
    private void fgm_sudoku_view_step_3_() {//同じフラグメントに別のボタンを配置する
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_of_fgm_btn_als_step_1, new fgm_sudoku_view_step_3());
        transaction.commit();//処理実行
    }
    private void fgm_sudoku_view_step_4_() {//同じフラグメントに別のボタンを配置する
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_of_fgm_btn_als_step_1, new fgm_sudoku_view_step_4());
        transaction.commit();//処理実行
    }
    private void fgm_sudoku_view_step_5_() {//同じフラグメントに別のボタンを配置する
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_of_fgm_btn_als_step_1, new fgm_sudoku_view_step_5());
        transaction.commit();//処理実行
    }
    private void fgm_sudoku_view_step_6_() {//同じフラグメントに別のボタンを配置する
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_of_fgm_btn_als_step_1, new fgm_sudoku_view_step_6());
        transaction.commit();//処理実行
    }
    private void fgm_sudoku_view_step_7_() {//同じフラグメントに別のボタンを配置する
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fgm_of_fgm_btn_als_step_1, new fgm_sudoku_view_step_7());
        transaction.commit();//処理実行
    }
    //フラグメント 特徴マップ
    private void fgm_sudoku_rlt_map_demo_demo_() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_button_sudoku_demo_rlt_map_demo, new fgm_sudoku_rlt_map_demo());
        transaction.commit();//処理実行
    }
    //ボタン数独のフラグメントを作成
    private void fgm_sudoku_table_demo_() {
        Fragment fragment_button_sudoku_demo_demo;//フラグメントのオブジェクト化
        fragment_button_sudoku_demo_demo = new fgm_sudoku_table();//javaクラスから生成させる
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//FragmentTransactionを生成
        transaction.replace(R.id.fragment_button_sudoku_demo, fragment_button_sudoku_demo_demo);
        transaction.commit();//処理実行
    }
    //ボタン数独のフラグメントを作成
    private void fgm_sudoku_analysis_states_demo_() {
        Fragment fragment_sudoku_analysis_states_demo;//フラグメントのオブジェクト化
        fragment_sudoku_analysis_states_demo = new fgm_sudoku_analysis_states_demo();//javaクラスから生成させる
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//FragmentTransactionを生成
        transaction.replace(R.id.fragment_button_sudoku_analysis_states_demo, fragment_sudoku_analysis_states_demo);
        transaction.commit();//処理実行
    }
    //ボタン数独のフラグメントを作成
    private void fgm_sudoku_input_number_() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//FragmentTransactionを生成
        transaction.replace(R.id.fragment_button_sudoku_input, new fgm_sudoku_input_number());//割り当てる
        transaction.commit();//処理実行
    }
    //ボタン数独のフラグメントを作成
    private void fgm_sudoku_analysis_states_() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//FragmentTransactionを生成
        transaction.replace(R.id.fragment_button_sudoku_analysis_states, new fgm_sudoku_analysis_states());//割り当てる
        transaction.commit();//処理実行
    }
    //ボタン数独のフラグメントを作成
    private void fgm_sudoku_table_() {
        Fragment fgm_sudoku_table_;//フラグメントのオブジェクト化
        fgm_sudoku_table_ = new fgm_sudoku_table();//javaクラスから生成させる
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//FragmentTransactionを生成
        transaction.replace(R.id.fragment_button_sudoku, fgm_sudoku_table_);//割り当てる
        transaction.commit();//処理実行
    }



}
