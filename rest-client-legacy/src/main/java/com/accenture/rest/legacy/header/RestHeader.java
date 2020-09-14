package com.accenture.rest.legacy.header;

public interface RestHeader<T, K> {
	K build(T header);
}
