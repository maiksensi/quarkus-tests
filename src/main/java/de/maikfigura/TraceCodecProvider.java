package de.maikfigura;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.pojo.PropertyCodecProvider;

public class TraceCodecProvider implements CodecProvider {

  @Override
  public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
    // if (clazz == Trace.class) {
    // TraceCodec traceCodec = new TraceCodec();

    // return new TraceCodec();
    // }

    if (clazz == Trace.class) {
      return (Codec<T>) PojoCodecProvider.builder().register("de.maikfigura").register(
          (PropertyCodecProvider) new TraceCodecProvider()).build()
          .get(clazz, registry);
    }

    return null;
  }

}