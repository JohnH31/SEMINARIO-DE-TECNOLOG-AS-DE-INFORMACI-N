/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 *
 * @author jona9
 */
public class encryptZipFile {
    
    public static void encryptZipFile(String inputFile, String outputFile, SecretKey key) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.ENCRYPT_MODE, key);

    try (FileInputStream fis = new FileInputStream(inputFile);
         FileOutputStream fos = new FileOutputStream(outputFile);
         CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            cos.write(buffer, 0, bytesRead);
        }
    }
}

    public static void decryptAndUnzip(String inputFile, String outputDir, SecretKey key) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(Cipher.DECRYPT_MODE, key);

    try (FileInputStream fis = new FileInputStream(inputFile);
         CipherInputStream cis = new CipherInputStream(fis, cipher);
         ZipInputStream zis = new ZipInputStream(cis)) {

        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            File outFile = new File(outputDir, entry.getName());

            try (FileOutputStream fos = new FileOutputStream(outFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = zis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
            zis.closeEntry();
        }
    }
}

    public static SecretKey generateKey() throws Exception {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
    keyGenerator.init(128); // Puedes cambiar el tamaño de la clave si es necesario
    return keyGenerator.generateKey();
}

    
}
