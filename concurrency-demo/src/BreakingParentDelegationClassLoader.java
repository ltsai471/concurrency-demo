import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BreakingParentDelegationClassLoader extends ClassLoader {
    private String classPath;

    public BreakingParentDelegationClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // 首先檢查類是否已經被加載
        Class<?> c = findLoadedClass(name);

        if (c == null) {
            try {
                // 打破雙親委派：先嘗試自己加載，而不是先委託給父類加載器
                c = findClass(name);
            } catch (ClassNotFoundException e) {
                // 如果自己加載失敗，再委託給父類加載器
                if (getParent() != null) {
                    c = getParent().loadClass(name);
                } else {
                    throw e;
                }
            }
        }

        if (resolve) {
            resolveClass(c);
        }

        return c;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getClassData(String className) {
        String path = classPath + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";

        try (FileInputStream fis = new FileInputStream(path);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesNumRead;
            while ((bytesNumRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesNumRead);
            }

            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        // 使用示例
        String classPath = "/path/to/your/classes"; // 替換為你的類文件目錄
        BreakingParentDelegationClassLoader loader =
                new BreakingParentDelegationClassLoader(classPath);

        // 加載類
        Class<?> clazz = loader.loadClass("com.example.TestClass");

        // 創建實例
        Object instance = clazz.newInstance();
        System.out.println("Loaded by: " + instance.getClass().getClassLoader());
    }
}