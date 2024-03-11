package com.es.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

//主要用于ElasticsearchClient
@Data
public class Product {
    private String id;
    private String name;
    private Date birthday;

    /**
     * ik_max_word:
     * 它倾向于做最大化的分词，即对于一个未知的词，它会尝试所有可能的分词方式，尽可能地把一个词拆分成多个部分。
     * 这种策略适用于那些希望提高召回率的场景，比如搜索引擎，因为即使有些分词不太合理，也能增加找到相关结果的可能性。
     * <p>
     * ik_smart:
     * 相比之下，ik_smart 倾向于做最精确的分词，它会在标准字典中查找单词，如果找不到，则保持原样作为一个整体词。
     * 这种策略适用于那些要求分词精度较高的场景，比如新闻、文档等，因为它能保证大部分词被正确地切分。
     * <p>
     * 简单来说，ik_max_word 更侧重于召回率，可能会产生更多的分词结果；而 ik_smart 更侧重于准确性，产生的分词数量相对较少。选择哪种策略取决于你的应用场景和对搜索效果的需求。
     */
    private String addr;

}