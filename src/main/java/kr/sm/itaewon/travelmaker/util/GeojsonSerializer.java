package kr.sm.itaewon.travelmaker.util;

import java.io.IOException;

import org.geolatte.common.dataformats.json.jackson.JsonMapper;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Point;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * This is a bridge serializer for Jackson 1.9 used by Geolatte and Jackson 2.x.
 */
 public  class GeojsonSerializer<T> extends JsonSerializer<T> {

    private JsonMapper jsonMapper;

    public GeojsonSerializer(JsonMapper jsonMapper) {
        this.jsonMapper= jsonMapper;
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    public GeojsonSerializer() {
        jsonMapper = new JsonMapper();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
   @Override
    public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

       String jsonValue = "null";
       try
       {
           if(value != null) {
               double lat = ((Point)value).getY();
               double lon = ((Point)value).getX();
               jsonValue = String.format("POINT (%s %s)", lat, lon);
           }
       }
       catch(Exception e) {}

       jgen.writeString(jsonValue);
    }
}