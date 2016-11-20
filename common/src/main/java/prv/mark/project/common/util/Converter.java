package prv.mark.project.common.util;

/**
 * Functional Interface to convert one type to another.
 *
 * @author mlglenn
 */
@FunctionalInterface
public interface Converter<F, T> {
    T convert(F from);
}
