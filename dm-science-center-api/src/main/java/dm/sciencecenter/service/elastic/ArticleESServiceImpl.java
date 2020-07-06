package dm.sciencecenter.service.elastic;

import dm.sciencecenter.dtos.ArticleDto;
import dm.sciencecenter.entities.Article;
import dm.sciencecenter.entities.Magazine;
import dm.sciencecenter.entities.ScientificArea;
import dm.sciencecenter.entities.elastic.ArticleES;
import dm.sciencecenter.repositories.MagazineRepository;
import dm.sciencecenter.repositories.ScientificAreaRepository;
import dm.sciencecenter.repositories.elastic.ArticleESRepository;
import dm.sciencecenter.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleESServiceImpl implements ArticleESService {

    @Autowired
    ArticleESRepository articleESRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    MagazineRepository magazineRepository;

    @Autowired
    ScientificAreaRepository scientificAreaRepository;

    //indeksiranje
    @Override
    public void saveWorkData(ArticleDto articleDto) {

        //cuvanje u h2 bazu
        Article article = new Article(articleDto);
        Magazine magazine = magazineRepository.getOne((long) 1);
        article.setMagazine(magazine);
        ScientificArea scientificArea = scientificAreaRepository.getOne((long) 1);
        article.setScientificArea(scientificArea);

        Article savedArticle = this.articleService.saveArticle(article);

        ArticleES articleES = new ArticleES();
        articleES.setId(savedArticle.getId());
        articleES.setName(articleDto.getName());
        articleES.setAuthor(articleDto.getAuthor());
        articleES.setArticleAbstract(articleDto.getArticleAbstract());
        articleES.setKeyWords(articleDto.getKeyWords());
        articleES.setMagazineName(articleDto.getMagazine());
        articleES.setScientificField(scientificArea.getName());
        articleES.setFile(articleDto.getFile());
        articleES.setFileName(articleDto.getFileName());

        //elasticsearch za indeksiranje
        ArticleES articleES1 = this.articleESRepository.save(articleES);
    }
}
