Tang-Nano-With-Chisel
===

# 概要
ChiselでTang Nanoの開発を行う.

# 内容

> src/main/scala/led/

Lチカを行ったり, ゲーミングLEDみたいな光らせ方をする.

![led](fig/images.jpeg)

> src/main/scala/lcd/

LCDの制御.

LCDのためのクロック生成はこちらのリポジトリのコードのverilogコード(rpll.v)をBlackBoxに入れて扱いました.

https://github.com/dotcypress/tang-nano-lcd

![led](fig/lcd.jpeg)
