package org.hswebframework.web.datasource;

import lombok.extern.slf4j.Slf4j;
import org.hswebframework.ezorm.rdb.executor.SqlRequest;
import org.hswebframework.ezorm.rdb.executor.jdbc.JdbcSyncSqlExecutor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author zhouhao
 */
@Transactional(rollbackFor = Throwable.class)
@Slf4j
public class DefaultJdbcExecutor extends JdbcSyncSqlExecutor {

    protected String getDatasourceId() {
        String id = DataSourceHolder.switcher().currentDataSourceId();
        return id == null ? "default" : id;
    }


    @Override
    public Connection getConnection(SqlRequest sqlRequest) {
        DataSource dataSource = DataSourceHolder.currentDataSource().getNative();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        boolean isConnectionTransactional = DataSourceUtils.isConnectionTransactional(connection, dataSource);
        if (log.isDebugEnabled()) {
            log.debug("DataSource ({}) JDBC Connection [{}] will {}be managed by Spring", getDatasourceId(), connection, (isConnectionTransactional ? "" : "not "));
        }
        return connection;
    }

    @Override
    public void releaseConnection(Connection connection, SqlRequest sqlRequest) {
        if (log.isDebugEnabled()) {
            log.debug("Releasing DataSource ({}) JDBC Connection [{}]", getDatasourceId(), connection);
        }
        try {
            DataSourceUtils.doReleaseConnection(connection, DataSourceHolder.currentDataSource().getNative());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            try {
                connection.close();
            } catch (Exception e2) {
                log.error(e2.getMessage(), e2);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void execute(SqlRequest request) {
        super.execute(request);
    }
}
