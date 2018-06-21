package example;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PGPEncryptionTest {

    private ByteSource expectedFile;
    private File inputFile;
    private Path publicKey;
    private EncryptionConfig config;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Before
    public void before() throws URISyntaxException {
        inputFile = new File(Resources.getResource("Example.txt").getFile());
        publicKey = Paths.get(Resources.getResource("Classmethod.key").toURI());
        expectedFile = Resources.asByteSource(Resources.getResource("Example.txt.gpg"));
        config = EncryptionConfig.builder().recipient("Classmethod") .keyFileName(publicKey).build();
    }

    @Test
    public void PGPで暗号化できる() throws IOException {
        final File outputFile = tempFolder.newFile();
        PGPEncryption.encrypt(config, outputFile, inputFile);
        assertTrue(outputFile.length() > 0);
    }

}