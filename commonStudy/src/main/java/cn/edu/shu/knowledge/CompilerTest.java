package cn.edu.shu.knowledge;

import javax.tools.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Vector;

/**
 * 动态编译
 * Created by jxxiangwen on 17-1-16.
 */
public class CompilerTest {

    static class StringSourceJavaObject extends SimpleJavaFileObject {
        private String content = null;

        public StringSourceJavaObject(String name, String content) throws
                URISyntaxException {
            super(URI.create("string:///" + name.replace('.', '/') +
                    JavaFileObject.Kind.SOURCE.extension), JavaFileObject.Kind.SOURCE);
            this.content = content;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return content;
        }
    }

    public static void main(String[] args) throws Exception {
        String expr = "(3+4)*7-10";
        String className = "CalculatorMain";
        String methodName = "calculate";
        String source = "public class " + className
                + " { public static double " + methodName + "() { return " + expr + "; } }";
//        String source = "package cn.edu.shu.knowledge; public class Main { public static void main(String[] args) {System.out.println(\"Hello World!\");} }";
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        StringSourceJavaObject stringSourceJavaObject = new StringSourceJavaObject(className, source);
        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(stringSourceJavaObject);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardJavaFileManager, null,
                null, null, fileObjects);
        boolean result = task.call();
        if (result) {
            System.out.println("编译成功。");
            Field f=ClassLoader.class.getDeclaredField("classes");
            f.setAccessible(true);
            Vector classes=(Vector)f.get(ClassLoader.getSystemClassLoader());
            System.out.println(classes);

            ClassLoader classLoader = CompilerTest.class.getClassLoader();
            Class<?> main = classLoader.loadClass(className);
            Method method = main.getMethod(methodName, new Class<?>[] {});
            Object invoke = method.invoke(null,new Object[] {});
            System.out.println(invoke.toString());
        }
    }
}
