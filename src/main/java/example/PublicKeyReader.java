package example;

import com.google.common.collect.Iterators;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 公開鍵の読み込みを行うヘルパー
 */
class PublicKeyReader {

    private PublicKeyReader(){}

    /**
     * 受信者を指定してキーリングから公開鍵を読みこむ
     * @param recipient 受信者
     * @param keyFile キーリングファイルのパス
     * @return 該当する公開鍵
     */
    static List<PGPPublicKey> getKeyOf(final String recipient, Path keyFile) {
        try {
            final PGPPublicKeyRingCollection keyRings = new PGPPublicKeyRingCollection(
                    PGPUtil.getDecoderStream(new FileInputStream(keyFile.toFile())), new JcaKeyFingerprintCalculator());
            return Stream.of(Iterators.toArray(keyRings.getKeyRings(), PGPPublicKeyRing.class))
                    .flatMap(ring -> Stream.of(Iterators.toArray(ring.getPublicKeys(), PGPPublicKey.class)))
                    .filter(key -> key.isEncryptionKey())
                    .filter(key -> Iterators.any(key.getUserIDs(), id -> recipient.equals(id)))
                    .collect(Collectors.toList());
        } catch (PGPException | IOException e) {
            throw new PGPEncryptionException(String.format("Error while reading public key file(%s)", keyFile), e);
        }
    }
}
