/*
 * easy come, easy go.
 *
 * contact : syiae.jwy@gmail.com
 *
 * · · · · ||   ..     __       ___      ____  ®
 * · · · · ||  ||  || _ ||   ||    ||   ||      ||
 * · · · · ||  ||  \\_ ||_.||    ||   \\_  ||
 * · · _//                                       ||
 * · · · · · · · · · · · · · · · · · ·· ·    ___//
 */
package com.jwy.meepo.feign.server;

import com.jwy.meepo.generator.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *     feign获取id
 * </p>
 *
 * @author Jiang Wanyu
 * @version 1.0
 * @date 2024/1/11
 */
@Slf4j
@RestController
@RequestMapping("/feign/id")
public class FeignIdController {

    @Autowired
    private IdGenerator idGenerator;

    @GetMapping("/next")
    public long nextId() {
        return idGenerator.nextId();
    }

}
