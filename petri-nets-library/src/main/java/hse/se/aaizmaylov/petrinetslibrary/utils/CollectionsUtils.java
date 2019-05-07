package hse.se.aaizmaylov.petrinetslibrary.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionsUtils {
    public static <T> T first(@NotNull Collection<? extends T> set) {
        return set.iterator().next();
    }

    @NotNull
    public static <T> Map<T, Integer> getIndices(@NotNull Iterable<? extends T> ts) {
        Map<T, Integer> result = new HashMap<>();
        int id = 0;

        for (T t : ts) {
            result.put(t, id++);
        }

        return result;
    }

    @NotNull
    public static <K, V> Map<V, K> invertMap(@NotNull Map<K, V> map) {
        return map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
