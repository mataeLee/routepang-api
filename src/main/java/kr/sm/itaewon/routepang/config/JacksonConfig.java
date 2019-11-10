package kr.sm.itaewon.routepang.config;


//import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import kr.sm.itaewon.routepang.util.GeojsonDeserializer;
import kr.sm.itaewon.routepang.util.GeojsonSerializer;
import org.geolatte.common.dataformats.json.jackson.JsonMapper;
import org.locationtech.jts.geom.Point;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    private JsonMapper jsonMapper;

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
