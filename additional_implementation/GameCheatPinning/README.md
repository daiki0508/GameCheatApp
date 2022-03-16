# GameCheatPinning
このアプリはGameCheatAppにSSLPinningを追加実装したアプリです。<br>
<br>
`SSLPinning`を実装することによって`Burp`や`OWASPZAP`等の`Proxy`が発行する証明書が使用できなくなり、パケットの盗聴が出来なくなります。<br>
`SSLPinning`を実装する方法はいくつかありますが、今回は`okhttpClient`に設定する方法にします。<br>
<br>
[詳細な情報](https://www.notion.so/GameCheatApp-3569b0e7c85e433287813105a3053e9a)
