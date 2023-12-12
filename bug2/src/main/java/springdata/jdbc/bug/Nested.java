package springdata.jdbc.bug;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Nested(
        AggregateReference<Child,Long>  child_id,
        String type
){
}
