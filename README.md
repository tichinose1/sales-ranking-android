# 2018年04月android

## アプリ概要

- App StoreのRSSフィードから取得したセールスランキングを表示する
- [RSS Generator](https://rss.itunes.apple.com/api/v1/jp/ios-apps/top-grossing/all/30/non-explicit.json)

## プロジェクトの新規作成

- `Android Studio.App`をクリックする
- [File]-[New]-[New Project...]

### ウィザード1

- Application name: `SalesRanking`
- Company domain: `example.com`
- Project location: `/Users/（ユーザー名）`以下のディレクトリを指定する
- include Kotlin supportにチェックする

### ウィザード2

- `Android 5`以上を選択

### ウィザード3

- `Empty Activity`を選択する

## Git（省略可）

- プロジェクトディレクトリにリポジトリを作成し、バージョン管理を開始する
- 作成したプロジェクトをコミットする

```bash
cd ~/dev/kagin1/SalesRanking
git init
git add -A
git commit -m "initial commit"
```

## エミュレータ

### 作成

- [Tools]-[AVD Manager]
- Created Virtual Device...
- `Nexus`や`Pixel`を選択する

### 実行

- [Run]-[Debug 'app']

## 動きを確かめる（省略可）

### アプリ名変更

- `app/res/values/strings.xml`の`app_name`を`セールスランキング`にする
- [Run]-[Debug 'app']

### テキストを変更

- `app/res/layout/activity_main.xml`の`TextView`の`android:text`を`ハローワールド！`にする
- [Run]-[Debug 'app']

## リストの追加

### app/res/layout/activity_main.xml

- `RecyclerView`のダウンロード
    - gradleファイルが変更される
- `TextView`を削除する
- `RecyclerView`をドラッグ＆ドロップする
- `android:id`を`@+id/recyclerView`にする

```xml
<android.support.v7.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

### app/build.gradle

```groovy
implementation 'com.android.support:recyclerview-v7:27.1.0'
```

### MainActivity

- 呼び出す側のコードを追加する

```kotlin
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

recyclerView.layoutManager = LinearLayoutManager(this)
```

## セルの追加

### app/res/layout/row.xml

- [File]-[New]-[XML]-[Layout XML File]
- `row.xml`という名前で新規作成する
- `ImageView`をドラッグ＆ドロップする
- `TextView`をドラッグ＆ドロップする
- `layout_height`を`60dp`にする

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        app:srcCompat="@mipmap/ic_launcher" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="TextView" />
</LinearLayout>
```

## Adapterの追加

### MainAdapter

- [File]-[New]-[Kotlin File/Class]
- `MainAdapter`という名前で新規作成する
- `MainAdapter.MainHolder`を追加
- `RecyclerView.Adapter`のメンバーを実装する（`implement members`を活用する）

```kotlin
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row.view.*

class MainAdapter(): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))
    }

    override fun getItemCount(): Int {
        return 30
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.itemView.textView.text = "モンスターストライク"
    }

    class MainHolder(view: View): RecyclerView.ViewHolder(view)
}
```

### MainActivity

```kotlin
recyclerView.adapter = MainAdapter()
```

### 実行

- [Run]-[Debug 'app']
- リストにデータが30行表示されていることを確認する

## データモデル

- App StoreのRSSフィードで得られる構造化データをプログラムで表現する

```bash
{
    "feed": {
        "title": ...,
        "id": ...,
        ...
        "results": [
            { "name": "xxx", "artworkUrl100": "yyy", ... },
            { "name": "xxx", "artworkUrl100": "yyy", ... },
            { "name": "xxx", "artworkUrl100": "yyy", ... },
            ...
        ]
    }
}
```

### MainItem

- [File]-[New]-[Kotlin File/Class]
- `MainItem`という名前で新規作成する

```kotlin
package com.example.saleranking

data class Item(val feed: Feed)

data class Feed(val results: Array<Result>)

data class Result(val name: String, val artworkUrl100: String)
```

## リストにデータを渡す

### MainAdapter

```kotlin
class MainAdapter(private val results: Array<Result>): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.itemView.textView.text = results[position].name
    }
```

### MainActivity

```kotlin
val results = arrayOf(
        Result("モンスターストライク", ""),
        Result("パズル＆ドラゴンズ","")
)
recyclerView.layoutManager = LinearLayoutManager(this)
recyclerView.adapter = MainAdapter(results)
```

### 実行

- [Run]-[Debug 'app']
- 渡したデータが表示されていることを確認する

## 画像の非同期取得

- OSSライブラリ`Glide`を使用してURLから非同期で画像を取得する
- [bumptech/glide: An image loading and caching library for Android focused on smooth scrolling](https://github.com/bumptech/glide)

### app/build.gradle

```groovy
implementation 'com.github.bumptech.glide:glide:4.6.1'
annotationProcessor 'com.github.bumptech.glide:compiler:4.6.1'
```

- `Sync Now`する

### MainActivity

```kotlin
Result("モンスターストライク", "https://is3-ssl.mzstatic.com/image/thumb/Purple128/v4/d7/3a/ae/d73aae7d-b2c0-998f-c7a1-a2f60215b880/AppIcon-1x_U007emarketing-85-220-7.png/200x200bb.png"),
Result("パズル＆ドラゴンズ","https://is1-ssl.mzstatic.com/image/thumb/Purple118/v4/c3/97/35/c397356b-bf5f-cae6-724f-5dc638a17f6c/AppIcon-1x_U007emarketing-0-85-220-0-9.png/200x200bb.png")
```

### MainAdapter

```kotlin
import com.bumptech.glide.Glide

...

Glide.with(holder.itemView).load(results[position].artworkUrl100).into(holder.itemView.imageView)
```

### app/manifests/AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### 実行

- [Run]-[Debug 'app']
- 画像が取得できていることを確認する

## RSS取得

- OSSライブラリ`OkHttp`を使用してバックグラウンドでHTTP通信する
- [square/okhttp: An HTTP+HTTP/2 client for Android and Java applications.](https://github.com/square/okhttp)

### app/build.gradle

```groovy
implementation 'com.squareup.okhttp3:okhttp:3.10.0'
```

### MainActivity

```kotlin
import android.util.Log
import okhttp3.*
import java.io.IOException

val request = Request.Builder()
        .url("https://rss.itunes.apple.com/api/v1/jp/ios-apps/top-grossing/all/30/non-explicit.json")
        .build()

OkHttpClient()
        .newCall(request)
        .enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                Log.d("test", response?.body()?.string())
            }

            override fun onFailure(call: Call?, e: IOException?) {
            }
        })
```

### 実行（省略可）

- メソッド`onResponse`にブレークポイントをおく
- [Run]-[Debug 'app']
- `Variable`に`response.body().string()`を追加して、データが取得できていることを確認する

## JSONパース

- OSSライブラリ`Moshi`を使用してJSON形式のデータをKotlinのインスタンスにマップする
- [square/moshi: A modern JSON library for Android and Java.](https://github.com/square/moshi)

### app/build.gradle

```groovy
implementation 'com.squareup.moshi:moshi-kotlin:1.5.0'
```

### MainActivity

- `Log.d("test", response?.body()?.string())`の行は削除する

```kotlin
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

...

val responseJson = response?.body()?.string() ?: return
Log.d("responseJson:", responseJson)

val item = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(Item::class.java)
        .fromJson(responseJson)
```

### 実行（省略可）

- メソッド`onResponse`にブレークポイントをおく
- [Run]-[Debug 'app']
- 変数`item`にデータが入っていることを確認する

## ランキングを表示する

### MainActivity

- `recyclerView.adapter = MainAdapter(results)`の行を削除する

```kotlin
import android.os.Handler
import android.os.Looper

...

Handler(Looper.getMainLooper()).post {
    recyclerView.adapter = MainAdapter(item!!.feed.results)
}
```

### 実行

- [Run]-[Debug 'app']
- RSSから取得したデータが画面に反映されていることを確認する

## レイアウトを整える

### app/res/layout/row.xml

- 以下のコードに書き換える

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_weight="1"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@mipmap/ic_launcher" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="0dp"
        android:layout_height="60dp"
        android:gravity="center_vertical|start"
        android:padding="8dp"
        android:text="TextView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@+id/imageView" />

</android.support.constraint.ConstraintLayout>
```

### 実行

- [Run]-[Debug 'app']
- レイアウトが崩れないことを確認する

## ダイアログを表示する

### app/res/layout/activity_main.xml

```xml
<ProgressBar
    android:id="@+id/progressBar"
    style="?android:attr/progressBarStyleLarge"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:translationZ="2dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent" />
```

### MainActivity

```kotlin
progressBar.visibility = View.GONE
```
