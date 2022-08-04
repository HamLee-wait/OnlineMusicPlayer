package com.xxxx.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="Collect对象", description="")
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "collectId", type = IdType.AUTO)
    private Integer collectId;

    private Integer userId;

    private Integer musicId;


}
