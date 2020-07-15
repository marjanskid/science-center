package dm.sciencecenter.service;

import dm.sciencecenter.dtos.ArticleDto;
import dm.sciencecenter.entities.Article;
import dm.sciencecenter.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final String path = "D:\\Milica\\fakultet\\master\\UPP\\naucna_centrala\\naucna-centrala\\src\\main\\resources\\files";

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }
    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void savePdf(ArticleDto articleDto) {
        System.out.println("submitArticleData");
        System.out.println("articleDto fileName: " + articleDto.getFileName());
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodedBytes = decoder.decodeBuffer(articleDto.getArticleFile());

            File file = new File("src/main/resources/pdfs/" + articleDto.getFileName());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(decodedBytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Article findOneById(Long articleID) {
        return articleRepository.findOneById(articleID);
    }

    @Override
    public String getArticlePath(Long id) throws UnsupportedEncodingException {
            Article article = articleRepository.findOneById(id);
            String ret = "src/main/resources/pdfs/" + article.getFileName();
            System.out.println("path: " + ret);
            return ret;
    }

}
