package com.accenture.rest.legacy.validation;

public interface Validation<T> {
	boolean test(T object);
}
