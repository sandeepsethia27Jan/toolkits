package com.interviewtest.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

public interface EqualsHashCode<T> {

    boolean equals(T t, Object o); // NOPMD

    int hashCode(T t);

    @Override
    @Deprecated
    boolean equals(Object o);

    @Override
    @Deprecated
    int hashCode();

    /**
     * <p>Eager allocation of getters. Usage:</p>
     * <pre>EqualsHashCode.of(t -> t.someProperty, T::getAnotherProperty)</pre>
     */
    @SafeVarargs
    static <T> EqualsHashCode<T> of(final Function<? super T, Object>... getters) {
        return of(() -> stream(getters));
    }

    /**
     * <p>Lazy allocation for getters. Usage:</p>
     * <pre>EqualsHashCode.of(() -> Stream.of(t -> t.someProperty, T::getAnotherProperty))</pre>
     */
    static <T> EqualsHashCode<T> of(final Supplier<Stream<Function<? super T, Object>>> getters) {
        return new EqualsHashCode<T>() {
            @Override
            @SuppressWarnings("unchecked")
            public boolean equals(final T self, final Object other) {
                return self == other || null != self
                        && self.getClass().isInstance(other)
                        && equalsCasted(self, (T) other);
            }

            private boolean equalsCasted(final T self, final T other) {
                final EqualsBuilder equals = new EqualsBuilder();
                getters.get().forEachOrdered(getter -> equals.append(getter.apply(self), getter.apply(other)));
                return equals.isEquals();
            }

            @Override
            public int hashCode(final T self) {
                final HashCodeBuilder hashCode = new HashCodeBuilder(17, 37);
                getters.get().forEachOrdered(getter -> hashCode.append(getter.apply(self)));
                return hashCode.toHashCode();
            }
        };
    }
}
