package co.com.autenthication.r2dbc.config;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class PostgresSQLConnectionPoolTest {

    @InjectMocks
    private PostgresSQLConnectionPool connectionPool;

    @Mock
    private PostgresqlConnectionProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(properties.getHost()).thenReturn("localhost");
        when(properties.getPort()).thenReturn(5432);
        when(properties.getDatabase()).thenReturn("ReactiveDB");
        when(properties.getUsername()).thenReturn("postgres");
        when(properties.getPassword()).thenReturn("1234");
    }

    @Test
    void connectionFactoryShouldNotBeNull() {
        ConnectionFactory connectionFactory = connectionPool.connectionFactory();
        assertNotNull(connectionFactory, "El ConnectionFactory no debería ser nulo");
    }
}