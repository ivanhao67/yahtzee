package project;



import io.cucumber.java.en.*;
import project.Player;
import static junit.framework.TestCase.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class StepDefGameNetwork {

	Player player;
	Player player2;
	Player player3;

	Player winner;

	/*
	 * common functions for all server related tests
	 */
	@Given("Server is on")
	public void server_is_on() throws IOException, ClassNotFoundException {
		// server is on and running
	}

	@Given("Player connects to server at port {int}")
	public void player_connects_to_server_at_port(Integer int1) {
		player = new Player("test");
		player.initializePlayers();
		player.connectToClient(int1);
	}

	@Given("Multiple players connect to server at port {int}")
	public void multiple_players_connects_to_server_at_port(Integer int1) {
		player = new Player("test 1");
		player.initializePlayers();
		player.connectToClient(int1);

		player2 = new Player("test 2");
		player2.initializePlayers();
		player2.connectToClient(int1);

		player3 = new Player("test 3");
		player3.initializePlayers();
		player3.connectToClient(int1);
	}

	/*
	 * testing single player functions
	 */
	@Then("Player receives player id")
	public void player_receives_player_id() {
		assertTrue(player.playerId != 0);
	}

	@When("Player starts the game")
	public void player_starts_the_game() {
		player.sendStringToServer("one");
		String inString = "3" + System.lineSeparator() + "1";
		ByteArrayInputStream in = new ByteArrayInputStream((inString).getBytes());
		System.setIn(in);
		player.startGame();
		player.sendStringToServer("end");
	}

	@Then("Player receives scores of other players")
	public void player_receives_scores_of_other_players() {
		assertFalse(player.players[1].getScoreSheet()[0] == -1);
		assertFalse(player.players[2].getScoreSheet()[0] == -1);
	}

	/*
	 * testing the end of the game (winners and bonus)
	 */
	@When("All rounds are complete")
	public void all_rounds_are_complete() {
		player.sendStringToServer("complete");
		winner = player.returnWinner();
		player.sendStringToServer("end");
	}

	@Then("Player receives complete scores with added bonus for all players")
	public void player_receives_complete_scores_with_added_bonus_for_all_players() {
		// the test code says p1--> has bonus, p2--> no bonus, p3--> bonus
		assertFalse(player.players[0].getScoreSheet()[14] == -1);
		assertFalse(player.players[1].getScoreSheet()[14] == -1);
		assertFalse(player.players[2].getScoreSheet()[14] == -1);
	}

	@Then("Player receives winner")
	public void player_receives_winner() {
		assertTrue(winner.playerId == 1 || winner.playerId == 2 || winner.playerId == 3);
	}

	/*
	 * testing game functionality for 13 rounds
	 */

	@When("Multiple players play a round")
	public void mutliple_players_play_a_round() {
		player.sendStringToServer("all");
		String inString = "3" + System.lineSeparator() + "1" + System.lineSeparator();
		ByteArrayInputStream in = new ByteArrayInputStream((inString).getBytes());
		System.setIn(in);
		player.startGame();
		inString = "3" + System.lineSeparator() + "1" + System.lineSeparator();
		in = new ByteArrayInputStream((inString).getBytes());
		System.setIn(in);
		player2.startGame();
		inString = "3" + System.lineSeparator() + "1" + System.lineSeparator();
		in = new ByteArrayInputStream((inString).getBytes());
		System.setIn(in);
		player3.startGame();
		player.sendStringToServer("end");
	}

	@Then("Player receives scores of other players before it")
	public void player_receives_scores_of_other_players_before_it() {
		// if player id == 1(then p2 and p3 scores should not be updated
		// if player id == 2(then p1 should be updated)
		// if player id == 3(then p1 and p2 should be updated)
		if (player.playerId == 1) {
			assertTrue(player.players[1].getScoreSheet()[0] == -1);
			assertTrue(player.players[2].getScoreSheet()[0] == -1);
		}
		if (player.playerId == 2) {
			assertFalse(player.players[0].getScoreSheet()[0] == -1);
			assertTrue(player.players[2].getScoreSheet()[0] == -1);
		}
		if (player.playerId == 3) {
			assertFalse(player.players[0].getScoreSheet()[0] == -1);
			assertFalse(player.players[1].getScoreSheet()[0] == -1);
		}

	}

}
