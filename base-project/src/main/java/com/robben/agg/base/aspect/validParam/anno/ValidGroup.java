package com.robben.agg.base.aspect.validParam.anno;

import javax.validation.groups.Default;

public interface ValidGroup{

    interface noParam  extends Default  {};

    interface param  extends Default  {};

}
