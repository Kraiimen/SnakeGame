package carmineerario.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameConfig {
	private static final String SETTINGS_JSON_PATH = "database/settings.json";
	private static final String RECORDS_JSON_PATH = "database/records.json";
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	//Class to deserialize settings Json file
	static class Config{
		int difficulty;
		int[] snakeColorArr = new int[3];
	}
	
	static class Records{
		List<RecordsArray> records;
	}
	
	//Class to deserialize records field from Json
	static class RecordsArray implements Comparable<RecordsArray>{
		public RecordsArray(String playerName, int playerRecord) {
			this.playerName = playerName;
			this.playerRecord = playerRecord;
		}
		
		private String playerName;
		private int playerRecord;

		public void setPlayerName(String playerName) { this.playerName = playerName; }
		public void setPlayerRecord(int playerRecord) { this.playerRecord = playerRecord; }
		public String getPlayerName() { return this.playerName; }
		public int getPlayerRecord() { return this.playerRecord; }

		@Override
		public int compareTo(GameConfig.RecordsArray other) {
			return Integer.compare(other.playerRecord, this.playerRecord);
		}
		
		@Override
		public String toString() {
			return playerName + ": " + playerRecord;
		}
	}
	
	//Method to save difficulty and snake color to Json file
	public static void saveConfig(int difficulty, int snakeColorArr[]) {
		Config config = new Config();
		
		config.difficulty = difficulty;
		config.snakeColorArr = snakeColorArr;
		
		try (FileWriter writer = new FileWriter(SETTINGS_JSON_PATH)){
			System.out.println("Saving config: " + gson.toJson(config));
			gson.toJson(config, writer);
		} catch (IOException e) {
			System.out.println("FileWriter: Json file not found!");
		}
	}
	
	//Method to save records to Json file
	 static void saveRecords(List<RecordsArray> records) {
		Records rec = new Records();
		rec.records = records;
		
		try (FileWriter writer = new FileWriter(RECORDS_JSON_PATH)){
			System.out.println("Saving records: " + gson.toJson(rec));
			gson.toJson(rec, writer);
		} catch (IOException e) {
			System.out.println("FileWriter: Json file not found!");
		}
	}
	
	// Method to save a new record into the top 5
	 static void saveNewRecord(RecordsArray player) {
		List<RecordsArray> tempRecords = loadRecords();
		
		System.out.println("tempRecords: " + tempRecords.toString());
		
		int newRecordToSave = player.playerRecord;
		int saveRecordPos = 0;
		
		// Check if new record is greater or equal to saved record
		for (int i = 0; i < tempRecords.size(); i++) {
			RecordsArray currentPlayer = tempRecords.get(i);
			// If greater we overwrite the saved record with the new one
			if(newRecordToSave > currentPlayer.playerRecord) {
				saveRecordPos = i;
				System.out.println("saveRecordPos > :"+i);
				break;
			}
			// If equal we save the record into the position before the saved record
			else if(newRecordToSave == currentPlayer.playerRecord) {
				saveRecordPos = i+1;
				System.out.println("saveRecordPos = :"+i+1);
				break;
			}
		}
		
		// Move each record by one to make space for the new one
		for(int i = tempRecords.size() - 1; i > saveRecordPos; i--) {
			tempRecords.set(i, tempRecords.get(i-1));
		}
		
		tempRecords.set(saveRecordPos, player);
		
		System.out.println("tempRecords after: " + tempRecords.toString());
		
		saveRecords(tempRecords);
	}
	
	public static boolean canRecordBeSaved(int playerRecord) {
		List<RecordsArray> tempRecords = loadRecords();

        for (RecordsArray tempRecord : tempRecords) {
            if (playerRecord >= tempRecord.playerRecord) {
                return true;
            }
        }
		
		return false;
	}
	
	//Method to get difficulty value from Json file
	public static int loadDifficulty() {
		try (FileReader reader = new FileReader(SETTINGS_JSON_PATH)){
			return gson.fromJson(reader, Config.class).difficulty;
		} catch (IOException e) {
			System.out.println("FileReader: Json file not found!");
			
			//If the file is not found, return 80 as the default difficulty
			return 80;
		}
	}
	
	//Method to get snakeColor value from Json file
	public static int[] loadSnakeColor() {
		try {
			FileReader reader = new FileReader(SETTINGS_JSON_PATH);
			return gson.fromJson(reader, Config.class).snakeColorArr;
		} catch (FileNotFoundException e) {
			System.out.println("FileReader: Json file not found!");
			
			//If the file is not found, it returns the RGB value for black
			return new int[] {0,0,0};
		}
	}
	
	//Method to get records values from Json file
	public static List<RecordsArray> loadRecords(){
		try {
			FileReader reader = new FileReader(RECORDS_JSON_PATH);
			List<RecordsArray> temp = gson.fromJson(reader, Records.class).records;
//			Collections.sort(temp);
			return temp;
		} catch (FileNotFoundException e) {
			System.out.println("FileReader: Json file not found!");
			
			//If the file is not found, it returns a predetermined list
			List<RecordsArray> list = new ArrayList<RecordsArray>();
			list.add(new RecordsArray("Player1", 0));
			list.add(new RecordsArray("Player2", 0));
			list.add(new RecordsArray("Player3", 0));
			list.add(new RecordsArray("Player4", 0));
			list.add(new RecordsArray("Player5", 0));
			
			return list;
		}
	}
}