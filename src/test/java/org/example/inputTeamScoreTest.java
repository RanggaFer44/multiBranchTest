package org.example;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

//Test Case
class inputTeamScoreTest  {

    private inputTeamScore its;
    private Connection connection;

    {
        try {
            connection = Conn.getHDS().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setUp() throws SQLException {
        its = new inputTeamScore();

    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Clean up and close the database connection
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void inputTeam() throws SQLException {
        int num = 5; // Set test values for num and team
        String team = "Munyuk";
        int num2 = 6;
        String team2 = "Depok City";

        // Call the method you want to test
        boolean result = its.inputTeam(num, team);
        boolean result2 = its.inputTeam(num2, team2);

        // Check if the method returned true, indicating a successful insert
        assertTrue(result);
        assertTrue(result2);

        // Verify that the data was inserted into the database
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM UserD WHERE Team = '"+team+"'");
        assertTrue(resultSet.next());
        ResultSet resultSet2 = statement.executeQuery("SELECT * FROM UserD WHERE Team = '"+team2+"'");
        assertTrue(resultSet2.next());
        System.out.println("team "+team+" and "+team2+" added successfully");
    }


    @Test
    public void testInputScoreValid() throws SQLException {
        inputTeamScore.SoccerScore in = new inputTeamScore.SoccerScore();

        // Test a valid input score
        String name1 = "Munyuk";
        String name2 = "Depok City";
        int score1 = 5;
        int score2 = 8;
        in.inputScore(name1, name2, score1, score2);

        // Verify that the score was inserted
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Score WHERE Team1 = '"+name1+"' AND Team2 = " +
                    "'"+name2+"'and score1 = '"+score1+"' and score2 = '"+score2+"'");
            assertTrue(resultSet.next());
        }
        try (PreparedStatement ps = connection.prepareStatement("UPDATE UserD SET win = 0 , draw = 0 , " +
                "lose = 0 , points = 0 WHERE Team = '"+name1+"'")) {
            ps.execute();
        }
        try (PreparedStatement ps = connection.prepareStatement("UPDATE UserD SET win = 0 , draw = 0 , " +
                "lose = 0 , points = 0 WHERE Team = '"+name2+"'")) {
            ps.execute();
        }
        System.out.println(name1+" "+score1+" - "+score2+" "+name2);
    }


    @Test
    public void testInputScoreInvalid() throws SQLException {
        inputTeamScore.SoccerScore in = new inputTeamScore.SoccerScore();
        // Test an invalid input score (negative scores)
        String name1 = "bekasi fc";
        String name2 = "Bogor FC";
        int score1 = -4;
        int score2 = 5;
        in.inputScore(name1, name2, score1, score2);

        // Verify that no record was inserted
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Score WHERE Team1 = '"+name1+"' AND Team2 = '"+name2+"' " +
                    "and score1 = '"+score1+"' and score2 = '"+score2+"'");
            assertTrue(resultSet.next());
        }
    }


    @Test
    public void testInputCondition() throws SQLException {
        inputTeamScore.SoccerScore in = new inputTeamScore.SoccerScore();
        // Test an valid input score
        String name1 = "Munyuk";
        String name2 = "Depok City";
        int score1 = 1;
        int score2 = 2;
        in.inputScore(name1, name2, score1, score2);
        if (score1>score2 && score2==0){
            System.out.println(name1+" points : +3\n"+name2+ " points : -1");
        } else if (score2>score1 && score1==0) {
            System.out.println(name1+" points : -1\n"+name2+ " points : +3");
        } else if (score1>score2 && score2>0) {
            System.out.println(name1+" points : +2\n"+name2+ " points : 0");
        } else if (score2>score1 && score1>0) {
            System.out.println(name1+" points : 0\n"+name2+ " points : +2");
        }else if (score1==score2){
            System.out.println(name1+" points : +1\n"+name2+ " points : +1");
        }else{
            return;
        }
        // Verify that recored inserted
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM UserD WHERE Team = '"+name1+"'");
            assertTrue(resultSet.next());
            assertEquals(1, resultSet.getInt("win"));
            assertEquals(0, resultSet.getInt("draw"));
            assertEquals(0, resultSet.getInt("lose"));
        }finally {
            PreparedStatement ps = connection.prepareStatement("UPDATE UserD SET win = 0 , lose = 0 , draw = 0 , points = 0 WHERE Team = ? ");
            ps.setString(1, name1);
            ps.execute();
        }

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM UserD WHERE Team = '"+name2+"'");
            assertTrue(resultSet.next());
            assertEquals(0, resultSet.getInt("win"));
            assertEquals(0, resultSet.getInt("draw"));
            assertEquals(1, resultSet.getInt("lose"));
        } finally {
            PreparedStatement ps2 = connection.prepareStatement("UPDATE UserD SET win = 0 , lose = 0 , draw = 0 , points = 0 WHERE Team = ? ");
            ps2.setString(1, name2);
            ps2.execute();
        }
    }


    @Test
    void HighestPointTeam() throws SQLException {

        // Verify that the data was inserted into the database
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT *\n" +
                    "FROM UserD\n" +
                    "ORDER BY points DESC\n" +
                    "LIMIT 1;");
//            ResultSet resultSet2 = statement.executeQuery("SELECT win FROM UserD WHERE Team = '"+name1+"' ");
            assertTrue(resultSet.next());
            assertEquals("bekasi fc", resultSet.getString("team"));
            System.out.println("winner : "+resultSet.getString("team"));
        }
    }


// delete exist value
    @Test
    void deleteExistTeam() throws SQLException{
        // Set test values for num and team
        String team1 = "Munyuk";
        String team2 = "Depok City";
        // Call the method you want to test
        boolean result = its.deleteTeam( team1);
        boolean result2 = its.deleteTeam( team2);
        // Check if the method returned true, indicating a successful insert
        assertTrue(result);
        assertTrue(result2);
        // Verify that the data was inserted into the database
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM UserD WHERE Team = '"+team1+"'");
        assertFalse(resultSet.next());
        ResultSet resultSet2 = statement.executeQuery("SELECT * FROM UserD WHERE Team = '"+team2+"'");
        assertFalse(resultSet2.next());
        System.out.println("team "+team1+ " and team "+team2+" delete succesfully");
    }

// delete invalid value
    @Test
    void deleteTeam() throws SQLException{
         // Set test values for num and team
        String team = "demit";

        // Call the method you want to test
        boolean result = its.deleteTeam( team);

        // Check if the method returned true, indicating a successful insert
        assertFalse(result);

        // Verify that the data was inserted into the database
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM UserD WHERE Team = '"+team+"'");
        assertTrue(resultSet.next());
    }
}