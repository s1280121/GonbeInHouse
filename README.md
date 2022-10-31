# 権兵衛オーダー

対象OS：Android 12  
開発環境/言語：Android Studio Dolphin | 2021.3.1 /  Kotlin 1.7.20  

機能概要：実際に注文は出来ないが、居酒屋権兵衛のテイクアウトの注文や口コミ投稿がスマホで出来る。  
・ログイン機能  
・ショッピングカート的機能   
・口コミ機能

画面概要：
・アカウント登録画面    
・注文画面    
・口コミ画面    
・設定風画面  
<img width="989" alt="画面遷移１" src="https://user-images.githubusercontent.com/87113276/199092705-af5145d9-0915-43d7-a10d-1b322f1a0f32.png">
<img width="989" alt="画面遷移２" src="https://user-images.githubusercontent.com/87113276/199092711-feb47184-89f0-4cd3-a0cf-bf9bf64f5a85.png">

    
コンセプト：家でも権兵衛の味を楽しめる。

こだわったポイント：Firebaseを使い、データベースやログイン機能を作成した。

デザイン面でこだわったポイント：背景やボタンの色合いを調整し統一感を出した。また、商品をカートに入れたら"カートに追加"ボタンと"個数の追加・削除"ボタンが切り替わるように実装出来た。

<インターンでのフィードバックを受けて>  
・文字列をstrings.xmlに定義した  
・ActivityをFragmentに切り替えた(まだ未完)  

<課題>  
1,メニューの数が一定以上超えるとカートへの追加ボタンがバグる。(menulist2.jsonを読み込んだ場合)    
2,口コミ画面で、ユーザー名と画像がうまく表示できない  
3,Activityを減らしFragmentで実装する
4,サインアップはできるがログインし直せない
5,プロフィール画面を作ったり設定画面を完成させたりする

自己評価：まだ直さなければならない所は沢山あるが、大体作りたい機能を完成させることができて良かった。また、インターンで受けたフィードバックを少ししか直せていないため、改良していきたい。
