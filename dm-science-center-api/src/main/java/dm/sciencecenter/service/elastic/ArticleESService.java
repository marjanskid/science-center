package dm.sciencecenter.service.elastic;

import dm.sciencecenter.dtos.ArticleDto;

public interface ArticleESService {

    void saveWorkData(ArticleDto articleDto);
}
