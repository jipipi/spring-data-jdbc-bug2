package springdata.jdbc.bug;

import org.springframework.data.annotation.Id;

public record Child(@Id Long id,
                    String name) {
}
