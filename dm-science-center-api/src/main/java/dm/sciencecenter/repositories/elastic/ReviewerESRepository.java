package dm.sciencecenter.repositories.elastic;

import dm.sciencecenter.entities.elastic.ReviewerES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ReviewerESRepository extends ElasticsearchRepository<ReviewerES, Long> {
}
