package com.weidashan.config;

import com.weidashan.pojo.BasePojo;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class Generator {
    public static void main(String[] args) {
        // 构建代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 构建配置器
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("weidashan");
        // 定义项目路径
        String path = System.getProperty("user.dir");
        gc.setOutputDir(path + "/src/main/java");
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://49.233.51.52:3306/shop?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("shop");
        dsc.setPassword("shop");
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.weidashan");
        pc.setEntity("pojo");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        Map<String,String> pathInfo = new HashMap<>();
        pathInfo.put("xml_path",path + "/src/main/resources/com/weidashan/mapper");
        pathInfo.put("entity_path",path + "/src/main/java/com/weidashan/pojo");
        pathInfo.put("mapper_path",path + "/src/main/java/com/weidashan/mapper");
        pathInfo.put("service_path",path + "/src/main/java/com/weidashan/service");
        pathInfo.put("service_impl_path",path + "/src/main/java/com/weidashan/service/impl");
        pathInfo.put("controller_path",path + "/src/main/java/com/weidashan/controller");
        pc.setPathInfo(pathInfo);
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BasePojo.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类

        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude("app_user");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 生成代码
        mpg.execute();
    }
}
