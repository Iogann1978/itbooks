package ru.home.itbooks.config;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.xslt.XsltView;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import ru.home.itbooks.converter.*;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    private ApplicationContext applicationContext;
    private AuthorsConverter authorsConverter;
    private BookFileConverter bookFileConverter;
    private DescriptConverter descriptConverter;
    private PublisherConverter publisherConverter;
    private TagsConverter tagsConverter;

    @Autowired
    public WebMvcConfig(ApplicationContext applicationContext,
                        AuthorsConverter authorsConverter,
                        BookFileConverter bookFileConverter,
                        DescriptConverter descriptConverter,
                        PublisherConverter publisherConverter,
                        TagsConverter tagsConverter) {
        this.applicationContext = applicationContext;
        this.authorsConverter = authorsConverter;
        this.bookFileConverter = bookFileConverter;
        this.descriptConverter = descriptConverter;
        this.publisherConverter = publisherConverter;
        this.tagsConverter = tagsConverter;
    }

    @Bean
    public ViewResolver viewResolver(){
        val viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        val templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        val templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("classpath:/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public ViewResolver xsltViewResolver() {
        val viewResolver = new XsltViewResolver();
        viewResolver.setOrder(1);
        viewResolver.setSourceKey("xmlSource");
        viewResolver.setPrefix("classpath:/WEB-INF/xsl/");
        viewResolver.setSuffix(".xslt");
        viewResolver.setViewClass(XsltView.class);
        viewResolver.setViewNames(new String[] {"contents", "xml"});
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/WEB-INF/templates/css/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(authorsConverter);
        registry.addConverter(bookFileConverter);
        registry.addConverter(descriptConverter);
        registry.addConverter(publisherConverter);
        registry.addConverter(tagsConverter);
        registry.addConverter(new FileHtmlConverter());
        registry.addConverter(new FileXmlConverter());
    }
}
