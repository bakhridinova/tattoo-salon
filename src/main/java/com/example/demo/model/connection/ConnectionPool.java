package com.example.demo.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Connection pool. For db initialisation use app.properties file with db extension for properties.
 * If there are no properties for driver and pool size in properties file, then default values are used.
 */
public class ConnectionPool {
    private static final String db_properties_file = "properties/app.properties";
    private static final String db_properties_prefix = "db.";
    private static final String db_url_property = "url";
    private static final String db_user_property = "user";
    private static final String db_password_property = "password";
    private static final String db_auto_reconnect_property= "autoReconnect";
    private static final String db_character_encoding_property= "characterEncoding";
    private static final String db_useU_unicode_property= "useUnicode";
    private static final String db_use_JDBC_compliant_time_shift_property= "useJDBCCompliantTimeShift";
    private static final String db_use_legacy_datetime_code_property= "useLegacyDatetimeCode";
    private static final String db_server_timezone_property= "serverTimezone";
    private static final String db_server_ssl_cert_property= "serverSslCert";
    private static final String db_diver_property = "driver";
    private static final String default_driver_property = "org.postgresql.Driver";
    private static final String db_url;
    private static final String pool_properties_prefix = "pool.";
    private static final String pool_size_property = "size";
    private static final int default_connection_pool_size = 8;
    private static final int connection_pool_size;
    private static ConnectionPool instance;
    private static final Logger logger = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final Lock instanceLock = new ReentrantLock(true);
    private static final AtomicBoolean isInstanceCreated = new AtomicBoolean(false);
    private final BlockingQueue<Connection> available = new LinkedBlockingQueue<>(connection_pool_size);
    private final BlockingQueue<Connection> occupied = new LinkedBlockingQueue<>(connection_pool_size);


    static {
        String driverProperty = null;
        try (InputStream propertiesStream = ConnectionPool.class.getClassLoader().getResourceAsStream(db_properties_file)) {

            Properties fileProperties = new Properties();
            fileProperties.load(propertiesStream);

            db_url = fileProperties.getProperty(db_properties_prefix + db_url_property);
            properties.put(db_user_property, fileProperties.getProperty(db_properties_prefix + db_user_property));
            properties.put(db_password_property, fileProperties.getProperty(db_properties_prefix + db_password_property));
            properties.put(db_auto_reconnect_property, fileProperties.getProperty(db_properties_prefix + db_auto_reconnect_property));
            properties.put(db_character_encoding_property, fileProperties.getProperty(db_properties_prefix + db_character_encoding_property));
            properties.put(db_useU_unicode_property, fileProperties.getProperty(db_properties_prefix + db_useU_unicode_property));
            properties.put(db_use_JDBC_compliant_time_shift_property, fileProperties.getProperty(db_properties_prefix + db_use_JDBC_compliant_time_shift_property));
            properties.put(db_use_legacy_datetime_code_property, fileProperties.getProperty(db_properties_prefix + db_use_legacy_datetime_code_property));
            properties.put(db_server_timezone_property, fileProperties.getProperty(db_properties_prefix + db_server_timezone_property));
            properties.put(db_server_ssl_cert_property, fileProperties.getProperty(db_properties_prefix + db_server_ssl_cert_property));

            if ((driverProperty = fileProperties.getProperty(db_properties_prefix + db_diver_property)) == null) {
                driverProperty = default_driver_property;
            }
            Class.forName(driverProperty);
            logger.debug("---------> registered driver: {}", driverProperty);

            String poolSizeParameter;
            int poolSize = default_connection_pool_size;
            if ((poolSizeParameter = fileProperties.getProperty(pool_properties_prefix + pool_size_property)) != null) {
                try {
                    poolSize = Integer.parseInt(poolSizeParameter);
                } catch (NumberFormatException nfe) {
                    logger.error("--------->  invalid pool size parameter in properties file: {}", poolSizeParameter);
                }
            }
            connection_pool_size = poolSize;
            logger.debug("---------> pool size: " + poolSize);
        } catch (IOException e) {
            logger.error("---------> cannot open properties file: {}", db_properties_file);
            throw new ExceptionInInitializerError("cannot open properties file: " + db_properties_file);
        } catch (ClassNotFoundException e) {
            logger.error("error loading driver: {}", driverProperty);
            throw new ExceptionInInitializerError("error loading driver: " + driverProperty);
        }
    }

    private ConnectionPool() {
        for (int i = 0; i < connection_pool_size; i++) {
            ProxyConnection connection;
            try {
                connection = new ProxyConnection(DriverManager.getConnection(db_url, properties));
                available.put(connection);
            } catch (SQLException | InterruptedException e) {
                logger.error("---------> error while initialising connection pool: {}", e.getMessage());
                throw new ExceptionInInitializerError("error while initialising connection pool: " + e.getMessage());
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (!isInstanceCreated.get()) {
            try {
                instanceLock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInstanceCreated.set(true);
                    logger.debug("---------> connection pool instance created");
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = available.take();
            occupied.put(connection);
        } catch (InterruptedException e) {
            logger.error("---------> thread killed while waiting id: {}, name: {}: {}",
                    Thread.currentThread().getId(),
                    Thread.currentThread().getName(),
                    e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            occupied.remove(connection);
            available.put(connection);
        } catch (InterruptedException e) {
            logger.error("---------> thread killed while waiting id: {}, name: {}: {}",
                    Thread.currentThread().getId(),
                    Thread.currentThread().getName(),
                    e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < 8; i++) {
            try {
                available.take().close();
            } catch (SQLException | InterruptedException e) {
                logger.error("---------> thread killed while waiting id: {}, name: {}: {}",
                        Thread.currentThread().getId(),
                        Thread.currentThread().getName(),
                        e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        deregisterDriver();
    }

    public void deregisterDriver() {
        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        java.sql.Driver driver;
        while (drivers.hasMoreElements()) {
            driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("---------> cannot deregister driver: {}", e.getMessage());
            }
        }
    }
}