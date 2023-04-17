package edu.illinois.mutarator.utils;

import java.io.*;

// broken
public class CustomerTestLoader extends ClassLoader {

    String root = null;

    public CustomerTestLoader(String root) {
        this.root = root;
    }

    /**
     * Get the class with a file path
     * @param URL String of the .class file
     * @return Class object of the corresponding file
     * @throws ClassNotFoundException
     */
    @Override
    public Class findClass(String URL) throws ClassNotFoundException {
        byte[] buf = loadClassFromFile(URL);
        String className = URL.substring(root.length(), URL.length() - 6).replaceAll("/", ".");
        return defineClass(className, buf, 0, buf.length);
    }

    /**
     * @param fileName name of class file, ex: example/example/ex.class
     * @return byte[] representing the target class file
     */
    private byte[] loadClassFromFile(String fileName) {
        System.out.println(fileName);

        File f = new File(fileName);
        byte[] output = new byte[(int) f.length()];
        try {
            FileInputStream fis = new FileInputStream(f);
            fis.read(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
