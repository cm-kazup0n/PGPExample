package example;

/**
 * PGPによる暗号化中に発生するエラー
 */
public class PGPEncryptionException extends RuntimeException {
    PGPEncryptionException(String format, Exception e) {
        super(format, e);
    }

    PGPEncryptionException(String msg) {
        super(msg);
    }
}
