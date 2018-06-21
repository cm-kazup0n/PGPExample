package example;


import lombok.Builder;
import lombok.Value;

import java.nio.file.Path;

/**
 * 暗号化の設定
 */
@Value
@Builder
public class EncryptionConfig {

    /**
     * 受信者名
     */
    private final String recipient;
    /**
     * 公開鍵ファイルのパス
     */
    private final Path keyFileName;


}
