package br.unicamp.ic.laser.utils;

@FunctionalInterface
public interface ReducibleApplyFunction<T, E> {
	T apply(T i, E e);
}