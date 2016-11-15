package prv.mark.project.common.util;

/**
 * Functional interface to convert from one type to another.
 */
@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}
