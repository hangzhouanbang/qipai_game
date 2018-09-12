package com.anbang.qipai.game.web.controller;

import com.anbang.qipai.game.cqrs.c.service.disruptor.CoreSnapshotService;
import com.anbang.qipai.game.web.vo.CommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snapshot")
public class SnapshotController {

    @Autowired
    private CoreSnapshotService snapshotServicel;

    @RequestMapping("/save")
    @ResponseBody
    public CommonVO saveSnapshot() {
        final CommonVO commonVO = new CommonVO();
        commonVO.setSuccess(true);
        try {
            this.snapshotServicel.makeSnapshot();
        } catch (Throwable throwable){
            throwable.printStackTrace();
            commonVO.setSuccess(false);
        }
        return commonVO;
    }

}
