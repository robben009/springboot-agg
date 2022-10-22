package com.robben.annotation.validParam;

import javax.validation.groups.Default;

public interface ValidGroup{

    interface noParam  extends Default  {};
    interface param  extends Default  {};

}
