package com.xxxx.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author lee
 * @since 2022-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Music对象", description="")
public class Music implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "musicId", type = IdType.AUTO)
    private Integer musicId;
    @ApiModelProperty(value = "音乐名称")
    private String title;
    @ApiModelProperty(value = "歌手")
    private String author;
    @ApiModelProperty(value = "上传时间")
    @TableField(value ="uploadtime" )
    private LocalDateTime uploadTime;
    @ApiModelProperty(value = "文件的路径")
    private String path;
    @ApiModelProperty(value = "上传者的id，即当前用户的id")
    private Integer userId;

}
