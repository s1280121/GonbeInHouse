# 権兵衛オーダー

###### 権兵衛とは私がアルバイトしている会津若松市の居酒屋です  

### アプリ画面
<img width="989" alt="画面遷移１" src="https://user-images.githubusercontent.com/87113276/199092705-af5145d9-0915-43d7-a10d-1b322f1a0f32.png">
<img width="989" alt="画面遷移２" src="https://user-images.githubusercontent.com/87113276/199092711-feb47184-89f0-4cd3-a0cf-bf9bf64f5a85.png">

#### 機能概要
実際に注文は出来ないが、居酒屋権兵衛のテイクアウト注文や口コミ投稿がスマホで出来る。  
・ログイン機能  
・ショッピングカート機能  
・口コミ機能  

## 開発環境
### OS・環境/言語
対象OS：Android 12  
開発環境/言語：Android Studio Dolphin | 2021.3.1 /  Kotlin 1.7.20

### 開発人数
1人

## こだわったポイント
・Firebaseを使い、データベースやログイン機能を作成した。  
・文字列をstrings.xmlに定義した   

## 不具合・課題
1,メニューの数が一定以上超えるとカートの追加ボタンがバグる。(menulist2.jsonを読み込んだ場合)    
2,口コミ画面で、画像がうまく表示できない  
3,Activityを減らしFragmentで実装する  
4,サインアップはできるがログインし直せない  
5,プロフィール画面、設定画面を作る  

## 自己評価
RecyclerViewやFirebaseなど難しい技術ばかりでしたが、調べながらなんとか形にすることが出来た。しかし、口コミ機能で写真やユーザー名を正しく表示出来ず、またメニューが多すぎるとバグが起きてしまった。また、参考にした動画が古くバージョンの違いでエラーが出てしまい、解決するのに時間がかかった。 