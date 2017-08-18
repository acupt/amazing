package com.acupt.service;

import com.acupt.domain.Biubiu;
import com.acupt.service.domain.ServiceResult;

/**
 * Created by liujie on 2017/8/14.
 */
public interface BiubiuService {

    ServiceResult<Biubiu> insert(Biubiu biubiu);

    ServiceResult<Biubiu> getById(long id);

    ServiceResult<String> biu();

}
