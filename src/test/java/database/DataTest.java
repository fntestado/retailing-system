package database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class DataTest {
    @Before
    public void setup() {
        Data.setConnection();
    }

    @Test
    public void getLastInvidRecordTest() throws SQLException {
        String expected = "19";
        assertEquals(expected, Data.getLastInvidRecord());
    }

    @After
    public void postTest() throws Exception {
        Data.DbDone();
    }
}