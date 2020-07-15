package dm.sciencecenter.service;

import dm.sciencecenter.dtos.ArticleDto;
import dm.sciencecenter.entities.Article;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ArticleService {

    List<Article> getAll();

    Article saveArticle(Article article);

    void savePdf(ArticleDto articleDto);

    Article findOneById(Long articleID);

    String getArticlePath(Long id) throws UnsupportedEncodingException;
}
