package dm.sciencecenter.controllers;

import dm.sciencecenter.dtos.ArticleDto;
import dm.sciencecenter.entities.Article;
import dm.sciencecenter.service.ArticleService;
import dm.sciencecenter.service.elastic.ArticleESService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/science-center-api/article")
public class ArticleController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ArticleESService articleESService;

    @Autowired
    ArticleService articleService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        log.info("article/all");
        List<Article> articleList = articleService.getAll();

        return new ResponseEntity<>(articleList, HttpStatus.OK);
    }

    @PostMapping(value = "/submitArticleData")
    public ResponseEntity<?> submitArticleData(@RequestBody ArticleDto articleDto) throws IOException {

        this.articleESService.saveWorkData(articleDto);
        this.articleService.savePdf(articleDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ArticleDto convertToDto(Article article) {
        return modelMapper.map(article, ArticleDto.class);
    }

    private Article convertToEntity(ArticleDto articleDto) {
        return modelMapper.map(articleDto, Article.class);
    }
}
