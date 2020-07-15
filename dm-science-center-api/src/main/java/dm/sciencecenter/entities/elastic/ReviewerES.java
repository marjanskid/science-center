package dm.sciencecenter.entities.elastic;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import javax.persistence.*;

@Document(indexName = "reviewers", type = "reviewer")
public class ReviewerES {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Field(type= FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String name;

    @GeoPointField
    private GeoPoint location;
}
