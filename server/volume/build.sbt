ThisBuild / scalaVersion := "2.11.7"

//共通設定
lazy val commonSettings = Seq(
  version := "0.1",
  scalacOptions := Seq(
    "-unchecked", //型消去などでパターンマッチが有効に機能しない
    "-deprecation", //今後廃止の予定のAPIを利用している
    "-feature", // 実験的な機能を利用している
    "-Xlint", //その他、望ましい書き方や落とし穴についての情報
    "-encoding",
    "utf8"
  ),
  libraryDependencies ++=
    Seq(
      "ch.qos.logback" % "logback-classic" % "1.1.3", // logback
      "org.slf4j" % "slf4j-api" % "1.7.12" // logger ファサード
    )
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .aggregate(appModule)
  .dependsOn(appModule)
  .settings(
    name := "main-project",
  )

lazy val appModule = (project in file("modules/app"))
  .settings(commonSettings: _*)
  .settings(
    name := "app-module",
    libraryDependencies ++= {
      val akkaV = "2.4.2"
      Seq(
        "com.typesafe.akka" %% "akka-http-core" % akkaV, // 主に低レベルのサーバーサイドおよびクライアントサイド HTTP/WebSocket API
        "com.typesafe.akka" %% "akka-http-experimental" % akkaV, // 高レベルのサーバーサイド API (experimental)
        "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV, // Akka で JSON を扱う場合はこれ (experimental)
        "com.typesafe.akka" %% "akka-http-xml-experimental" % akkaV // Akka で XML を扱う場合はこれ (experimental)
      )
    }
  )

lazy val daemonModule = (project in file("modules/daemon"))
  .dependsOn(appModule) // import して利用可能
  .aggregate(appModule) // compile が実行される
  .settings(commonSettings: _*)
  .settings(
    name := "daemon-module",
    version := "1.0",
    mainClass in assembly := Some("my.daemon.app.Main"),
    retrieveManaged := true, // 依存jarをプロジェクト内に配置
    libraryDependencies ++= Seq(
      "commons-daemon" % "commons-daemon" % "1.1.0",
    )
  )
