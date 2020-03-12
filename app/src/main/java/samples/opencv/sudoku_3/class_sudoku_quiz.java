package samples.opencv.sudoku_3;

import android.annotation.SuppressLint;

import java.util.Random;

public class class_sudoku_quiz {

    //数独のクイズ問題集 問題内容、正解、不正解、不正解、不正解
    String[][] s_quiz = {{"数独の初期配置の最少個数はいくつか","17個","16個","18個","19個",},
            {"「数独においてヒントが16個以下のものは解法を持ちえない」ということを証明した人物はだれか","ゲイリー・マクガイア","トーマス・エジソン","エジソン・カバーニ","ガリレオ・ガリレイ"},
            {"数独はいくつの数字で構成されているか","9個","8個","10個","11個"},
            {"数独のマスの合計数はいくつか","81個","80個","100個","101個"},
            {"大学入試で数独が出題された大学はどこか","慶応義塾大学","デジタルハリウッド大学","天使大学","サイバー大学"},
            {"数独を英語で表すとどれ","Sudoku","Suudoku","Syudoku","suddoku"},
            {"無料で遊べる数独サイト「ナンプレ20」では毎日いくつの問題が出題されているか","20","1","200","2020"},
            {"数独とは何の略か","数字は独身に限る","数字は独学に限る","数は独学に限る","数学は独学に限る"},
            {"「数独」はどの会社が商標登録しているか","株式会社ニコリ","株式会社ニトリ","株式会社ニヤリ","株式会社ニゴリ"},
            {"日本に数独が初めて紹介されたのはいつか","1980年頃","1960年頃","1940年頃","1920年頃"},
            {"このアプリ作成者はなぜこのアプリを作ったのか","入社まで時間があったから","数独が制作者を大きく成長させたから","数独を幅広く知ってもらいたいから","数独に命を救われたから"},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},
            {"","","","",""},};

    //数独クイズの問題選択肢
    int[] btn_sudoku_quiz = {0,R.id.btn_s_quiz_choice1,R.id.btn_s_quiz_choice2,R.id.btn_s_quiz_choice3,R.id.btn_s_quiz_choice4};


    //選択肢をランダムに設定し、再度出題しやすい変数を設ける
    String[] set_s_quiz_ = {"","","","",""};

    //正解がどこに含まれているかをチェックする
    int correct_number;

    //数独クイズを出題しやすいように、ランダムで並び替える
    @SuppressLint("Assert")//警告
    public void setS_quiz(int number_) throws InterruptedException {
        //重複しているかをチェック
        boolean[] check_override = {false,true,true,true,true};
        //選択肢用にインクリメントさせる
        int increment_set_quiz = 1;
        //ランダムを発生
        Random random = new Random();
        //乱数を発生 問題の選択肢が1~4までに配列しているため
        while (true){
            //1~4までを生成する
            int randomValue = random.nextInt(4 ) + 1;
            //乱数番目の配列にインクリメント番号の数独クイズの選択肢を入れる
            if(check_override[randomValue]){
                //i番目のクイズの答えを乱数番目に入れる
                set_s_quiz_[randomValue] = s_quiz[number_][increment_set_quiz];
                //s_quiz[][1]に正解が入っている 正解が入っている番号をキー変数として保持
                if(increment_set_quiz == 1){
                    correct_number = randomValue;//乱数を発生させても、正解の番号を保持
                }
                //問題の選択肢をインクリメント
                increment_set_quiz++;
                //選ばれた乱数番号はfalseにする
                check_override[randomValue] = false;
            }

            //選択肢4つすべて埋まったかを確認
            int increment_if_4_=0;
            //拡張for文で値を全て取得
            for(boolean if_4_ : check_override){
                //falseをカウント
                if(!if_4_){
                    increment_if_4_++;
                }
            }
            //全ての選択肢が全てfalseに変わっていたらbreak
            if(increment_if_4_ == check_override.length) break;

        }


//        wait(5000);


    }




    public void initialize_sudoku_quiz(){

    }


}
