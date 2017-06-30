package cn.edu.shu.function;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by jxxiangwen on 17-5-16.
 */
public class Collect {
    public static void main(String[] args) {
        List<String> list = Stream.of("a", "b", "c").map(String::toUpperCase).collect(toList());
        String s = Stream.of("a", "b", "c").min(Comparator.comparing(String::length)).get();
        String reduce = Stream.of("a", "b", "c").reduce("", (initial, item) -> initial + item);
    }
}
