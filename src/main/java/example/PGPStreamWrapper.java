package example;

import com.google.common.io.Closeables;
import lombok.AllArgsConstructor;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.List;

@AllArgsConstructor
class PGPStreamWrapper {

    private final EncryptionConfig config;

    /**
     * inputFileの内容を暗号化してOutputStreamに書き出す
     * @param inputFile 入力ファイル
     * @param wrappee 出力先のStream
     * @param buff 書き出し時に使うバッファ
     */
    public void wrapOutputStream(final File inputFile, final OutputStream wrappee, final byte[] buff) {

        //受信者に該当する公開鍵を取り出し
        final List<PGPPublicKey> publicKey = PublicKeyReader.getKeyOf(config.getRecipient(), config.getKeyFileName());

        //鍵は1つだけのはず

        if(publicKey.isEmpty()){
            throw new PGPEncryptionException(String.format("There's no key for %s", config.getKeyFileName().toString()));
        }

        if(publicKey.size() > 1){
            throw new PGPEncryptionException(String.format("There's multiple keys for %s", config.getKeyFileName().toString()));
        }

        //TODO 暗号化アルゴリズムを必要に応じて変更する
        //preferred symmetric algorithms は鍵から取得できないので決めうち
        final PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(
                new JcePGPDataEncryptorBuilder(SymmetricKeyAlgorithmTags.AES_256)
                        .setWithIntegrityPacket(true)
                        .setSecureRandom(new SecureRandom())
                        .setProvider(new BouncyCastleProvider())
        );

        encryptedDataGenerator.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(publicKey.get(0)).setProvider(new BouncyCastleProvider()));

        OutputStream edgStream = null;
        try {
            edgStream = encryptedDataGenerator.open(wrappee, buff);
            PGPUtil.writeFileToLiteralData(edgStream, PGPLiteralData.BINARY, inputFile);
        } catch (PGPException | IOException e) {
            throw new PGPEncryptionException(e.getMessage(), e);
        }finally{
            try {
                Closeables.close(edgStream, false);
            } catch (IOException e) {
                throw new PGPEncryptionException(e.getMessage(), e);
            }
        }
    }

}
