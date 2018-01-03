package io.github.microvibe.util.castor.support;

import io.github.microvibe.util.castor.PrimeCastors;

public class ByteCastor extends AbstractMarshallableCastor<Byte> {

	public ByteCastor() {
		super(Byte.class);
	}

	public ByteCastor(Class<Byte> type) {
		super(type);
	}

	@Override
	public Byte castFromBasic(Object orig) {
		return Byte.valueOf(PrimeCastors.castToByte(orig));
	}

	@Override
	public Byte fromString(String s) {
		return Byte.valueOf(s);
	}

}
