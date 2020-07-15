package dm.sciencecenter.service.elastic;

import com.fasterxml.jackson.databind.JsonNode;
import dm.sciencecenter.dtos.ArticleDto;
import dm.sciencecenter.dtos.elastic.ArticleESDto;
import dm.sciencecenter.entities.Article;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ArticleESService {

    void saveWorkData(ArticleDto articleDto) throws IOException;

    List<ArticleESDto> getArticleESDtos(JsonNode node, String highlight);

    String parsePDF(Article article) throws IOException;

    List<ArticleESDto> getRadESDTOAdvanced(JsonNode node);
}
