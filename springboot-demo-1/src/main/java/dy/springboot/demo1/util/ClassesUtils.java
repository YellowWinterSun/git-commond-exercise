package dy.springboot.demo1.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 扫包
 *
 * @author HuangDongYang<huangdy @ pvc123.com>
 * @date 2019/4/3
 */
public class ClassesUtils {

    public static List<Class<?>> searchClass(String packageName) throws Exception {
        // 包名转为路径
        String path = ClassesUtils.class.getResource("/").getPath();
        path = path.replace("%20", " ");
        packageName = packageName.replace(".", File.separator);

        List<String> classesPaths = new LinkedList<>();

        //D:\ProgrammaSoftware\InteliJ IDEA\workspace2\dy-main-java\target\classes\dy\main\springioc\service
        File baseFile = new File(path + packageName);
        if (!baseFile.exists()) {
            throw new Exception("package is not exists");
        }
        if (!baseFile.isDirectory()) {
            throw new Exception("package is not a folder");
        }

        LinkedList<File> folderQueue = new LinkedList<>();
        folderQueue.addLast(baseFile);
        while (!folderQueue.isEmpty()) {
            File file = folderQueue.getFirst();
            folderQueue.removeFirst();

            // 遍历当前文件夹所有文件
            for (File forElement : file.listFiles()) {
                if (forElement.isDirectory()) {
                    // 文件夹
                    folderQueue.addLast(forElement);
                }
                else {
                    // 文件
                    if (forElement.getName().endsWith(".class")) {
                        classesPaths.add(forElement.getPath());
                    }
                }
            }
        }

        System.out.println("--------");
        List<Class<?>> resultList = new ArrayList<>(classesPaths.size());
        for (String s : classesPaths) {
            s = s.replace(path.replace("/","\\").replaceFirst("\\\\",""),"").replace("\\",".").replace(".class","");
            Class cls = Class.forName(s);
            resultList.add(cls);
            System.out.println(cls);
        }

        return resultList;
    }

    public static void main(String[] args) {
        System.out.println("hi");
        try {
            searchClass("dy.main.springioc.service");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
