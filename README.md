## BouncyCastle PGP File encryption example

BouncyCastleを使ってPGPでファイル暗号化するサンプルコードです。

サンプルのエントリポイントは[example.Main](./src/main/java/example/Main.java) です。

### 内容物

- EncryptionConfig PGP暗号化の設定
- PGPEncryption ファサードクラス
- PGPStreamWrapper ファイルの内容を暗号化して任意のOutputStreamに書き込むラッパー
- PublicKeyReader キーリングから受信者を指定して公開鍵を取得するヘルパー



## テスト用の公開鍵の作成方法

```
# 鍵の作成ウィザード
gpg --full-generate-key

# 作成した鍵を確認
gpg --list-secret-keys                                                                                                                                                                                                                                                                                                            11:32:41

# 公開鍵をエクスポート
gpg -o <ファイル名>.key --export <入力したユーザー名とメールアドレス>
```