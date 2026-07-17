package com.hmdp.listener;

import cn.hutool.json.JSONUtil;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 秒杀消息消费者
 */
@Component
@Slf4j
public class SeckillVoucherListener {

    @Resource
    private ISeckillVoucherService seckillVoucherService;

    @Resource
    private IVoucherOrderService voucherOrderService;

    /**
     * 正常队列消费者
     */
    @RabbitListener(queues = "QA")
    public void receivedA(Message message) throws Exception {
        String msg = new String(message.getBody());
        log.info("正常队列:");
        VoucherOrder voucherOrder = JSONUtil.toBean(msg, VoucherOrder.class);
        log.info(voucherOrder.toString());
        voucherOrderService.save(voucherOrder);
        // 数据库秒杀库存减一
        Long voucherId = voucherOrder.getVoucherId();
        seckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherId)
                .gt("stock", 0)
                .update();
    }

    /**
     * 死信队列消费者
     */
    @RabbitListener(queues = "QD")
    public void receivedD(Message message) throws Exception {
        log.info("死信队列:");
        String msg = new String(message.getBody());
        VoucherOrder voucherOrder = JSONUtil.toBean(msg, VoucherOrder.class);
        log.info(voucherOrder.toString());
        voucherOrderService.save(voucherOrder);

        Long voucherId = voucherOrder.getVoucherId();
        seckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherId)
                .gt("stock", 0)
                .update();
    }
}
