import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

import edu.cvtc.bigram.*;

@SuppressWarnings({"SpellCheckingInspection"})
class MainTest {
  @Test
  void createConnection() {
    assertDoesNotThrow(
        () -> {
          Connection db = Main.createConnection();
          assertNotNull(db);
          assertFalse(db.isClosed());
          db.close();
          assertTrue(db.isClosed());
        }, "Failed to create and close connection."
    );
  }

  @Test
  void reset() {
    Main.reset();
    assertFalse(Files.exists(Path.of(Main.DATABASE_PATH)));
  }

  @Test
  void mainArgs() {
    assertAll(
        () -> {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          System.setOut(new PrintStream(out));
          Main.main(new String[]{"--version"});
          String output = out.toString();
          assertTrue(output.startsWith("Version "));
        },
        () -> {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          System.setOut(new PrintStream(out));
          Main.main(new String[]{"--help"});
          String output = out.toString();
          assertTrue(output.startsWith("Add bigrams"));
        },
        () -> assertDoesNotThrow(() -> {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          System.setErr(new PrintStream(out));
          Main.main(new String[]{"--reset"});
          String output = out.toString();
          assertTrue(output.startsWith("Expected"));
        }),
        () -> assertDoesNotThrow(() -> Main.main(new String[]{"./sample-texts/non-existant-file.txt"})),
        () -> assertDoesNotThrow(() -> Main.main(new String[]{"./sample-texts/empty.txt"}))
    );
  }

    @Test
    void main() {
    }

    @Test
    void getWordCount() {
    }









    // TODO: Create your test(s) below. /////////////////////////////////////////
    @Test
    void createBigrams() {
        Connection db = Main.createConnection();
        String src = "hello world";
        assertDoesNotThrow(
                () ->{
                    Main.createBigrams(db, src);
                    try (Statement stmt = db.createStatement();
                         ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM bigrams")) {
                        assertTrue(rs.next(), "Bigrams should be created");
                        assertEquals(1, rs.getInt("count"), "There should be one bigram");
                    }

                }
        );
    }

    @Test
    public void testGetId() throws SQLException {

    }
    @Test
    void addBigram() {

    }



}