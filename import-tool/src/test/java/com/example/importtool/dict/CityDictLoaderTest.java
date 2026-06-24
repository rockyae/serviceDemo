package com.example.importtool.dict;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.importtool.entity.SysArea;
import com.example.importtool.mapper.SysAreaMapper;
import com.example.importtool.service.SysAreaService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CityDictLoaderTest {

    private static final Logger log = LoggerFactory.getLogger(CityDictLoaderTest.class);

    @Autowired
    private CityDictLoader cityDictLoader;
    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Autowired
    private SysAreaService sysAreaService;

    /**
     * 默认 {@code @Transactional} 测试会在方法结束后<strong>回滚</strong>，库里看不到插入是预期行为。
     * {@link Rollback @Rollback(false)} 表示测试成功<strong>提交</strong>，便于你在库里验数。
     * 日志里 “Transaction synchronization deregistering SqlSession” 是事务结束关闭 SqlSession 的正常信息，不是失败原因。
     */
    @Test
    @Transactional
    @Rollback(false)
    void loadsClasspathSampleAndFlattens() throws IOException {
        // 按行政区划编码清理旧数据；配合 @Rollback(false) 会真实删/插并提交
        sysAreaMapper.delete(Wrappers.<SysArea>lambdaQuery().eq(SysArea::getAdcode, 100L));
        sysAreaMapper.delete(Wrappers.<SysArea>lambdaQuery().eq(SysArea::getAdcode, 130001L));

        CityDictEnvelope envelope = cityDictLoader.load();

        List<SysAreaDraft> rows = CityDictRowFactory.toDrafts(envelope, "admin", java.time.LocalDateTime.now());
        log.info("draft rows: {}", rows);
        List<SysArea> entities = rows.stream().map(SysArea::fromDraft).toList();
        assertThat(sysAreaService.saveBatch(entities)).isTrue();

    }
}
