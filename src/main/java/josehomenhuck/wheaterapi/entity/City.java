package josehomenhuck.wheaterapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "cities")
public class City {
    @Id
    private String id;

    private String city;

    private Weather weather;

    @CreatedDate
    private String createdAt;

    @LastModifiedDate
    private String updatedAt;
}
