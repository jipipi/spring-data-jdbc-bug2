package springdata.jdbc.bug;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;

public record Parent(
        @Id Long id,
        String name,
        @Embedded.Nullable(prefix = "nested_")
        Nested nested
){
}
