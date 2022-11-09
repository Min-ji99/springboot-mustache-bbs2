package com.mustache.bbs2.controller;

import com.mustache.bbs2.domain.dto.ArticleDto;
import com.mustache.bbs2.domain.entity.Article;
import com.mustache.bbs2.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {
    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    @GetMapping(value="/list")
    public String list(Model model){
        List<Article> articles=articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "list";
    }
    @GetMapping(value="/")
    public String index(){
        return "redirect:/articles/list";
    }

    @GetMapping(value="/new")
    public String createPage(){
        return "new";
    }
    @GetMapping(value="/{id}")
    public String selectSingle(@PathVariable Long id, Model model){
        Optional<Article> optArticle = articleRepository.findById(id);
        if(!optArticle.isEmpty()) {
            model.addAttribute("article", optArticle.get());
            return "show";
        }else{
            return "";
        }
    }
    @PostMapping("")
    public String articles(ArticleDto articleDto){
        log.info(articleDto.getTitle());
        Article savedArticle=articleRepository.save(articleDto.toEntity());
        return String.format("redirect:/articles/%d",savedArticle.getId()) ;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        Optional<Article> optArticle = articleRepository.findById(id);
        if(!optArticle.isEmpty()) {
            model.addAttribute("article", optArticle.get());
            return "edit";
        }else{
            model.addAttribute("message", String.format("%d가 없습니다", id));
            return "error";
        }
    }
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Model model){
        articleRepository.deleteById(id);
        return "redirect:/articles/";
    }
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, ArticleDto articleDto) {
        log.info("title:{} content:{}", articleDto.getTitle(), articleDto.getContent());
        Article article=articleRepository.save(articleDto.toEntity());

        return "redirect:/articles/"+article.getId();
    }
}
