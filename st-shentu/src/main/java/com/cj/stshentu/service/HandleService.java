package com.cj.stshentu.service;


import com.cj.stcommon.utils.entity.ApiResult;
import com.cj.stshentu.entity.DLLCLass;
import com.cj.stshentu.entity.P2;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface HandleService {

    public DLLCLass callJNI(List<P2> list);

    public ApiResult getResult(DLLCLass dllClass, ApiResult apiResult ,HttpSession session);
}
