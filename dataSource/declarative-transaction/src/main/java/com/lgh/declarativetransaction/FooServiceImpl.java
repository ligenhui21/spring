package com.lgh.declarativetransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务的传播（Propagation）特性：
 * REQUIRED       当前有事务就用当前的，没有就用新的
 * SUPPORTS       事务可有可无
 * MANDATORY      当前一定要有事务，否则抛出异常
 * REQUIRES_NEW   无论当前是否有事务，都起一个新的事务
 * NOT_SUPPORTED  不支持事务，按非事务方式执行
 * NEVER          不支持事务，如果有事务则抛出异常
 * NESTED         当前有事务就在当前事务里再起一个新的事务
 * @author ligh
 */
@Component
@Slf4j
public class FooServiceImpl implements FooService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FooService fooService;

    @Override
    @Transactional(rollbackFor = RollBackException.class)
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }

    /**
     * 内部方法：被调用方法
     * 外部方法：调用方法
     * 被调用方法事务的传播特性是REQUIRES_NEW时，内部方法和外部方法互不影响
     * 被调用方法事务的传播特性是NESTED时
     * 1、内部方法出现异常回滚时不会影响到外部方法的事务
     * 2、外部方法出现异常回滚时会影响到内部方法的事务，内部方法也会回滚
     * @throws RollBackException
     */
    @Override
    @Transactional(rollbackFor = RollBackException.class, propagation = Propagation.NESTED)
    public void insertThenRollback() throws RollBackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
        throw new RollBackException();
    }

    /**
     * 直接调用同一个类中带有@Transactional注解的时候，被调用方法的事务不会回滚
     * @throws RollBackException
     */
    @Override
    public void invokeInsertThenRollback1() throws RollBackException {
        insertThenRollback();
    }

    /**
     * 可以注入本类的依赖来调用带有事务的方法
     * @throws RollBackException
     */
    @Override
    @Transactional(rollbackFor = RollBackException.class)
    public void invokeInsertThenRollback2() throws RollBackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('CCC')");
        try {
            fooService.insertThenRollback();
        } catch (RollBackException e) {
            log.error("insertThenRollback()方法抛出RollBackException");
        }
        throw new RollBackException();
    }
}
