package cn.tanjianff.igoodo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by tanjian on 2017/6/3.
 */
@Configuration
@ComponentScan(basePackages = {"cn.tanjianff.igoodo"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,
                value = EnableWebMvc.class)})
public class RootConfig {
}