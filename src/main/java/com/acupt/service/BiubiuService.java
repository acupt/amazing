package com.acupt.service;

import com.acupt.dao.BiubiuDAO;
import com.acupt.domain.Result;
import com.acupt.entity.Biubiu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liujie on 2017/8/14.
 */
@Service
public class BiubiuService {

    private static long maxId;

    @Resource
    private BiubiuDAO biubiuDAO;

    public Result<Biubiu> insert(Biubiu biubiu) {
        biubiuDAO.saveAndFlush(biubiu);
        maxId = biubiu.getId();
        return new Result<Biubiu>(biubiu);
    }

    public Result<Biubiu> getById(long id) {
        return new Result<>(biubiuDAO.findOne(id));
    }

    public Result<String> biu() {
        if (maxId == 0) {
            maxId = biubiuDAO.count();
        }
        long id = (long) (Math.random() * (double) maxId + 1);
        Biubiu biubiu = biubiuDAO.findOne(id);
        if (biubiu != null) {
            return new Result<>(biubiu.getContent());
        }
        return new Result(404, "you biu nothing, try?");
    }

    public long getMaxId() {
        return maxId;
    }
}
