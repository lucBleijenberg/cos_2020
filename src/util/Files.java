package util;

import jasmin.ClassFile;
import nl.saxion.cos.AssembleException;

import java.io.*;

public class Files {

    private Files() {}

    /**
     * writes [content] to the file associated with [fileName]
     * if no such file exists, throws an [IOException]
     */
    public static void writeToFile(String fileName, String content) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(fileName));
        writer.println(content);
        writer.close();
    }

    /**
     * writes [content] to the file associated with [fileName]
     * if no such file exists, throws an [IOException]
     */
    public static void writeToFile(String fileName, byte[] bytes) throws IOException {
        FileOutputStream stream = new FileOutputStream(fileName);
        stream.write(bytes);
        stream.close();
    }

    /**
     * tries to return a byte array in .class format,
     * which can be interpreted by the JVM
     */
    public static byte[] toClassBytes(String className, String bytecode) throws IOException, AssembleException {
        ClassFile classFile = new ClassFile();
        try {
            BufferedReader in = new BufferedReader(new StringReader(bytecode));
            classFile.readJasmin(in, className + ".j", true);
            in.close();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            classFile.write(out);
            return out.toByteArray();
        }
        catch (IOException exception) {
            throw exception;
        }
        catch (Exception exception) {
            throw new AssembleException(exception.getMessage(), exception);
        }
    }

}
