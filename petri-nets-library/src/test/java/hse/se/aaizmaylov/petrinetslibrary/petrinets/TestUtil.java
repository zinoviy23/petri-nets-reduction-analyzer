package hse.se.aaizmaylov.petrinetslibrary.petrinets;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TestUtil {
    @NotNull
    public static String getPath(@NotNull String path, Class clazz) {
        return Objects.requireNonNull(clazz.getClassLoader().getResource(path)).getPath();
    }
}
