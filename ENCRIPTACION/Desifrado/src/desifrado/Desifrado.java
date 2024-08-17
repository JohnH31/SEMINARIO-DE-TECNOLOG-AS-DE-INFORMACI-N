/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desifrado;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author jona9
 */
public class Desifrado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       try (Scanner scanner = new Scanner(System.in)) {
            // Leer la clave secreta desde el archivo
            System.out.print("Ingrese la ruta del archivo de la Clave Secreta: ");
            String Secret = scanner.nextLine();
            SecretKey key = loadKey(Secret);

            // Solicitar archivo cifrado al usuario
            System.out.print("Ingrese la ruta del archivo cifrado que desea descifrar: ");
            String encryptedZip = scanner.nextLine();

            // Solicitar nombre para el archivo descifrado
            System.out.print("Ingrese el nombre del archivo descifrado (sin la extensión .zip): ");
            String decryptedZip = scanner.nextLine();

            // Agregar la extensión .zip si no está presente
            if (!decryptedZip.toLowerCase().endsWith(".zip")) {
                decryptedZip += ".zip";
            }

            // Descifrar el archivo y guardarlo
            decryptZipFile(encryptedZip, decryptedZip, key);
            System.out.println("El archivo ha sido descifrado y guardado exitosamente como " + decryptedZip);
                         // Esperar entrada del usuario antes de cerrar
            System.out.println("Presione Enter para salir...");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error en el sistema la ruta del archivo esta mala\n Se sugiere colocar los archivos en el escritorio y copiar la ruta y colocarla");
        }
    }

    public static void decryptZipFile(String inputFile, String outputFile, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             CipherInputStream cis = new CipherInputStream(fis, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

public static SecretKey loadKey(String fileName) throws Exception {
    try (FileInputStream fis = new FileInputStream(fileName)) {
        // Leer todos los bytes del archivo manualmente
        byte[] encodedKey = new byte[fis.available()];
        fis.read(encodedKey);
        return new SecretKeySpec(encodedKey, "AES");
    }
    }
    }
    
