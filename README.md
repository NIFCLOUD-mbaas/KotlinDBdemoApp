# 【Android】ニフクラ mobile backend を体験しよう！ for Kotlin

![画像2](/readme-img/001.png)

## 概要
* AndroidStudioで作成したAndroidアプリから、[ニフクラ mobile backend](https://mbaas.nifcloud.com/)へデータ登録を行うサンプアプリです
* アプリを起動して「START DEMO」ボタンをタップするとクラウドにデータを保存できます
* 簡単な操作ですぐに [ ニフクラ mobile backend ](https://mbaas.nifcloud.com/)の機能を体験いただけます

##  ニフクラ mobile backend って何？？
スマートフォンアプリのバックエンド機能（プッシュ通知・データストア・会員管理・ファイルストア・SNS連携・位置情報検索・スクリプト）が**開発不要**、しかも基本**無料**(注1)で使えるクラウドサービス！

注1：詳しくは[こちら](https://mbaas.nifcloud.com/price.htm)をご覧ください

## 動作環境

* MacOS Monterey version 12.5
* Android Studio Chipmunk | 2021.2.1 Patch 2
* Pixel 2 - Android 13 (Simulator)
* Android SDK v3
  - SDK v2系だと動作しないので注意

※上記内容で動作確認をしています。

## 手順
### 1. ニフクラ mobile backend の会員登録・ログインとアプリの新規作成
* 下記リンクから会員登録（無料）をします
  * https://console.mbaas.nifcloud.com/signup
* 登録ができたら下記リンクからログインします
  * https://console.mbaas.nifcloud.com/
* 下図のように「アプリの新規作成」画面が出るのでアプリを作成します
  * 既に mobile backend を利用したことがある方は左上の「＋新しいアプリ」をクリックすると同じ画面が表示されます

![画像3](/readme-img/003.png)

* アプリ作成されるとAPIキー（アプリケーションキーとクライアントキー）が発行されます
* この２種類のAPIキーはこの後ダウンロードするサンプルアプリと ニフクラ mobile backend を紐付けるため、あとで使います。

![画像04](/readme-img/004.png)

* ついでに、この後動作確認でデータが保存される場所も確認しておきましょう

![画像05](/readme-img/005.png)

### 2. サンプルプロジェクトのダウンロード
* 下記リンクをクリックしてプロジェクトをダウンロードします
 * https://github.com/NiFCloud-mbaas/KotlinDBdemoApp/archive/master.zip
* ダウンロードしたプロジェクトを解凍します
* AndroidStudio を開きます、「Open an existing Android Studio projct」をクリックして解凍したプロジェクトを選択します

![android_studio.png](/readme-img/android_studio.png)

* プロジェクトが開かれます

![androidstudio1.png](/readme-img/ProjectDesign.png)

### 3. SDKの導入（実装済み）

※このサンプルアプリには既にSDKが実装済み（下記手順）となっています。（ver.1.2.0)<br>　最新版をご利用の場合は入れ替えてご利用ください。

* SDKダウンロード
SDKはここ（[SDK リリースページ](https://github.com/NIFCLOUD-mbaas/ncmb_kotlin/releases)）から取得してください.
  - NCMB.jarファイルがダウンロードします。
* SDKをインポート
  - app/libsフォルダにNCMB.jarをコピーします
* 設定追加
  - app/build.gradleファイルに以下を追加します
```gradle
dependencies {
    implementation 'com.squareup.okhttp3:okhttp:4.8.1'
    implementation 'com.google.code.gson:gson:2.3.1'
    api files('libs/NCMB.jar')

    //同期処理を使用する場合はこちらを追加していただく必要があります
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9'
}
```
  - androidManifestの設定
    - <application>タグの直前に以下のpermissionを追加します
```html
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 4. APIキーの設定

* AndroidStudio で MainActivity.kt を開きます
  * ディレクトリはデフォルトで「Android」が選択されていますので、「Project」に切り替えてから探してください
![画像09](/readme-img/009.png)

* APIキー（アプリケーションキーとクライアントキー）の設定をします

![画像07](/readme-img/007.png)

* それぞれ `YOUR_APPLICATION_KEY` と `YOUR_CLIENT_KEY` の部分を書き換えます
 * このとき、ダブルクォーテーション（`"`）を消さないように注意してください

### 5. 動作確認

* エミュレーターでアプリをビルドします
  * 失敗する場合は一度「Clean Project」を実行してから再度ビルドしてください
* エミュレーターが起動したら「START DEMO」ボタンをタップタブし、保存成功！とメッセージが表示しました。

![5554_Nexus_5_API_23_3.png](/readme-img/5554_Nexus_5_API_23_3.png)

mBaaS側もデータが保存されたことを確認しています！

![画像08](/readme-img/008.png)



## コード説明

### SDKおよび必要なライブラリーのインポート

```kotlin
import com.nifcloud.mbaas.core.NCMB
import com.nifcloud.mbaas.core.NCMBCallback
import com.nifcloud.mbaas.core.NCMBException
import com.nifcloud.mbaas.core.NCMBObject
```

### SDKを初期化
* MainActivity の `OnCreate` メソッド内

```kotlin

override fun onCreate(savedInstanceState: Bundle?) {
    <省略>
    //**************** APIキーの設定とSDKの初期化 **********************
    NCMB.initialize(this, "YOUR_APPLICATION_KEY", "YOUR_CLIENT_KEY")
}
```

### データの保存

* Android SDK が提供する `NCMBObject` クラスは、データストアを操作するためのクラスです
* データを保存するには、このクラスが提供する `saveInBackground` メソッドを利用します
* `NCMBObject` のインスタンス `obj` を作成し、クラス名「`"TestClass"`」を設定することで、データストアにデータの保存先クラスである「TestClass」クラスを作成・操作することができます
* `obj.put(Key, Value)` で、フィールド「Key」に値「Value」を設定します
* `saveInBackground()` : 保存の実行を行います（非同期処理）

```kotlin
val obj = NCMBObject("TestClass")
try {
    obj.put("message", "Hello, NCMB!")
    obj.saveInBackground(NCMBCallback { e, ncmbObj ->
        if (e != null) {
            //保存失敗
            
        } else {
                //保存成功
            }
        }
            })
} catch (e: NCMBException) {
    e.printStackTrace()
}
```

## 参考
データ保存・データ検索・会員管理・プッシュ通知などの機能を実装したい場合は、以下のドキュメント（Android for Java）もご参照ください。

* [ドキュメント](https://mbaas.nifcloud.com/doc/current/)
  * [データストア](https://mbaas.nifcloud.com/doc/current/datastore/basic_usage_android.html)
  * [会員管理](https://mbaas.nifcloud.com/doc/current/user/basic_usage_android.html)
  * [プッシュ通知](https://mbaas.nifcloud.com/doc/current/push/basic_usage_android.html)

## Contributing
*    Fork it!
*    Create your feature branch: git checkout -b my-new-feature
*    Commit your changes: git commit -am 'Add some feature'
*    Push to the branch: git push origin my-new-feature
*    Submit a pull request :D

# License

    MITライセンス
    NIFCLOUD mobile backendのAndroid SDKのライセンス
