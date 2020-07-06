package dm.sciencecenter.service;

import dm.sciencecenter.dtos.ArticleDto;
import dm.sciencecenter.entities.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getAll();
    Article saveArticle(Article article);
    void savePdf(ArticleDto articleDto);
}
