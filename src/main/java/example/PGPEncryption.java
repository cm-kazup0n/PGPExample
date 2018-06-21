package example;

import com.google.common.io.Closeables;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 暗号化クラス
 */
public class PGPEncryption {

    private PGPEncryption() {
    }

    /**
     * inputFileで指定するファイルを暗号化してoutputFileへ書き出す. ファイルへの書き出し中のエラーはハンドリングしない
     * @param config 暗号化設定
     * @param outputFile 出力先ファイル
     * @param inputFile 元ファイル
     */
    public static void encrypt(final EncryptionConfig config, final File outputFile, final File inputFile) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(outputFile);
            new PGPStreamWrapper(config).wrapOutputStream(inputFile, out, new byte[4096]);
        } catch (IOException e) {
            throw new PGPEncryptionException(e.getMessage(), e);
        } finally {
            try {
                Closeables.close(out, true);
            } catch (IOException e) {
                //ignore
            }
        }
    }
}
