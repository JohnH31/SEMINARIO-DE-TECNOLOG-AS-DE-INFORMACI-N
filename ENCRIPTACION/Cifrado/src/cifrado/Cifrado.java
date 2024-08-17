/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado;

import static cifrado.encryptZipFile.decryptAndUnzip;
import static cifrado.encryptZipFile.encryptZipFile;
import static cifrado.encryptZipFile.generateKey;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author jona9
 */
public class Cifrado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
try (Scanner scanner = new Scanner(System.in)) {
            // Generar una clave secreta
            SecretKey key = generateKey();

            // Solicitar archivo .zip al usuario
            System.out.print("Ingrese la ruta del archivo .zip que desea cifrar: ");
            String originalZip = scanner.nextLine();

            // Solicitar nombre para el archivo cifrado
            System.out.print("Ingrese el nombre del archivo cifrado (ejemplo: archivo_encrypted.zip): ");
            String encryptedZip = scanner.nextLine();

            // Cifrar el archivo
            encryptZipFile(originalZip, encryptedZip, key);
            System.out.println("El archivo ha sido cifrado exitosamente como " + encryptedZip);

            // Guardar la clave en un archivo para su uso posterior en el descifrado
            try (FileOutputStream keyOut = new FileOutputStream("secretKey.key")) {
                keyOut.write(key.getEncoded());
            }
            System.out.println("La clave secreta ha sido guardada en el archivo 'secretKey.key'.");
             // Esperar entrada del usuario antes de cerrar
            System.out.println("Presione Enter para salir...");
            scanner.nextLine();
        } catch (Exception e) {
                        System.out.println("Error en el sistema la ruta del archivo esta mala\n Se sugiere colocar los archivos en el escritorio y copiar la ruta y colocarla");
        }
    }

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

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // Puedes cambiar el tamaño de la clave si es necesario
        return keyGenerator.generateKey();
    }
}
