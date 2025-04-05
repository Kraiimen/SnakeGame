package carmineerario.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameConfig {
	private static final String SETTINGS_JSON_PATH = "config/settings.json";
	private static final String RECORDS_JSON_PATH = "config/records.json";
	// Create a Gson instance with pretty printing enabled for formatted JSON output
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	//Class to deserialize settings Json file
	static class Config{
		private int difficulty; // Game difficulty
		private int[] snakeColorArr = new int[3]; // Snake color

		// Setters and Getters
		public void setDifficulty(int difficulty){ this.difficulty = difficulty; }
		public void setSnakeColorArr(int[] snakeColorArr){ this.snakeColorArr = snakeColorArr; }
		public int getDifficulty() { return this.difficulty; }
		public int[] getSnakeColorArr() { return this.snakeColorArr; }
	}

	// Static class that holds a list of Player objects representing the game records saved in the JSON
	static class Records{
		List<Player> records;
	}

	//Class to deserialize records.json
		public record Player(String playerName, int playerRecord) implements Comparable<Player> {

		// Compares two Player objects based on their playerRecord (score) in descending order.
		// Returns a positive value if the current player has a higher score,
		// a negative value if the other player has a higher score,
		// and zero if both players have the same score.
		@Override
		public int compareTo(Player other) {
			return Integer.compare(other.playerRecord, this.playerRecord);
		}
	}
	// TODO migliorare i commenti nel codice
	// Saves the game configuration (difficulty and snake color) to a JSON file
	public static void saveConfig(int difficulty, int[] snakeColorArr) {
		// Creates a new Config object and sets its properties with the provided values
		Config config = new Config();
		config.setDifficulty(difficulty);
		config.setSnakeColorArr(snakeColorArr);
		
		try (FileWriter writer = new FileWriter(SETTINGS_JSON_PATH)){
			System.out.println("Saving config: " + gson.toJson(config));
			gson.toJson(config, writer);
		} catch (IOException e) {
			System.out.println("FileWriter: Json file not found!");
		}
	}
	
	//Method to save records to Json file
	static void saveRecords(List<Player> records) {
		Records rec = new Records();
		rec.records = records;
		
		try (FileWriter writer = new FileWriter(RECORDS_JSON_PATH)){
			gson.toJson(rec, writer);
		} catch (IOException e) {
			System.out.println("FileWriter: Json file not found!");
		}
	}
	
	// Method to save a new record into the top 5
	public static void saveNewRecord(Player player) {
		List<Player> tempRecords = loadRecords();
		int newRecordToSave = player.playerRecord;

		int insertPos = 0;
		while (insertPos < tempRecords.size() && tempRecords.get(insertPos).playerRecord >= newRecordToSave){
			insertPos++;
		}

		tempRecords.add(insertPos, player);

		if (tempRecords.size() > 5){
			tempRecords.removeLast();
		}

		System.out.println(tempRecords);
		saveRecords(tempRecords);
	}

	// Method to check if the new record can be saved
	public static boolean canRecordBeSaved(int playerRecord) {
		List<Player> tempRecords = loadRecords();

		if(!(playerRecord == tempRecords.getLast().playerRecord())){
            for (Player player : tempRecords) {
                if (playerRecord >= player.playerRecord()) {
                    return true;
                }
            }
		}

		return false;
	}
	
	//Method to get difficulty value from Json file
	public static int loadDifficulty() {
        try (FileReader reader = new FileReader(SETTINGS_JSON_PATH)) {
            return gson.fromJson(reader, Config.class).getDifficulty();
        } catch (IOException e) {
            System.out.println("FileReader: Json file not found!");

            //If the file is not found, return 80 as the default difficulty
            return 80;
        }
	}
	
	//Method to get snakeColor value from Json file
	public static int[] loadSnakeColor() {
		try (FileReader reader = new FileReader(SETTINGS_JSON_PATH)) {
			return gson.fromJson(reader, Config.class).getSnakeColorArr();
		} catch (IOException e) {
			System.out.println("FileReader: Json file not found!");
			
			//If the file is not found, it returns the RGB value for black
			return new int[] {0,0,0};
		}
	}
	
	//Method to get records values from Json file
	public static List<Player> loadRecords(){
		try (FileReader reader = new FileReader(RECORDS_JSON_PATH)) {
			return gson.fromJson(reader, Records.class).records;
		} catch (IOException e) {
			System.out.println("FileReader: Json file not found!");
			
			//If the file is not found, it returns a predetermined list
			List<Player> list = new ArrayList<Player>();
			list.add(new Player("Player1", 0));
			list.add(new Player("Player2", 0));
			list.add(new Player("Player3", 0));
			list.add(new Player("Player4", 0));
			list.add(new Player("Player5", 0));
			
			return list;
		}
	}
}