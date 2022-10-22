//package com.robben.config;
//
//import com.google.common.base.Function;
//import com.google.common.base.Optional;
//import com.google.common.base.Predicate;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.RequestHandler;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfigDesc {
//
//    @Value("${server.servlet.context-path}")
//    private String projectName;
//
//    //作为分割包的标识符
//    private static final String splitor = ";";
//
//
//    //---------------------------------默认的指定空间--------------------------------------------
//    @Bean
//    public Docket api(){
////        //添加head参数(role) start,注意此处增加的参数是全局参数
////        ParameterBuilder tokenPar = new ParameterBuilder();
////        List<Parameter> pars = new ArrayList<Parameter>();
////        tokenPar.name("role").description("角色令牌").modelRef(new ModelRef("string"))
////                .parameterType("header").required(false).build();
////        pars.add(tokenPar.build());
////        //添加head参数(role)  end
//
//        return new Docket(DocumentationType.OAS_30)
//             .groupName("defalut1")  //默认模块的名称不需要写,不然会报错
//             .enable(true)
//                //增加全局的请求参数
////             .globalOperationParameters(pars)
//             .apiInfo(apiInfo())
//             .pathMapping("/")
//              //选产那些路经和api会生成document
//             .select()
//              // 可以自行修改为自已的包路经(下面的可以增加多个包路径)
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))  //只是扫描带注解的
////             .apis(basePackage("com.robben.controller"
////                + splitor + "com.robben.rabbitmq"
////                + splitor + "com.robben.elasticSearch"
////                + splitor + "com.robben.bigData"
////                + splitor + "com.robben.kafka"))
////             .apis(RequestHandlerSelectors.any())  //对所有api进行监控
//              // 不显示错误的接口地址
////             .paths(Predicates.not(PathSelectors.regex("/error.*"))) //错误路径不监控
//             .paths(PathSelectors.regex("/.*"))  //对根下所有路径进行监控
//             .build();
//    }
//
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                //标题
//                .title(projectName.split("/")[1] +"接口文档")
//                //描述
//                .description("lalala")
//                .version("1.0.0")
//                //服务条款
//                //.termsOfServiceUrL("http://www.tfjybj.com")
//                //许可证
//                //.license("LICENSE")
//                //许可证地址
//                //.licenseUrl("http://www.dmsdbj.com")
//                .contact(new Contact("robben",null,"EX-robbenOO1@pingan.com.cn"))
//                .build();
//    }
//
//
//    //-----------------------------------新模块扫描------------------------------------------
//    //默认的指定空间
//    @Bean
//    public Docket newApi(){
//        return new Docket(DocumentationType.OAS_30)
//                .groupName("newModule")
//                .enable(true)
//                .apiInfo(newApiInfo())
//                .pathMapping("/")
//                //选产那些路经和api会生成document
//                .select()
//                // 可以自行修改为自已的包路经(下面的可以增加多个包路径)
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))  //只扫描带注解的
////                .apis(basePackage("com.robben.controller"))
//                // 不显示错误的接口地址
////                .paths(Predicates.not(PathSelectors.regex("/error.*"))) //错误路径不监控
//                .paths(PathSelectors.regex("/.*"))  //对根下所有路径进行监控
//                .build();
//    }
//
//    private ApiInfo newApiInfo(){
//        return new ApiInfoBuilder()
//                //标题
//                .title(projectName.split("/")[1] +"接口文档")
//                //描述
//                .description("newlalala")
//                .version("1.0.0")
//                .contact(new Contact("robben",null,"EX-robbenOO1@pingan.com.cn"))
//                .build();
//    }
//
//
//
//
//    //-------------------------------swagger根据包名字来扫描的工具类-------------------------------------------
//    /**
//     * https://www.jianshu.com/p/dcaff30f91cc?tdsourcetag=s_pctim_aiomsg
//     * 上面是参考链接可以依据加载的包名称来扫描
//     * @param basePackage
//     * @return
//     */
//    public static Predicate<RequestHandler> basePackage(final String basePackage) {
//        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
//    }
//
//    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage)     {
//        return input -> {
//            // 循环判断匹配
//            for (String strPackage : basePackage.split(splitor)) {
//                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
//                if (isMatch) {
//                    return true;
//                }
//            }
//            return false;
//        };
//    }
//
//    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
//        return Optional.fromNullable(input.declaringClass());
//    }
//
//
//}
