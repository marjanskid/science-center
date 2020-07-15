package dm.sciencecenter.repositories.elastic;

import dm.sciencecenter.entities.elastic.ArticleES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleESRepository extends ElasticsearchRepository<ArticleES, Long> {

}
