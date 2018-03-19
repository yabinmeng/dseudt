package com.ymeng.utils.codec;

import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.TypeCodec;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.exceptions.InvalidTypeException;

import java.nio.ByteBuffer;

/**
 * Custom Codec class for PName
 */
public class PNameCodec extends TypeCodec<PName> {

    private final TypeCodec<UDTValue> innerCodec;

    private final UserType userType;

    public PNameCodec(TypeCodec<UDTValue> innerCodec, Class<PName> javaType) {
        super(innerCodec.getCqlType(), javaType);
        this.innerCodec = innerCodec;
        this.userType = (UserType)innerCodec.getCqlType();
    }

    @Override
    public ByteBuffer serialize(PName value, ProtocolVersion protocolVersion) throws InvalidTypeException {
        return innerCodec.serialize(toUDTValue(value), protocolVersion);
    }

    @Override
    public PName deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) throws InvalidTypeException {
        return toPName(innerCodec.deserialize(bytes, protocolVersion));
    }

    @Override
    public PName parse(String value) throws InvalidTypeException {
        return value == null || value.isEmpty()  ? null : toPName(innerCodec.parse(value));
    }

    @Override
    public String format(PName value) throws InvalidTypeException {
        return value == null ? null : innerCodec.format(toUDTValue(value));
    }

    protected PName toPName(UDTValue value) {
        return value == null ? null : new PName(
                value.getString("fname"),
                value.getString("lname")
        );
    }

    protected UDTValue toUDTValue(PName value) {
        return value == null ? null : userType.newValue()
                .setString("fname", value.getFName())
                .setString("lname", value.getLName());
    }
}
