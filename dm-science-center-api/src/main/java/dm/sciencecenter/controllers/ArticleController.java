package dm.sciencecenter.controllers;

import dm.sciencecenter.dtos.ArticleDto;
import dm.sciencecenter.entities.Article;
import dm.sciencecenter.service.ArticleService;
import dm.sciencecenter.service.elastic.ArticleESService;
import org.apache.pdfbox.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

        this.articleService.savePdf(articleDto);
        this.articleESService.saveWorkData(articleDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/download/{id}")
    public @ResponseBody
    ResponseEntity downloadRad(@PathVariable Long id) throws IOException {

        Article article = articleService.findOneById(id);

        InputStream in = new FileInputStream(new File("src/main/resources/pdfs/" + article.getFileName()));

        byte[] bytes = IOUtils.toByteArray(in);


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""  + "rad.pdf\"")
                .body(new ByteArrayResource(bytes));
    }

    private ArticleDto convertToDto(Article article) {
        return modelMapper.map(article, ArticleDto.class);
    }

    private Article convertToEntity(ArticleDto articleDto) {
        return modelMapper.map(articleDto, Article.class);
    }
}
