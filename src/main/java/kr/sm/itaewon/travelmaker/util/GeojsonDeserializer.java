package kr.sm.itaewon.travelmaker.util;

import java.io.IOException;

import java.io.IOException;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.geolatte.common.dataformats.json.jackson.JsonException;
import org.geolatte.common.dataformats.json.jackson.JsonMapper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * This is a bridge deserializer for Jackson 1.9 used by Geolatte and Jackson 2.x.
 */
public class GeojsonDeserializer<T> extends JsonDeserializer<Point> {

    private JsonMapper jsonMapper;

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 26910);

    private final Class<T> type;

    public GeojsonDeserializer(Class<T> type, JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
        this.type = type;
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
//    public GeojsonDeserializer() {
//        jsonMapper = new JsonMapper();
//        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
//    }
    @Override
    public Point deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            String text = jp.getText();
            if (text == null || text.length() <= 0)
                return null;

            String[] coordinates = text.replaceFirst("POINT ?\\(", "").replaceFirst("\\)", "").split(" ");
            double lat = Double.parseDouble(coordinates[0]);
            double lon = Double.parseDouble(coordinates[1]);

            Point point = geometryFactory.createPoint(new Coordinate(lat, lon));

            return point;
            //return jsonMapper.fromJson(jp.readValueAsTree().toString(), type);
        } catch (Exception e) {
            throw new JsonMappingException(e.getMessage(), jp.getCurrentLocation(), e.getCause());
        }
    }
}