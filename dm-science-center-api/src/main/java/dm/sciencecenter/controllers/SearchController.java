package dm.sciencecenter.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dm.sciencecenter.dtos.AdvancedSearchDto;
import dm.sciencecenter.dtos.elastic.ArticleESDto;
import dm.sciencecenter.entities.Article;
import dm.sciencecenter.entities.Person;
import dm.sciencecenter.service.ArticleService;
import dm.sciencecenter.service.PersonService;
import dm.sciencecenter.service.elastic.ArticleESService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/science-center-api/search")
public class SearchController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleESService articleESService;

    @Autowired
    PersonService personService;

    @GetMapping(path = "/customSearch/{fraza}/{parametar}/{tip}", produces = "application/json")
    public @ResponseBody
    ResponseEntity customSearch(@PathVariable Integer fraza, @PathVariable String parametar, @PathVariable String tip) throws Exception {

        System.out.println("fraza: " + fraza);
        System.out.println("parametar: " + parametar);
        System.out.println("tip: " + tip);

        String query = "";
        if (fraza == 0) {

            query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match\" : {\n" +
                    "            \"" + tip + "\" : {\n" +
                    "                \"query\" : \"" + parametar + "\"\n" +
                    "            }\n" +
                    "           \n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"highlight\" : {\n" +
                    "        \"fields\" : {\n" +
                    "            \"" + tip + "\" : {\n" +
                    "            \t\"type\":\"plain\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";

        } else if (fraza == 1) {

            query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match_phrase\" : {\n" +
                    "            \"" + tip + "\" : {\n" +
                    "                \"query\" : \"" + parametar + "\"\n" +
                    "         \n" +
                    "            }\n" +
                    "           \n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"highlight\" : {\n" +
                    "        \"fields\" : {\n" +
                    "            \"" + tip + "\" : {\n" +
                    "            \t\"type\":\"plain\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonquery = objectMapper.readTree(query);
        HttpEntity<JsonNode> request = new HttpEntity<>(jsonquery);

        String resourceUrl = "http://localhost:9200/magazine/article/_search?pretty";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, request, String.class);
        System.out.println(response);
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode locatedNode = rootNode.path("hits").path("hits");
        List<ArticleESDto> retVal = articleESService.getArticleESDtos(locatedNode, tip);

        return new ResponseEntity(retVal, HttpStatus.OK);
    }

    @PostMapping(path = "/advancedSearch", consumes = "application/json", produces = "application/json")
     public @ResponseBody ResponseEntity advancedSearch(@RequestBody AdvancedSearchDto advancedSearchDto) throws Exception{

        String must = "\"must\" : [\n" ;
        String should = "\"should\" : [\n";
        String phrase = "";

        boolean shouldBe = false;
        boolean mustBe = false;

        if (advancedSearchDto.isPhraseMagazinName()) {
            phrase = "match_phrase";
        } else {
            phrase = "match";
        }

        if (advancedSearchDto.isMagazineNameSelected() && !advancedSearchDto.getMagazineName().equals("")) {
            must += "{ \"" + phrase + "\" : { \"magazineName\" : \""+advancedSearchDto.getMagazineName()+"\" } },";
            mustBe = true;
        } else if (!advancedSearchDto.isMagazineNameSelected() && !advancedSearchDto.getMagazineName().equals("")) {
            should += "{ \"" + phrase + "\" : { \"magazineName\" : \""+advancedSearchDto.getMagazineName()+"\" } },";
            shouldBe = true;
        }

        if (advancedSearchDto.isPhraseName()) {
            phrase = "match_phrase";
        } else {
            phrase = "match";
        }

        if (advancedSearchDto.isNameSelected() && !advancedSearchDto.getName().equals("")) {
            must += "{ \"" + phrase + "\" : { \"name\" : \""+advancedSearchDto.getName()+"\" } },";
            mustBe = true;

        } else if (!advancedSearchDto.isNameSelected() && !advancedSearchDto.getName().equals("")) {
            should += "{ \"" + phrase + "\" : { \"name\" : \""+advancedSearchDto.getName()+"\" } },";
            shouldBe = true;
        }

        if (advancedSearchDto.isPhraseKeyWords()) {
            phrase = "match_phrase";
        } else {
            phrase = "match";
        }

        if (advancedSearchDto.isKeyWordsSelected() && !advancedSearchDto.getKeyWords().equals("")) {
            must += "{ \"" + phrase + "\" : { \"keyWords\" : \""+advancedSearchDto.getKeyWords()+"\" } },";
            mustBe = true;

        } else if (!advancedSearchDto.isKeyWordsSelected() && !advancedSearchDto.getKeyWords().equals("")) {
            should += "{ \"" + phrase + "\" : { \"keyWords\" : \""+advancedSearchDto.getKeyWords()+"\" } },";
            shouldBe = true;
        }

        if (advancedSearchDto.isPhraseAuthor()) {
            phrase = "match_phrase";
        } else {
            phrase = "match";
        }

        if (advancedSearchDto.isAuthorSelected() && !advancedSearchDto.getAuthor().equals("")) {
            must += "{ \"" + phrase + "\" : { \"author\" : \""+advancedSearchDto.getAuthor()+"\" } },";
            mustBe = true;

        } else if (!advancedSearchDto.isAuthorSelected() && !advancedSearchDto.getAuthor().equals("")) {
            should += "{ \"" + phrase + "\" : { \"author\" : \""+advancedSearchDto.getAuthor()+"\" } },";
            shouldBe = true;
        }

        if (advancedSearchDto.isPhraseArticleFile()) {
            phrase = "match_phrase";
        } else {
            phrase = "match";
        }

        if (advancedSearchDto.isArticleFileSelected() && !advancedSearchDto.getArticleFile().equals("")) {
            must += "{ \"" + phrase + "\" : { \"tekst\" : \""+advancedSearchDto.getArticleFile()+"\" } },";
            mustBe = true;

        } else if (!advancedSearchDto.isArticleFileSelected() && !advancedSearchDto.getArticleFile().equals("")) {
            should += "{ \"" + phrase + "\" : { \"tekst\" : \""+advancedSearchDto.getArticleFile()+"\" } },";
            shouldBe = true;
        }

        must = must.substring(0,  must.length() - 1);
        should = should.substring(0,should.length()-1);

        must += "],";
        should += "],";

        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\" : {\n";
        if (mustBe) {
            query += must;
        }

        if (shouldBe) {
            query += should;
        }

        query += "         \"boost\" : 1.0\n" +
                "    }\n" +
                "  },\n" +
                "\"highlight\" : {\n" +
                "        \"fields\" : {\n" +
                "            \"magazineName\" : {" +
                "               \t\"type\":\"plain\"\n" +
                "},\n" +
                "        \"name\" : {" +
                "           \t\"type\":\"plain\"\n" +
                "},\n" +
                "        \"keyWords\" : {" +
                "             \t\"type\":\"plain\"\n" +
                "},\n" +
                "       \"articleFile\" : {" +
                "               \t\"type\":\"plain\"\n" +
                "},\n" +
                "        \"author\" : {" +
                "               \t\"type\":\"plain\"\n" +
                "}\n" +
                "        }\n" +
                "    }"+
                "}";


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonquery = objectMapper.readTree(query);
        HttpEntity<JsonNode> request = new HttpEntity<>(jsonquery);

        String resourceUrl = "http://localhost:9200/magazine/article/_search?pretty";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, request, String.class);
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode locatedNode = rootNode.path("hits").path("hits");
        List<ArticleESDto> retVal =  articleESService.getRadESDTOAdvanced(locatedNode);

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @GetMapping(path="/moreLikeThis/{articleId}", produces = "application/json")
    public @ResponseBody ResponseEntity moreLikeThis(@PathVariable String articleId) throws Exception {
        System.out.println("articleId: " + articleId);
        Article article = articleService.findOneById(Long.parseLong(articleId));

        String tekst = articleESService.parsePDF(article);
        System.out.println("naslov rada: " + article.getFileName());
        tekst = StringEscapeUtils.escapeJson(tekst);

        String query = "{\n" +
                "    \"query\": {\n" +
                "        \"more_like_this\" : {\n" +
                "            \"fields\" : [\"articleFile\"],\n" +
                "            \"like\" : \"" + tekst + "\",\n" +
                "            \"min_term_freq\" : 5,\n" +
                "            \"max_query_terms\" : 50,\n" +
                "            \"min_doc_freq\": 2\n" +
                "        }\n" +
                "    }\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonquery = objectMapper.readTree(query);
        HttpEntity<JsonNode> request = new HttpEntity<>(jsonquery);

        String resourceUrl = "http://localhost:9200/magazine/article/_search?pretty";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, request, String.class);
        System.out.println(response);
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode locatedNode = rootNode.path("hits").path("hits");
        List<ArticleESDto> retVal = articleESService.getArticleESDtos(locatedNode, "articleFile");

        if (retVal.size() == 0) {
            System.out.println("Size je nula");
        } else
            System.out.println("Kurac je nula");

        return new ResponseEntity(retVal, HttpStatus.OK);
    }

    @GetMapping(path = "/geoSpacing/{articleId}", produces = "application/json")
    public @ResponseBody ResponseEntity getReviewersByLocation(@PathVariable String articleId) throws Exception{

        System.out.println("articleId: " + articleId);
        Article article = articleService.findOneById(Long.parseLong(articleId));

        Person author = personService.findOneByName(article.getAuthor());
        System.out.println(author);
        Double lat = author.getLatitude();
        Double lon = author.getLongitude();
        System.out.println("author latitude: " + lat + "; longitude: " + lon);

        String query="{\n" +
                "    \"query\": {\n" +
                "        \"bool\" : {\n" +
                "            \"must\" : {\n" +
                "                \"match_all\" : {}\n" +
                "            },\n" +
                "            \"filter\" : {\n" +
                "            \"bool\" : {\n"+
                "               \"must_not\" : {\n"+
                "                \"geo_distance\" : {\n" +
                "                    \"distance\" : \"100km\",\n" +
                "                    \"location\" : {\n" +
                "                        \"lat\" :" + lat + ",\n" +
                "                        \"lon\" :" + lon + "\n" +
                "                    }\n" +
                "                }\n" +
                "               }\n"+
                "              }\n"+
                "            }\n" +
                "        }\n" +
                "    }\n"+
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonquery = objectMapper.readTree(query);
        HttpEntity<JsonNode> request = new HttpEntity<>(jsonquery);

        String resourceUrl = "http://localhost:9200/reviewers/reviewer/_search?pretty";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, request, String.class);
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        //dobijem korisnike koji nisu u okolini autora
//        List<RecenzentDTO> retVal = this.getRecenzentiFromResponse(rootNode);
//
//        System.out.println("recenzenti size: " + retVal.size());
//        for (RecenzentDTO temp: retVal) {
//            System.out.println(temp.getName());
//        }

        return new ResponseEntity(rootNode, HttpStatus.OK);
    }

//    public List<RecenzentDTO> getRecenzentiFromResponse(JsonNode rootNode){
//
//        List<RecenzentDTO> reviewers=new ArrayList<>();
//        JsonNode locatedNode = rootNode.path("hits").path("hits");
//
//        for(int i = 0; i < locatedNode.size(); i++){
//            RecenzentDTO recenzentDTO = new RecenzentDTO();
//            Integer recenzentId = Integer.parseInt(locatedNode.get(i).path("_source").path("recenzentId").asText());
//            recenzentDTO.setId(recenzentId);
//            recenzentDTO.setName(locatedNode.get(i).path("_source").path("ime").asText() + " " + locatedNode.get(i).path("_source").path("prezime").asText());
//
//            reviewers.add(recenzentDTO);
//        }
//
//        return reviewers;
//    }
}
