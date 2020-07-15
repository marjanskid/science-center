package dm.sciencecenter.service.elastic;

import com.fasterxml.jackson.databind.JsonNode;
import dm.sciencecenter.dtos.ArticleDto;
import dm.sciencecenter.dtos.elastic.ArticleESDto;
import dm.sciencecenter.entities.Article;
import dm.sciencecenter.entities.Magazine;
import dm.sciencecenter.entities.elastic.ArticleES;
import dm.sciencecenter.repositories.MagazineRepository;
import dm.sciencecenter.repositories.ScientificAreaRepository;
import dm.sciencecenter.repositories.elastic.ArticleESRepository;
import dm.sciencecenter.service.ArticleService;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void saveWorkData(ArticleDto articleDto) throws IOException {

        //cuvanje u h2 bazu
        Article article = new Article(articleDto);
        Magazine magazine = magazineRepository.findOneByName(articleDto.getMagazine());
        System.out.println("found magazine - " + magazine.getName());
        System.out.println("found magazine scientific area - " + magazine.getScientificArea().getName());
        article.setMagazine(magazine);
        article.setScientificArea(magazine.getScientificArea());
        article.setFileName(articleDto.getFileName());

        Article savedArticle = articleService.saveArticle(article);

        ArticleES articleES = new ArticleES();
        articleES.setId(savedArticle.getId());
        articleES.setName(articleDto.getName());
        articleES.setAuthor(articleDto.getAuthor());
        articleES.setArticleAbstract(articleDto.getArticleAbstract());
        articleES.setKeyWords(articleDto.getKeyWords());
        articleES.setMagazineName(articleDto.getMagazine());
        articleES.setScientificField(magazine.getScientificArea().getName());
        articleES.setArticleFile(articleDto.getArticleFile());
        articleES.setFileName(articleDto.getFileName());

        String text = this.parsePDF(article);
        articleES.setArticleFile(text);

        //indeksiranje za elasticsearch
        ArticleES articleES1 = articleESRepository.save(articleES);
        System.out.println("Sve proslo kul!");
    }

    @Override
    public List<ArticleESDto> getArticleESDtos(JsonNode node, String highlight) {
        List<ArticleESDto> retVal = new ArrayList<ArticleESDto>();

        ArticleESDto articleESDto = new ArticleESDto();
        for (int i = 0; i < node.size(); i++){
            Long articleId = Long.parseLong(node.get(i).path("_source").path("id").asText());
            Article article = articleService.findOneById(articleId);

            articleESDto.setId(articleId);
            articleESDto.setName(node.get(i).path("_source").path("name").asText());
            articleESDto.setMagazineName(node.get(i).path("_source").path("magazineName").asText());
            articleESDto.setArticleAbstract(node.get(i).path("_source").path("articleAbstract").asText());
            articleESDto.setKeyWords(node.get(i).path("_source").path("keyWords").asText());
            articleESDto.setScientificField(node.get(i).path("_source").path("scientificField").asText());
            articleESDto.setAuthor(node.get(i).path("_source").path("author").asText());
            articleESDto.setOpenAccess(true);

            String highText = "";
            for(int j = 0; j < node.get(i).path("highlight").path(highlight).size(); j++) {
                highText += node.get(i).path("highlight").path(highlight).get(j).asText() + "...";
            }

            articleESDto.setHighlight(highText);
            retVal.add(articleESDto);
        }

        return retVal;
    }

    @Override
    public String parsePDF(Article article) throws IOException {
        String path = articleService.getArticlePath(article.getId());
        File pdf = new File(path);
        String text = null;
        try {
            System.out.println("*******************************************");
            System.out.println("Parsiranje PDF-a");
            System.out.println("Path: " + path);
            System.out.println("*******************************************");
            PDFParser parser = new PDFParser(new RandomAccessFile(pdf, "r"));
            parser.parse();
            text = getText(parser);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    @Override
    public List<ArticleESDto> getRadESDTOAdvanced(JsonNode node) {
        List<ArticleESDto> retVal = new ArrayList<>();

        ArticleESDto articleESDto = new ArticleESDto();
        for (int i = 0; i < node.size(); i++) {
            Long articleId = Long.parseLong(node.get(i).path("_source").path("id").asText());
            Article article = articleService.findOneById(articleId);

            articleESDto.setId(articleId);
            articleESDto.setName(node.get(i).path("_source").path("name").asText());
            articleESDto.setMagazineName(node.get(i).path("_source").path("magazineName").asText());
            articleESDto.setArticleAbstract(node.get(i).path("_source").path("articleAbstract").asText());
            articleESDto.setKeyWords(node.get(i).path("_source").path("keyWords").asText());
            articleESDto.setScientificField(node.get(i).path("_source").path("scientificField").asText());
            articleESDto.setAuthor(node.get(i).path("_source").path("author").asText());
            articleESDto.setOpenAccess(article.isOpenAccess());

            String highText = node.get(i).path("highlight").toString();

            highText = highText.replace("\"","");
            highText = highText.replace("{","...");
            highText = highText.replace("}","...");
            highText = highText.replace("["," ");
            highText = highText.replace("]"," ");
            highText = highText.replace("\\r\\n","...");
            articleESDto.setHighlight(highText);

            retVal.add(articleESDto);
        }

        return retVal;
    }

    public String getText(PDFParser parser) {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(parser.getPDDocument());
            return text;
        } catch (IOException e) {
            System.out.println("Greksa pri konvertovanju dokumenta u pdf");
        }

        return null;
    }
}
