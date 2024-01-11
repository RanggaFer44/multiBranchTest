package org.example;

import java.sql.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class inputTeamScore {

    public boolean inputTeam(int num, String team) throws SQLException {
        Connection con = Conn.getHDS().getConnection();
        PreparedStatement ps;

        String qry = """
        insert into UserD\s
        set Team = ?,\s
        id= (select num from ranks where num = ?);
    """;

        ps = con.prepareStatement(qry);

        ps.setString(1, team);
        ps.setInt(2, num);

        boolean success = ps.executeUpdate() > 0; // Check if any rows were affected

        ps.close();
        con.close();

        return success;
    }


    public static class SoccerScore  {
        private Connection connection = Conn.getHDS().getConnection();
    
        public SoccerScore() throws SQLException {
        }

        /*public SoccerScore(Connection connection) {
            this.connection = connection;
        }*/

        public void inputScore(String team1, String team2, int sct1, int sct2) throws SQLException {
            if (sct1 < 0 || sct2 < 0) {
                System.out.println("Invalid score");
                return;
            }

            insertScore(team1, team2, sct1, sct2);
            updateWinLossDraw(team1, team2, sct1, sct2);
            updatePoints(team1, team2, sct1, sct2);
        }

        private void insertScore(String team1, String team2, int sct1, int sct2) throws SQLException {
            try (PreparedStatement ps = connection.prepareStatement("insert into Score (Team1, score1, score2, Team2) values (?, ?, ?, ?);")) {
                ps.setString(1, team1);
                ps.setInt(2, sct1);
                ps.setInt(3, sct2);
                ps.setString(4, team2);
                ps.execute();
            }
        }

        private void updateWinLossDraw(String team1, String team2, int sct1, int sct2) throws SQLException {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE UserD SET win = win + 1 WHERE Team = ?")) {
                if (sct1 > sct2) {
                    ps.setString(1, team1);
                    ps.execute();
                } else if (sct2 > sct1) {
                    ps.setString(1, team2);
                    ps.execute();
                }else { PreparedStatement ps2 = connection.prepareStatement("UPDATE UserD SET draw = draw + 1 WHERE Team = ?");
                    ps2.setString(1, team2);
                    ps2.execute();
                }

            }

            try (PreparedStatement ps = connection.prepareStatement("UPDATE UserD SET lose = lose + 1 WHERE Team = ?")) {
                if (sct1 > sct2) {
                    ps.setString(1, team2);
                    ps.execute();
                } else if (sct2 > sct1) {
                    ps.setString(1, team1);
                    ps.execute();
                }else { PreparedStatement ps2 = connection.prepareStatement("UPDATE UserD SET draw = draw + 1 WHERE Team = ?");
                    ps2.setString(1, team1);
                    ps2.execute();
                }

            }

        }

        private void updatePoints(String team1, String team2, int sct1, int sct2) throws SQLException {
            // Implement the logic for updating points based on the scores here
            // You can use the same try-with-resources pattern as above
            // Modify the SQL statements and set the appropriate values for points updates

            try (PreparedStatement ps = connection.prepareStatement("Update UserD set points = points + 3  where Team = ?")) {
                if(sct1>sct2 && sct2==0){
                    ps.setString(1, team1);
                } else if (sct2>sct1 && sct1 == 0) {
                    ps.setString(1, team2);
                } else {
                    ps.setString(1, "");
                }
                ps.execute();
            }

            try(PreparedStatement ps = connection.prepareStatement("Update UserD set points = points + 2  where Team = ?")){
                if(sct1>sct2 && sct2>=1){
                    ps.setString(1, team1);
                } else if (sct2>sct1 && sct1 >= 1) {
                    ps.setString(1, team2);
                } else {
                    ps.setString(1, "");
                }
                ps.execute();
            }

            try (PreparedStatement ps = connection.prepareStatement("Update UserD set points = points + 1  where Team = ?")) {
                if(sct1==sct2){
                    ps.setString(1, team1);
                    ps.setString(1, team2);
                } else {
                    ps.setString(1, "");
                }
                ps.execute();
            }

            try (PreparedStatement ps = connection.prepareStatement("Update UserD set points = points + 0  where Team = ?")) {
                if(sct1>sct2 && sct2>=0){
                    ps.setString(1, team2);
                } else if (sct2>sct1 && sct1 >= 1) {
                    ps.setString(1, team1);
                }else {
                    ps.setString(1, "");
                }
                ps.execute();
            }

            try (PreparedStatement ps = connection.prepareStatement("Update UserD set points = points - 1   where Team = ?")) {
                if(sct1>sct2 && sct2==0){
                    ps.setString(1, team2);
                } else if (sct2>sct1 && sct1 == 0) {
                    ps.setString(1, team1);
                } else {
                    ps.setString(1, "");
                }
                ps.execute();
            }

        }
    }

    public void winner () throws SQLException {
        Connection con = Conn.getHDS().getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT *\n" +
                    "FROM UserD\n" +
                    "ORDER BY points DESC\n" +
                    "LIMIT 1;");
//            ResultSet resultSet2 = statement.executeQuery("SELECT win FROM UserD WHERE Team = '"+name1+"' ");
            assertTrue(resultSet.next());
//            assertEquals(7, resultSet.getInt("points"));
        System.out.println("winner : "+ resultSet.getString("Team"));

    }



    public boolean deleteTeam(String team) throws SQLException {
        Connection con = Conn.getHDS().getConnection();
        PreparedStatement ps;

        String qry = """
                    delete from UserD where Team = ?;
                """;

        ps = con.prepareStatement(qry);

        ps.setString(1, team);

        boolean success = ps.executeUpdate() > 0; // Check if any rows were affected

        ps.close();
        con.close();

        return success;
    }


}
