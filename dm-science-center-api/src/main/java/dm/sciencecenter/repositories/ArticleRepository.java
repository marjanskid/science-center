package dm.sciencecenter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import dm.sciencecenter.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
