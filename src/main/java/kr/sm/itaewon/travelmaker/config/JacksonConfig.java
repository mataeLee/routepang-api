package kr.sm.itaewon.travelmaker.config;


//import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import kr.sm.itaewon.travelmaker.util.GeojsonDeserializer;
import kr.sm.itaewon.travelmaker.util.GeojsonSerializer;
import org.geolatte.common.Feature;
import org.geolatte.common.dataformats.json.jackson.JsonMapper;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.Polygon;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.sql.Time;

@Configuration
public class JacksonConfig {

    private JsonMapper jsonMapper;

//    @Bean
//    public JtsModule jtsModule() {
//        return new JtsModule();
//    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        return builder;
    }

    @Bean
    public Module facilityModule() {

        return new SimpleModule("geometryModule") {{
            jsonMapper = new JsonMapper();
//            addSerializer(Geometry.class, new GeojsonSerializer<>(jsonMapper));
//            addDeserializer(Geometry.class, new GeojsonDeserializer<>(Geometry.class, jsonMapper));
//
//            addSerializer(Polygon.class, new GeojsonSerializer<>(jsonMapper));
//            addDeserializer(Polygon.class, new GeojsonDeserializer<>(Polygon.class, jsonMapper));

            addSerializer(Point.class, new GeojsonSerializer<>(jsonMapper));
            addDeserializer(Point.class, new GeojsonDeserializer<>(Point.class, jsonMapper));

//            addSerializer(Feature.class, new GeojsonSerializer<>(jsonMapper));
//            addSerializer(Phone.class, new PhoneSerializer());
//
//            addSerializer(Time.class, new ToStringSerializer());
        }};
    }}
