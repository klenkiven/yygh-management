package xyz.klenkiven.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-cmn")
@Service
public interface DictFeignClient {

    /**
     * 根据dictcode和value查询字典内容
     * @param dictCode 字典名
     * @param value 字典值
     * @return 对应的字典内容
     */
    @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")
    String getName(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value);

    /**
     * 根据Value查询字典内容
     * @param value 字典值
     * @return 字典内容
     */
    @GetMapping("/admin/cmn/dict/getName/{value}")
    String getName(@PathVariable("value") String value);
}
