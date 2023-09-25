package com.bootStart.groupId;

import com.bootStart.groupId.generator.domain.TbOrder;
import com.bootStart.groupId.generator.mapper.TbOrderMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class TbOrderController {
    @Resource
    private TbOrderService tbOrderService;
    @Resource
    private TbOrderMapper tbOrderMapper;

    /**
     * 根据id查询订单
     *
     * @return
     */
    @RequestMapping("/getById")
    public TbOrder getById(@RequestParam("id") Long id) {
        //根据id查询订单
        TbOrder tbOrder = tbOrderMapper.selectById(id);
        return tbOrder;
    }
    /**
     * 创建订单
     *
     * @return
     */
    @RequestMapping("/create")
    public String create(@RequestBody TbOrder order) {
        //创建订单
        tbOrderService.create(order);
        return "sucess";
    }
    /**
     * 对订单进行支付
     *
     * @param id
     * @return
     */
    @RequestMapping("/pay")
    public String pay(@RequestParam("id") Long id) {
        //对订单进行支付
        tbOrderService.pay(id);
        return "success";
    }

    /**
     * 对订单进行发货
     *
     * @param id
     * @return
     */
    @RequestMapping("/deliver")
    public String deliver(@RequestParam("id") Long id) {
        //对订单进行确认收货
        tbOrderService.deliver(id);
        return "success";
    }
    /**
     * 对订单进行确认收货
     *
     * @param id
     * @return
     */
    @RequestMapping("/receive")
    public String receive(@RequestParam("id") Long id) {
        //对订单进行确认收货
        tbOrderService.receive(id);
        return "success";
    }
}