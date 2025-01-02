/*****************************************************************
 * Copyright (c) 2017 EcoleTree. All Rights Reserved.
 * 
 * Author : 장윤석
 * Create Date : 2023. 02. 24
 * File Name : EcoleTreeBootConfig.java
 * DESC : SpringBoot 구동시 필요한 Config 정의
*****************************************************************/
package kr.co.ecoletree.commons.config;

import kr.co.ecoletree.common.auth.ETLoginCheckInterceptor;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import javax.servlet.descriptor.TaglibDescriptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

@Configuration
@PropertySources({
    @PropertySource("classpath:config.properties"),
})
public class EcoleTreeBootConfig implements WebMvcConfigurer, JspConfigDescriptor {


	
	/**
	 * Resource Handler 정의
	 *  /static 폴더에 있는 것들을 전부 Resource 처리
	 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
        
        registry.addResourceHandler("/jslib/**")
        .addResourceLocations("/webjars/")
        .resourceChain(false) // 버전 관계 없이 처리 하도록
        ;
    }
    

    /**
     * 암호화 관련 메소드
     * @return
     */
    @Bean(name="jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		final String key = "11bfef1071f14276";
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(key);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPoolSize("1");
        encryptor.setConfig(config);
        return encryptor;
	}


    private Collection<JspPropertyGroupDescriptor> jspPropertyGroups = new LinkedHashSet<JspPropertyGroupDescriptor>();
    
    private Collection<TaglibDescriptor> taglibs = new HashSet<TaglibDescriptor>();
 
    @Override
    public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups() {
        JspPropertyGroup jspPropertyGroup = new JspPropertyGroup();
        jspPropertyGroup.addUrlPattern("*.jsp");
        jspPropertyGroup.setPageEncoding("UTF-8");
        jspPropertyGroup.setScriptingInvalid("true");
        jspPropertyGroup.addIncludePrelude("/WEB-INF/views/include/globalHeader.jsp");
        jspPropertyGroup.setTrimWhitespace("true");
        JspPropertyGroupDescriptorImpl jspPropertyGroupDescriptor = new JspPropertyGroupDescriptorImpl(jspPropertyGroup);
        jspPropertyGroups.add(jspPropertyGroupDescriptor);
        return jspPropertyGroups;
    }
 
    @Override
    public Collection<TaglibDescriptor> getTaglibs() {
        return taglibs;
    }

}
