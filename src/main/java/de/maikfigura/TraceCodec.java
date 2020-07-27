package de.maikfigura;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.mongodb.MongoClientSettings;

import de.maikfigura.Trace;
import de.maikfigura.Person;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

public class TraceCodec implements CollectibleCodec<Trace> {

  private final Codec<Document> documentCodec;

  public TraceCodec() {
    this.documentCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
  }

  @Override
  public void encode(BsonWriter writer, Trace trace, EncoderContext encoderContext) {
    Document doc = new Document();
    doc.put("traceOwner", trace.traceOwner);
    doc.put("participants", trace.participants);
    doc.put("startTime", trace.startTime);
    doc.put("stopTime", trace.stopTime);
    doc.put("comment", trace.comment);
    this.documentCodec.encode(writer, doc, encoderContext);
  }

  @Override
  public Class<Trace> getEncoderClass() {
    return Trace.class;
  }

  @Override
  public Trace decode(BsonReader reader, DecoderContext decoderContext) {
    Document doc = documentCodec.decode(reader, decoderContext);
    Trace trace = new Trace();
    if (doc.getString("id") != null && ObjectId.isValid(doc.getString("id"))) {
      trace.id = new ObjectId(doc.getString("id"));
    }
    trace.traceOwner = new Person(doc.getString("traceOwner"));
    trace.participants = Person.from((doc.getString("participants")));
    trace.startTime = ZonedDateTime.parse(doc.getString("startTime"));
    trace.stopTime = ZonedDateTime.parse(doc.getString("stopTime"));
    trace.comment = doc.getString("comment");
    return trace;
  }

  @Override
  public Trace generateIdIfAbsentFromDocument(Trace document) {
    if (!documentHasId(document)) {
      document.id = new ObjectId(UUID.randomUUID().toString());
    }
    return document;
  }

  @Override
  public boolean documentHasId(Trace document) {
    if (document.id != null) {
      return true;
    }
    return false;
  }

  @Override
  public BsonValue getDocumentId(Trace document) {
    return new BsonString(document.id.toString());
  }

}