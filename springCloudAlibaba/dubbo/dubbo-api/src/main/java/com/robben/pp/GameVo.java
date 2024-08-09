package com.robben.pp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description： TODO
 * Author: robben
 * Date: 2021/3/9 19:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameVo implements Serializable {

    private String gameName;


}
