package example;

import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;

public class PublicKeyReaderTest {

    private Path keyFile = TestResources.publicKey;

    @Test
    public void 存在する受信者を指定すると鍵が取得できる(){
        assertFalse(PublicKeyReader.getKeyOf("Classmethod", keyFile).isEmpty());
    }

    @Test
    public void 存在しない受信者を指定すると鍵は取得できない(){
        assertTrue(PublicKeyReader.getKeyOf("hogehoge", keyFile).isEmpty());
    }
}