package example;


import java.io.File;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        final String KEYFILE = "./pub.key";
        final String OUTFILE = "README.md.pgp";
        final String INFILE = "README.md";
        final String recipient = "Kazuhiro Sasaki <sasaki.kazuhiro@classmethod.jp>";

        PGPEncryption.encrypt(
                EncryptionConfig.builder().keyFileName(Paths.get(KEYFILE)).recipient(recipient).build(),
                new File(OUTFILE),
                new File(INFILE));
    }


}
