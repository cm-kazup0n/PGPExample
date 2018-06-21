package example;

import com.google.common.io.Resources;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestResources {

    public static final File inputFile;
    public static final Path publicKey;
    public static final EncryptionConfig config;

    static {
        try {
            inputFile = new File(Resources.getResource("Example.txt").getFile());
            publicKey = Paths.get(Resources.getResource("Classmethod.key").toURI());
            config = EncryptionConfig.builder().recipient("Classmethod").keyFileName(publicKey).build();
        } catch (URISyntaxException e) {
            throw new AssertionError(e);
        }
    }
}
