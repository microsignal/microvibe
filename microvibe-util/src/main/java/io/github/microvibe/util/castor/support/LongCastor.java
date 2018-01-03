package io.github.microvibe.util.castor.support;

import io.github.microvibe.util.castor.PrimeCastors;

public class LongCastor extends AbstractMarshallableCastor<Long> {
	public LongCastor() {
		super(Long.class);
	}

	public LongCastor(Class<Long> type) {
		super(type);
	}

	@Override
	public Long castFromBasic(Object orig) {
		return Long.valueOf(PrimeCastors.castToLong(orig));
	}

	@Override
	public Long fromString(String s) {
		return Long.valueOf(s);
	}

}
