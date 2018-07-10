package com.inpas.libparseparams;

public interface IParserParameters {
    public <T> T parse(final byte[] data, final Class<T> tlvClass) throws Exception;
}
