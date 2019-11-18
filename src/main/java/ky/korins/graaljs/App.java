package ky.korins.graaljs;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static Context createContext() {
        Context context = Context.newBuilder()
                .allowAllAccess(true)
                .allowCreateThread(true)
                .build();

        context.getBindings("js").putMember("sharedObject", SharedObject.getInstance());

        return context;
    }

    private static Source openJsFile(File file) throws IOException {
        return Source.newBuilder("js", file).build();
    }

    public static Source openJsFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return openJsFile(file);
        }

        URL url = App.class.getClassLoader().getResource(path);
        if (url == null) {
            throw new IOException("Can't find file: " + path);
        }

        file = new File(url.getFile());
        return openJsFile(file);
    }

    public static void main(String[] args) throws Exception {
        List<Thread> threads = new ArrayList<>();
        if (args.length == 0) {
            System.err.println("You should specify at least one js file to execute");
        }
        for (String entry : args) {
            final Source appJS = openJsFile(entry);
            threads.add(new Thread(() -> {
                try (Context context = createContext()) {
                    context.eval(appJS);
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
