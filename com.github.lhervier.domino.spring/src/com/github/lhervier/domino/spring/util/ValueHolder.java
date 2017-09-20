package com.github.lhervier.domino.spring.util;

public class ValueHolder<T> {

	private T value;
	
	public ValueHolder() {}
	
	public ValueHolder(T value) {
		this.value = value;
	}
	
	public T get() {
		return this.value;
	}
	
	public void set(T value) {
		this.value = value;
	}
}
