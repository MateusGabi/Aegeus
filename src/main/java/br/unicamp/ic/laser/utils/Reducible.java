package br.unicamp.ic.laser.utils;

import java.util.Collection;


public class Reducible<E, T> {
	private Collection<E> collection;	
	private T initial;
	
	public Reducible(Collection<E> collection, T initial) {
		super();
		this.collection = collection;
		this.initial = initial;
	}
	
	public T calc(ReducibleApplyFunction<T, E> function) {
		T i = this.initial;
		for (E e : collection) {
			i = function.apply(i, e);
		}
		return i;
	}

}
