package ky.korins.graaljs;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

import java.net.URL;

public class App {

    public static Context createContext() {
        return Context.newBuilder()
                .allowAllAccess(true)
                .allowCreateThread(true)
                .build();
    }

    public static void main(String[] args) throws Exception {
        try (Context context = createContext()) {
            URL appJS = App.class.getClassLoader().getResource("app.js");
            context.eval(Source.newBuilder("js", appJS).build());
        }
    }
}
