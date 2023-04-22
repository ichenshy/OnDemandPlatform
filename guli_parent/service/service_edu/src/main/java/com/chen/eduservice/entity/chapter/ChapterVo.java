package com.chen.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchenx
 * @version v1.0
 * @date 2021/12/4 13:05
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    List<VideoVo> children  = new ArrayList<>();
}
