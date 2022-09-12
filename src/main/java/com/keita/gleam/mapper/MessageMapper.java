package com.keita.gleam.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;

public class MessageMapper extends ObjectMapper {

    public MessageMapper(OutputStream stream, Object o) throws IOException {
        super.writeValue(stream, o);
    }
}
